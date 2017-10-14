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
import techguns.tileentities.DungeonScannerTileEnt;
import techguns.world.dungeon.TemplateSegment;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class RenderDungeonScanner extends TileEntitySpecialRenderer<DungeonScannerTileEnt> {

	public static final ResourceLocation GHOST_TEXTURE = new ResourceLocation(Techguns.MODID, "textures/entity/white.png");
	
	public RenderDungeonScanner() {
	}

	@Override
	public void render(DungeonScannerTileEnt te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		if (te == null || !te.showGhost) return;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		
		int spacing = DungeonScannerTileEnt.SPACING;
		
		for (SegmentType type: TemplateSegment.templateSegments.keySet()) {
			GlStateManager.pushMatrix();
			TemplateSegment temp = TemplateSegment.templateSegments.get(type);
			GlStateManager.translate(spacing + (te.sizeXZ + spacing) * temp.col, 0, spacing + (te.sizeXZ + spacing) * temp.row);
			
			TGRenderHelper.enableBlendMode(RenderType.ADDITIVE);
			GlStateManager.disableCull();
			
			drawGhost(type, te.sizeXZ, te.sizeY);
			
			GlStateManager.enableCull();
			TGRenderHelper.disableBlendMode(RenderType.ADDITIVE);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.popMatrix();
	}

	private void drawGhost(SegmentType type, int sizeXZ, int sizeY) {
		float offset = 0.05f; //to avoid clipping
		//draw floor
		drawGhostBox(offset,offset,offset, sizeXZ-2*offset, 1.0-2*offset, sizeXZ-2*offset, 1.0f, 0.5f, 0.5f, 0.25f);
		//draw ceiling
		if (type != SegmentType.RAMP && type != SegmentType.FOUNDATION && type != SegmentType.PILLARS) drawGhostBox(offset, offset+sizeY-1, offset, sizeXZ-2*offset, 1.0-2*offset, sizeXZ-2*offset, 0.5f, 1.0f, 0.5f, 0.25f);
		
		switch (type) {
		case STRAIGHT:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+sizeXZ-1, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			break;
		case CURVE:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+1.0f, 1.0f-2*offset, sizeY-2-offset*2, sizeXZ-1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset+sizeXZ-1.0f, offset+1.0f, offset+sizeXZ-1.0f, 1.0f-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			break;
		case FORK:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+sizeXZ-1.0f, 1.0f-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset+sizeXZ-1.0f, offset+1.0f, offset+sizeXZ-1.0f, 1.0f-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			break;
		case CROSS:
			drawGhostBox(offset, offset+1.0f, offset+sizeXZ-1.0f, 1.0f-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset+sizeXZ-1.0f, offset+1.0f, offset+sizeXZ-1.0f, 1.0f-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			
			drawGhostBox(offset, offset+1.0f, offset, 1.0f-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset+sizeXZ-1.0f, offset+1.0f, offset, 1.0f-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			
			break;
		case END:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+sizeXZ-1, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+1.0f, 1.0f-2*offset, sizeY-2-offset*2, sizeXZ-2.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			break;
		case RAMP:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY*2-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+sizeXZ-1, sizeXZ-2*offset, sizeY*2-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(sizeXZ-offset-1.0f, offset+1.0f, offset+1.0f, 1.0f-2*offset, sizeY-1-offset*2, sizeXZ-2.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			
			drawGhostBox(offset,offset+sizeY-1,offset+1, 1.0-2*offset, 1.0-2*offset, sizeXZ-2-2*offset, 0.5f, 1.0f, 0.5f, 0.25f);
			//+1 Floor & Ceiling
			//drawGhostBox(offset,offset+sizeY,offset, sizeXZ-2*offset, 1.0-2*offset, sizeXZ-2*offset, 1.0f, 0.5f, 0.5f, 0.125f);
			drawGhostBox(sizeXZ-1+offset,offset+sizeY,offset+1, 1.0-2*offset, 1.0-2*offset, sizeXZ-2-2*offset, 1.0f, 0.5f, 0.5f, 0.125f);
			drawGhostBox(offset, offset+(sizeY*2)-1, offset, sizeXZ-2*offset, 1.0-2*offset, sizeXZ-2*offset, 0.5f, 1.0f, 0.5f, 0.25f);
			//+1 walls
			//drawGhostBox(offset, sizeY+offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			//drawGhostBox(offset, sizeY+offset+1.0f, offset+sizeXZ-1, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, sizeY+offset, offset+1.0f, 1.0f-2*offset, sizeY-1-offset*2, sizeXZ-2.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			//Ramp
			float f = (float)sizeXZ / ((float)sizeY+1);
			for (int i = 1; i < sizeY; i++) {
				drawGhostBox(offset + f*(float)i, offset+i, ((float)sizeXZ)*0.5f-0.5f, 1.0f-2*offset, 1.0f-2*offset, 1.0f-2*offset, 0.5f, 1.0f, 1.0f, 0.25f);
			}
			break;
		case ENTRANCE:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+sizeXZ-1, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+1.0f, 1.0f-2*offset, sizeY-2-offset*2, sizeXZ-2.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			
			drawGhostBox(-offset, 1, sizeXZ*0.5 - 0.5 - offset, 1+2*offset, 2+offset, 1+2*offset, 1.0f, 0.5f, 1.0f, 0.25f);
			break;
		case ROOM_WALL:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			break;
		case ROOM_CORNER:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+1.0f, 1.0f-2*offset, sizeY-2-offset*2, sizeXZ-1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			break;
		case ROOM_INNER:
			break;
		case ROOM_DOOR:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(sizeXZ*0.5 - 0.5 - offset, 1, -offset, 1+2*offset, 2+offset, 1+2*offset, 1.0f, 0.5f, 1.0f, 0.25f);
			break;
		case ROOM_DOOR_CORNER1:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+1.0f, 1.0f-2*offset, sizeY-2-offset*2, sizeXZ-1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			
			drawGhostBox(sizeXZ*0.5 - 0.5 - offset, 1, -offset, 1+2*offset, 2+offset, 1+2*offset, 1.0f, 0.5f, 1.0f, 0.25f);
			break;
		case ROOM_DOOR_CORNER2:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+1.0f, 1.0f-2*offset, sizeY-2-offset*2, sizeXZ-1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			
			drawGhostBox(-offset, 1, sizeXZ*0.5 - 0.5 - offset, 1+2*offset, 2+offset, 1+2*offset, 1.0f, 0.5f, 1.0f, 0.25f);
			break;
		case ROOM_DOOR_CORNER_DOUBLE:
			drawGhostBox(offset, offset+1.0f, offset, sizeXZ-2*offset, sizeY-2-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset+1.0f, offset+1.0f, 1.0f-2*offset, sizeY-2-offset*2, sizeXZ-1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			
			drawGhostBox(sizeXZ*0.5 - 0.5 - offset, 1, -offset, 1+2*offset, 2+offset, 1+2*offset, 1.0f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(-offset, 1, sizeXZ*0.5 - 0.5 - offset, 1+2*offset, 2+offset, 1+2*offset, 1.0f, 0.5f, 1.0f, 0.25f);
			break;
		case FOUNDATION:
			drawGhostBox(offset, offset, offset, sizeXZ-2*offset, sizeY-offset*2, sizeXZ-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			break;
		case PILLARS:
			drawGhostBox(offset, offset, offset, 1.0f-2*offset, sizeY-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset+sizeXZ-1, offset, offset, 1.0f-2*offset, sizeY-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset+sizeXZ-1, offset, offset+sizeXZ-1, 1.0f-2*offset, sizeY-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			drawGhostBox(offset, offset, offset+sizeXZ-1, 1.0f-2*offset, sizeY-offset*2, 1.0f-2*offset, 0.5f, 0.5f, 1.0f, 0.25f);
			break;		
		}
		
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
