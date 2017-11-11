package techguns.tileentities;

import net.minecraft.util.SoundEvent;
import techguns.TGConfig;
import techguns.TGSounds;

public class ExplosiveChargeAdvTileEnt extends ExplosiveChargeTileEnt {
	
	public ExplosiveChargeAdvTileEnt() {
		this.maxBlastradius=7;
		this.maxBlastLength=10;
		
		this.blastradius=5;
		this.blastlength=10;
	}

	@Override
	public SoundEvent getTickSound() {
		return TGSounds.C4_TICK;
	}

	@Override
	public SoundEvent getSoundPlant() {
		return TGSounds.C4_PLANT;
	}

	@Override
	public SoundEvent getInitSound() {
		return TGSounds.C4_INIT;
	}

	@Override
	protected boolean canBreakBlockHardness(float hardness) {
		return hardness >=0 && hardness <= TGConfig.explosiveChargeAdvancedMaxBlockHardness;
	}
}
