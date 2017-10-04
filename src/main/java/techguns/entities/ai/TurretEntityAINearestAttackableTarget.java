package techguns.entities.ai;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
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