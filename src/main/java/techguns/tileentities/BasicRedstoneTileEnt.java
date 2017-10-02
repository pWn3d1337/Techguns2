package techguns.tileentities;

import static techguns.gui.ButtonConstants.BUTTON_ID_REDSTONE;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BasicRedstoneTileEnt extends BasicOwnedTileEnt {

	/**
	 * 0-ignore
	 * 1-high
	 * 2-low
	 */
	protected byte redstoneBehaviour=0;
	
	public BasicRedstoneTileEnt(int inventorySize, boolean hasRotation) {
		super(inventorySize, hasRotation);
	}

	/**
	 * has currently a redstone signal?
	 */
	protected boolean hasSignal=false;
	
	public void onNeighborBlockChange(){
		if (!this.world.isRemote){
			int signal_str = this.world.isBlockIndirectlyGettingPowered(this.pos);
			boolean signal = signal_str>0;
			if(signal!=hasSignal){
				hasSignal=signal;
				this.needUpdate();
			}
		}
	}
	
	/**
	 * Returns if this tileent should operate depending on current redstone signal and setting
	 */
	public boolean isRedstoneEnabled(){
		return this.redstoneBehaviour==0 || (this.hasSignal && this.redstoneBehaviour==1) || (!this.hasSignal && this.redstoneBehaviour==2);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.hasSignal = tags.getBoolean("hasSignal");
		this.redstoneBehaviour = tags.getByte("redstoneBehaviour");
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setBoolean("hasSignal", this.hasSignal);
		tags.setByte("redstoneBehaviour", this.redstoneBehaviour);
	}
	

	@SideOnly(Side.CLIENT)
	public String getRedstoneModeText() {
		switch (this.redstoneBehaviour) {
		case 0:
			return "techguns.redstonemode.ignore";
		case 1:
			return "techguns.redstonemode.high";
		case 2:
			return "techguns.redstonemode.low";
		}
		return "invalid";
	}

	@SideOnly(Side.CLIENT)
	public String getSignalStateText() {
		if (this.hasSignal) {
			return "techguns.redstonemode.signal.high";
		} else {
			return "techguns.redstonemode.signal.low";
		}
	}
	
	/**
	 * 0-ignore
	 * 1-high
	 * 2-low
	 */
	public byte getRedstoneBehaviour() {
		return redstoneBehaviour;
	}

	public void setRedstoneBehaviour(byte redstoneBehaviour) {
		this.redstoneBehaviour = redstoneBehaviour;
	}
	
	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		if ((id==BUTTON_ID_REDSTONE) && (this.isUseableByPlayer(ply))){
			this.redstoneBehaviour++;
			this.redstoneBehaviour=(byte) (this.redstoneBehaviour%3);
			if(!this.world.isRemote){
				this.needUpdate();
			}
		} 
		super.buttonClicked(id, ply, data);
	}
}
