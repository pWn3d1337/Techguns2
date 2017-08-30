package techguns.items.guns;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.SlotItemHandler;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.ShooterValues;
import techguns.client.audio.TGSoundCategory;
import techguns.items.guns.ammo.AmmoType;
import techguns.packets.PacketPlaySound;

public class GenericGunMeleeCharge extends GenericGunCharge implements IGenericGunMelee<GenericGunMeleeCharge> {

	HashMap<String, Integer> mininglevels = new HashMap<>();
	
	public GenericGunMeleeCharge(String name, IChargedProjectileFactory projectile, AmmoType ammo, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage,
			SoundEvent firesound, SoundEvent reloadsound, int TTL, float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy, fullChargeTime, ammoConsumedOnFullCharge);
	}

	@Override
	public HashMap<String, Integer> getMiningLevels() {
		return mininglevels;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		return this.getDigSpeed(stack, state);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		return this.getToolHarvestLevel(stack, toolClass, player, blockState);
	}

	@Override
	public float getEffectiveDigSpeed() {
		return this.digSpeed;
	}

	@Override
	public int getMiningAmmoConsumption() {
		return this.miningAmmoConsumption;
	}

	@Override
	public GenericGunMeleeCharge setDigSpeed(float speed){
		this.digSpeed=speed;
		return this;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		boolean openingContainer=false;
		if (!entityLiving.world.isRemote) {
			if(entityLiving instanceof EntityPlayer){
				EntityPlayer ply = (EntityPlayer) entityLiving;
				if(ply.openContainer!=null){
					if (!(ply.openContainer.getClass() == ContainerPlayer.class)){
						openingContainer=true;
					}
				}
				//System.out.println("Container:"+ply.openContainer);
			}
		}
		
		if ( this.getCurrentAmmo(stack) > 0) {
			if (entityLiving.world.isRemote) {
				if (ShooterValues.getRecoiltime(entityLiving, false) < System.currentTimeMillis()) {
					ShooterValues.setRecoiltime(entityLiving, false, System.currentTimeMillis() + 250, 250, (byte) 0);
				}
				return true;
			} else {
				
				boolean sendSound=true;
				if(entityLiving instanceof EntityPlayer){
					TGExtendedPlayer props = TGExtendedPlayer.get((EntityPlayer)entityLiving);
					if(props.swingSoundDelay>0){
						sendSound=false;
					} else {
						props.swingSoundDelay=5;
					}
				}
				//server side:;
				//SoundUtil.playSoundOnEntityGunPosition(entityLiving.worldObj, entityLiving, "techguns:guns.powerhammerSwing", SOUND_DISTANCE, 1.0F, false, false);	
				if(!openingContainer && sendSound){
					TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.POWERHAMMER_SWING, entityLiving, 1.0f, 1.0f, false, false, true, true,TGSoundCategory.GUN_FIRE), TGPackets.targetPointAroundEnt(entityLiving, 32.0f));
				}
				
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if(this.getCurrentAmmo(stack)>0){
			this.useAmmo(stack, 1);
			if (entityLiving instanceof EntityPlayer) {
				worldIn.playSound((EntityPlayer)entityLiving, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, TGSounds.POWERHAMMER_IMPACT, SoundCategory.PLAYERS, 1.0f, 1.0f);
			}
			return true;
		}
		return false;
	}
	
}
