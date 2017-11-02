package techguns.client.render.entities.npcs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelMultipart;
import techguns.client.models.npcs.ModelTurret;
import techguns.debug.Keybinds;
import techguns.entities.npcs.NPCTurret;
import techguns.items.guns.GenericGun;

public class RenderNPCTurret extends RenderLiving<NPCTurret>{
	private static final ModelMultipart model = new ModelTurret();
	//private float gunOffsetX=0.025f;
	private float gunOffsetY=0.85f;
	//private float gunOffsetZ=0.3f;
	
	
	public RenderNPCTurret(RenderManager renderManager) {
		super(renderManager, model, 0f);
		//this.addLayer(new LayerHeldItem(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(NPCTurret entity) {
		return entity.getTexture();
	}

	@Override
	public void doRender(NPCTurret turret, double x, double y, double z, float entityYaw, float partialTicks) {
		//System.out.println("Turret:"+turret.mountedTileEnt);
			GlStateManager.pushMatrix();
	        GlStateManager.translate(x, y, z);
	        
	        GlStateManager.enableRescaleNormal();
	        
	    	EnumFacing facing = EnumFacing.UP;
	    	if (turret.mountedTileEnt!=null) {
	    		facing=turret.mountedTileEnt.getFacing();
	    	}
	        float mirrorYaw=1.0f;
	        float mirrorPitch=1.0f;
	        float yawOffset=0f;
	        
	        if(facing==EnumFacing.DOWN) {
	        	mirrorYaw=-1f;
	        	mirrorPitch=-1f;
	        	yawOffset=180.0f;
	        }
	    	
	        Minecraft.getMinecraft().getTextureManager().bindTexture(turret.getTexture());
	        
	        this.translate(facing, turret);
	        //GlStateManager.rotate(180.0f, 1.0f, 0, 0);
	        this.rotatetoBase(facing);
	        
	        GlStateManager.rotate(turret.rotationYawHead*mirrorYaw+yawOffset,0,1f,0);
	        
	        //this.rotatetoBase(turret);
	        model.render(turret, 0, 0, 0, 0, 0, 0.0625F, 0, 0.0f, TransformType.FIXED, 0, 0.0f, 0f);
	        
	        float rotoffset=0.9f;

	        GlStateManager.translate(0, rotoffset, 0);
	        GlStateManager.rotate(turret.rotationPitch*mirrorPitch, 1f, 0, 0);
	        GlStateManager.translate(0, -rotoffset, 0);
	        model.render(turret, 0, 0, 0, 0, 0, 0.0625F, 0, 0.0f, TransformType.FIXED, 1, 0.0f, 0f);
	        
	        this.renderEquippedItems(turret);

	        GlStateManager.disableRescaleNormal();
	        GlStateManager.popMatrix();
	}

	protected void rotatetoBase(EnumFacing facing) {

		if (facing==EnumFacing.UP) {
			GlStateManager.rotate(180.0f, 1f, 0, 0);
		}

	}
	
	protected void translate(EnumFacing facing, NPCTurret turret) {
		if (facing==EnumFacing.UP) {
	        GlStateManager.translate(0, 1.5f, 0);
		} else if (facing==EnumFacing.DOWN) {
			GlStateManager.translate(0, -0.6f, 0);
		}
	}
	
	protected void renderEquippedItems(NPCTurret turret) {
		ItemStack gunstack = turret.getHeldItemMainhand();
		if (!gunstack.isEmpty())
        {
			GenericGun gun = (GenericGun) gunstack.getItem();
			
            GlStateManager.pushMatrix();

            GlStateManager.translate(gun.turretPosOffsetX+Keybinds.X, gun.turretPosOffsetY+this.gunOffsetY+Keybinds.Y, gun.turretPosOffsetZ+Keybinds.Z);
            //this.translateToHand(handSide);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
           /* boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);*/
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(turret, gunstack, TransformType.THIRD_PERSON_RIGHT_HAND, false);
            GlStateManager.popMatrix();
        }
	}

	
	
	
}
