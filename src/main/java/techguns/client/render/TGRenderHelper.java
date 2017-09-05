package techguns.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import techguns.client.render.TGRenderHelper.RenderType;

public class TGRenderHelper {

	protected static float lastBrightnessX=0;
	protected static float lastBrightnessY=0;
		
	
	/*private static final int STATE_LIGHTING=2896;
	private static final GLStateHandler STATE_LIGHTING_DISABLER = new GLStateHandler(STATE_LIGHTING, true);
	
	private static final int STATE_BLEND =3042;
	private static final GLStateHandler STATE_BLEND_ENABLER = new GLStateHandler(STATE_BLEND, false);
	
	private static final int STATE_DEPTH_TEST=2929;
	private static final GLStateHandler STATE_DEPTH_TEST_DISABLER = new GLStateHandler(STATE_DEPTH_TEST, true);
	
	private static final int STATE_RESCALE_NORMAL=32826;
	private static final GLStateHandler STATE_RESCALE_NORMAL_ENABLER = new GLStateHandler(STATE_RESCALE_NORMAL, false);
	
	private static final int STATE_CULL_FACE=2884;
	private static final GLStateHandler STATE_CULL_FACE_DISABLER = new GLStateHandler(STATE_CULL_FACE, true);*/
	
	
	
	private static int lastBlendSrc=0;
	private static int lastBlendDst=0;
	private static void snapshotBlendFunc() {
		lastBlendSrc=GL11.glGetInteger(GL11.GL_BLEND_SRC);
		lastBlendDst=GL11.glGetInteger(GL11.GL_BLEND_DST);
		//System.out.println("Snapshotting BlendFunc:"+lastBlendSrc+":"+lastBlendDst);
	}
	private static void restoreBlendFunc() {
		GL11.glBlendFunc(lastBlendSrc, lastBlendDst);
	}
	
	/*public static void enableRescaleNormal() {
		STATE_RESCALE_NORMAL_ENABLER.enableState();
	}
	public static void undoEnableRescaleNormal() {
		STATE_RESCALE_NORMAL_ENABLER.undoState();
	}*/
	
	public static void enableFXLighting()
    {
    	//GlStateManager.pushMatrix();	
    //	STATE_LIGHTING_DISABLER.enableState();
    	lastBrightnessX= OpenGlHelper.lastBrightnessX;
		lastBrightnessY= OpenGlHelper.lastBrightnessY;
	//	System.out.println("Saving BrightnessX:"+lastBrightnessX+" BrightnessY:"+lastBrightnessY);
		GlStateManager.disableLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
    }
	
    public static void disableFXLighting()
    {
    	GlStateManager.enableLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
    	//STATE_LIGHTING_DISABLER.undoState();
    	//GlStateManager.popMatrix();
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
    
    
   /* public static void disableCulling() {
    	STATE_CULL_FACE_DISABLER.enableState();
    }
    
    public static void undoDisableCulling() {
    	STATE_CULL_FACE_DISABLER.undoState();
    }
  
    public static void disableDepthTest() {
    	STATE_DEPTH_TEST_DISABLER.enableState();
    }
    
    public static void undoDisableDepthTest() {
    	STATE_DEPTH_TEST_DISABLER.undoState();
    }
    */
    
   /* private static boolean lastMaskState=false;
    public static void disableDepthMask() {
    	lastMaskState = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
    	if (lastMaskState) {
    		GL11.glDepthMask(false);
    	}
    }
    public static void undoDisableDepthMask() {
		GL11.glDepthMask(lastMaskState);
    }*/
    
    /*public static void enableAlphaBlend() {
    	GlStateManager.enableBlend();
    }
    
    public static void disableAlphaBlend() {
    	GlStateManager.disableBlend();
    }*/
    
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
        	//GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        } else if (type == RenderType.ADDITIVE || type==RenderType.NO_Z_TEST) {
        	//GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
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
			//restoreBlendFunc();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        if (type==RenderType.NO_Z_TEST){
        	GlStateManager.depthMask(true);
        	GlStateManager.enableDepth();
        }

	}
	

//    /**
//     * This includes FXLighting!
//     */
//    public static void enableBlendMode(RenderType type) {
//    	if (type != RenderType.SOLID) {
//    		STATE_BLEND_ENABLER.enableState();//GlStateManager.enableBlend();
//    	}
//        if (type == RenderType.ALPHA) {
//        	snapshotBlendFunc();
//        	GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        } else if (type == RenderType.ADDITIVE || type==RenderType.NO_Z_TEST) {
//        	snapshotBlendFunc();
//        	GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
//        }
//        
//        if (type==RenderType.NO_Z_TEST){
//        	/*GlStateManager.depthMask(false);
//        	GlStateManager.disableDepth();*/
//        	STATE_DEPTH_TEST_DISABLER.enableState();
//        }
//        
//        if (type != RenderType.ALPHA_SHADED) TGRenderHelper.enableFXLighting();
//	}
//	
//    /**
//     * This includes FXLighting!
//     */
//	public static void disableBlendMode(RenderType type) {
//		if (type != RenderType.ALPHA_SHADED) TGRenderHelper.disableFXLighting();
//		if (type != RenderType.SOLID) {
//    		STATE_BLEND_ENABLER.undoState(); //GlStateManager.disableBlend();
//    	}
//		if (type == RenderType.ALPHA || type == RenderType.ADDITIVE || type==RenderType.NO_Z_TEST) {
//			restoreBlendFunc();
//        }
//        if (type==RenderType.NO_Z_TEST){
//        	/*GlStateManager.depthMask(true);
//        	GlStateManager.enableDepth();*/
//        	STATE_DEPTH_TEST_DISABLER.undoState();
//        }
//
//	}
	
	
	/*protected static class GLStateHandler {
		private int CAP_ID;
		private boolean state_undo=false;
		private boolean disabler=false;
		protected GLStateHandler(int cap_id, boolean disabler) {
			CAP_ID = cap_id;
			this.disabler=disabler;
		}
		
		protected void enableState() {
			if (!disabler) {
				this.state_undo=!GL11.glIsEnabled(CAP_ID);
				if(this.state_undo) {
					GL11.glEnable(CAP_ID);
				}
			} else {
				this.state_undo=GL11.glIsEnabled(CAP_ID);
				if(this.state_undo) {
					GL11.glDisable(CAP_ID);
				}
			}
		}
		
		protected void undoState() {
			if (!disabler) {
				if (this.state_undo) {
					GL11.glDisable(CAP_ID);
				}
			} else {
				if (this.state_undo) {
					GL11.glEnable(CAP_ID);
				}
			}
		}
	}*/
}
