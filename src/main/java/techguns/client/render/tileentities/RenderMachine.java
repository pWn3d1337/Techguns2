package techguns.client.render.tileentities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import techguns.client.models.machines.ModelMachine;
import techguns.tileentities.BasicMachineTileEnt;

public class RenderMachine extends TileEntitySpecialRenderer<BasicMachineTileEnt> {

	protected ModelMachine model;
	protected ResourceLocation texture;

	public RenderMachine(ModelMachine model, ResourceLocation texture) {
		super();
		this.model = model;
		this.texture = texture;
	}

	@Override
	public void render(BasicMachineTileEnt te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
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
        	Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        }
		
		GlStateManager.pushMatrix();
		GlStateManager.translate( x + 0.5d, y + 1.5d, z + 0.5d);
		
		GlStateManager.pushMatrix();
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);

		float progress = 0.0f;
		if (te != null) {
			progress = ((BasicMachineTileEnt) te).getProgress();
			byte rotation = ((BasicMachineTileEnt) te).rotation;
			GlStateManager.rotate(90.0f * rotation, 0, 1, 0);
		}
		

	//	this.setLighting(te.getWorld(), te);
		
		this.model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f, progress);
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		
		 if (destroyStage >= 0)
         {
             GlStateManager.matrixMode(5890);
             GlStateManager.popMatrix();
             GlStateManager.matrixMode(5888);
         }
	}
	
	/*protected void setLighting(World w, TileEntity tile) {
		int i = w.getCombinedLight(tile.getPos(), 0);
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}*/

}
