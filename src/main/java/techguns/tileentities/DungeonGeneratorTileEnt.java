package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import techguns.Techguns;

public class DungeonGeneratorTileEnt extends BasicOwnedTileEnt {

	public DungeonGeneratorTileEnt() {
		super(0, false);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(Techguns.MODID+".container.dungeongenerator", new Object[0]);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		// TODO Auto-generated method stub
		super.readClientDataFromNBT(tags);
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		// TODO Auto-generated method stub
		super.writeClientDataToNBT(tags);
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		// TODO Auto-generated method stub
		super.buttonClicked(id, ply, data);
	}

	
	
}
