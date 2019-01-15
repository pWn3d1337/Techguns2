package techguns.client.render.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class OreDrillCube {
	//bottom
	protected static final double bo_x0 = 0.0D;
	protected static final double bo_y0 = 0.0D;
	protected static final double bo_x1 = 1.0D;
	protected static final double bo_y1 = 1.0D;
	
	//top
	protected static final double t_x0 = 0.0D;
	protected static final double t_x1 = 1.0D;
	protected static final double t_y0 = 0.0D;
	protected static final double t_y1 = 1.0D;

	//front
	protected static final double f_x0 = 0.0D;
	protected static final double f_x1 = 1.0D;
	protected static final double f_y0 = 0.0D;
	protected static final double f_y1 = 1.0D;

	//back
	protected static final double ba_x0 = 0.0D;
	protected static final double ba_x1 = 1.0D;
	protected static final double ba_y0 = 0.0D;
	protected static final double ba_y1 = 1.0D;

	//right
    protected static final double r_x0 = 0.0D;
	protected static final double r_x1 = 1.0D;
	protected static final double r_y0 = 0.0D;
	protected static final double r_y1 = 1.0D;

	//left
	protected static final double l_x0 = 0.0D;
	protected static final double l_x1 = 1.0D;
	protected static final double l_y0 = 0.0D;
	protected static final double l_y1 = 1.0D;

	protected ResourceLocation texture;

	public OreDrillCube(ResourceLocation texture) {
		this.texture = texture;
	}

	public void drawCube(float sizeX, float sizeY, float sizeZ, Tessellator tess) {

		BufferBuilder buf = tess.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		drawTop(buf, sizeX, sizeY, sizeZ);
		drawBottom(buf, sizeX, sizeY, sizeZ);
		drawFront(buf, sizeX, sizeY, sizeZ);
		drawBack(buf, sizeX, sizeY, sizeZ);
		drawRight(buf, sizeX, sizeY, sizeZ);
		drawLeft(buf, sizeX, sizeY, sizeZ);

		tess.draw();
	}

	protected void drawTop(BufferBuilder buf, float sizeX, float sizeY, float sizeZ) {
		// top
		buf.pos(-sizeX, sizeY, -sizeZ).tex(t_x0, t_y1).normal(0, 1, 0).endVertex();
		buf.pos(-sizeX, sizeY, sizeZ).tex(t_x0, t_y0).normal(0, 1, 0).endVertex();
		buf.pos(sizeX, sizeY, sizeZ).tex(t_x1, t_y0).normal(0, 1, 0).endVertex();
		buf.pos(sizeX, sizeY, -sizeZ).tex(t_x1, t_y1).normal(0, 1, 0).endVertex();
	}

	protected void drawBottom(BufferBuilder buf, float sizeX, float sizeY, float sizeZ) {
		// bottom
		buf.pos(sizeX, -sizeY, -sizeZ).tex(bo_x1, bo_y0).normal(0, -1, 0).endVertex();
		buf.pos(sizeX, -sizeY, sizeZ).tex(bo_x1, bo_y1).normal(0, -1, 0).endVertex();
		buf.pos(-sizeX, -sizeY, sizeZ).tex(bo_x0, bo_y1).normal(0, -1, 0).endVertex();
		buf.pos(-sizeX, -sizeY, -sizeZ).tex(bo_x0, bo_y0).normal(0, -1, 0).endVertex();
	}

	protected void drawFront(BufferBuilder buf, float sizeX, float sizeY, float sizeZ) {
		// front
		buf.pos(-sizeX, -sizeY, -sizeZ).tex(f_x0, f_y1).normal(1, 0, 0).endVertex();
		buf.pos(-sizeX, sizeY, -sizeZ).tex(f_x0, f_y0).normal(1, 0, 0).endVertex();
		buf.pos(sizeX, sizeY, -sizeZ).tex(f_x1, f_y0).normal(1, 0, 0).endVertex();
		buf.pos(sizeX, -sizeY, -sizeZ).tex(f_x1, f_y1).normal(1, 0, 0).endVertex();
	}

	protected void drawBack(BufferBuilder buf, float sizeX, float sizeY, float sizeZ) {
		// back
		buf.pos(sizeX, -sizeY, sizeZ).tex(ba_x1, ba_y0).normal(-1, 0, 0).endVertex();
		buf.pos(sizeX, sizeY, sizeZ).tex(ba_x1, ba_y1).normal(-1, 0, 0).endVertex();
		buf.pos(-sizeX, sizeY, sizeZ).tex(ba_x0, ba_y1).normal(-1, 0, 0).endVertex();
		buf.pos(-sizeX, -sizeY, sizeZ).tex(ba_x0, ba_y0).normal(-1, 0, 0).endVertex();
	}

	protected void drawLeft(BufferBuilder buf, float sizeX, float sizeY, float sizeZ) {
		// left
		buf.pos(-sizeX, -sizeY, sizeZ).tex(l_x1, l_y0).normal(0, 0, 1).endVertex();
		buf.pos(-sizeX, sizeY, sizeZ).tex(l_x1, l_y1).normal(0, 0, 1).endVertex();
		buf.pos(-sizeX, sizeY, -sizeZ).tex(l_x0, l_y1).normal(0, 0, 1).endVertex();
		buf.pos(-sizeX, -sizeY, -sizeZ).tex(l_x0, l_y0).normal(0, 0, 1).endVertex();
	}

	protected void drawRight(BufferBuilder buf, float sizeX, float sizeY, float sizeZ) {
		// right
		buf.pos(sizeX, -sizeY, -sizeZ).tex(r_x0, r_y0).normal(0, 0, -1).endVertex();
		buf.pos(sizeX, sizeY, -sizeZ).tex(r_x0, r_y1).normal(0, 0, -1).endVertex();
		buf.pos(sizeX, sizeY, sizeZ).tex(r_x1, r_y1).normal(0, 0, -1).endVertex();
		buf.pos(sizeX, -sizeY, sizeZ).tex(r_x1, r_y0).normal(0, 0, -1).endVertex();
	}
}
