package techguns.client.particle;

import elucent.albedo.lighting.Light;
import net.minecraftforge.fml.common.Optional;
import techguns.client.ClientProxy;

public class LightPulse {

	private int ttl;
	protected int lifetime;
	protected int fade_in;
	
	protected double x;
	protected double y;
	protected double z;
	
	protected float rad_start;
	protected float rad_end;
	
	protected float r;
	protected float g;
	protected float b;
	
	public LightPulse(double x, double y, double z, int fade_in, int fade_out, float rad_large, float rad_small, float r, float g, float b) {
		this.lifetime = fade_in+fade_out;
		this.ttl=lifetime;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rad_start = rad_large;
		this.rad_end = rad_small;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public LightPulse(double x, double y, double z, int lifetime, float rad_start, float rad_end, float r, float g, float b) {
		this.fade_in=0;
		this.lifetime = lifetime;
		this.ttl=lifetime;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rad_start = rad_start;
		this.rad_end = rad_end;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Return if still alive after this tick
	 * @return
	 */
	public boolean updateGameTick() {
		--ttl;
		return ttl>0;
	}
	
	
	@Optional.Method(modid="albedo")
	public Light provideLight() {
		float ptt = ClientProxy.get().PARTIAL_TICK_TIME;
		
		float existed = (lifetime-ttl)+ptt;
		
		float prog;
		if (existed<this.fade_in) {
			prog = (existed)/((float)(fade_in));
		} else {
			prog = ((existed-fade_in))/((float)(lifetime-fade_in));
		}
		
		/*System.out.println("----------------------");
		System.out.println("Existed:   "+existed);
		System.out.println("PulseProg: "+prog);*/
		
		//prog = ((float)(existed))/((float) lifetime);
		//float prog = ((lifetime-ttl)+ptt)/((float) lifetime);
		float inv_prog = 1f-prog;
		
		
		return Light.builder()
				.pos(x,y,z)
				.color(r,g,b)
				.radius(rad_end*prog+rad_start*inv_prog)
				.build();
	}
	
	
}
