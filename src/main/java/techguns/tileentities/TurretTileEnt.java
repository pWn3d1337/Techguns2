package techguns.tileentities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.api.guns.IGenericGun;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.entities.npcs.NPCTurret;
import techguns.gui.ButtonConstants;
import techguns.items.guns.GenericGun;
import techguns.packets.PacketRequestTileEntitySync;
import techguns.tileentities.operation.AmmoPressBuildPlans;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.util.InventoryUtil;
import techguns.util.ItemUtil;

public class TurretTileEnt extends BasicPoweredTileEnt implements ITickable{

	public static final int SLOT_INPUT1=0;
	public static final int INPUTS_SIZE=9;
	
	public static final int SLOT_OUTPUT1=SLOT_INPUT1+INPUTS_SIZE;
	public static final int OUTPUTS_SIZE=9;
	
	public static final int SLOT_WEAPON=SLOT_OUTPUT1+OUTPUTS_SIZE;
	public static final int SLOT_ARMOR=SLOT_WEAPON+1;
	
	public static final int BUTTON_ID_TARGET_ANIMALS = ButtonConstants.BUTTON_ID_REDSTONE+1;
	
	public NPCTurret mountedTurret =null;	
	private long lastRequest=0;
	
	public boolean turretDeath=false;
	public int repairTime=0;
	public int turretHealTime = 0;
		
	public boolean attackAnimals=false;
	
	protected EnumFacing facing=EnumFacing.UP;
	
	public TurretTileEnt() {
		super(SLOT_ARMOR+1, false, 5000);
		//9 slots input, 9 slots output, 1 weaponslot, 1 armorslot
		
		this.inventory = new ItemStackHandlerPlus(SLOT_ARMOR+1) {

			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				if(slot==SLOT_WEAPON) {
					TurretTileEnt.this.updateWeapon();
				}
			}

			@Override
			protected boolean allowItemInSlot(int slot, ItemStack stack) {
				if (slot==SLOT_WEAPON) {
					return stack.getItem() instanceof IGenericGun;
				} else if (slot==SLOT_ARMOR) {
					return stack.getItem() instanceof ITGSpecialSlot && ((ITGSpecialSlot)stack.getItem()).getSlot(stack)==TGSlotType.TURRETARMOR;
				} else if (slot>=SLOT_INPUT1 && slot < SLOT_INPUT1+INPUTS_SIZE) {
					return true;
				}
				return false;
			}

			@Override
			protected boolean allowExtractFromSlot(int slot, int amount) {
				return slot >= SLOT_OUTPUT1 && slot < SLOT_OUTPUT1+OUTPUTS_SIZE;
			}
		};
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(Techguns.MODID+".container.turretbase", new Object[0]);
	}
	
	public int getRepairTimeScaled(int max){
		int totaltime = this.getRepairTimeMax();
		return max-(this.repairTime*max/totaltime);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		if(tags.hasKey("mountedTurret")){
			if (this.world!=null){
				Entity ent = this.world.getEntityByID(tags.getInteger("mountedTurret"));
				if (ent !=null && ent instanceof NPCTurret){
					this.mountedTurret=(NPCTurret) ent;
					this.mountedTurret.setTileEnt(this);
					//System.out.println("Set tileent for mounted turret");
				} else {
//					System.out.println("Link failed, entity still null");
				}
			} else {
//				System.out.println("Link failed, world is null");
			}
			//System.out.println("Turret:"+this.mountedTurret);
		}else{
//			this.mountedTurret=null;
		}
		
		this.facing = EnumFacing.getFront(tags.getByte("facing"));
		this.turretDeath=tags.getBoolean("turretDeath");
		this.repairTime=tags.getInteger("repairTime");
		this.turretHealTime=tags.getInteger("turretHealTime");
		this.attackAnimals=tags.getBoolean("attackAnimals");
		
		NBTTagCompound tagsWeapon = tags.getCompoundTag("weapon");
		if(tagsWeapon!=null && !tagsWeapon.hasNoTags()) {
			this.inventory.setStackInSlot(SLOT_WEAPON, new ItemStack(tagsWeapon));
		}
		
		NBTTagCompound tagsArmor = tags.getCompoundTag("armor");
		if(tagsArmor!=null && !tagsArmor.hasNoTags()) {
			this.inventory.setStackInSlot(SLOT_ARMOR, new ItemStack(tagsArmor));
		}
	}

	
	
	@Override
	public boolean hasRotation() {
		return true;
	}

	@Override
	public void rotateTile(EnumFacing sideHit) {
		if (this.facing!=sideHit && (sideHit==EnumFacing.UP || sideHit==EnumFacing.DOWN)) {
			this.facing = sideHit;
			if(this.mountedTurret!=null) {
				float health = this.mountedTurret.getHealth();
				
				this.mountedTurret.setHealth(0);
				this.mountedTurret.setDead();
				this.spawnTurret(this.world, this.getPos(), health);
			}
			if(!this.world.isRemote) {
				this.needUpdate();
			}
		}
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		if(this.mountedTurret!=null){
			tags.setInteger("mountedTurret", this.mountedTurret.getEntityId());
		}
		tags.setByte("facing", (byte) this.facing.getIndex());
		tags.setBoolean("turretDeath", this.turretDeath);
		tags.setInteger("repairTime", this.repairTime);
		tags.setInteger("turretHealTime",this.turretHealTime);
		tags.setBoolean("attackAnimals", this.attackAnimals);

		NBTTagCompound tagsWeapon = new NBTTagCompound();
		this.inventory.getStackInSlot(SLOT_WEAPON).writeToNBT(tagsWeapon);
		
		NBTTagCompound tagsArmor = new NBTTagCompound();
		this.inventory.getStackInSlot(SLOT_ARMOR).writeToNBT(tagsArmor);
		
		tags.setTag("weapon", tagsWeapon);
		tags.setTag("armor", tagsArmor);
	}
	
	public EnumFacing getFacing() {
		return facing;
	}

	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}

	/**
	 * Returns the itemstack in the gun slot.
	 * @return
	 */
	public ItemStack getWeapon(){
		return this.inventory.getStackInSlot(SLOT_WEAPON);
	}
	
	public boolean consumeAmmo(){
		
		ItemStack gunstack = this.getWeapon();
		if (!gunstack.isEmpty()){
			GenericGun gun = (GenericGun) gunstack.getItem();
			
			if (gun.getCurrentAmmo(gunstack)<=0){
				this.doReload(gunstack);
			}
			
			if (gun.getCurrentAmmo(gunstack)>=1){
				gun.useAmmo(gunstack, 1);
				return true;
			}
		}

		return false;
	}
	
	protected void doReload(ItemStack gunstack){
		GenericGun gun = (GenericGun) gunstack.getItem();
		ItemStack ammo = gun.getAmmoType().getAmmo(gun.getCurrentAmmoVariant(gunstack));
		ItemStack emptyMag = gun.getAmmoType().getAmmo(gun.getCurrentAmmoVariant(gunstack));
		
		if (InventoryUtil.consumeAmmo(this.inventory, ammo,SLOT_INPUT1,SLOT_INPUT1+INPUTS_SIZE)){
			if (emptyMag!=null){
				int tooMuch=InventoryUtil.addItemToInventory(this.inventory,TGItems.newStack(emptyMag,1),SLOT_OUTPUT1,SLOT_OUTPUT1+OUTPUTS_SIZE);
				if (!this.world.isRemote){
					this.world.spawnEntity(new EntityItem(this.world, this.pos.getX(), this.pos.getY()+1, this.pos.getZ(), TGItems.newStack(emptyMag, tooMuch)));
				}
			}
	    			
			if (gun.getAmmoCount() >1) {
				int i =1;
				while (i<gun.getAmmoCount() && InventoryUtil.consumeAmmo(this.inventory,ammo,SLOT_INPUT1,SLOT_INPUT1+INPUTS_SIZE)){
					i++;
				}
				
				gun.reloadAmmo(gunstack,i);    				
			} else {
				gun.reloadAmmo(gunstack);
			}
			
			if (!this.world.isRemote){
				this.needUpdate();
			}
		}
	}
	
	protected int getRepairTimeMax() {
		return 10*20;
	}
	
	protected int getRepairPowerAmount() {
		return 50;
	}
	
	protected int getOperationPowerAmount() {
		return 5;
	}
	
	protected int getPowerAmountHealTick() {
		return 500;
	}
	
	protected int getHealTickDelay() {
		return 20*3;
	}
	
	public void setMountedTurret(NPCTurret mountedTurret) {
		this.mountedTurret = mountedTurret;
	}

	public int getTurretArmorValue(){
		ItemStack armor = this.inventory.getStackInSlot(SLOT_ARMOR);
		if (!armor.isEmpty()){
			if(ItemUtil.isItemEqual(armor,TGItems.TURRET_ARMOR_IRON)) {
				return 5;
			} else if (ItemUtil.isItemEqual(armor,TGItems.TURRET_ARMOR_STEEL)) {
				return 10;
			} else if (ItemUtil.isItemEqual(armor,TGItems.TURRET_ARMOR_OBSIDIAN_STEEL)) {
				return 15;
			} else if (ItemUtil.isItemEqual(armor,TGItems.TURRET_ARMOR_CARBON)) {
				return 20;
			}
		}
		return 0;
	}
	
	/**
	 * called when base block is broken
	 */
	public void breakTurret(){
		if (this.mountedTurret!= null){
			this.mountedTurret.setHealth(0);
			this.mountedTurret.setDead();
		}
	}
	
	/**
	 * Called from the turret npc when he dies
	 */
	public void onTurretDeath(){
		if (!this.world.isRemote){
			this.mountedTurret=null;
			this.turretDeath=true;
			this.repairTime=this.getRepairTimeMax();
			//System.out.println("Turret Death");
			this.needUpdate();
		}
	}
	
	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if(id< BUTTON_ID_TARGET_ANIMALS) {
		super.buttonClicked(id, ply);
		} else if ((id==BUTTON_ID_TARGET_ANIMALS) && (this.isUseableByPlayer(ply))){
			this.attackAnimals=!this.attackAnimals;
			if(!this.world.isRemote){
				this.needUpdate();
			}
		} 
	}
	
	public void updateWeapon() {
		if (this.mountedTurret!=null) {
			this.mountedTurret.setCombatTask();
			if (!this.world.isRemote) {
				this.needUpdate();
			}
		}
	}

	@Override
	public void update() {

		if (!this.world.isRemote) {
		
			if (this.turretDeath){
				if (this.isRedstoneEnabled()){
					if (this.consumePower(this.getRepairPowerAmount())){
						this.repairTime--;
					}
					
					if(repairTime <=0){
						repairTime=0;
						this.turretDeath=false;
						
						if (!this.world.isRemote){
							spawnTurret(this.world,this.pos,10.0f);
							this.needUpdate();
						}
					}
				}
				
			} else if (this.mountedTurret!=null){
				
				if(this.isRedstoneEnabled()){
				
					if(!this.consumePower(this.getOperationPowerAmount())){
						//power off turret
						if (this.mountedTurret.active){
							this.mountedTurret.disable();
							this.mountedTurret.active=false;
						}
						
					} else if (!this.mountedTurret.active){
						//activate turret
						this.mountedTurret.active=true;
						this.mountedTurret.setAITasks();
						this.mountedTurret.setCombatTask();
					}
					
					if(this.turretHealTime<=0){
						if(this.mountedTurret.getHealth()<this.mountedTurret.getMaxHealth()){
							if (this.consumePower(this.getPowerAmountHealTick())){
								this.mountedTurret.heal(1.0f);
								this.turretHealTime=this.getHealTickDelay();
							}
						}
					} else {
						this.turretHealTime--;
					}
				} else {
					
					if (this.mountedTurret.active){
						this.mountedTurret.disable();
						this.mountedTurret.active=false;
					}
					
				}
			}
		} else {
			/**
			 * CLIENT SIDE
			 */
			//System.out.println("Mounted Turret:"+this.mountedTurret);
			if (this.mountedTurret==null && !this.turretDeath){
				if((Minecraft.getSystemTime()-this.lastRequest)>1000){
					TGPackets.network.sendToServer(new PacketRequestTileEntitySync(this.getPos()));
					this.lastRequest=Minecraft.getSystemTime();
	//				System.out.println("Sent request for sync");
				}
			} else if (this.mountedTurret!=null && this.mountedTurret.mountedTileEnt==null){

				this.mountedTurret.setTileEnt(this);
			}
		
		}
	}
	
	public int getTurretTier()
	{
		ItemStack armorstack = this.inventory.getStackInSlot(SLOT_ARMOR);
		if(!armorstack.isEmpty()&& armorstack.getItem()==TGItems.SHARED_ITEM) {
			int meta = armorstack.getItemDamage();
			if(meta == TGItems.TURRET_ARMOR_IRON.getItemDamage()) {
				return 1;
			} else if (meta ==TGItems.TURRET_ARMOR_STEEL.getItemDamage()) {
				return 2;
			} else if (meta ==TGItems.TURRET_ARMOR_OBSIDIAN_STEEL.getItemDamage()) {
				return 3;
			} else if (meta == TGItems.TURRET_ARMOR_CARBON.getItemDamage()) {
				return 4;
			}
		}
		return 0;
	}	
	public ResourceLocation getTurretBaseTexture(){
		return NPCTurret.getTexture(this.getTurretTier());
	}
	
	public void setTurretPosition() {
		double x= this.pos.getX()+0.5d;
		double y= this.pos.getY()+0.5d;
		double z= this.pos.getZ()+0.5d;
		
		switch(this.facing) {
		case DOWN:
			y-=0.5d;
			y-=this.mountedTurret.height;
			break;
		case EAST:
			x+=0.5d;
			break;
		case NORTH:
			z-=0.5d;
			break;
		case SOUTH:
			z+=0.5d;
			break;
		case UP:
			y+=0.5d;
			break;
		case WEST:
			x-=0.5d;
			break;
		default:
			break;
		}
		this.mountedTurret.setPosition(x, y, z);
	}
	
	public void spawnTurret(World w,BlockPos p,float health){
		
		if (!w.isRemote){
			this.mountedTurret = new NPCTurret(w, this);
			//this.mountedTurret.setPosition(p.getX()+0.5d, p.getY()+1, p.getZ()+0.5d);
			this.setTurretPosition();
			this.mountedTurret.setHealth(health);
			this.mountedTurret.setTurretFacing(this.facing);
			w.spawnEntity(this.mountedTurret);
		}
	}
	public void spawnTurret(World w,BlockPos p){
		
		if (!w.isRemote){
			this.mountedTurret = new NPCTurret(w, this);
			//this.mountedTurret.setPosition(p.getX()+0.5d, p.getY()+1, p.getZ()+0.5d);
			this.setTurretPosition();
			this.mountedTurret.setTurretFacing(this.facing);
			w.spawnEntity(this.mountedTurret);
		}
	}

	@Override
	public void onBlockBreak() {
		super.onBlockBreak();
		if (this.mountedTurret!= null){
			this.mountedTurret.setHealth(0);
			this.mountedTurret.setDead();
		}
	}
}
