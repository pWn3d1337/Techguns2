package techguns.client.render.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import techguns.api.npc.INPCTechgunsShooter;

public class LayerHeldItemTranslateGun extends LayerHeldItem {

	public LayerHeldItemTranslateGun(RenderLivingBase<?> livingEntityRendererIn) {
		super(livingEntityRendererIn);
	}
	
	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
        ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();

        if (!itemstack.isEmpty() || !itemstack1.isEmpty())
        {
            GlStateManager.pushMatrix();

            if (this.livingEntityRenderer.getMainModel().isChild)
            {
                float f = 0.5F;
                GlStateManager.translate(0.0F, 0.75F, 0.0F);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
            }

            this.renderHeldItemTranslateGun(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            this.renderHeldItemTranslateGun(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
        }
    }

    protected void renderHeldItemTranslateGun(EntityLivingBase ent, ItemStack stack, ItemCameraTransforms.TransformType transformType, EnumHandSide handSide)
    {
        if (!stack.isEmpty())
        {
            GlStateManager.pushMatrix();
           
          
            
            if (ent.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            // Forge: moved this call down, fixes incorrect offset while sneaking.
            this.translateToHand(handSide);
            
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            
            this.setEntityTranslation(ent,flag);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(ent, stack, transformType, flag);
            
            GlStateManager.popMatrix();
        }
    }
    
    protected void setEntityTranslation(EntityLivingBase ent, boolean lefthand) {
    	if (ent instanceof INPCTechgunsShooter) {
    		INPCTechgunsShooter shooter = (INPCTechgunsShooter) ent;
    		
    		GlStateManager.translate(lefthand ? -shooter.getWeaponPosX() : shooter.getWeaponPosX(), shooter.getWeaponPosY(), shooter.getWeaponPosZ());   		
    	}
    }
}
