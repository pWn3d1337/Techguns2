package techguns.client.render.item;

import net.minecraft.client.Minecraft;
import techguns.client.models.ModelMultipart;
import techguns.client.render.fx.ScreenEffect;

public class RenderGunFlamethrower extends RenderGunBase90 {

	public RenderGunFlamethrower(ModelMultipart model, int parts) {
		super(model, parts);
	}
	
	@Override
	protected void drawIdleFx(boolean leftHand) {
		if (Minecraft.getMinecraft().player.isOffsetPositionInLiquid(0, 1.5, 0)) {
			float p = ((float)(Minecraft.getMinecraft().world.getTotalWorldTime() % 30) / 30.0f);
			float x= leftHand?this.muzzleFX_x_l:this.muzzleFX_x_r;
			ScreenEffect.FlamethrowerMuzzleFlame.doRender(p, x, this.muzzleFX_y, this.muzzleFX_z, this.muzzleFX_scale*0.5f, false);
		}
	}
	
	@Override
	protected void drawIdleFx3P(boolean leftHand) {
		if (Minecraft.getMinecraft().player.isOffsetPositionInLiquid(0, 1.5, 0)) {
			float p = ((float)(Minecraft.getMinecraft().world.getTotalWorldTime() % 30) / 30.0f);
			float x= leftHand?this.muzzleFX_x_l:this.muzzleFX_x_r;
			ScreenEffect.FlamethrowerMuzzleFlame.doRender(p, 0, this.muzzleFX_3p_y, this.muzzleFX_3p_z, this.muzzleFX_scale*0.5f*this.muzzleFX_3p_scale, true);
		}
	}

}
