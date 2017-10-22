package techguns.items.guns;

import java.util.HashMap;
import java.util.List;

import org.omg.PortableServer.POAManagerPackage.State;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.ShooterValues;
import techguns.client.audio.TGSoundCategory;
import techguns.items.guns.ammo.AmmoType;
import techguns.packets.PacketPlaySound;
import techguns.util.BlockUtils;

public class GenericGunMeleeCharge extends GenericGunCharge implements IGenericGunMelee<GenericGunMeleeCharge> {

	HashMap<String, Integer> mininglevels = new HashMap<>();
	
	/**
	 * Extra radius for minig, 0 = 1 block, 1 = 3x3, 2 = 5x5, ...
	 */
	protected int miningRadius=0;
	
	public GenericGunMeleeCharge(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound,
			int TTL, float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
	}

	public GenericGunMeleeCharge setMiningRadius(int miningRadius) {
		this.miningRadius = miningRadius;
		return this;
	}
	
	public int getMiningRadius(ItemStack stack) {
		return miningRadius+getExtraMiningRadius(stack);
	}

	@Override
	public HashMap<String, Integer> getMiningLevels() {
		return mininglevels;
	}
	
	public int getExtraMiningRadius(ItemStack stack) {
		return 0;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return this.getDigSpeed(stack, state);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		return this.getToolHarvestLevel(stack, toolClass, player, blockState);
	}

	@Override
	public float getEffectiveDigSpeed(ItemStack itemstack) {
		return this.digSpeed+this.getExtraDigSpeed(itemstack);
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

	
	protected SoundEvent getSwingSound() {
		return TGSounds.POWERHAMMER_SWING;
	}
	
	protected SoundEvent getBlockBreakSound() {
		return TGSounds.POWERHAMMER_IMPACT;
	}
	
	@Override
	protected void addInitialTags(NBTTagCompound tags) {
		super.addInitialTags(tags);
		tags.setByte("miningHead", (byte) 0);
	}

	public int getMiningHeadLevel(ItemStack stack) {
		NBTTagCompound tags = stack.getTagCompound();
		if(tags!=null) {
			return tags.getByte("miningHead");
		}
		return 0;
	}
	
	public byte getMiningHeadLevelForHead(ItemStack head) {
		return 0;
	}
	
	@Override
	public int getExtraMiningLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		return getMiningHeadLevel(stack);
	}

	public float getExtraDigSpeed(ItemStack stack) {
		int headlevel = this.getMiningHeadLevel(stack);
		return 1.0f*headlevel;
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
					TGPackets.network.sendToAllAround(new PacketPlaySound(getSwingSound(), entityLiving, 1.0f, 1.0f, false, false, true, true,TGSoundCategory.GUN_FIRE), TGPackets.targetPointAroundEnt(entityLiving, 32.0f));
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
				SoundEvent sound = this.getBlockBreakSound();
				if(sound!=null) {
					worldIn.playSound((EntityPlayer)entityLiving, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
				}
			}
			return true;
		}
		return false;
	}
	
	public EnumFacing getSideHitMining(World world, EntityPlayer player) {
		RayTraceResult result = this.rayTrace(world, player, false);
		if(result != null && result.typeOfHit == Type.BLOCK) {
			return result.sideHit;
		}
		return null;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
	
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
		
		if (slot == EntityEquipmentSlot.MAINHAND)
        {
			double meleedmg=this.meleeDamagePwr;
			int ammoleft = this.getCurrentAmmo(stack);
			if (ammoleft <=0){
				meleedmg=this.meleeDamageEmpty;
			}
			
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", meleedmg, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

		return multimap;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		if(!player.world.isRemote && !player.isSneaking() && this.getMiningRadius(itemstack)>0) {
			
			EnumFacing sideHit = this.getSideHitMining(player.world, player);
			if(sideHit!=null) {
				
				World w = player.world;
				List<BlockPos> otherblocks = BlockUtils.getBlockPlaneAroundAxisForMining(player.world, player, pos, sideHit.getAxis(), this.getMiningRadius(itemstack), false, this, itemstack);
				otherblocks.forEach(b -> {
					if(w.isBlockLoaded(b)) {
						IBlockState state = w.getBlockState(b);
						if(state.getBlock()!=null && !(state.getBlock()==Blocks.AIR) && state.getPlayerRelativeBlockHardness(player, w, b) > 0f 
								&& state.getBlock().canHarvestBlock(w, b, player) && this.isEffectiveToolForState(itemstack, state)) {
			
							EntityPlayerMP plyMP = (EntityPlayerMP) player;
							int xpAmount = ForgeHooks.onBlockBreakEvent(w, plyMP.interactionManager.getGameType(), plyMP, b);
							if (xpAmount>=0) {
								Block block = state.getBlock();
								block.onBlockHarvested(w, b, state, player);
								TileEntity tile = w.getTileEntity(b);
								
								/*if(b.equals(pos)) {
									itemstack.onBlockDestroyed(w, state, b, player);
								}*/
								if(block.removedByPlayer(state, w, b, player, true)){
									block.onBlockDestroyedByPlayer(w, b, state);
									if(!player.capabilities.isCreativeMode) {
										block.harvestBlock(w, player, pos, state, tile, itemstack);
										block.dropXpOnBlockBreak(w, b, xpAmount);
									}
									
									w.playEvent(2001, b, Block.getStateId(state));
									plyMP.connection.sendPacket(new SPacketBlockChange(w, b));
								}
							}
						}
					}
				});
				
			}
			
		}
		return false;
	}
	

	
}
