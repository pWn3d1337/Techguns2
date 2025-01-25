package techguns.client.render;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import techguns.api.guns.GunManager;
import techguns.api.render.IItemRenderer;
import techguns.api.render.IItemTGRenderer;
import techguns.items.guns.IGenericGunMelee;

public class ItemRenderHack {

    protected static HashMap<Item, IItemRenderer> renderRegistry = new HashMap<>();

    public static void registerItemRenderer(Item item ,IItemRenderer renderer){
        renderRegistry.put(item, renderer);
    }

    public static IItemRenderer getRendererForItem(Item item){
        return renderRegistry.get(item);
    }

    public static float getAttackStrengthForRendering() {
        EntityPlayer ply = Minecraft.getMinecraft().player;
        ItemStack mainhand = ply.getHeldItemMainhand();
        ItemStack offhand = ply.getHeldItemOffhand();
        if(!mainhand.isEmpty() && mainhand.getItem() instanceof IGenericGunMelee) {
            return 1f;
        }
        return ply.getCooledAttackStrength(1.0f);
    }

    public static boolean renderItem(ItemStack stack, EntityLivingBase elb, ItemCameraTransforms.TransformType transform, boolean leftHanded){

        if (!shouldRenderItem(stack, elb, transform, leftHanded)) {
            return true;
        }

        if (stack == null || !(stack.getItem() instanceof IItemTGRenderer)) {
            return false; // Skip rendering invalid items
        }

        if (!stack.isEmpty() && stack.getItem() instanceof IItemTGRenderer){
            IItemTGRenderer item = (IItemTGRenderer) stack.getItem();

            if (!item.shouldUseRenderHack(stack)){
                return false;
            }

            IItemRenderer renderer = getRendererForItem(stack.getItem());
            if (renderer != null){

                boolean jeifix = JEI_Fix(transform, leftHanded, item, stack);

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableRescaleNormal();

                renderer.renderItem(transform, stack, elb, leftHanded);

                if (jeifix) JEI_Fix_undo();

                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    protected static boolean JEI_Fix(ItemCameraTransforms.TransformType type, boolean leftHanded, IItemTGRenderer item, ItemStack stack) {
        if (type == TransformType.GUI && leftHanded) {
            GlStateManager.pushMatrix();

            if(item.isModelBase(stack)) {
                GlStateManager.translate(0.5f, 0.5f, 0f);
            } else {
                GlStateManager.translate(0.5f, 0.5f, 0.5f);
            }
            return true;
        } else {
            return false;
        }
    }

    protected static void JEI_Fix_undo() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.popMatrix();
    }

    protected static boolean shouldRenderItem(ItemStack stack, EntityLivingBase elb, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
        if (!(transform == TransformType.FIRST_PERSON_LEFT_HAND || transform == TransformType.THIRD_PERSON_LEFT_HAND || 
              transform == TransformType.FIRST_PERSON_RIGHT_HAND || transform == TransformType.THIRD_PERSON_RIGHT_HAND)) {
            return true;
        }

        boolean mainhand = transform == TransformType.FIRST_PERSON_RIGHT_HAND || transform == TransformType.THIRD_PERSON_RIGHT_HAND;
        if (elb.getPrimaryHand() == EnumHandSide.LEFT) {
            mainhand = !mainhand;
        }
        if (mainhand) {
            return true;
        } else {
            return GunManager.canUseOffhand(elb.getHeldItemMainhand(), stack, elb);
        }
    }
}
