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

public class Bandit extends GenericNPC {

	public static final ResourceLocation LOOT = new ResourceLocation(Techguns.MODID, "entities/Bandit");
	
	public Bandit(World world) {
		super(world);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
	}

	@Override
	protected void addRandomArmor(DifficultyInstance difficulty) {

		// Armors
		
		this.setItemStackToSlot(EntityEquipmentSlot.CHEST,new ItemStack(TGArmors.t1_scout_Chestplate));
		this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(TGArmors.t1_scout_Leggings));
		this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(TGArmors.t1_scout_Boots));
		
		double chance = 0.5;
		if (Math.random() <= chance) {
			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(TGArmors.t1_scout_Helmet));
		}			 

		// Weapons
		Random r = new Random();
		Item weapon = null;
		switch (r.nextInt(6)) {
		case 0:
			weapon = TGuns.pistol;
			break;
		case 1:
			weapon = TGuns.ak47;
			break;
		case 2:
			weapon = TGuns.sawedoff;
			break;
		case 3:
			weapon = TGuns.thompson;
			break;
		case 4:
			weapon = TGuns.revolver;
			break;
		default:
			weapon = TGuns.boltaction;
			break;
		}
		if (weapon != null) this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(weapon));
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		return LOOT;
	}
}