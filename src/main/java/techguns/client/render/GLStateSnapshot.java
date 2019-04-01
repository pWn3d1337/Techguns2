package techguns.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;

/**
 * does not work!
 * used to protect GL state changes during TG particle rendering (RenderWorldLast Event)
 */
@Deprecated
public class GLStateSnapshot {

	private BlendFuncState blend_func;
	private BooleanState blend;
	private BooleanState depthMask;
	private BooleanState depthTest;
	private LightMapState lightmap;
	private BooleanState lightingState;
	private BooleanState cullingState;
	private BooleanState lighting;
	
	@Deprecated
	/**
	 * Only use for debug printing states, does not work
	 */
	public GLStateSnapshot() {
		blend_func = new BlendFuncState();
		blend = new BooleanState(GL11.GL_BLEND);
		depthMask = new BooleanState(GL11.GL_DEPTH_WRITEMASK);
		depthTest = new BooleanState(GL11.GL_DEPTH_TEST);
		lightingState = new BooleanState(GL11.GL_LIGHTING);
		cullingState = new BooleanState(GL11.GL_CULL_FACE);
		lightmap = new LightMapState();
	}
	
	public void printDebug() {
		System.out.println("BlendFunc: "+blend_func.value_src + " " + blend_func.value_dst);
		System.out.println("Blend: "+blend.value);
		System.out.println("DMask: "+depthMask.value);
		System.out.println("DTest: "+depthTest.value);
		System.out.println("Culling:"+cullingState.value);
		System.out.println("Lighting: "+lightingState.value);
		System.out.println("Lightmap: "+lightmap.lightmapX+ " "+ lightmap.lightmapY);
	}
	
	/**
	 * restore states to this snapshot
	 */
	public void restore() {
		blend_func.restore();
		blend.restore();
		depthMask.restore();
		depthTest.restore();
		cullingState.restore();
		lightingState.restore();
		lightmap.restore();
	}
	
	private class BooleanState {
		private int id;
		private boolean value;
		
		public BooleanState(int id) {
			this.id = id;
			this.value = GL11.glGetBoolean(id);
		}
		
		public void restore() {
			if(GL11.glGetBoolean(id)!=value) {
				if(value) {
					GL11.glEnable(id);
				} else {
					GL11.glDisable(id);
				}
			}
		}
		
	}
	
	private class LightMapState {
		private float lightmapX;
		private float lightmapY;
		
		public LightMapState() {
			lightmapX = OpenGlHelper.lastBrightnessX;
			lightmapY = OpenGlHelper.lastBrightnessY;	
		}
		
		public void restore() {
			if(OpenGlHelper.lastBrightnessX!= lightmapX || OpenGlHelper.lastBrightnessY!= lightmapY) {
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
			}
		}
	}
	
	private class BlendFuncState {
		private int value_src;
		private int value_dst;
		
		public BlendFuncState() {
			this.value_src = GL11.glGetInteger(GL11.GL_BLEND_SRC);
			this.value_dst = GL11.glGetInteger(GL11.GL_BLEND_DST);
		}
		
		public void restore() {
			if(GL11.glGetInteger(GL11.GL_BLEND_SRC)!=value_src || GL11.glGetInteger(GL11.GL_BLEND_DST) != value_dst) {
				GL11.glBlendFunc(value_src, value_dst);
			}
		}
		
	}
}
