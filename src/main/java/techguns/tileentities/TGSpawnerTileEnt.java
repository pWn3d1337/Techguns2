package techguns.tileentities;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.ITickable;
import techguns.blocks.machines.BasicMachine;

public class TGSpawnerTileEnt extends BasicTGTileEntity implements ITickable {

	protected Random rand = new Random();
	protected ArrayList<String> spawnTypes = new ArrayList<String>();
	protected int delay=200;
	protected int maxDelay = 200;
	protected int mobsLeft=5;
	
	
	public TGSpawnerTileEnt() {
		super(false);
	}

	@Override
	public boolean canBeWrenchRotated() {
		return false;
	}

	@Override
	public boolean canBeWrenchDismantled() {
		return false;
	}

	@Override
	public void update() {

		this.delay--;
		
		if(this.delay<=0) {
			//check spawn, reset delay;
			
			
			
			this.delay = this.maxDelay;
		}
		
		if(this.mobsLeft<=0) {
			if(!this.world.isRemote) {
				this.world.setBlockToAir(this.getPos());
			}
		}

	}

	
	
	
}
