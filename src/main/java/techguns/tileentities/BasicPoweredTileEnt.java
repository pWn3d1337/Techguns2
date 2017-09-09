package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import techguns.TGConfig;
import static techguns.gui.ButtonConstants.*;

public abstract class BasicPoweredTileEnt extends BasicOwnedTileEnt {
	
	protected EnergyStoragePlus energy;
	
	public BasicPoweredTileEnt(int inventorySize, boolean hasRotation, int maximumPower) {
		super(inventorySize, hasRotation);
		energy = new EnergyStoragePlus(maximumPower);
	}
	
	/**
	 * 0-ignore
	 * 1-high
	 * 2-low
	 */
	protected byte redstoneBehaviour=0;
	
	public EnergyStoragePlus getEnergyStorage() {
		return energy;
	}

	/**
	 * Drain amount power from internal storage, return if enough power left
	 * @param amount
	 * @return
	 */
	protected boolean consumePower(int amount){
		if ( TGConfig.machinesNeedNoPower){
			return true;
		}
		if (this.energy.extractEnergy(amount, false)>=amount){
			return true;
		} else {
			return false;
		}
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
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? (T)energy : super.getCapability(capability, facing);
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
	
	public byte getRedstoneBehaviour() {
		return redstoneBehaviour;
	}

	public void setRedstoneBehaviour(byte redstoneBehaviour) {
		this.redstoneBehaviour = redstoneBehaviour;
	}
}
