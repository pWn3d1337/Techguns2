package techguns.client.render.tileentities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.tileentities.DungeonGeneratorTileEnt;

public class RenderDungeonGenerator extends TileEntitySpecialRenderer<DungeonGeneratorTileEnt> {

	public static final ResourceLocation GHOST_TEXTURE = new ResourceLocation(Techguns.MODID, "textures/entity/white.png");
	
	public RenderDungeonGenerator() {
	}

	@Override
	public void render(DungeonGeneratorTileEnt te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		
		if (te == null || !te.showGhost) return;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		

		TGRenderHelper.enableBlendMode(RenderType.ADDITIVE);
		GlStateManager.disableCull();
		
		drawGhostBox(x+1, y, z+1, te.sizeX, te.sizeY, te.sizeZ, 0.5f, 0.5f, 1.0f, 0.125f);
		
		GlStateManager.enableCull();
		TGRenderHelper.disableBlendMode(RenderType.ADDITIVE);
	
		
		GlStateManager.popMatrix();
		
	}

	
	protected void drawGhostBox(double x, double y, double z, double sizeX, double sizeY, double sizeZ, float r, float g, float b, float alpha ){		
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(GHOST_TEXTURE);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        
        //sides
        drawGhostBoxPlane(buffer, x, y, z, sizeX, sizeY, 0, r, g, b, alpha);
        drawGhostBoxPlane(buffer, x, y, z, 0, sizeY, sizeZ, r, g, b, alpha);
        drawGhostBoxPlane(buffer, x+sizeX, y, z+sizeZ, -sizeX, sizeY, 0, r, g, b, alpha);
        drawGhostBoxPlane(buffer, x+sizeX, y, z+sizeZ, 0, sizeY, -sizeZ, r, g, b, alpha);
        //bottom and top
        //drawGhostBoxPlane(buffer, x, y, z, -sizeX, 0, -sizeZ, r, g, b, alpha);
        //drawGhostBoxPlane(buffer, x, y+sizeY, z, -sizeX, 0, -sizeZ, r, g, b, alpha);
        
        buffer.pos(x, y, z).tex(0, 1).color(r,g,b, alpha).endVertex();
        buffer.pos(x, y, z+sizeZ).tex(1, 1).color(r,g,b, alpha).endVertex();
        buffer.pos(x+sizeX, y, z+sizeZ).tex(1, 0).color(r,g,b, alpha).endVertex();
        buffer.pos(x+sizeX, y, z).tex(0, 0).color(r,g,b, alpha).endVertex();
        
        buffer.pos(x, y+sizeY, z).tex(0, 1).color(r,g,b, alpha).endVertex();
        buffer.pos(x, y+sizeY, z+sizeZ).tex(1, 1).color(r,g,b, alpha).endVertex();
        buffer.pos(x+sizeX, y+sizeY, z+sizeZ).tex(1, 0).color(r,g,b, alpha).endVertex();
        buffer.pos(x+sizeX, y+sizeY, z).tex(0, 0).color(r,g,b, alpha).endVertex();
        
        tessellator.draw();
	}
	
	protected void drawGhostBoxPlane(BufferBuilder buffer, double x, double y, double z,  double w_x, double h, double w_z, float r, float g, float b, float alpha )
    {   
        buffer.pos((double)(x + 0), (double)(y + h), (double)z+w_z).tex(0, 1).color(r,g,b, alpha).endVertex();
        buffer.pos((double)(x + w_x), (double)(y + h), (double)z).tex(1, 1).color(r,g,b, alpha).endVertex();
        buffer.pos((double)(x + w_x), (double)(y + 0), (double)z).tex(1, 0).color(r,g,b, alpha).endVertex();
        buffer.pos((double)(x + 0), (double)(y + 0), (double)z+w_z).tex(0, 0).color(r,g,b, alpha).endVertex();
    }
}
