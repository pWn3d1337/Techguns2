package techguns.client.render.entities.projectiles;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.entities.projectiles.LaserProjectile;

public class RenderLaserProjectile extends Render<LaserProjectile> {
		
	public RenderLaserProjectile(RenderManager renderManager) {
		super(renderManager);
		this.texture = new ResourceLocation("techguns:textures/fx/laser3.png");
		this.textureStart = new ResourceLocation("techguns:textures/fx/laser3_start.png");
		this.renderType = RenderType.ADDITIVE;
		this.laserWidth = 3.0d;
	}

	private ResourceLocation texture;
	private ResourceLocation textureStart;
	private double laserWidth;	
	private RenderType renderType;

	@Override
	public void doRender(LaserProjectile laser, double x, double y, double z, float entityYaw, float partialTicks) {
        // Vec3 source = Vec3.createVectorHelper(laser.posX, laser.posY, laser.posZ);
         //Vec3 target = Vec3.createVectorHelper(laser.targetX, laser.targetY, laser.targetZ);    	 		 
         //Vec3 viewer = Vec3.createVectorHelper(this.renderManager.viewerPosX, this.renderManager.viewerPosY, this.renderManager.viewerPosZ);
         
		 //System.out.printf("Renderer: Pitch: %.3f, Yaw: %.3f, Distance: %.3f\n", laser.laserPitch, laser.laserYaw, laser.distance);
		// System.out.printf("maxTicks: %d, ticksExisted: %d\n", laser.maxTicks, laser.ticksExisted);
		 double distance = laser.distance;//source.distanceTo(target);
		 int maxTicks = laser.maxTicks;
		 
		 float prog = ((float)laser.ticksExisted)/((float)maxTicks);
		 double width = laserWidth * (Math.sin(Math.sqrt(prog)*Math.PI))*2;
		 
		 
		 double distance_start = Math.min(1.0d, distance);
		 
		 //distance = distance - distance_start;
		 
		 double u = (distance / laserWidth) * 2.0D;
		 
		 	 
    	 this.bindEntityTexture(laser);        
         GlStateManager.pushMatrix();
    	 //TGRenderHelper.enableFXLighting();
    	 TGRenderHelper.enableBlendMode(RenderType.ADDITIVE);
   //      TGRenderHelper.enableRescaleNormal();
    	  GlStateManager.enableRescaleNormal();
    //     TGRenderHelper.disableCulling();
    	  GlStateManager.disableCull();
         //TGRenderHelper.disableDepthTest();
         //TGRenderHelper.disableDepthMask();
         GlStateManager.depthMask(false);
         
         //GlStateManager.disableCull();
         //GlStateManager.depthMask(false);
    	 
         GlStateManager.translate(x, y, z);
         GlStateManager.rotate(laser.laserYaw-90F, 0.0F, 1.0F, 0.0F);              
         GlStateManager.rotate(laser.laserPitch, 0.0F, 0.0F, 1.0F);
       
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         float f10 = 0.0125F;
         
         
         distance*=80.0D;
         distance_start*=80.0D;
         
         float rot_x = 45f+(prog*180f);
         
         GlStateManager.rotate(rot_x+90f , 1.0F, 0.0F, 0.0F);
         GlStateManager.scale(f10, f10, f10);

         float brightness = (float) Math.sin(Math.sqrt(prog)*Math.PI);
        		
         if (distance > distance_start) { //Beam Segment
	         GlStateManager.pushMatrix();
	         for (int i = 0; i < 2; ++i)
	         {
	             GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	             GlStateManager.glNormal3f(0.0F, 0.0F, f10); //????
	  
	             bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
	             
	             bufferbuilder.pos(distance, -width, 0.0D).tex(u+prog, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	             bufferbuilder.pos(distance_start, -width, 0.0D).tex(prog, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	             bufferbuilder.pos(distance_start, width, 0.0D).tex( prog, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	             bufferbuilder.pos(distance, width, 0.0D).tex(u+prog, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	             tessellator.draw();
	         }
	         GlStateManager.popMatrix();
         }
         GlStateManager.pushMatrix();
         renderManager.renderEngine.bindTexture(textureStart);
         for (int i = 0; i < 2; ++i) //Beam start segment
         {
             GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
             GlStateManager.glNormal3f(0.0F, 0.0F, f10); //????
  
             bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
             
             bufferbuilder.pos(distance_start, -width, 0.0D).tex(1, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             bufferbuilder.pos(0, -width, 0.0D).tex(0, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             bufferbuilder.pos(0, width, 0.0D).tex(0, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             bufferbuilder.pos(distance_start, width, 0.0D).tex( 1, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             tessellator.draw();
         }
         GlStateManager.popMatrix();

         GlStateManager.depthMask(true);
         //GlStateManager.enableCull();
       //  TGRenderHelper.undoDisableDepthMask();
         //TGRenderHelper.undoDisableDepthTest();
        // GlStateManager.depthMask(true);
       //  TGRenderHelper.undoDisableCulling();
      //   TGRenderHelper.undoEnableRescaleNormal();
         GlStateManager.enableCull();
         GlStateManager.disableRescaleNormal();	 
        TGRenderHelper.disableBlendMode(RenderType.ADDITIVE);
         //TGRenderHelper.disableFXLighting(); //pops a matrix
         GlStateManager.popMatrix();
	}


	@Override
	protected ResourceLocation getEntityTexture(LaserProjectile entity) {
		return texture;
	}
	
	

}
