package techguns.entities.npcs;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.capabilities.TGSpawnerNPCData;
import techguns.capabilities.TGSpawnerNPCDataCapProvider;

public abstract class GenericNPCUndead extends GenericNPC {

	public GenericNPCUndead(World world) {
		super(world);
	}

	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
	@Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

	/**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
	@Override
    public void onLivingUpdate()
    {
        if (this.world.isDaytime() && !this.world.isRemote && this.shouldBurnInDay())
        {
            float f = this.getBrightness();

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))
            {
                this.setFire(8);
            }
        }

        super.onLivingUpdate();
    }

	protected boolean shouldBurnInDay() {
		TGSpawnerNPCData data = this.getCapability(TGSpawnerNPCDataCapProvider.TG_GENERICNPC_DATA);
		if(data!=null) {
			return !data.hasSpawner();
		}
		return true;
	}
}
