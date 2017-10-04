package techguns.client.models.npcs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.api.guns.IGenericGun;
import techguns.entities.npcs.GenericNPC;

@SideOnly(Side.CLIENT)
public class ModelGenericNPC extends ModelBiped
{
    public ModelGenericNPC()
    {
        this(0.0F, false);
    }

    public ModelGenericNPC(float modelSize, boolean b)
    {
        super(modelSize, 0.0F, 64, b ? 32 : 64);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
 
        if(entityIn instanceof GenericNPC) {
        	GenericNPC npc = (GenericNPC) entityIn;
        	
        	if(!npc.getHeldItemMainhand().isEmpty() && npc.getHeldItemMainhand().getItem() instanceof IGenericGun) {
        		if (npc.isLeftHanded()) {
        			this.leftArmPose=ArmPose.BOW_AND_ARROW;
        		} else {
        			this.rightArmPose=ArmPose.BOW_AND_ARROW;
        		}
        	} else if(!npc.getHeldItemMainhand().isEmpty()){
        		if (npc.isLeftHanded()) {
        			this.leftArmPose=ArmPose.ITEM;
        		} else {
        			this.rightArmPose=ArmPose.ITEM;
        		}
        	} else {
        		this.leftArmPose=ArmPose.EMPTY;
        		this.rightArmPose=ArmPose.EMPTY;
        	}
        }
        
    }
}