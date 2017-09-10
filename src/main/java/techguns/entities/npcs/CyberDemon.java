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
import techguns.TGConfig;
import techguns.TGArmors;
import techguns.TGuns;
import techguns.Techguns;
import techguns.debug.Keybinds;

public class CyberDemon extends GenericNPC {

	public static final ResourceLocation LOOT = new ResourceLocation(Techguns.MODID, "entities/cyberdemon");
	
	public CyberDemon(World world) {
		super(world);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
		this.isImmuneToFire = true;
		this.hasAimedBowAnim = false;
	}

	@Override
	protected void addRandomArmor(DifficultyInstance difficulty) {

		// No Armor
		// Weapons
		Item weapon = null;
		
		weapon = TGuns.netherblaster;
		
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
	public boolean hasWeaponArmPose() {
		return false;
	}

	
	
	@Override
	public float getWeaponPosX() {
		return 0.16f;
	}

	@Override
	public float getWeaponPosY() {
		return 0.72f;
	}

	@Override
	public float getWeaponPosZ() {
		return 0.1f;
	}

	@Override
	public float getGunScale() {
		return 1.5f;
	}

	
	
	@Override
	public float getBulletOffsetSide() {
		return 0.3f;
	}

	@Override
	public float getBulletOffsetHeight() {
		return -0.59f;
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