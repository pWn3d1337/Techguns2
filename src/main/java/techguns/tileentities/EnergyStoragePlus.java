package techguns.tileentities;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyStoragePlus extends EnergyStorage {

	public EnergyStoragePlus(int capacity) {
		super(capacity);
	}

	public EnergyStoragePlus(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	public EnergyStoragePlus(int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
	}

	public EnergyStoragePlus(int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
	}
	
	public void setEnergyStored(int value) {
		this.energy=value;
	}

}
