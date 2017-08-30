package techguns.client.render.entities.projectiles;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.entities.projectiles.NDRProjectile;
import techguns.util.MathUtil;

public class RenderNDRProjectile extends Render<NDRProjectile>{
	
	private static final ResourceLocation LaserTextures = new ResourceLocation("techguns:textures/fx/nukebeam.png");
	
	private double laserWidth = 3.0D;

	public RenderNDRProjectile(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(NDRProjectile entity, double p_x, double p_y, double p_z, float entityYaw, float ptt) {
		 Random rand = new Random(entity.getEntityId()); //laser.seed);
		 double distance = entity.distance;//source.distanceTo(target);
		 float prog = ((float)entity.ticksExisted+ptt)/((float)entity.maxTicks);
		 double width = laserWidth * (Math.sin(Math.sqrt(prog)*Math.PI))*2;

		 Entity e = Minecraft.getMinecraft().player.world.getEntityByID(entity.shooterID);
		 EntityLivingBase shooter = null;
		 if (e instanceof EntityLivingBase)
			shooter = (EntityLivingBase) e;
		 
	     if (shooter == null) return;
		 
    	 this.bindEntityTexture(entity);
    	 
    	 double x = shooter.posX-Minecraft.getMinecraft().player.posX;
         double y = shooter.posY+shooter.getEyeHeight()-(Minecraft.getMinecraft().player.posY/*+Minecraft.getMinecraft().player.getEyeHeight()*/);
         double z = shooter.posZ-Minecraft.getMinecraft().player.posZ;
         
         float pitch = MathUtil.interpolateRotation(shooter.prevRotationPitch, shooter.rotationPitch, ptt);
         float yaw = MathUtil.interpolateRotation(shooter.prevRotationYawHead, shooter.rotationYawHead, ptt);
         
         if (shooter == Minecraft.getMinecraft().player && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) { 	 
//        	 if (Minecraft.getMinecraft().gameSettings.viewBobbing)
//             {
//                 setupViewBobbing(ptt);
//             } 
//        	 Vec3d offset = new Vec3d(0, -0.08, 0.12);
//        	 
//        	 System.out.println(String.format("Pitch = %.3f,  Yaw = %.3f", pitch, yaw));
//        	 
//	         offset = offset.rotateYaw((float) (-(yaw-90F)*MathUtil.D2R));
//	         offset = offset.rotatePitch((float) (pitch*MathUtil.D2R));
//	         x+=offset.x;
//	         y+=offset.y;
//	         z+=offset.z;
         }else {
	         x -= (double)(MathHelper.cos(shooter.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
	         y -=-0.10000000149011612D - entity.get3PYOffset();
	         z -= (double)(MathHelper.sin(shooter.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
	    }
         
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
         
        GlStateManager.rotate(-(yaw-90F), 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(pitch, 0.0F, 0.0F, 1.0F);
        
       GlStateManager.translate(0, -0.08, -0.12);
         
         if (shooter == Minecraft.getMinecraft().player && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {       	 
        	 if (Minecraft.getMinecraft().gameSettings.viewBobbing)
             {
                 setupViewBobbing(ptt);
             }
         }
         
         //Move forward
         GlStateManager.translate(-0.25, 0, 0);
         distance+= 0.25;
    	 
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         float f10 = 0.0125F;
      
         distance*=80.0D;
         
         float rot_x = 45f+(prog*360f);
         
         GlStateManager.rotate(rot_x+90f , 1.0F, 0.0F, 0.0F);
         
         
         GlStateManager.scale(f10, f10, f10);

         float brightness = (float) Math.sin(Math.sqrt(prog)*Math.PI);
 
         //---
         // RENDER BEAM
         double UVscale = 2.0D; 
         int numFrames = 17;
         float frametime = 0.5f; //ticks per frame
         double u = distance / (laserWidth*8.0 * UVscale);
         	
         int frame = (int) ((((float)entity.ticksExisted+ptt) * frametime) % numFrames);
         double v1 = (1.0D / (double)numFrames) * (double)frame;
         double v2 = (1.0D / (double)numFrames) * (double)(frame+1);
         
         TGRenderHelper.enableBlendMode(RenderType.ADDITIVE);
         GlStateManager.disableCull(); 
         GlStateManager.enableRescaleNormal();
        
         for (int i = 0; i < 2; ++i)
         {
        	 GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
             GlStateManager.glNormal3f(0.0F, 0.0F, f10);
             
             bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);             
             bufferbuilder.pos(-distance, -width, 0.0D).tex(0+i, v1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             bufferbuilder.pos(0, -width, 0.0D).tex(u+i, v1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             bufferbuilder.pos(0, width, 0.0D).tex(u+i, v2).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             bufferbuilder.pos(-distance, width, 0.0D).tex(0+i, v2).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             tessellator.draw();
         }
         //-------
         
         //Render Spiral
         //---
         distance = Math.min(distance, 400.0); //SHORTEN LENGTH FOR SPIRAL
         int segments = (int) (distance/8.0);
         double w = 4.0; //width/2
         double radius = 0.0;
         double angle = Math.PI/8.0;
         double prevX = 0.0;
         double prevY = 0.0;
         double prevZ = 0.0;
         double prevu = 0.0;
         double cos2 = Math.cos((Math.PI-angle)*0.5)*2.0;
         double angleOffset = (rand.nextDouble()*Math.PI*2.0);
         UVscale = 8.0;
         //-
         double d = distance/segments;
         for (int i = 0; i < segments; i++) {
        	 /*double*/ x = -d*i;
        	 
        	 float iprog =  (float)i / (float)segments;
        	 radius = 0.5 * (1-Math.cos(2.0*Math.sqrt(iprog)*Math.PI)) * 8.0;
        	 
        	 Vec2f yz = MathUtil.polarOffsetXZ(0, 0, (float)radius, (float)(angleOffset-angle*i));
        	 /*double*/ y = yz.x;
        	 /*double*/ z = yz.y;
        	 u = prevu+(cos2*radius/UVscale);
        	 
        	 bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);             
        	 bufferbuilder.pos(prevX-w, prevY, prevZ).tex(prevu, v2).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
        	 bufferbuilder.pos(x-w, y, z).tex(u, v2).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
        	 bufferbuilder.pos(x+w, y, z).tex(u, v1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
        	 bufferbuilder.pos(prevX+w, prevY, prevZ).tex(prevu, v1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             tessellator.draw();
             
             prevX = x;
             prevY = y;
             prevZ = z;
             prevu = u;
         }
         
         GlStateManager.disableRescaleNormal();
         GlStateManager.enableCull();
         TGRenderHelper.disableBlendMode(RenderType.ADDITIVE);    
         
         GlStateManager.popMatrix();
	}
	
	private void setupViewBobbing(float p_78475_1_)
    {
        if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
            float f1 = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
            float f2 = -(entityplayer.distanceWalkedModified + f1 * p_78475_1_);
            float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * p_78475_1_;
            float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * p_78475_1_;

            float F1 = -1.0f; //(float) Keybinds.X;
            float F2 = -1.0f; //(float) Keybinds.Y;
            
            GlStateManager.translate(MathHelper.sin(f2 * (float)Math.PI) * f3 * 0.5F * F1, -Math.abs(MathHelper.cos(f2 * (float)Math.PI) * f3) * F2, 0.0F);
            GlStateManager.rotate(MathHelper.sin(f2 * (float)Math.PI) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(Math.abs(MathHelper.cos(f2 * (float)Math.PI - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f4, 1.0F, 0.0F, 0.0F);

        }
    }

	@Override
	protected ResourceLocation getEntityTexture(NDRProjectile entity) {
		return LaserTextures;
	}
	

}
