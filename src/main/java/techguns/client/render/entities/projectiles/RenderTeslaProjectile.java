package techguns.client.render.entities.projectiles;

import java.util.Random;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import techguns.TGuns;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.entities.projectiles.LaserProjectile;
import techguns.entities.projectiles.TeslaProjectile;

public class RenderTeslaProjectile extends Render<TeslaProjectile> {
	
	final static int SIN_COUNT = 5; // Number of overlapping sin functions
	final static double WIDTH = 0.5;
	final static double SIN_DISTANCE = 10.0; //ideal distance for one sinus curve;
	
	double offset = 0.20; // Distance per bolt vertex
	
		
	public RenderTeslaProjectile(RenderManager renderManager) {
		super(renderManager);
		this.texture = new ResourceLocation("techguns:textures/fx/laser_blue.png");
		this.renderType = RenderType.ADDITIVE;
		this.laserWidth = 3.0d;
	}

	private ResourceLocation texture;
	private ResourceLocation textureStart;
	private double laserWidth;	
	private RenderType renderType;

	@Override
	public void doRender(TeslaProjectile laser, double posX, double posY, double posZ, float entityYaw, float partialTicks) {
        // Vec3 source = Vec3.createVectorHelper(laser.posX, laser.posY, laser.posZ);
         //Vec3 target = Vec3.createVectorHelper(laser.targetX, laser.targetY, laser.targetZ);    	 		 
         //Vec3 viewer = Vec3.createVectorHelper(this.renderManager.viewerPosX, this.renderManager.viewerPosY, this.renderManager.viewerPosZ);
         
	//	 System.out.printf("Renderer: Pitch: %.3f, Yaw: %.3f, Distance: %.3f\n", laser.laserPitch, laser.laserYaw, laser.distance);
		// System.out.printf("maxTicks: %d, ticksExisted: %d\n", laser.maxTicks, laser.ticksExisted);
		 double distance = laser.distance;//source.distanceTo(target);
		 int maxTicks = laser.maxTicks;
		 
		 float progress = ((float)laser.ticksExisted)/((float)maxTicks);
	//	 double width = laserWidth * (Math.sin(Math.sqrt(prog)*Math.PI))*2;
		 
		 
		 Random rand = new Random(laser.seed); //Fixed seed for 1 bolt;
	    	
    	double[] dY = new double[SIN_COUNT];
    	double[] dZ = new double[SIN_COUNT];
    	
    	for (int i = 0; i < SIN_COUNT; i++) {	    	
    		//TODO: Y/Z random vectors with fixed length, or whatever
    		dY[i] = 0.5-rand.nextDouble(); //fixed for this bolt
    		dZ[i] = 0.5-rand.nextDouble();
    	}
    	
    	int count = (int) Math.round(distance / offset);
    	offset = (distance / (double) count);
    	
    	float xOffset = 0.0f;
    	
    	int xreps = Math.max(1, (int) Math.round(distance / SIN_DISTANCE)); //TODO: get modulo as additional y/z scale?
    	double xprev = 0, yprev = 0, zprev = 0, widthprev = 1.0, alphaprev = 1.0;

		 
		 
		 //double distance_start = Math.min(1.0d, distance);
		 
		 //distance = distance - distance_start;
		 
		 double u = (distance / laserWidth) * 2.0D;
		 
		 	 
    	 this.bindEntityTexture(laser);        
         GlStateManager.pushMatrix();
    	 //TGRenderHelper.enableFXLighting();
    	 TGRenderHelper.enableBlendMode(RenderType.ADDITIVE);
         //TGRenderHelper.enableRescaleNormal();// GlStateManager.enableRescaleNormal();
         //TGRenderHelper.disableCulling();
         //TGRenderHelper.disableDepthMask();
         GlStateManager.depthMask(false);
         
         GlStateManager.disableCull();
    	 
         GlStateManager.translate(posX, posY, posZ);
         GlStateManager.rotate(laser.laserYaw-90F, 0.0F, 1.0F, 0.0F);              
         GlStateManager.rotate(laser.laserPitch, 0.0F, 0.0F, 1.0F);
       
//         float f10 = 0.0125F;
//         
//         
//         distance*=80.0D;
//         double distance_start = 0.0D;
//         
//         float rot_x = 45f+(prog*180f);
//         
//         GlStateManager.rotate(rot_x+90f , 1.0F, 0.0F, 0.0F);
//         GlStateManager.scale(f10, f10, f10);
//
//         float brightness = (float) Math.sin(Math.sqrt(prog)*Math.PI);
//        		
//         if (distance > distance_start) { //Beam Segment
//	         GlStateManager.pushMatrix();
//	         for (int i = 0; i < 2; ++i)
//	         {
//	             GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
//	             GlStateManager.glNormal3f(0.0F, 0.0F, f10); //????
//	  
//	             bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
//	             
//	             bufferbuilder.pos(distance, -width, 0.0D).tex(u+prog, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
//	             bufferbuilder.pos(distance_start, -width, 0.0D).tex(prog, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
//	             bufferbuilder.pos(distance_start, width, 0.0D).tex( prog, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
//	             bufferbuilder.pos(distance, width, 0.0D).tex(u+prog, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
//	             tessellator.draw();
//	         }
//	         GlStateManager.popMatrix();
//         }
         
         
         for (int i = 0; i <= count; i++) {
     		double d = (double)i/(double)count; //distance progress (0-1)
     		
     		
     		double x = xOffset + (double)i*offset;
     		double y = 0;
     		double z = 0;
     		double randomness = 0.00;
     		
     		if (i > 1) {
 	    		for (int j = 1; j <= SIN_COUNT; j++) {
 	    			double yfactor = ((rand.nextDouble()-0.5) + (progress*dY[j-1] *1.0)) * (2.0/(double)j);
 	    			double zfactor = ((rand.nextDouble()-0.5) + (progress*dZ[j-1] *1.0)) * (2.0/(double)j);
 	    			y+= Math.sin((d * Math.PI) * (double)( j * xreps))*yfactor;
 	    			z+= Math.sin((d * Math.PI) * (double)( j * xreps))*zfactor;
 	    		}
// 	    		y+=randomness*(Math.random()-0.5);
// 	    		z+=randomness*(Math.random()-0.5);
// 	    		x+=randomness*(Math.random()-0.5);
     		}
     		
     		
     		double pulse = 1.0 - Math.sqrt(Math.abs(progress-d)*2.0);
     		//1-WURZEL(ABS(B2-C2))
     		
     		//System.out.printf("X/Y/Z: (%2.2f/%2.2f/%2.2f)\n",x,y,z);
     		double width =  Math.max(0.0, WIDTH*pulse); //WIDTH+(WIDTH*10.0*pulse);
     		if (i >= 1) {
     			drawSegment(xprev, yprev, zprev, x,y,z, widthprev, width, alphaprev, pulse);
     		}
     		widthprev = width;
     		alphaprev = pulse;
     		xprev = x;
     		yprev = y;
     		zprev = z;
     	}
         
         

         GlStateManager.depthMask(true);
         GlStateManager.enableCull();
         //TGRenderHelper.undoDisableDepthMask();
         //TGRenderHelper.undoDisableDepthTest();
         
         //TGRenderHelper.undoDisableCulling(); 
         TGRenderHelper.disableBlendMode(RenderType.ADDITIVE);
         
         //TGRenderHelper.disableFXLighting(); //pops a matrix
         GlStateManager.popMatrix();
	}

	void drawSegment(double x1, double y1, double z1, double x2, double y2, double z2, double width1, double width2, double a1, double a2) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
		
		double scale = 0.5;
		y1*=scale;
		y2*=scale;
		z1*=scale;
		z2*=scale;
		width1*=scale;
		width2*=scale;
//		for (int i = 0; i < 4; i++) { //crossed planes with backsides
			//TODO:<disable backface cull>
			//GL11.glDisable(GL11.GL_CULL_FACE);
		
		GlStateManager.pushMatrix();
		GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
		//Tessellator tessellator = Tessellator.instance;
		
		//set alpha1
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);			
		bufferbuilder.pos(x1,  y1- width1,  z1).tex(0, 0).color(1.0f, 1.0f, 1.0f, (float)a1).endVertex();
		bufferbuilder.pos(x1,  y1+ width1,  z1).tex(0, 1).color(1.0f, 1.0f, 1.0f, (float)a1).endVertex();
		bufferbuilder.pos(x2,  y2+ width2,  z2).tex(1, 1).color(1.0f, 1.0f, 1.0f, (float)a1).endVertex();
		bufferbuilder.pos(x2,  y2- width2,  z2).tex(1, 0).color(1.0f, 1.0f, 1.0f, (float)a1).endVertex();
		tessellator.draw();
		//--
		//set alpha2	
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);			
		bufferbuilder.pos(x1,  y1, z1-width1).tex(0, 0).color(1.0f, 1.0f, 1.0f, (float)a1).endVertex();
		bufferbuilder.pos(x1,  y1, z1+width1).tex(0, 1).color(1.0f, 1.0f, 1.0f, (float)a1).endVertex();
		bufferbuilder.pos(x2,  y2, z2+width2).tex(1, 1).color(1.0f, 1.0f, 1.0f, (float)a1).endVertex();
		bufferbuilder.pos(x2,  y2, z2-width2).tex(1, 0).color(1.0f, 1.0f, 1.0f, (float)a1).endVertex();
		tessellator.draw();
		
		GlStateManager.popMatrix();

	}
	

	@Override
	protected ResourceLocation getEntityTexture(TeslaProjectile entity) {
		return texture;
	}
	
	

}
