package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import scala.swing.TextComponent;
import techguns.gui.ButtonConstants;

public class DungeonScannerTileEnt extends BasicOwnedTileEnt {
	
	public static final int BUTTON_ID_GHOST = ButtonConstants.BUTTON_ID_SECURITY+1;
	
	public static final int BUTTON_ID_SIZE_XZ_PLUS = ButtonConstants.BUTTON_ID_SECURITY+2;
	public static final int BUTTON_ID_SIZE_XZ_MINUS = ButtonConstants.BUTTON_ID_SECURITY+3;
	
	public static final int BUTTON_ID_SIZE_Y_PLUS = ButtonConstants.BUTTON_ID_SECURITY+4;
	public static final int BUTTON_ID_SIZE_Y_MINUS = ButtonConstants.BUTTON_ID_SECURITY+5;
	
	public static final int BUTTON_ID_SCAN = ButtonConstants.BUTTON_ID_SECURITY+6;
	public static final int BUTTON_ID_PLACE = ButtonConstants.BUTTON_ID_SECURITY+7;

	//---
	
	public static final int SPACING = 1; 
	
	public int sizeXZ = 5;
	public int sizeY = 4;
	
	public boolean showGhost = true;

	public DungeonScannerTileEnt() {
		super(0, false);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("techguns.container.dungeonscanner",new Object[0]);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.sizeXZ = tags.getInteger("sizeXZ");
		this.sizeY = tags.getInteger("sizeY");
		this.showGhost = tags.getBoolean("showGhost");
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setInteger("sizeXZ", sizeXZ);
		tags.setInteger("sizeY", sizeY);
		tags.setBoolean("showGhost", showGhost);
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if (!isUseableByPlayer(ply)) return;	
		switch(id) {
			case BUTTON_ID_GHOST:
				showGhost = !showGhost;
				break;
			case BUTTON_ID_SIZE_XZ_MINUS:
				sizeXZ = Math.max(1, sizeXZ-1);
				break;
			case BUTTON_ID_SIZE_XZ_PLUS:
				sizeXZ = sizeXZ+1;
				break;
			case BUTTON_ID_SIZE_Y_MINUS:
				sizeY = Math.max(1, sizeY-1);
				break;
			case BUTTON_ID_SIZE_Y_PLUS:
				sizeY = sizeY+1;
				break;
			//case BUTTON_ID_SCAN:				
			//	break;
			default:
				super.buttonClicked(id, ply);
		}
		this.needUpdate();
	}
	
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		int renderbounds = SPACING+(sizeXZ+SPACING)*20;
		return new net.minecraft.util.math.AxisAlignedBB(pos, pos.add(renderbounds, sizeY, renderbounds));
	}
	
}
