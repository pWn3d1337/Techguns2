package techguns.entities.npcs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGuns;
import techguns.Techguns;
import techguns.damagesystem.TGDamageSource;

public class SuperMutantBasic extends GenericNPC {
	
	public static final ResourceLocation LOOT = new ResourceLocation(Techguns.MODID, "entities/supermutantbasic");

	public SuperMutantBasic(World world) {
		super(world);
		this.setSize(getMutantWidth(), 2F*this.getModelScale());
	}
	
	public int gettype() {
		return 0;
	};
	
	protected float getMutantWidth() {
		return 1.0f;
	}
	
	public double getModelHeightOffset(){
		return 0.55d;
	}
	
	public float getModelScale() {
		return 1.35f;
	}

	@Override
	public float getWeaponPosY() {
		return 0f;
	}
	
	@Override
	public float getWeaponPosX() {
		return 0.13f;
	}

	@Override
	public float getWeaponPosZ() {
		return -0.18f;
	}

	
	@Override
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		switch(dmgsrc.damageType){
			case EXPLOSION:
			case LIGHTNING:
			case ENERGY:
			case FIRE:
			case ICE:
				return 10.0f;
			case PHYSICAL:
			case PROJECTILE:
				return 7.0f;
			case POISON:
			case RADIATION:
				return 15.0f;
			case UNRESISTABLE:
		default:
			return 0.0f;
		}
	}

	@Override
	public int getTotalArmorValue() {
		return 7;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
	}

	

	@Override
	protected void addRandomArmor(int difficulty) {

			// Weapons
		Random r = new Random();
		Item weapon = null;
		switch (r.nextInt(5)) {
			case 0:
				weapon = TGuns.rocketlauncher;
				break;
			case 1:
				weapon = TGuns.ak47;
				break;
			case 2:
				weapon = TGuns.combatshotgun;
				break;
			default:
				weapon = TGuns.lasergun;
				break;
			/*default:
				weapon = Items.IRON_SHOVEL;
				break;*/
		}
		if (weapon != null) this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(weapon));
	}

	@Override
	public SoundEvent getAmbientSound() {
		return techguns.TGSounds.CYBERDEMON_IDLE;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return techguns.TGSounds.CYBERDEMON_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return techguns.TGSounds.CYBERDEMON_DEATH;
	}

	public SoundEvent getStepSound() {
		return techguns.TGSounds.CYBERDEMON_STEP;
	}
	
	@Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }
	
	
	@Override
	protected ResourceLocation getLootTable() {
		return LOOT;
	}
}
