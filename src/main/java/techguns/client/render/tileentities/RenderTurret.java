package techguns.client.render.tileentities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.tileentities.TurretTileEnt;

public class RenderTurret extends TileEntitySpecialRenderer<TurretTileEnt> {

	protected static final ResourceLocation defaultTexture = new ResourceLocation(Techguns.MODID,"textures/blocks/turret_base.png");
	protected ModelBase model;

	public RenderTurret(ModelBase model) {
		super();
		this.model = model;
	}

	@Override
	public void render(TurretTileEnt te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		//super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
        	if(te!=null) {
        		Minecraft.getMinecraft().renderEngine.bindTexture(te.getTurretBaseTexture());
        	} else {
        		Minecraft.getMinecraft().renderEngine.bindTexture(defaultTexture);
        	}
        }
      
		//default up for TESR itemstac
        EnumFacing facing = EnumFacing.UP;
        if(te!=null) {
        	facing = te.getFacing();
        }
        
        switch(facing) {
			case DOWN:
				GlStateManager.pushMatrix();
				GlStateManager.translate( x + 0.5d, y - 0.5d, z + 0.5d);
				
				GlStateManager.pushMatrix();
				//GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
			    GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				break;
			case EAST:
				GlStateManager.pushMatrix();
				GlStateManager.translate( x + 1.5d, y + 0.5d, z + 0.5d);
				
				GlStateManager.pushMatrix();
				GlStateManager.rotate(90F, 0.0F, 0.0F, 1.0F);
				break;
			case NORTH:
				GlStateManager.pushMatrix();
				GlStateManager.translate( x + 0.5d, y + 0.5d, z - 0.5d);
				
				GlStateManager.pushMatrix();
				GlStateManager.rotate(90F, 1.0F, 0.0F, 0.0F);
				break;
			case SOUTH:
				GlStateManager.pushMatrix();
				GlStateManager.translate( x + 0.5d, y + 0.5d, z + 1.5d);
				
				GlStateManager.pushMatrix();
				GlStateManager.rotate(-90F, 1.0F, 0.0F, 0.0F);
				break;
			case WEST:
				GlStateManager.pushMatrix();
				GlStateManager.translate( x - 0.5d, y + 0.5d, z + 0.5d);
				
				GlStateManager.pushMatrix();
				GlStateManager.rotate(-90F, 0.0F, 0.0F, 1.0F);
				break;
			case UP:
			default:
				GlStateManager.pushMatrix();
				GlStateManager.translate( x + 0.5d, y + 1.5d, z + 0.5d);
				
				GlStateManager.pushMatrix();
				GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
			    GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				break;
        
        }
        
		this.model.render(null, 0, 0, 0, 0, 0, 0.0625f);
		
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		
		 if (destroyStage >= 0)
         {
             GlStateManager.matrixMode(5890);
             GlStateManager.popMatrix();
             GlStateManager.matrixMode(5888);
         }
	}
	
}
