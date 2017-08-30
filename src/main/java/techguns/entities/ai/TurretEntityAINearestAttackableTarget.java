package techguns.entities.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;

public class TurretEntityAINearestAttackableTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>{

	protected double yOffset;
	
	public TurretEntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget, int chance,
			boolean checkSight, boolean onlyNearby, Predicate<? super T> targetSelector, double yOffset) {
		super(creature, classTarget, chance, checkSight, onlyNearby, targetSelector);
		this.yOffset=yOffset;
	}

	@Override
	protected AxisAlignedBB getTargetableArea(double targetDistance) {
		return this.taskOwner.getEntityBoundingBox().grow(targetDistance, yOffset, targetDistance);
	}
	
	
	
}