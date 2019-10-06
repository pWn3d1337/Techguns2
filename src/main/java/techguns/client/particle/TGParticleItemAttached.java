package techguns.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.debug.Keybinds;
import techguns.util.MathUtil;



@SideOnly(Side.CLIENT)
public class TGParticleItemAttached extends TGParticle {

	protected Entity parentEntity;
	
	public TGParticleItemAttached(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, TGParticleSystem particleSystem, Entity parentEntity) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, particleSystem);
		this.parentEntity=parentEntity;
	}

	@Override
	public void doRender(BufferBuilder buffer, Entity renderViewEntity, float partialTickTime, float rotX, float rotZ,
			float rotYZ, float rotXY, float rotXZ) {
		boolean thirdp = true;
		if(parentEntity!=null && parentEntity == renderViewEntity) {
			if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
				thirdp=false;
			}
		}
		this.renderParticle(buffer, renderViewEntity, partialTickTime, rotX, rotZ, rotYZ, rotXY, rotXZ, thirdp);
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTickTime, float rotX, float rotZ,
			float rotYZ, float rotXY, float rotXZ) {
		renderParticle(buffer, entityIn, partialTickTime, rotX, rotZ, rotYZ, rotXY, rotXZ, true);
	}
	
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTickTime, float rotX, float rotZ,
			float rotYZ, float rotXY, float rotXZ, boolean thirdp) {
		float progress = ((float)this.particleAge+partialTickTime) / (float)this.particleMaxAge;
    	
    	preRenderStep(progress);   	
    	
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
    	
        Minecraft.getMinecraft().getTextureManager().bindTexture(type.texture);

        float fscale = 0.1F * this.particleScale;

        float fPosX = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTickTime - (!this.itemAttached ? TGParticleManager.interpPosX :0));
        float fPosY = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTickTime - (!this.itemAttached ? TGParticleManager.interpPosY :0));
        float fPosZ = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTickTime - (!this.itemAttached ? TGParticleManager.interpPosZ :0));
    
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
		
		if (this.type.groundAligned) {
			float s = fscale;
			p1 = new Vec3d(-s,0,-s);
			p2 = new Vec3d(s,0,-s);
			p3 = new Vec3d(s,0,s);
			p4 = new Vec3d(-s,0,s);
			if (a > 0.0001f) {
				p1 = p1.rotateYaw((float) a);
				p2 = p2.rotateYaw((float) a);
				p3 = p3.rotateYaw((float) a);
				p4 = p4.rotateYaw((float) a);
			}
		}else {
	        p1 = new Vec3d((double)(- rotX * fscale - rotXY * fscale), (double)(- rotZ * fscale), (double)(- rotYZ * fscale - rotXZ * fscale));
	        p2 = new Vec3d((double)(- rotX * fscale + rotXY * fscale), (double)( + rotZ * fscale), (double)( - rotYZ * fscale + rotXZ * fscale));
	        p3 = new Vec3d((double)( rotX * fscale + rotXY * fscale), (double)( + rotZ * fscale), (double)( + rotYZ * fscale + rotXZ * fscale));
	        p4 = new Vec3d((double)( rotX * fscale - rotXY * fscale), (double)( - rotZ * fscale), (double)( + rotYZ * fscale - rotXZ * fscale));        
	        
	        if (a > 0.0001f) {
		        Vec3d axis = p1.normalize().crossProduct(p2.normalize());
				double cosa = Math.cos(a);
				double sina = Math.sin(a);
		        
		        p1 = rotAxis(p1, axis, sina, cosa);
		        p2 = rotAxis(p2, axis, sina, cosa);
		        p3 = rotAxis(p3, axis, sina, cosa);
		        p4 = rotAxis(p4, axis, sina, cosa);     
	        }	        		
		}
		
	//if(!thirdp) {
		buffer.pos(p1.x + fPosX, p1.y + fPosY, p1.z + fPosZ).tex((double)ua, (double)va).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
		buffer.pos(p2.x + fPosX, p2.y + fPosY, p2.z + fPosZ).tex((double)ub, (double)vb).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
		buffer.pos(p3.x + fPosX, p3.y + fPosY, p3.z + fPosZ).tex((double)uc, (double)vc).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
		buffer.pos(p4.x + fPosX, p4.y + fPosY, p4.z + fPosZ).tex((double)ud, (double)vd).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
	//}
		if(thirdp) {
			buffer.pos(p1.z + fPosZ, p1.y + fPosY, p1.x + fPosX).tex((double)ua, (double)va).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.pos(p2.z + fPosZ, p2.y + fPosY, p2.x + fPosX).tex((double)ub, (double)vb).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.pos(p3.z + fPosZ, p3.y + fPosY, p3.x + fPosX).tex((double)uc, (double)vc).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.pos(p4.z + fPosZ, p4.y + fPosY, p4.x + fPosX).tex((double)ud, (double)vd).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			
			double h = (p1.y+p3.y)*0.5d;
			
			buffer.pos(p1.x + fPosX, h + fPosY, -p2.x + fPosX).tex((double)ua, (double)va).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.pos(p2.x + fPosX, h + fPosY, -p1.x + fPosX).tex((double)ub, (double)vb).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.pos(p3.x + fPosX, h + fPosY, -p4.x + fPosX).tex((double)uc, (double)vc).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.pos(p4.x + fPosX, h + fPosY, -p3.x + fPosX).tex((double)ud, (double)vd).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();

		}
		
        Tessellator.getInstance().draw();

        disableBlendMode();

	}
	
	
}
