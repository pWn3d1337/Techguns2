package techguns.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.client.ClientProxy;
import techguns.util.MathUtil;

public class TGParticleStreak extends TGParticle{

	protected TGParticleStreak prev;
	protected TGParticleStreak next;
	
	protected Vec3d pos1; //This streak segment's vertices
	protected Vec3d pos2; 
	
	public TGParticleStreak(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, TGParticleSystem particleSystem) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, particleSystem);
	}

	
	
	 /**
     * Renders the particle
     */
	@Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTickTime, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ)
    {
    	float progress = ((float)this.particleAge+partialTickTime) / (float)this.particleMaxAge;
    	
//    	System.out.printf("renderParticle: rotX = %.4f,  rotZ = %.4f,  rotYZ = %.4f,  rotXY = %.4f,  rotXZ = %.4f\n", rotX, rotZ, rotYZ, rotXY, rotXZ);
//    	Vec3d View = ClientProxy.get().getPlayerClient().getLook(partialTickTime);
//    	System.out.printf("PlayerView: X = %.4f,  Y = %.4f,  Z = %.4f\n---\n", View.x, View.y, View.z);
    	
    	preRenderStep(progress);
    	
    	if (this.next == null) {
    		this.particleAlpha = 0.0f;
    	}
    	
		/*-------------------
		 * ANIMATION
		 */	
		int currentFrame = 0;
        if (type.hasVariations) {
        	currentFrame = variationFrame;
        }else {
        	currentFrame = ((int)((float)type.frames*(progress * this.animationSpeed))) % type.frames;
        }
    	
    	/* -------------
         * RENDER PARTICLE
         */
        this.particleScale = sizePrev + (size-sizePrev)*partialTickTime;
    	
        
        //Minecraft.getMinecraft().renderEngine.bindTexture(type.texture);
        Minecraft.getMinecraft().getTextureManager().bindTexture(type.texture);

//        float f6 = ((float)this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
//        float f7 = f6 + 0.015609375F;
//        float f8 = ((float)this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
//        float f9 = f8 + 0.015609375F;
        float fscale = 0.1F * this.particleScale;

        float fPosX = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTickTime - TGParticleManager.interpPosX);
        float fPosY = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTickTime - TGParticleManager.interpPosY);
        float fPosZ = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTickTime - TGParticleManager.interpPosZ);
    
        float r = fscale;
        
		int col = currentFrame % type.columns;
		int row = (currentFrame / type.columns);
		
		float u = 1.f/type.columns;
		float v = 1.f/type.columns;
		
		float U1 = col*u;
		float V1 = row*v;
		float U2 = (col+1)*u;
		float V2 = (row+1)*v;
		
		float ua, va, ub, vb, uc, vc, ud, vd;
		ua=U2; va=V2; ub = U2; vb= V1; uc = U1; vc = V1; ud=U1; vd = V2;
		
		enableBlendMode();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        buffer.begin(7, VERTEX_FORMAT);
        double a = (angle + (partialTickTime * angleRate)) * MathUtil.D2R;
		Vec3d p1, p2, p3, p4;
		
		//System.out.println("streak");
		
		//RotX = yaw
		//RotZ = pitch

//		double x1, x2, x3, x4, z1, z2, z3, z4;
//		x1 = (double)(- rotX * fscale - rotXY * fscale);
//		x2 = (double)(- rotX * fscale + rotXY * fscale);
//		x3 = (double)( rotX * fscale + rotXY * fscale);
//		x4 = (double)( rotX * fscale - rotXY * fscale);
//		z1 = (double)(- rotYZ * fscale - rotXZ * fscale);
//		z2 = (double)( - rotYZ * fscale + rotXZ * fscale);
//		z3 = (double)( + rotYZ * fscale + rotXZ * fscale);
//		z4 =  (double)( + rotYZ * fscale - rotXZ * fscale);
		
        //p1 = new Vec3d((x1+x2)*0.5, (double)(- rotZ * fscale), (z1+z2)*0.5);
        //p2 = new Vec3d((x3+x4)*0.5, (double)( + rotZ * fscale), (z3+x4)*0.5);
        
//		p1 = new Vec3d((x1+x4)*0.5, (double)(- rotZ * fscale), (z1+z4)*0.5);
//        p2 = new Vec3d((x2+x3)*0.5, (double)( + rotZ * fscale), (z2+x3)*0.5);
		
        if (prev == null) {
            this.pos1 = null; //new Vec3d(fPosX, fPosY, fPosZ);
            this.pos2 = null; //new Vec3d(fPosX, fPosY, fPosZ);
        }else {        	
    		Vec3d v_view = ClientProxy.get().getPlayerClient().getLook(partialTickTime);
    		
    		//Vec3d v_prev = new Vec3d(prev.posX, prev.posY, prev.posZ).subtract(ClientProxy.get().getPlayerClient().getPositionEyes(partialTickTime));
    		Vec3d v_prev = new Vec3d(prev.posX, prev.posY, prev.posZ).subtract(ClientProxy.get().getPlayerClient().getPositionVector());
    		
//            System.out.printf("fPos: X=%.3f,  Y=%.3f, Z=%.3f\n", fPosX, fPosY, fPosZ);
//            System.out.printf("PrevPos: X=%.3f,  Y=%.3f, Z=%.3f\n", v_prev.x, v_prev.y, v_prev.z);
//            System.out.printf("PrevP1: X=%.3f,  Y=%.3f, Z=%.3f\n", prev.pos1.x, prev.pos1.y, prev.pos1.z);
    		
    		Vec3d v_dir = v_prev.subtract(fPosX, fPosY, fPosZ).normalize();
            
    		Vec3d v_cross = v_view.crossProduct(v_dir).normalize();
    		
            p1 = new Vec3d(v_cross.x*fscale + fPosX, v_cross.y*fscale  + fPosY, v_cross.z*fscale  + fPosZ);
            p2 = new Vec3d(v_cross.x* -fscale + fPosX, v_cross.y* -fscale  + fPosY, v_cross.z* -fscale  + fPosZ);
        	
            this.pos1 = p1;
            this.pos2 = p2;
            
            
            float fscaleP = prev.particleScale *0.1f;
            
            
            if (prev.pos1 != null && prev.pos2 != null) {
            	p3 = prev.pos2;
            	p4 = prev.pos1;
            }else {
            	p4 = new Vec3d(v_cross.x*fscaleP + v_prev.x, v_cross.y*fscaleP  + v_prev.y, v_cross.z*fscaleP  + v_prev.z);
                p3 = new Vec3d(v_cross.x* -fscaleP + v_prev.x, v_cross.y* -fscaleP  + v_prev.y, v_cross.z* -fscaleP  + v_prev.z);
                
                prev.pos1 = p4;
                prev.pos2 = p3;
            }
            
            //p4 = new Vec3d(v_cross.x*fscaleP + v_prev.x, v_cross.y*fscaleP  + v_prev.y, v_cross.z*fscaleP  + v_prev.z);
            //p3 = new Vec3d(v_cross.x* -fscaleP + v_prev.x, v_cross.y* -fscaleP  + v_prev.y, v_cross.z* -fscaleP  + v_prev.z);
		
            
            
            //p3 = p3.add(prev.pos2).scale(0.5);
            //p4 = p4.add(prev.pos1).scale(0.5);
            
            //prev.pos1 = p4;
            //prev.pos2 = p3;
            
	        //AngleZ Rotation
	        
	        //double a = angle * MathUtil.D2R;
	        
	//        if (a > 0.0001f) {
	//	        Vec3d axis = p1.normalize().crossProduct(p2.normalize());
	//			double cosa = Math.cos(a);
	//			double sina = Math.sin(a);
	//	        
	//	        p1 = rotAxis(p1, axis, sina, cosa);
	//	        p2 = rotAxis(p2, axis, sina, cosa);
	//	        p3 = rotAxis(p3, axis, sina, cosa);
	//	        p4 = rotAxis(p4, axis, sina, cosa);     
	//        }	        			
	
	        
			buffer.pos(p1.x, p1.y, p1.z).tex((double)ua, (double)va).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.pos(p2.x, p2.y, p2.z).tex((double)ub, (double)vb).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.pos(p3.x, p3.y, p3.z).tex((double)uc, (double)vc).color(prev.particleRed, prev.particleGreen, prev.particleBlue, prev.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.pos(p4.x, p4.y, p4.z).tex((double)ud, (double)vd).color(prev.particleRed, prev.particleGreen, prev.particleBlue, prev.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
	


        }

        Tessellator.getInstance().draw();
        disableBlendMode();
        

    }

	public TGParticleStreak getPrev() {
		return prev;
	}

	public void setPrev(TGParticleStreak prev) {
		this.prev = prev;
	}

	public TGParticleStreak getNext() {
		return next;
	}

	public void setNext(TGParticleStreak next) {
		this.next = next;
	}

	@Override
	public boolean doNotSort() {
		return true;
	}
	
}
