package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.items.guns.ammo.AmmoType;
import techguns.packets.PacketSpawnParticle;
import techguns.util.ItemUtil;

public class PowerHammer extends GenericGunMeleeCharge {
	
	public PowerHammer(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
			float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
		
		this.setMiningHeads(TGItems.POWERHAMMERHEAD_OBSIDIAN, TGItems.POWERHAMMERHEAD_CARBON);
	}

	@Override
	protected TGDamageSource getMeleeDamageSource(EntityPlayer player,ItemStack stack) {
		TGDamageSource src = new TGDamageSource("player", player, player, DamageType.PHYSICAL, DeathType.GORE);
		if(this.getCurrentAmmo(stack)>0){
			src.goreChance=1.0f;
			src.armorPenetration=this.penetration;
			src.knockbackMultiplier=1.5f;
		} else{
			src.deathType = DeathType.DEFAULT;
		}
		return src;
	}
	
	@Override
	protected void spawnSweepParticle(World w, double x, double y, double z, double motionX, double motionY,
			double motionZ) {
		TGPackets.network.sendToAllAround(new PacketSpawnParticle("PowerhammerImpact",x,y,z), new TargetPoint(w.provider.getDimension(), x, y, z, 32.0f));
	}
	
	public void spawnChargedProjectile(final World world, final EntityLivingBase player, ItemStack itemStack, float spread, float charge, int ammoConsumed, EnumBulletFirePos firePos) {
		int level = this.getMiningHeadLevel(itemStack);
		float extraDmg = 0.5f*level;
		
		IChargedProjectileFactory fact = this.chargedProjectile_selector.getFactoryForType(this.getCurrentAmmoVariantKey(itemStack));
		GenericProjectile proj = fact.createChargedProjectile(world, player, damage+extraDmg, speed, this.ticksToLive, spread, this.damageDropStart, damageDropEnd, this.damageMin+extraDmg, penetration, getDoBlockDamage(player), firePos, radius, gravity, charge, ammoConsumed);
		if (proj != null) world.spawnEntity(proj);
	}
	
	@Override
	public float getExtraDigSpeed(ItemStack stack) {
		int headlevel = this.getMiningHeadLevel(stack);
		return 2.0f*headlevel;
	}

	@Override
	protected void playSweepSoundEffect(EntityPlayer player) {
		player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, TGSounds.POWERHAMMER_IMPACT,
				player.getSoundCategory(), 1.0F, 1.0F);
	}

}
