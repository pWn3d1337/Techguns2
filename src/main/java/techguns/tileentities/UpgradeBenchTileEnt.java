package techguns.tileentities;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import techguns.Techguns;

public class UpgradeBenchTileEnt extends BasicOwnedTileEnt {

	public static final AxisAlignedBB BLOCK_BB = new AxisAlignedBB(0d, 0d, 0d, 1d, 13d/16d, 1d);
	
	public UpgradeBenchTileEnt() {
		super(0, false);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(Techguns.MODID+".container.armorbench", new Object[0]);
	}

}
