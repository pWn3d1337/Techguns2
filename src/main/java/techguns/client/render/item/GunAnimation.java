package techguns.client.render.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;

public class GunAnimation {

	static ICurveType C_ZERO = new ICurveType() {
		@Override
		public float f(float x) {
			return 0.0f;
		}	
	};	
	static ICurveType C_LINEAR = new ICurveType(){
		@Override
		public float f(float x) {
			return x;
		}	
	};
	static ICurveType C_FAST = new ICurveType(){
		@Override
		public float f(float x) {
			return 1f-((1f-x)*(1f-x));
		}	
	};
	static ICurveType C_SLOW = new ICurveType(){
		@Override
		public float f(float x) {
			return x*x;
		}	
	};
	static ICurveType C_SMOOTH = new ICurveType(){
		@Override
		public float f(float x) {
			double d = Math.sin(Math.PI*x*0.5);
			return (float) (d*d);
		}	
	};	
	static ICurveType C_SINUS = new ICurveType() {
		@Override
		public float f(float x) {
			return (float) Math.sin(Math.PI*x*2.0);
		}	
	};

	public static GunAnimation genericRecoil = new GunAnimation().addTranslate(0, 0, 1.0f).addRotate(1.0f, 1.0f, 0, 0).addSegment(0, 0.25f, C_FAST, 0f, 1f).addSegment(0.25f, 1f, C_SMOOTH, 1f, 0f);
	public static GunAnimation swayRecoil = new GunAnimation().addTranslate(1.0f, 0.75f, 0.50f).addRotate(1.0f, 1.0f, -0.75f, 0.25f).addSegment(0, 1.0f,C_SINUS, 0.0f, 1.0f);
	
	public static GunAnimation genericReload = new GunAnimation().addTranslate(-0.15f, -0.05f, 0.15f).addRotate(1.0f, -0.5f, 1.0f, -0.25f).addSegment(0, 0.2f, C_SMOOTH, 0f, 1f).addSegment(0.2f, 0.8f, C_LINEAR, 1.0f, 1.0f).addSegment(0.8f, 1.0f, C_SMOOTH, 1.0f, 0.0f);
	public static GunAnimation breechReload = new GunAnimation().addTranslate(0, 1.0f, -1.0f).addRotate(1.0f, -1.0f, 0f, 0f).addSegment(0, 0.25f, C_SMOOTH, 0f, 1f).addSegment(0.25f, 0.5f, C_LINEAR, 1f, 1f).addSegment(0.5f, 0.85f, C_SMOOTH, 1.0f, -0.25f).addSegment(0.85f, 1f, C_SMOOTH, -0.25f, 0f); 

	public static GunAnimation scopeRecoil = new GunAnimation().addTranslate(0.25f, 1.0f, 0.75f).addRotate(1.0f, 1.0f, 0, 0).addSegment(0, 0.25f, C_FAST, 0f, 1f).addSegment(0.25f, 1f, C_SMOOTH, 1f, 0f);
	public static GunAnimation scopeRecoilAdv = new GunAnimation().addTranslate(1.0f, 0f, 0f).addTranslate(0.0f, 1f, 0f).addTranslate(0.0f, 0f, 1f).addRotate(1.0f, 1.0f, 0, 0).addSegment(0, 0.25f, C_FAST, 0f, 1f).addSegment(0.25f, 1f, C_SMOOTH, 1f, 0f);
	
	public static GunAnimation pulseRifleRecoil = new GunAnimation().addTranslate(0, 0, 1.0f).addRotate(1.0f, 1.0f, 0, 0).addSegment(0, 0.1f, C_FAST, 0f, 0.4f).addSegment(0.1f, 0.2f, C_SMOOTH, 0.4f, 0.3f).addSegment(0.2f, 0.40f, C_FAST, 0.3f, 1f).addSegment(0.40f, 1f, C_SMOOTH, 1f, 0f);
	
	public static GunAnimation swordSweepRecoil = new GunAnimation().addRotate(1.0f, 0, 0, 1.0f).addSegment(0, 0.25f, C_FAST, 0f, 1f).addSegment(0.25f, 1f, C_SMOOTH, 1f, 0f);
	
	
	private List<AnimationSegment> segments;
	private List<Transformation> transformations;
	
	public GunAnimation() {
		segments= new ArrayList<AnimationSegment>();
		transformations= new ArrayList<Transformation>();
	}
	
	public GunAnimation addSegment(float start, float end, ICurveType curve, float val1, float val2) {
		this.segments.add(new AnimationSegment(start, end, curve, val1, val2));
		return this;
	}
	public GunAnimation addTranslate(float x, float y, float z) {
		this.transformations.add(new Translate(x, y, z));
		return this;
	}
	public GunAnimation addRotate(float angle, float x, float y, float z) {
		this.transformations.add(new Rotate(angle, x, y, z));
		return this;
	}
	
	class AnimationSegment {
		
		public AnimationSegment(float start, float end, ICurveType curve, float val1, float val2) {
			super();
			this.start = start;
			this.end = end;
			this.curve = curve;
			this.val1 = val1;
			this.val2 = val2;
		}
		float start;
		float end;
		float val1;
		float val2;
		ICurveType curve;
		public float getValue(float progress) {
			float v = curve.f(progress);
			return val1 + v * (val2-val1);
		}
	}
	
	interface ICurveType {
		float f(float x);
	}
	
	
	abstract class Transformation {
		public abstract void apply(float f, boolean mirror);
	}
	
	class Translate extends Transformation {
		float x;
		float y;
		float z;		
		public Translate(float x, float y, float z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
		}
		@Override
		public void apply(float f, boolean mirror) {
			GlStateManager.translate(mirror  ? (x*-f) : (x*f), y*f, z*f);
		}		
	}
	
	class Rotate extends Transformation {
		float angle;
		float x;
		float y;
		float z;		
		public Rotate(float angle, float x, float y, float z) {
			super();
			this.angle = angle;
			this.x = x;
			this.y = y;
			this.z = z;
		}
		@Override
		public void apply(float f, boolean mirror) {
			//System.out.printf("Rotate: angle = %.3f, x/y/z= %.3f/%.3f/%.2f\n", angle, x, y, z));
			GlStateManager.rotate(f*angle, x, mirror ? -y : y, mirror ? -z : z);
		}		
	}	
	
	public void play(float progress, boolean mirror, float... magnitudes) {
		float prev = 0.0f;
		float value = 0.0f;
		for (AnimationSegment segment : segments) {
			if (progress > segment.start && progress <= segment.end) {
				float p = (progress-prev)/(segment.end-segment.start);
				value += segment.getValue(p);
			}
			prev = segment.end;
		}
		int i = 0;
		for (Transformation trans : transformations) {
			float v = value;
			if (magnitudes.length > i) v*=magnitudes[i++];
			trans.apply(v, mirror);
		}
	}
}
