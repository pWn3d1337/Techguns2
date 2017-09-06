package techguns.entities.npcs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGuns;
import techguns.Techguns;
import techguns.items.armors.GenericArmorMultiCamo;

public class ZombiePigmanSoldier extends GenericNPC {

	public static final ResourceLocation LOOT = new ResourceLocation(Techguns.MODID, "entities/zombiepigmansoldier");
	
	public ZombiePigmanSoldier(World world) {
		super(world);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
	}

	@Override
	protected void addRandomArmor(DifficultyInstance difficulty) {

		// Armors
		double chance = 0.5;
		int camo=3;
		if (Math.random() <= chance)
			GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Helmet, camo);
			this.setItemStackToSlot(EntityEquipmentSlot.HEAD,
					GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Helmet, camo));
		if (Math.random() <= chance)
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, 
					GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Chestplate, camo));
		if (Math.random() <= chance)
			this.setItemStackToSlot(EntityEquipmentSlot.LEGS, GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Leggings, camo));
		if (Math.random() <= chance)
			this.setItemStackToSlot(EntityEquipmentSlot.FEET, GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Boots, camo));

		// Weapons
		Random r = new Random();
		Item weapon = null;
		int bound = 9;
		
		switch (r.nextInt(bound)) {
		case 0:
		case 1:
		case 2:
			weapon = TGuns.thompson;
			break;
		case 3:
		case 4:
			weapon = TGuns.revolver;
			break;
		case 5:
		case 6:
			weapon = TGuns.ak47;
			break;
		case 7:
		case 8:
			weapon = TGuns.pistol;
			break;
		case 9:
		case 10:
			weapon = TGuns.flamethrower;
			break;
		case 11:
		case 12:
			weapon = TGuns.combatshotgun;
			break;
		case 13:
			weapon = TGuns.lmg;
			break;
		case 14:
		default:
			weapon = TGuns.rocketlauncher;
			break;
		}
		if (weapon != null) this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(weapon));
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_ZOMBIE_PIG_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ZOMBIE_PIG_DEATH;
	}

	public SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
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
