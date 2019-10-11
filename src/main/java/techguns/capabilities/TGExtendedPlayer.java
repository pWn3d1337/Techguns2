package techguns.capabilities;

import java.util.BitSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.ItemStackHandler;
import techguns.TGRadiationSystem;
import techguns.Techguns;
import techguns.api.capabilities.AttackTime;
import techguns.api.capabilities.ITGExtendedPlayer;
import techguns.gui.player.TGPlayerInventoryOld;
import techguns.gui.player.TGPlayerInventory;
import techguns.util.DataUtil;

public class TGExtendedPlayer implements ITGExtendedPlayer {

	public static final int MAX_RADIATION=1000;
	
	public static final DataParameter<ItemStack> DATA_FACE_SLOT = EntityDataManager.<ItemStack>createKey(EntityPlayer.class, DataSerializers.ITEM_STACK);
	public static final DataParameter<ItemStack> DATA_BACK_SLOT = EntityDataManager.<ItemStack>createKey(EntityPlayer.class, DataSerializers.ITEM_STACK);
	public static final DataParameter<ItemStack> DATA_HAND_SLOT = EntityDataManager.<ItemStack>createKey(EntityPlayer.class, DataSerializers.ITEM_STACK);
	
	public static final DataParameter<Boolean> DATA_FLAG_CHARGING_WEAPON = EntityDataManager.<Boolean>createKey(EntityPlayer.class, DataSerializers.BOOLEAN);
	
	public int fireDelayMainhand=0;
	public int fireDelayOffhand=0;
	
	public int loopSoundDelayMainhand=0;
	public int loopSoundDelayOffhand=0;
	
	public EntityPlayer entity;
	
	protected AttackTime[] attackTimes = {new AttackTime(),new AttackTime()};
	
	public int swingSoundDelay=0;

	public boolean gotCreativeFlightLastTick=false;
	public boolean wasFlying=false;
	
	public ItemStack gunMainHand=ItemStack.EMPTY;
	public ItemStack gunOffHand=ItemStack.EMPTY;
	
	public Entity lockOnEntity; //Guided Missile tracking target
	public int lockOnTicks; //number of ticks the tracked target has been locked on.
	
	/**
	 * Used by server only, needed for jetpack, not saved to nbt
	 */
	protected  boolean isJumpkeyPressed=false;
	
	/**
	 * Used by server only, needed for jetpack, not saved to nbt
	 */
	public boolean isForwardKeyPressed=false;

	/**
	 * only used by client, not synced
	 */
	public boolean isGliding=false;
	
	/**
	 * SAVED FIELDS
	 */
	public TGPlayerInventory tg_inventory;
	
	public boolean enableJetpack=true;
	public boolean enableStepAssist=true;
	public boolean enableNightVision=false;
	public boolean enableSafemode=false;
	
	/**
	 * for jetpack
	 */
	public boolean enableHovermode=false;
		
	public int radlevel=0;
	
	public short foodleft=0;
	public float lastSaturation=1.0f;
	
	/**
	 * used by client only, but saved serverside
	 */
	public boolean showTGHudElements=true;
	
	public TGExtendedPlayer(EntityPlayer entity) {
		this.entity = entity;
		this.tg_inventory = new TGPlayerInventory(entity);
		entity.getDataManager().register(DATA_FACE_SLOT, ItemStack.EMPTY);
		entity.getDataManager().register(DATA_BACK_SLOT, ItemStack.EMPTY);
		entity.getDataManager().register(DATA_HAND_SLOT, ItemStack.EMPTY);
		
		entity.getDataManager().register(DATA_FLAG_CHARGING_WEAPON, false);
	}
	
	@Override
	public EntityPlayer getEntity() {
		return entity;
	}

	public void copyFrom(TGExtendedPlayer other){

		this.entity=other.getEntity();
		
		this.fireDelayMainhand=other.fireDelayMainhand;
		this.fireDelayOffhand=other.fireDelayOffhand;
		
		this.loopSoundDelayMainhand=other.loopSoundDelayMainhand;
		this.loopSoundDelayOffhand=other.loopSoundDelayOffhand;
		
		this.attackTimes = other.attackTimes;
		
		this.swingSoundDelay=other.swingSoundDelay;

		this.gotCreativeFlightLastTick=other.gotCreativeFlightLastTick;
		this.wasFlying=other.wasFlying;
		
		this.gunMainHand=other.gunMainHand;
		this.gunOffHand=other.gunOffHand;
		this.isJumpkeyPressed=other.isJumpkeyPressed;
		this.isForwardKeyPressed=other.isForwardKeyPressed;

		this.isGliding=other.isGliding;
		
		this.tg_inventory = other.tg_inventory;
		
		this.enableJetpack=other.enableJetpack;
		this.enableStepAssist=other.enableStepAssist;
		this.enableNightVision=other.enableNightVision;
		this.enableSafemode=other.enableSafemode;

		this.enableHovermode=other.enableHovermode;
			
		this.radlevel=other.radlevel;
		
		this.foodleft=other.foodleft;
		this.lastSaturation=other.lastSaturation;

		this.showTGHudElements=other.showTGHudElements;
	}
	
	public static TGExtendedPlayer get(EntityPlayer ply){
		return (TGExtendedPlayer) ply.getCapability(TGExtendedPlayerCapProvider.TG_EXTENDED_PLAYER, null);
	}

	@Override
	public AttackTime getAttackTime(boolean offHand) {
		return attackTimes[offHand?1:0];
	}

	@Override
	public int getFireDelay(EnumHand hand) {
		switch(hand){
			case MAIN_HAND:
				return fireDelayMainhand;
			case OFF_HAND:
				return fireDelayOffhand;
		}
		return 0;
	}

	@Override
	public void setFireDelay(EnumHand hand, int delay) {
		switch(hand){
		case MAIN_HAND:
			this.fireDelayMainhand=delay;
			break;
		case OFF_HAND:
			this.fireDelayOffhand=delay;
			break;
		}
	}
	
	public int getLoopSoundDelay(EnumHand hand) {
		switch(hand){
			case MAIN_HAND:
				return loopSoundDelayMainhand;
			case OFF_HAND:
				return loopSoundDelayOffhand;
		}
		return 0;
	}

	public void setLoopSoundDelay(EnumHand hand, int delay) {
		switch(hand){
		case MAIN_HAND:
			this.loopSoundDelayMainhand=delay;
			break;
		case OFF_HAND:
			this.loopSoundDelayOffhand=delay;
			break;
		}
	}

	@Override
	public boolean isRecoiling(boolean offHand) {
		return getAttackTime(offHand).isRecoiling();
	}

	@Override
	public boolean isReloading(boolean offHand) {
		return getAttackTime(offHand).isReloading();
	}
	
	public void swapAttackTimes() {
		AttackTime a = this.attackTimes[0];
		this.attackTimes[0]=this.attackTimes[1];
		this.attackTimes[1]=a;
		
		int i = fireDelayMainhand;
		this.fireDelayMainhand=fireDelayOffhand;
		this.fireDelayOffhand=i;
	}

	@Override
	public ItemStackHandler getTGInventory() {
		return this.tg_inventory;
	}

	@Override
	public void saveToNBT(NBTTagCompound tags) {
		tags.setTag("inventory", tg_inventory.serializeNBT());
		
		byte data = DataUtil.compress(this.enableJetpack,this.enableNightVision,this.enableSafemode,this.enableStepAssist,this.showTGHudElements,this.enableHovermode);
		tags.setByte("states", data);
		tags.setShort("foodLeft", this.foodleft);
		tags.setFloat("lastSaturation", this.lastSaturation);
		tags.setInteger("radlevel", this.radlevel);
	}

	@Override
	public void loadFromNBT(NBTTagCompound tags) {
		if(tags.hasKey("inventory")){
			tg_inventory.deserializeNBT(tags.getCompoundTag("inventory"));
		}
		else if(tags.hasKey("Items")){
			// try to restore old IInventory
			TGPlayerInventoryOld oldInventory = new TGPlayerInventoryOld(null);
			oldInventory.loadNBTData(tags);

			for(int slot=0; slot < oldInventory.getSizeInventory(); slot++){
				tg_inventory.setStackInSlot(slot, oldInventory.getStackInSlot(slot));
			}
		}

		BitSet states = DataUtil.uncompress(tags.getByte("states"));
		
		this.enableJetpack=states.get(0);
		this.enableNightVision=states.get(1);
		this.enableSafemode=states.get(2);
		
		if (entity!=null && !Techguns.instance.permissions.canUseUnsafeMode(entity)) {
			this.enableSafemode=true;
		}
		
		this.enableStepAssist=states.get(3);
		this.showTGHudElements=states.get(4);
		this.enableHovermode=states.get(5);
		
		this.foodleft = tags.getShort("foodLeft");
		this.lastSaturation = tags.getFloat("lastSaturation");
		this.radlevel = tags.getInteger("radlevel");
	}	
	
	public boolean isJumpkeyPressed() {
		return isJumpkeyPressed;
	}

	public void setJumpkeyPressed(boolean isJumpkeyPressed) {
		
		/*if(this.entity.world.isRemote){
			if(isJumpkeyPressed==true && jetPackLoop==null){
				jetPackLoop = new TGSound(TGSounds.JETPACK_LOOP, this.entity, 1.0f, 1.0f, true, true, false, TGSoundCategory.PLAYER_EFFECT);
				SoundUtil.playSoundAtEntityPos(this.entity.world, this.entity, TGSounds.JETPACK_START, 1.0f, 1.0f, false, TGSoundCategory.PLAYER_EFFECT);
				Minecraft.getMinecraft().getSoundHandler().playSound(jetPackLoop);
			}
			
			if(isJumpkeyPressed && !this.isJumpkeyPressed){
				//Minecraft.getMinecraft().getSoundHandler().playSound(jetPackLoop);
			} else if ( !isJumpkeyPressed && this.isJumpkeyPressed){
				if(jetPackLoop!=null){
					jetPackLoop.setDonePlaying();
					jetPackLoop=null;
					SoundUtil.playSoundAtEntityPos(this.entity.world, this.entity, TGSounds.JETPACK_END, 1.0f, 1.0f, false, TGSoundCategory.PLAYER_EFFECT);
				}
			}
			
			
		}*/
		
		this.isJumpkeyPressed = isJumpkeyPressed;
	}
	
	/**
	 * Drops to world (player death)
	 */
	public void dropInventory(EntityPlayer player){
		if(!player.world.isRemote){
			if (!player.world.getGameRules().getBoolean("keepInventory"))
	        {
	            //this.inventory.dropAllItems();
	            int i;
	        
	            player.captureDrops=true;
	            for (i = 0; i < this.tg_inventory.getSlots(); ++i)
	            {
	                if (!this.tg_inventory.getStackInSlot(i).isEmpty())
	                {
	                	//System.out.println("Dropping "+TG_inventory.inventory[i].getDisplayName()+" x"+TG_inventory.inventory[i].stackSize);
	                    player.dropItem(this.tg_inventory.getStackInSlot(i), true, false);
	                    this.tg_inventory.setStackInSlot(i, ItemStack.EMPTY);
	                }
	            }
	            player.captureDrops=false;
	         
	        }
		}
	}
	
	public void addDropsToList(EntityPlayer player, List<EntityItem> list ){
		if(!player.world.isRemote){
			if (!player.world.getGameRules().getBoolean("keepInventory"))
	        {
	            //this.inventory.dropAllItems();
	            int i;
	        
	            //player.captureDrops=true;
	            for (i = 0; i < this.tg_inventory.getSlots(); ++i)
	            {
	                if (!this.tg_inventory.getStackInSlot(i).isEmpty())
	                {
	                	//System.out.println("Dropping "+TG_inventory.inventory[i].getDisplayName()+" x"+TG_inventory.inventory[i].stackSize);
	                    EntityItem item = player.dropItem(this.tg_inventory.getStackInSlot(i), true, false);
	                    if(item!=null) {
	                    	list.add(item);
	                    }
	                    this.tg_inventory.setStackInSlot(i, ItemStack.EMPTY);
	                }
	            }
	            //player.captureDrops=false;
	         
	        }
		}
	}
	
	public boolean isChargingWeapon() {
		return this.entity.getDataManager().get(DATA_FLAG_CHARGING_WEAPON);
	}
	
	public void setChargingWeapon(boolean charging) {
		this.entity.getDataManager().set(DATA_FLAG_CHARGING_WEAPON, charging);
	}
	
	public void addRadiation(int amount) {
		
		if (!TGRadiationSystem.isEnabled()) {amount=0;}
		
		this.radlevel+=amount;
		if(this.radlevel<0) {
			this.radlevel=0;
		} else if (this.radlevel>MAX_RADIATION) {
			this.radlevel=MAX_RADIATION;
		}
	}
}
