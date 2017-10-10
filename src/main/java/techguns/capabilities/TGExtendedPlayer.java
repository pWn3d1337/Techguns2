package techguns.capabilities;

import java.util.BitSet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraftforge.server.permission.PermissionAPI;
import techguns.TGPermissions;
import techguns.api.capabilities.AttackTime;
import techguns.api.capabilities.ITGExtendedPlayer;
import techguns.gui.player.TGPlayerInventory;
import techguns.util.DataUtil;

public class TGExtendedPlayer implements ITGExtendedPlayer {

	public static final DataParameter<ItemStack> DATA_FACE_SLOT = EntityDataManager.<ItemStack>createKey(EntityPlayer.class, DataSerializers.ITEM_STACK);
	public static final DataParameter<ItemStack> DATA_BACK_SLOT = EntityDataManager.<ItemStack>createKey(EntityPlayer.class, DataSerializers.ITEM_STACK);
	public static final DataParameter<ItemStack> DATA_HAND_SLOT = EntityDataManager.<ItemStack>createKey(EntityPlayer.class, DataSerializers.ITEM_STACK);
	
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
	public IInventory getTGInventory() {
		return this.tg_inventory;
	}

	@Override
	public void saveToNBT(NBTTagCompound tags) {
		this.tg_inventory.saveNBTData(tags);
		
		byte data = DataUtil.compress(this.enableJetpack,this.enableNightVision,this.enableSafemode,this.enableStepAssist,this.showTGHudElements,this.enableHovermode);
		tags.setByte("states", data);
		tags.setShort("foodLeft", this.foodleft);
		tags.setFloat("lastSaturation", this.lastSaturation);
		tags.setInteger("radlevel", this.radlevel);
	}

	@Override
	public void loadFromNBT(NBTTagCompound tags) {
		this.tg_inventory.loadNBTData(tags);
		BitSet states = DataUtil.uncompress(tags.getByte("states"));
		
		this.enableJetpack=states.get(0);
		this.enableNightVision=states.get(1);
		this.enableSafemode=states.get(2);
		
		if (entity!=null && !PermissionAPI.hasPermission(entity, TGPermissions.ALLOW_UNSAFE_MODE)) {
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
}
