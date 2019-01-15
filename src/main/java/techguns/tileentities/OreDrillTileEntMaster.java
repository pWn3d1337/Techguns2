package techguns.tileentities;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import techguns.TGConfig;
import techguns.TGItems;
import techguns.TGOreClusters.OreCluster;
import techguns.Techguns;
import techguns.blocks.BlockOreCluster;
import techguns.blocks.EnumOreClusterType;
import techguns.blocks.machines.multiblocks.OreDrillDefinition;
import techguns.tileentities.ChemLabTileEnt.ChemLabFluidHandler;
import techguns.tileentities.operation.FluidTankPlus;
import techguns.tileentities.operation.MachineOperation;

public class OreDrillTileEntMaster extends MultiBlockMachineTileEntMaster {

	protected int engines;
	protected int rods;
	protected int radius;
	protected EnumFacing drill_direction;
	protected boolean hasPower=false;
	protected boolean hasDrill=false;
	protected int drillType=0;
	
	protected static final double useRangeSquared=128.8d;
	
	public static final int CAPACITY_INPUT_TANK=16*Fluid.BUCKET_VOLUME;
	public static final int CAPACITY_OUTPUT_TANK=32*Fluid.BUCKET_VOLUME;
	
	public FluidTank inputTank;
	public FluidTank outputTank;
	
	public OreDrillTileEntMaster() {
		super(11, 500000);
		
		this.hasDrill=false;
		
		this.inputTank = new FluidTankPlus(this,CAPACITY_INPUT_TANK);
		this.inputTank.setTileEntity(this);
		
		this.outputTank = new FluidTankPlus(this,CAPACITY_OUTPUT_TANK);
		this.outputTank.setTileEntity(this);
	}

	public void formOreDrill(EnumFacing direction, int engines, int rods, int radius, EnumFacing drill_direction ) {
		this.engines= engines;
		this.rods=rods;
		this.radius=radius;
		this.drill_direction=drill_direction;
		
		super.form(direction);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(Techguns.MODID+".container.oredrill", new Object[0]);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(this.isFormed()) {
			BlockPos p = this.getPos();
			BlockPos rod_end = this.getPos().offset(drill_direction, rods+engines);
			
			BlockPos other = rod_end.offset(OreDrillDefinition.getSide2(drill_direction), +radius);
			BlockPos first = p.offset(OreDrillDefinition.getSide1(drill_direction), -radius);
			return new AxisAlignedBB(first,other);
		} else {
			return super.getRenderBoundingBox();
		}
	}
	
	/**
	 * If currently a drill item is in the slot
	 * @return
	 */
	public boolean hasDrill() {
		return false;
	}
	
	/**
	 * Equipped Drilltype
	 * 0-no drill
	 * 1-steel
	 * 2-obsidian
	 * 3-carbon
	 * @return
	 */
	public int getDrillType() {
		return this.drillType;
	}
	
	public boolean hasPower() {
		return hasPower;
	}

	/**
	 * Not used for ore drills
	 */
	@Deprecated
	@Override
	public EnumFacing getMultiblockDirection() {
		return EnumFacing.NORTH;
	}

	/**
	 * use formOreDrill to form an ore drill
	 */
	@Deprecated
	@Override
	public void form(EnumFacing facing) {
		super.form(facing);
	}

	@Override
	public void unform() {
		super.unform();
		this.engines=0;
		this.rods=0;
		this.radius=0;
		this.drill_direction=null;
	}

	public int getEngines() {
		return engines;
	}

	public int getRods() {
		return rods;
	}

	public int getRadius() {
		return radius;
	}

	public EnumFacing getDrill_direction() {
		return drill_direction;
	}

	
	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		if(this.formed) {
			this.drill_direction=EnumFacing.getFront(tags.getByte("drill_direction"));
			this.engines= tags.getByte("engines");
			this.rods =tags.getByte("rods");
			this.radius = tags.getByte("radius");
			this.hasPower=tags.getBoolean("haspower");
			this.hasDrill=tags.getBoolean("hasDrill");
			this.drillType=tags.getByte("drillType");
		}
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		if (this.formed) {
			tags.setByte("drill_direction", (byte) this.drill_direction.getIndex());
			tags.setByte("engines", (byte) engines);
			tags.setByte("rods", (byte) rods);
			tags.setByte("radius", (byte) radius);
			tags.setBoolean("haspower", this.hasPower);
			tags.setBoolean("hasDrill", this.hasDrill);
			tags.setByte("drillType", (byte)this.drillType);
		}
	}

	@Override
	public AxisAlignedBB getBBforSlave(BlockPos slavePos) {
		return Block.FULL_BLOCK_AABB;
	}

	@Override
	protected int getNeededPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void checkAndStartOperation() {
		this.contentsChanged=false;
		//look for cluster
				int distance = this.rods+this.engines+1;
				BlockPos targetPos = this.getPos().offset(drill_direction, distance);

				if (OreDrillDefinition.isOreCluster(this.world, targetPos))
				{
					
					//TODO unhardcorde to more than 1 ore cluster block type
					IBlockState bs = this.world.getBlockState(targetPos);
					BlockOreCluster clusterblock = (BlockOreCluster) bs.getBlock();
					EnumOreClusterType type = (EnumOreClusterType) bs.getValue(clusterblock.TYPE);
					
					OreCluster cluster = Techguns.orecluster.getClusterForType(type);
					
					int drillheadmininglevel = this.getDrillItemMiningLevel();
					if (drillheadmininglevel >0){
						this.hasDrill=true;
						int effectivemininglevel = drillheadmininglevel+this.radius-cluster.getMininglevel();
						this.drillType = this.getDrillType();
						
						if (effectivemininglevel >=0){
							//int clustersize = this.getClusterSize(0, targetPos[0],targetPos[1],targetPos[2], new ArrayList<BlockCoords>(), cluster,meta);
							int clustersize = this.getClusterSize(0, targetPos, new ArrayList<BlockPos>(), cluster, bs);
							int effectiveclustersize = Math.min(clustersize, this.getTotalDrillLength());
					
							double orePerHour = ((effectiveclustersize*3)+(effectiveclustersize*effectivemininglevel*0.5))*TGConfig.oreDrillMultiplierOres*cluster.getMultiplier_amount();
							
							this.currentOperation= cluster.getNewOperation(this.world, orePerHour);
							this.progress=0;
							
							
							this.totaltime=(int)(60.0f/orePerHour*1200.0);
							//moved to operation
							//this.currentRFperTick=(int)(80*orePerHour*Math.max(this.radius, 1)*TGConfig.oreDrillMultiplierPower*cluster.getMultiplier_power());
						} else {
							this.currentOperation=cluster.getCobbleStoneOperation();

							this.progress=0;
							this.totaltime=20*20;
							//this.currentRFperTick=(int) (240*TGConfig.oreDrillMultiplierPower); moved to operation
							
						}
					} else {
						//no drillhead, broken
						this.hasDrill=false;
						this.drillType = 0;
					}
					this.needUpdate();
					
					
					
				} else {
					//System.out.println("There is no ore Cluster at drill point, multiblock broken");
					this.unform();
					this.formed=false;
					this.needUpdate();
				}

	}

	public int getTotalDrillLength(){
		return this.engines+this.rods;
	}
	
	public int getDrillItemMiningLevel() {
		return 0;
	}

	@Override
	protected void finishedOperation() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void playAmbientSound() {
		// TODO Auto-generated method stub

	}
	
	public int getClusterSize(int count,BlockPos startPos, ArrayList<BlockPos> visited, OreCluster clusterType, IBlockState clusterMeta){

		IBlockState targetState = this.world.getBlockState(startPos);

		if (targetState==clusterMeta && !visited.contains(startPos)){
			count++;
			visited.add(startPos);
			
			count=getClusterSize(count,startPos.add(1, 0, 0), visited,clusterType,clusterMeta);
			count=getClusterSize(count,startPos.add(-1, 0, 0), visited,clusterType,clusterMeta);
			count=getClusterSize(count,startPos.add(0, 1, 0), visited,clusterType,clusterMeta);
			count=getClusterSize(count,startPos.add(0, -1, 0), visited,clusterType,clusterMeta);
			count=getClusterSize(count,startPos.add(0, 0, 1), visited,clusterType,clusterMeta);
			count=getClusterSize(count,startPos.add(0, 0, -1), visited,clusterType,clusterMeta);
			
			return count;

		} else {
			return count;
		}
	}
	
}
