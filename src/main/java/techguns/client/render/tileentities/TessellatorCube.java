package techguns.client.render.tileentities;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class TessellatorCube {
	/*Bottome 0/1 values*/
	public double bo_x0 = 0.0D;
	public double bo_y0 = 0.0D;
	public double bo_x1 = 1.0D;
	public double bo_y1 = 1.0D;
	/*top*/
	public double t_x0 = 0.0D;
	public double t_x1 = 1.0D;
	public double t_y0 = 0.0D;
	public double t_y1 = 1.0D;
	
	/*front*/
	public double f_x0 = 0.0D;
	public double f_x1 = 1.0D;
	public double f_y0 = 0.0D;
	public double f_y1 = 1.0D;
	
	/*back*/
	public double ba_x0 = 0.0D;
	public double ba_x1 = 1.0D;
	public double ba_y0 = 0.0D;
	public double ba_y1 = 1.0D;
	
	/*right*/
	public double r_x0 = 0.0D;
	public double r_x1 = 1.0D;
	public double r_y0 = 0.0D;
	public double r_y1 = 1.0D;
	
	/*left*/
	public double l_x0 = 0.0D;
	public double l_x1 = 1.0D;
	public double l_y0 = 0.0D;
	public double l_y1 = 1.0D;
	
	public double minX = 0;
	public double minY = 0;
	public double minZ = 0;
	public double maxX = 1;
	public double maxY = 1;
	public double maxZ = 1;
	
	
	public void drawCube(BufferBuilder buf, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, boolean scaleUVs, TextureAtlasSprite tex){
	
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		
		if (scaleUVs) {
			//Bottom			

			bo_x0 = (double)tex.getMinU();
			bo_y0 = (double)tex.getMinV();
			bo_x1 = (double)tex.getMaxU();
			bo_y1 = (double)tex.getMaxV();
			
			//Top
			t_x0 = (double)tex.getMinU();
			t_y0 = (double)tex.getMinV();
			t_x1 = (double)tex.getMaxU();
			t_y1 = (double)tex.getMaxV();	
			
			//front
			f_x0 = (double)tex.getMinU();
			f_y0 = (double)tex.getMinV();
			f_x1 = (double)tex.getMaxU();
			f_y1 = (double)tex.getMaxV();	
			
			//back
			ba_x0 = (double)tex.getMinU();
			ba_y1 = (double)tex.getMinV();
			ba_x1 = (double)tex.getMaxU();
			ba_y0 = (double)tex.getMaxV();	
			
			//left
			l_x0 = (double)tex.getMinU();
			l_y1 = (double)tex.getMinV();
			l_x1 = (double)tex.getMaxU();
			l_y0 = (double)tex.getMaxV();	
			
			//right
			r_x0 = (double)tex.getMinU();
			r_y1 = (double)tex.getMinV();
			r_x1 = (double)tex.getMaxU();
			r_y0 = (double)tex.getMaxV();	
			
		}else {
			//bottom
			bo_x0 = (double)tex.getInterpolatedU(minX*16D);
			bo_x1 = (double)tex.getInterpolatedU(maxX*16D);
			bo_y0 = (double)tex.getInterpolatedV(minZ*16D);
			bo_y1 = (double)tex.getInterpolatedV(maxZ*16D);
				
			//top		
			t_x0 = (double)tex.getInterpolatedU(minX*16D);
			t_x1 = (double)tex.getInterpolatedU(maxX*16D);
			t_y0 = (double)tex.getInterpolatedV(minZ*16D);
			t_y1 = (double)tex.getInterpolatedV(maxZ*16D);
			
			//front
			f_x0 = (double)tex.getInterpolatedU(minX*16D);
			f_x1 = (double)tex.getInterpolatedU(maxX*16D);
			f_y0 = (double)tex.getInterpolatedV(minY*16D);
			f_y1 = (double)tex.getInterpolatedV(maxY*16D);			
			
			//back
			ba_x0 = (double)tex.getInterpolatedU(minX*16D);
			ba_x1 = (double)tex.getInterpolatedU(maxX*16D);
			ba_y1 = (double)tex.getInterpolatedV(minY*16D);
			ba_y0 = (double)tex.getInterpolatedV(maxY*16D);
			//left
			l_x0 = (double)tex.getInterpolatedU(minZ*16D);
			l_x1 = (double)tex.getInterpolatedU(maxZ*16D);
			l_y1 = (double)tex.getInterpolatedV(minY*16D);
			l_y0 = (double)tex.getInterpolatedV(maxY*16D);
			
			//right
			r_x0 = (double)tex.getInterpolatedU(minZ*16D);
			r_x1 = (double)tex.getInterpolatedU(maxZ*16D);
			r_y1 = (double)tex.getInterpolatedV(minY*16D);
			r_y0 = (double)tex.getInterpolatedV(maxY*16D);
			
		}	
		
	     drawBottom(buf);
	     drawTop(buf);
	     drawFront(buf);
	     drawBack(buf);
	     drawLeft(buf);
	     drawRight(buf);
		}
		
	protected void drawTop(BufferBuilder buf){
		// top
		buf.pos(minX, maxY, minZ).tex(t_x0, t_y0).normal(0.0F, 1.0F, 0.0F).endVertex();
		buf.pos(minX, maxY, maxZ).tex(t_x0, t_y1).normal(0.0F, 1.0F, 0.0F).endVertex();
		buf.pos(maxX, maxY, maxZ).tex(t_x1, t_y1).normal(0.0F, 1.0F, 0.0F).endVertex();
		buf.pos(maxX, maxY, minZ).tex(t_x1, t_y0).normal(0.0F, 1.0F, 0.0F).endVertex();

	}

	protected void drawBottom(BufferBuilder buf) {
		// bottom
		buf.pos(maxX, minY, minZ).tex(bo_x1, bo_y0).normal(0.0F, -1.0F, 0.0F).endVertex();
		buf.pos(maxX, minY, maxZ).tex(bo_x1, bo_y1).normal(0.0F, -1.0F, 0.0F).endVertex();
		buf.pos(minX, minY, maxZ).tex(bo_x0, bo_y1).normal(0.0F, -1.0F, 0.0F).endVertex();
		buf.pos(minX, minY, minZ).tex(bo_x0, bo_y0).normal(0.0F, -1.0F, 0.0F).endVertex();
	}

	protected void drawFront(BufferBuilder buf) {
		// front
		buf.pos(minX, minY, minZ).tex(f_x0, f_y1).normal(1.0F, 0.0F, 0.0F).endVertex();
		buf.pos(minX, maxY, minZ).tex(f_x0, f_y0).normal(1.0F, 0.0F, 0.0F).endVertex();
		buf.pos(maxX, maxY, minZ).tex(f_x1, f_y0).normal(1.0F, 0.0F, 0.0F).endVertex();
		buf.pos(maxX, minY, minZ).tex(f_x1, f_y1).normal(1.0F, 0.0F, 0.0F).endVertex();
	}

	protected void drawBack(BufferBuilder buf) {
		// back
		buf.pos(maxX, minY, maxZ).tex(ba_x1, ba_y0).normal(-1.0F, 0.0F, 0.0F).endVertex();
		buf.pos(maxX, maxY, maxZ).tex(ba_x1, ba_y1).normal(-1.0F, 0.0F, 0.0F).endVertex();
		buf.pos(minX, maxY, maxZ).tex(ba_x0, ba_y1).normal(-1.0F, 0.0F, 0.0F).endVertex();
		buf.pos(minX, minY, maxZ).tex(ba_x0, ba_y0).normal(-1.0F, 0.0F, 0.0F).endVertex();
	}

	protected void drawLeft(BufferBuilder buf) {
		// left
		buf.pos(minX, minY, maxZ).tex(l_x1, l_y0).normal(0.0F, 0.0F, 1.0F).endVertex();
		buf.pos(minX, maxY, maxZ).tex(l_x1, l_y1).normal(0.0F, 0.0F, 1.0F).endVertex();
		buf.pos(minX, maxY, minZ).tex(l_x0, l_y1).normal(0.0F, 0.0F, 1.0F).endVertex();
		buf.pos(minX, minY, minZ).tex(l_x0, l_y0).normal(0.0F, 0.0F, 1.0F).endVertex();
	}

	protected void drawRight(BufferBuilder buf) {
		// right
		buf.pos(maxX, minY, minZ).tex(r_x0, r_y0).normal(0.0F, 0.0F, -1.0F).endVertex();
		buf.pos(maxX, maxY, minZ).tex(r_x0, r_y1).normal(0.0F, 0.0F, -1.0F).endVertex();
		buf.pos(maxX, maxY, maxZ).tex(r_x1, r_y1).normal(0.0F, 0.0F, -1.0F).endVertex();
		buf.pos(maxX, minY, maxZ).tex(r_x1, r_y0).normal(0.0F, 0.0F, -1.0F).endVertex();
	}
}
