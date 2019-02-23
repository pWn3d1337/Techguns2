package techguns.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;

public class TGRenderHelper {

	protected static float lastBrightnessX=0;
	protected static float lastBrightnessY=0;
	
	protected static int lastBlendFuncSrc=0;
	protected static int lastBlendFuncDest=0;
	
	/*private static int lastBlendSrc=0;
	private static int lastBlendDst=0;
	private static void snapshotBlendFunc() {
		lastBlendSrc=GL11.glGetInteger(GL11.GL_BLEND_SRC);
		lastBlendDst=GL11.glGetInteger(GL11.GL_BLEND_DST);
	}
	private static void restoreBlendFunc() {
		GL11.glBlendFunc(lastBlendSrc, lastBlendDst);
	}*/

	public static void enableFXLighting()
    {
    	lastBrightnessX= OpenGlHelper.lastBrightnessX;
		lastBrightnessY= OpenGlHelper.lastBrightnessY;

		GlStateManager.disableLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
    }
	
    public static void disableFXLighting()
    {
    	GlStateManager.enableLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
    }
    
    public static void enableFluidGlow(int luminosity) {
    	lastBrightnessX= OpenGlHelper.lastBrightnessX;
		lastBrightnessY= OpenGlHelper.lastBrightnessY;
		
		float newLightX = Math.min((luminosity/15.0f)*240.0f + lastBrightnessX, 240.0f);
		float newLightY = Math.min((luminosity/15.0f)*240.0f + lastBrightnessY, 240.0f);
		
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, newLightX, newLightY);
    }
    
    public static void disableFluidGlow() {
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
    }

    public enum RenderType {
    	ALPHA, ADDITIVE, SOLID, ALPHA_SHADED, NO_Z_TEST;
    }
	
    /**
     * This includes FXLighting!
     */
    public static void enableBlendMode(RenderType type) {
    	if (type != RenderType.SOLID) {
    		GlStateManager.enableBlend();
    	}
        if (type == RenderType.ALPHA) {
        	lastBlendFuncSrc = GlStateManager.glGetInteger(GL11.GL_BLEND_SRC);
			lastBlendFuncDest = GlStateManager.glGetInteger(GL11.GL_BLEND_DST);
        	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        	
        } else if (type == RenderType.ADDITIVE || type==RenderType.NO_Z_TEST) {
        	lastBlendFuncSrc = GlStateManager.glGetInteger(GL11.GL_BLEND_SRC);
			lastBlendFuncDest = GlStateManager.glGetInteger(GL11.GL_BLEND_DST);
        	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }      
        if (type==RenderType.NO_Z_TEST){
        	GlStateManager.depthMask(false);
        	GlStateManager.disableDepth();
        }
        
        if (type != RenderType.ALPHA_SHADED) TGRenderHelper.enableFXLighting();
	}
	
    /**
     * This includes FXLighting!
     */
	public static void disableBlendMode(RenderType type) {
		if (type != RenderType.ALPHA_SHADED) TGRenderHelper.disableFXLighting();
		if (type != RenderType.SOLID) {
    		GlStateManager.disableBlend();
    	}
		if (type == RenderType.ALPHA || type == RenderType.ADDITIVE || type==RenderType.NO_Z_TEST) {
			//GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(lastBlendFuncSrc, lastBlendFuncDest);
        }
        if (type==RenderType.NO_Z_TEST){
        	GlStateManager.depthMask(true);
        	GlStateManager.enableDepth();
        }

	}
	
}
