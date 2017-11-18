package techguns.client.render.fx;

import java.util.ArrayList;

public class MultiScreenEffect implements IScreenEffect {

	public static final float z_offset = 0.01f;
	
	public ArrayList<EffectEntry> effects = new ArrayList<>();
	
	public MultiScreenEffect() {
	}
	
	public void doRender(float progress, float offsetX, float offsetY, float offsetZ, float scale, float rot_x, float rot_y, float rot_z, boolean is3p) {
		int i = 0;
		for (EffectEntry entry : effects) {		
			if (progress > entry.start && progress < entry.end) {
				float prog = (progress - entry.start) / (entry.end-entry.start); //Math.max(0.0f,Math.min(1.0f, (progress - entry.start) / (entry.end-entry.start)));
				entry.effect.doRender(prog, offsetX, offsetY, offsetZ + z_offset * i, scale * entry.scale, rot_x, rot_y, rot_z, is3p);
				i++;
			}		
		}
	}
	
	public MultiScreenEffect add(IScreenEffect effect, float start, float end, float scale) {
		effects.add(new EffectEntry(effect, start, end, scale));
		return this;
	}
	
	public MultiScreenEffect add(IScreenEffect effect) {
		effects.add(new EffectEntry(effect));
		return this;
	}

	class EffectEntry {
		IScreenEffect effect;
		float start;
		float end;
		float scale;
		
		public EffectEntry(IScreenEffect effect) {
			this(effect, 0.0f, 1.0f, 1.0f);
		}
		
		public EffectEntry(IScreenEffect effect, float start, float end, float scale) {
			this.effect = effect;
			this.start = start;
			this.end = end;
			this.scale = scale;
		}
	}
}
