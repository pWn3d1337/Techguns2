package techguns.items.guns;

import java.util.Arrays;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.ClientProxy;
import techguns.client.ShooterValues;
import techguns.client.audio.TGSoundCategory;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.items.guns.ammo.AmmoType;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.util.EntityCondition;
import techguns.util.InventoryUtil;
import techguns.util.SoundUtil;

public class GenericGunCharge extends GenericGun {

	/**
	 * In ticks
	 */
	public float fullChargeTime=20.0f;
	public int ammoConsumedOnFullCharge=10;

	protected ChargedProjectileSelector chargedProjectile_selector;
	
	public boolean hasChargedFireAnim = true;
	public boolean canFireWhileCharging = false;
	
	SoundEvent startChargeSound = null;
	String chargeFX = null;
	private float chargeFXoffsetX = 0.0f;
	private float chargeFXoffsetY = 0.0f;
	private float chargeFXoffsetZ = 0.0f;
	
	public GenericGunCharge(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound,
			int TTL, float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
		this.fullChargeTime=fullChargeTime;
		this.ammoConsumedOnFullCharge=ammoConsumedOnFullCharge;
		this.chargedProjectile_selector=projectile_selector;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {

	TGExtendedPlayer extendedPlayer = TGExtendedPlayer.get(player);
	ItemStack item = player.getHeldItem(handIn);	
	World world = player.getEntityWorld();
	
		/*
		 * Check if player wants to zoom
		 */
		if (canZoom && player.isSneaking() && this.toggleZoom) {
			if (world.isRemote) {
				ClientProxy cp = ClientProxy.get();
				if (cp.player_zoom != 1.0f) {
					cp.player_zoom = 1.0f;
				} else {
					cp.player_zoom = this.zoomMult;
				}
			}

		} else {
			//int dur = item.getItemDamage();
			int ammo = this.getCurrentAmmo(item);
			
			if (ammo > 0) {
				// bullets left

				int firedelay = extendedPlayer.getFireDelay(handIn);

				if (firedelay<=0) {

					extendedPlayer.setFireDelay(handIn, this.minFiretime);

					player.setActiveHand(handIn);
					//player.setItemInUse(item, this.getMaxItemUseDuration(item));
					
					this.startCharge( item, world, player);
					TGExtendedPlayer txp = TGExtendedPlayer.get(player);
					txp.setChargingWeapon(true);
					
					if (this.startChargeSound != null) {
						SoundUtil.playSoundOnEntityGunPosition(world, player ,this.startChargeSound, SOUND_DISTANCE, 1.0F, false, true, false, TGSoundCategory.GUN_FIRE, EntityCondition.CHARGING_WEAPON);
						//SoundUtil.playSoundOnEntityGunPosition(world, player ,this.startChargeSound, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.GUN_FIRE);
					}
					
					if (this.chargeFX != null) {
						float x;
						if (player.getPrimaryHand() == EnumHandSide.RIGHT) x = this.chargeFXoffsetX;
						else x = -this.chargeFXoffsetX;
						TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity(this.chargeFX, player, x, this.chargeFXoffsetY, this.chargeFXoffsetZ, true, EntityCondition.CHARGING_WEAPON), TGPackets.targetPointAroundEnt(player, 25.0f));						
					}
				}

			} else {
				// mag empty, reload needed

				// look for ammo
				if (InventoryUtil.consumeAmmoPlayer(player, this.ammoType.getAmmo(this.getCurrentAmmoVariant(item)))) {

					
					/*if (!this.ammoType.getEmptyMag().isEmpty()) {
						player.inventory.addItemStackToInventory(TGItems.newStack(this.ammoType.getEmptyMag(), 1));
					}*/
					Arrays.stream(this.ammoType.getEmptyMag()).forEach( e -> {
    					if (!e.isEmpty()){
        					//player.inventory.addItemStackToInventory(new ItemStack(emptyMag.getItem(),1,emptyMag.getItemDamage()));
        					int amount=InventoryUtil.addAmmoToPlayerInventory(player, TGItems.newStack(e, 1));
        					if(amount >0 && !world.isRemote){
        						player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, TGItems.newStack(e, amount)));
        					}
        				}
    				});

					// stop toggle zooming when reloading
					if (world.isRemote) {
						if (canZoom && this.toggleZoom) {
							ClientProxy cp = ClientProxy.get();
							if (cp.player_zoom != 1.0f) {
								cp.player_zoom = 1.0f;
							}
						}
					}
					
					extendedPlayer.setFireDelay(handIn, this.reloadtime-this.minFiretime);

					if (ammoCount > 1) {
						int i = 1;
						while (i < ammoCount && InventoryUtil.consumeAmmoPlayer(player, this.ammoType.getAmmo(this.getCurrentAmmoVariant(item)))) {
							i++;
						}
						this.reloadAmmo(item, i);
					} else {
						this.reloadAmmo(item);

					}

					//world.playSoundAtEntity(player, reloadsound, 1.0F, 1.0F);

					SoundUtil.playSoundOnEntityGunPosition(world, player, reloadsound, 1.0f, 1.0f, false, true, TGSoundCategory.RELOAD);
					
					if (world.isRemote) {
						int time = (int) (((float) reloadtime / 20.0f) * 1000);

						//ClientProxy cp = ClientProxy.get();
						//cp.setplayerReloadtime(player,System.currentTimeMillis()+time, time, (byte)1);
						ShooterValues.setReloadtime(player, handIn==EnumHand.OFF_HAND, System.currentTimeMillis()+time, time, (byte)1);
						
						
						client_startReload();
					}

				} else {

					//TODO emptySound
					/*if (!world.isRemote) {
						world.playSoundAtEntity(player, "mob.villager.idle", 1.0F, 1.0F);
					}*/
				}
			}
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, item);
	}

	/**
	 * Override for onCharge code.
	 */
	protected void startCharge(ItemStack item, World world, EntityPlayer player) {}

	@Override
	public void shootGunPrimary(ItemStack stack, World world, EntityPlayer player, boolean zooming, EnumHand hand, Entity target) {
		if (this.canFireWhileCharging || player.getActiveItemStack() != stack) {
			super.shootGunPrimary(stack, world, player, zooming, hand, target);
		}
	}
	
	
	@Override
	public void onPlayerStoppedUsing(ItemStack item, World world, EntityLivingBase entityLiving, int timeLeft) {
		if ( entityLiving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entityLiving;
			
			TGExtendedPlayer txp = TGExtendedPlayer.get(player);
			txp.setChargingWeapon(false);
			
			int j = this.getMaxItemUseDuration(item) - timeLeft;
	
			float f = j/this.fullChargeTime;
			
			if (f > 1.0F) {
				f = 1.0F;
			}
	
			//int ammoConsumed = 0;
			//if (!player.capabilities.isCreativeMode) {
			int	ammoConsumed = this.consumeAmmoCharge(item, f,player.capabilities.isCreativeMode);
			//}
			if (!world.isRemote) {
	
				// If SERVER, create projectile
	
				
				//EnumBulletFirePos firePos = player.getPrimaryHand()==EnumHandSide.RIGHT?EnumBulletFirePos.RIGHT:EnumBulletFirePos.LEFT;
				EnumBulletFirePos firePos;
	        	if (player.getPrimaryHand() == EnumHandSide.RIGHT) {
	        		firePos = EnumBulletFirePos.RIGHT;
	        	}else {
	        		firePos = EnumBulletFirePos.LEFT;
	        	}
				
				//Charged shot has to be from main hand!
				spawnChargedProjectile(world, player, item, accuracy, f, ammoConsumed, firePos);
				if (shotgun) {
					for (int i = 0; i < bulletcount; i++) {
						spawnChargedProjectile(world, player, item, spread, f, ammoConsumed,  firePos);
					}
				}
				
				if (this.hasChargedFireAnim) {
	
					//world.playSoundAtEntity(player, firesound, 1.0F, 1.0F);
					SoundUtil.playSoundOnEntityGunPosition(world, player, firesound, SOUND_DISTANCE, 1.0f, false, false,true, TGSoundCategory.GUN_FIRE);
		
					if (!(rechamberSound==null)) {
						//world.playSoundAtEntity(player, rechamberSound, 1.0F, 1.0F);
						SoundUtil.playSoundOnEntityGunPosition(world, player, rechamberSound, 1.0f, 1.0f, false, false,true, TGSoundCategory.GUN_FIRE);
					}
	
				}
				
			} else {
	
				if (this.hasChargedFireAnim) {
					// If CLIENT, do Effects
		
					int recoiltime_l = getRecoilTime(f);
					int muzzleFlashtime_l = getMuzzleFlashTime(f);
					//ClientProxy.setplayerRecoiltime(System.currentTimeMillis() + recoiltime_l);
					//ClientProxy.setplayerRecoiltimeTotal(recoiltime_l);
					
					ShooterValues.setRecoiltime(player, player.getActiveHand()==EnumHand.OFF_HAND, System.currentTimeMillis() + recoiltime_l, recoiltime_l, (byte)1);
					
					//ClientProxy cp = ClientProxy.get();
					//cp.setplayerRecoiltime(player,System.currentTimeMillis() + recoiltime_l, recoiltime_l, (byte)1);
					//cp.setLocalMuzzleFlashTime(EnumHand.MAIN_HAND, System.currentTimeMillis() + muzzleFlashtime_l);
					//cp.setLocalMuzzleFlashTimeTotal(EnumHand.MAIN_HAND,  muzzleFlashtime_l);
					
					ShooterValues.setMuzzleFlashTime(player, player.getActiveHand()==EnumHand.OFF_HAND, System.currentTimeMillis() + muzzleFlashtime_l, muzzleFlashtime_l);
	
				}
				client_weaponFired();
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 288000;
	}

	public int getRecoilTime(float charge) {
		return ((int) (((float) recoiltime / 20.0f) * 1000.0f));
	}

	public int getMuzzleFlashTime(float charge) {
		return ((int) (((float) muzzleFlashtime / 20.0f) * 1000.0f));
	}

	/**
	 * consume ammo from NBTTag
	 * @param item
	 * @param f charge amount
	 * @return ammount of ammo actually consumed
	 */
	public int consumeAmmoCharge(ItemStack item, float f, boolean creative) {
		
		int amount = (int) Math.floor(f * this.ammoConsumedOnFullCharge);
		
		if(!creative){
			amount = this.useAmmo(item, amount);
		}
		return amount;
	}

	public void spawnChargedProjectile(final World world, final EntityLivingBase player, ItemStack itemStack, float spread, float charge, int ammoConsumed, EnumBulletFirePos firePos) {
		/*world.spawnEntity(new GenericProjectile(world, player, damage, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd,
				this.damageMin, this.penetration, getDoBlockDamage(player), leftGun));*/
		IChargedProjectileFactory fact = this.chargedProjectile_selector.getFactoryForType(this.getCurrentAmmoVariantKey(itemStack));
		GenericProjectile proj = fact.createChargedProjectile(world, player, damage, speed, this.ticksToLive, spread, this.damageDropStart, damageDropEnd, this.damageMin, penetration, getDoBlockDamage(player), firePos, radius, gravity, charge, ammoConsumed);
		if (proj != null) world.spawnEntity(proj);
	}

	@Override
	public boolean canCharge() {
		return true;
	}
	
	public GenericGunCharge setChargeFireAnims(boolean hasAnims) {
		this.hasChargedFireAnim = hasAnims;
		return this;
	}
	
	public GenericGunCharge setFireWhileCharging(boolean canFire) {
		this.canFireWhileCharging = canFire;
		return this;
	}
	
	
	public GenericGunCharge setChargeSound(SoundEvent startChargeSound) {
		this.startChargeSound = startChargeSound;
		return this;
	}
	
	public GenericGunCharge setChargeFX(String fx, float offsetX, float offsetY, float offsetZ) {
		this.chargeFX = fx;
		this.chargeFXoffsetX = offsetX;
		this.chargeFXoffsetY = offsetY;
		this.chargeFXoffsetZ = offsetZ;
		return this;
	}
}
