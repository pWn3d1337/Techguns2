package techguns.client.render.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;

@SideOnly(Side.CLIENT)
public class ScreenEffect {
		
	//public static ScreenEffect muzzleFlash = new ScreenEffect("textures/guns/handgun.png", 2, 2, 4);
	//public static ScreenEffect muzzleFlashYellow = new ScreenEffect("textures/fx/muzzleFlashYellow2x2.png", 2, 2, 4, RenderType.SOLID);
	public static ScreenEffect muzzleFlashLaser = new ScreenEffect("textures/fx/laserflare02.png", 4, 4, 7, RenderType.ADDITIVE);
	public static ScreenEffect muzzleFlashLightningOld = new ScreenEffect("textures/fx/lightningflare4x4_2.png", 4, 4, 16, RenderType.ADDITIVE);
	public static ScreenEffect muzzleFlashFireball_alpha = new ScreenEffect("textures/fx/fireball_4x4_alpha.png", 4, 4, 16, RenderType.ALPHA);
	public static ScreenEffect muzzleFlashFireball_add = new ScreenEffect("textures/fx/fireball_4x4_add.png", 4, 4, 16, RenderType.ADDITIVE);
	public static ScreenEffect muzzleFlashBlaster = new ScreenEffect("textures/fx/blasterflare01.png", 4, 4, 14, RenderType.ADDITIVE);
	public static ScreenEffect muzzleFlashAlienBlaster = new ScreenEffect("textures/fx/alienflare.png", 4, 4, 14, RenderType.ADDITIVE);
	public static ScreenEffect muzzleFlashLightning = new ScreenEffect("textures/fx/teslaflare01.png", 4, 4, 14, RenderType.ADDITIVE);
	public static ScreenEffect FlamethrowerMuzzleFlame = new ScreenEffect("textures/fx/flamethrower2.png", 4, 4, 16, RenderType.ADDITIVE).setFlipAxis(false, true);
	public static ScreenEffect FlamethrowerMuzzleFlash = new ScreenEffect("textures/fx/flamethrower.png", 4, 4, 16, RenderType.ADDITIVE).setFlipAxis(false, true);//.setFade(FadeType.SMOOTH);
	public static ScreenEffect muzzleFlashSonic = new ScreenEffect("textures/fx/sonicwave4x4.png", 4, 4, 16, RenderType.ADDITIVE);
	public static ScreenEffect muzzleFlashNukeBeam = new ScreenEffect("textures/fx/nukebeamflare.png", 4, 4, 16, RenderType.ADDITIVE);
	
	//public static ScreenEffect muzzleFlashNew2 = new ScreenEffect("textures/fx/muzzleflashnew.png", 4, 4, 16, RenderType.ALPHA);
	
	//<Proper Generic Gun muzzle flashes that should be in use>
	public static ScreenEffect muzzleFlash_rifle = new ScreenEffect("textures/fx/muzzleflashnew_add.png", 4, 4, 12, RenderType.ADDITIVE); //.setJitter(0.1f, 0.1f, 0.1f, 0.1f);
	public static ScreenEffect muzzleFlash_gun = new ScreenEffect("textures/fx/muzzleflashnew_2_add_2.png", 4, 4, 11, RenderType.ADDITIVE);
	public static ScreenEffect muzzleFlash_minigun = new ScreenEffect("textures/fx/muzzleflash_minigun.png", 2, 2, 4, RenderType.ADDITIVE);
	public static ScreenEffect muzzleFlash_blue = new ScreenEffect("textures/fx/bluemuzzleflash.png", 4, 4, 8, RenderType.ADDITIVE).setFlipAxis(true, true);
    //</Proper Generic Gun muzzle flashes that should be in use>
	
	//<Old gun muzzle flashes>
	@Deprecated
	public static ScreenEffect muzzleFlash = new ScreenEffect("textures/fx/muzzleflash4x4.png", 4, 4, 10, RenderType.SOLID);
	@Deprecated
	public static ScreenEffect muzzleFlash2 = new ScreenEffect("textures/fx/muzzleflash2.png", 4, 4, 8, RenderType.SOLID);
	@Deprecated
	public static ScreenEffect muzzleFlashNew = new ScreenEffect("textures/fx/muzzleflashnew_2.png", 4, 4, 16, RenderType.ALPHA);
	//</Old gun muzzle flashes>
	
    public static ScreenEffect muzzleGreenFlare = new ScreenEffect("textures/fx/lensflare1.png", 1, 1, 1, RenderType.ADDITIVE).setFade(FadeType.SMOOTH).setColor(0.5f, 1.0f, 0.25f, 1.0f);	
	public static ScreenEffect sniperScope = new ScreenEffect("textures/fx/testscope.png",1,1,1, RenderType.SOLID);
	public static ScreenEffect techScope = new ScreenEffect("textures/fx/techscope.png",1,1,1, RenderType.ALPHA).setFlipAxis(false, true);
	
	protected ResourceLocation fxTexture;
	protected int cols;
	protected int rows;
	protected int numSprites;
	protected RenderType type;
	
	protected float colorR = 1.0f;
	protected float colorG = 1.0f;
	protected float colorB = 1.0f;
	protected float alpha = 1.0f;
	protected FadeType fadeType = FadeType.NONE;
	
	protected boolean flipY=false;
	protected boolean flipX=false;
	
	public ScreenEffect(String fxTexture, int cols,
			int rows, int numSprites, RenderType type) {
		super();
		this.fxTexture = new ResourceLocation(Techguns.MODID,fxTexture);
		this.cols = cols;
		this.rows = rows;
		this.numSprites = numSprites;
		this.type = type;
	}
	
	public ScreenEffect setColor(float R, float G, float B, float A) {
		this.colorR = R;
		this.colorG = G;
		this.colorB = B;
		this.alpha = A;
		return this;
	}
	
	public ScreenEffect setFade(FadeType type) {
		this.fadeType = type;
		return this;
	}
	
	public ScreenEffect setFlipAxis(boolean x, boolean y) {
		this.flipX=x;
		this.flipY=y;
		return this;
	}
	
//	public ScreenEffect setJitter(float jX, float jY, float jAngle, float jScale) {
//		this.jitterX = jX;
//		this.jitterY = jY;
//		this.jitterAngle = jAngle;
//		this.jitterScale = jScale;
//		return this;
//	}
	
	public void doRender(float progress, float offsetX, float offsetY, float offsetZ, float scale, boolean is3p) {
		doRender(progress, offsetX, offsetY,offsetZ,scale,0,0,0,is3p);
	}
	
	public void doRender(float progress, float offsetX, float offsetY, float offsetZ, float scale, float rot_x, float rot_y, float rot_z, boolean is3p) {
		int currentFrame = (int)((float)numSprites*progress);
		if (currentFrame < numSprites) {
			int col = currentFrame % cols;
			int row = (currentFrame / cols);
			
			float u = 1.f/cols;
			float v = 1.f/rows;
			
			float U1 = col*u;
			float V1 = row*v;
			float U2 = (col+1)*u;
			float V2 = (row+1)*v;
			
			//TGRenderHelper.enableFXLighting();
			TGRenderHelper.enableBlendMode(type);
			//GlStateManager.color(1f, 1f, 1f);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(fxTexture);

			GlStateManager.rotate(rot_x, 1.0f, 0, 0);
			GlStateManager.rotate(rot_y, 0, 1.0f, 0);
			GlStateManager.rotate(rot_z, 0, 0, 1.0f);

			if (this.flipX) {
				float tmp=U1;
				U1=U2;
				U2=tmp;
			}
			
			if (this.flipY) {
				float tmp=V1;
				V1=V2;
				V2=tmp;
			}
			
			
			Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder buffer = tessellator.getBuffer();
			
			float f = scale/2.0f;
			
//			if (!is3p) { //Local player in 1st person
//				ClientProxy cp = ClientProxy.get();
//				if (this.jitterScale > 0.0f) f = f + jitterScale*cp.muzzleFlashJitterScale;
//				if (this.jitterX > 0.0f) offsetX += jitterX*cp.muzzleFlashJitterX*f;
//				if (this.jitterY > 0.0f) offsetY += jitterY*cp.muzzleFlashJitterY*f;
//				//if (this.jitterAngle > 0.0f) angle += jitterAngle*cp.muzzleFlashJitterAngle*f;
//			}
//			
			float alpha = 0.0f;
			
			switch (fadeType) {
	
			case SMOOTH:
				double d = Math.sin(Math.PI*progress);
				alpha = (float) (d*d)*this.alpha;
				break;
			case NONE:
			default:
				alpha = this.alpha;
				break;
	
			}

			if(is3p) {
				GlStateManager.depthMask(false);
			}
			
			//Jitter
			//ClientProxy cp = ClientProxy.
			//offsetX += jitterX;

	        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
	        
	        buffer.pos(offsetX-f, offsetY+f, offsetZ).tex(U1,V2).color(colorR, colorG, colorB, alpha).endVertex();
	        buffer.pos(offsetX-f, offsetY-f, offsetZ).tex(U1,V1).color(colorR, colorG, colorB, alpha).endVertex();
	        buffer.pos(offsetX+f, offsetY-f, offsetZ).tex(U2,V1).color(colorR, colorG, colorB, alpha).endVertex();
	        buffer.pos(offsetX+f, offsetY+f, offsetZ).tex(U2,V2).color(colorR, colorG, colorB, alpha).endVertex();
	        
	        if (is3p) {
	        	buffer.pos(offsetX-f, offsetY, offsetZ+f).tex(U1,V2).color(colorR, colorG, colorB, alpha).endVertex();
	 	        buffer.pos(offsetX-f, offsetY, offsetZ-f).tex(U1,V1).color(colorR, colorG, colorB, alpha).endVertex();
	 	        buffer.pos(offsetX+f, offsetY, offsetZ-f).tex(U2,V1).color(colorR, colorG, colorB, alpha).endVertex();
	 	        buffer.pos(offsetX+f, offsetY, offsetZ+f).tex(U2,V2).color(colorR, colorG, colorB, alpha).endVertex();
	 	        
	 	        buffer.pos(offsetX, offsetY-f, offsetZ+f).tex(U1,V2).color(colorR, colorG, colorB, alpha).endVertex();
	 	        buffer.pos(offsetX, offsetY-f, offsetZ-f).tex(U1,V1).color(colorR, colorG, colorB, alpha).endVertex();
	 	        buffer.pos(offsetX, offsetY+f, offsetZ-f).tex(U2,V1).color(colorR, colorG, colorB, alpha).endVertex();
	 	        buffer.pos(offsetX, offsetY+f, offsetZ+f).tex(U2,V2).color(colorR, colorG, colorB, alpha).endVertex();
	        }
	        
	        tessellator.draw();

	        if(is3p) {
	        	GlStateManager.depthMask(true);
	        }
           //TGRenderHelper.disableFXLighting();
	        TGRenderHelper.disableBlendMode(type);
	        
		}
	}
	
	enum FadeType {
		SMOOTH, NONE;
	}
}