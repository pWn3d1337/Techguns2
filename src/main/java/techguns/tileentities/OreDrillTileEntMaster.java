package techguns.tileentities;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGConfig;
import techguns.TGFluids;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.TGOreClusters.OreCluster;
import techguns.Techguns;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.blocks.BlockOreCluster;
import techguns.blocks.EnumOreClusterType;
import techguns.blocks.machines.multiblocks.OreDrillDefinition;
import techguns.tileentities.ChemLabTileEnt.ChemLabFluidHandler;
import techguns.tileentities.operation.FluidTankPlus;
import techguns.tileentities.operation.ITileEntityFluidTanks;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.tileentities.operation.MachineOperation;
import techguns.util.InventoryUtil;
import techguns.items.GenericItemShared;

public class OreDrillTileEntMaster extends MultiBlockMachineTileEntMaster implements ITileEntityFluidTanks{

	protected static final float SOUND_VOLUME=0.5f;
	
	protected int engines;
	protected int rods;
	protected int radius;
	protected EnumFacing drill_direction;
	protected boolean hasPower=false;
	protected boolean hasDrill=false;
	protected int drillType=0;
	
	//amount of internalFuel
	protected int currentFuelBufferMax=0;
	protected int fuelBuffer=0;
	
	protected int soundLoopDelay=0;
	
	public ItemStack lastDrill=ItemStack.EMPTY;
	
	protected static final double useRangeSquared=128.8d;
	
	public static final int CAPACITY_INPUT_TANK=16*Fluid.BUCKET_VOLUME;
	public static final int CAPACITY_OUTPUT_TANK=32*Fluid.BUCKET_VOLUME;
	
	public FluidTank inputTank;
	public FluidTank outputTank;
	
	public static final int SLOT_DRILL=0;
	public static final int SLOT_FURNACE=1;
	public static final int SLOT_OUTPUT1=2;
	public static final int SLOT_OUTPUT_COUNT=9;
	
	public OreDrillTileEntMaster() {
		super(11, 500000);
	
		this.inventory = new ItemStackHandlerPlus(11) {

			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				setContentsChanged(true);
			}

			@Override
			protected boolean allowItemInSlot(int slot, ItemStack stack) {
				if(slot<=SLOT_FURNACE) {
					return isItemValidForSlot(slot, stack);
				} else {
					return false; //no inputing to output slots
				}
			}
			
			@Override
			protected boolean allowExtractFromSlot(int slot, int amount) {
				return slot >= SLOT_OUTPUT1 && slot < SLOT_OUTPUT1+SLOT_OUTPUT_COUNT;
			}
		};
		
		
		this.hasDrill=false;
		
		this.inputTank = new FluidTankPlus(this,CAPACITY_INPUT_TANK);
		this.inputTank.setTileEntity(this);
		
		this.outputTank = new FluidTankPlus(this,CAPACITY_OUTPUT_TANK);
		this.outputTank.setTileEntity(this);
	}

	protected boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(stack.isEmpty()) return false;
		if (slot==SLOT_DRILL && stack.getItem() instanceof ITGSpecialSlot) {
			TGSlotType slotType = ((ITGSpecialSlot)stack.getItem()).getSlot(stack);
			return slotType == TGSlotType.DRILL_SMALL || slotType == TGSlotType.DRILL_MEDIUM || slotType == TGSlotType.DRILL_LARGE;
		} else if (slot==SLOT_FURNACE) {
			return TileEntityFurnace.getItemBurnTime(stack)>0;
		}
		return false;
	}

	public void formOreDrill(EnumFacing direction, int engines, int rods, int radius, EnumFacing drill_direction ) {
		this.engines= engines;
		this.rods=rods;
		this.radius=radius;
		this.drill_direction=drill_direction;
		
		super.form(direction);
	}
	
	public void setFuelBuffer(int fuelBuffer) {
		this.fuelBuffer = fuelBuffer;
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
		return hasDrill;
	}
	
	/**
	 * Equipped Drilltype
	 * 0-no drill
	 * 1-steel
	 * 2-obsidian
	 * 3-carbon
	 * @return
	 */
	protected int getDrillHeadType() {
		int mininglevel = this.getDrillItemMiningLevel();
		if(mininglevel<0) return 0;
		return mininglevel;
	}
	
	public int getDrillType() {
		return drillType;
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
	
	/**
	 * The "radius" of the drill including the middle block, so 1 for 1x1xN, 2 for 3x3xN. 3 for 5x5xN and 4 for 7x7xN
	 * 1x1x1 drill with only controller has radius 0
	 * @return
	 */
	public int getDrillRadius() {
		if(this.engines==0) return 0;
		return radius+1;
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
		
		this.fuelBuffer=tags.getInteger("fuelBuffer");
		this.currentFuelBufferMax = tags.getInteger("fuelBufferMax");
		
		this.loadTanksFromNBT(tags);
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
		
		tags.setInteger("fuelBuffer", this.fuelBuffer);
		tags.setInteger("fuelBufferMax", this.currentFuelBufferMax);
		
		this.saveTanksToNBT(tags);
	}

	@Override
	public AxisAlignedBB getBBforSlave(BlockPos slavePos) {
		return Block.FULL_BLOCK_AABB;
	}

	@Override
	protected int getNeededPower() {
		if(this.currentOperation!=null) {
			return this.currentOperation.getPowerPerTick();
		}
		return 0;
	}

	
	
	@Override
	protected boolean consumePower(int amount) {
		if ( TGConfig.machinesNeedNoPower){
			return true;
		}
		
		float FACTOR=TGConfig.oreDrillMultiplierFuel;
		
		//check fuel buffer
		if (this.fuelBuffer*FACTOR>=amount){
			this.fuelBuffer -= Math.max(amount/FACTOR,1);
			return true;
		} else {
			amount -=this.fuelBuffer*FACTOR;
			this.fuelBuffer=0;
			this.currentFuelBufferMax=0;
			//check fuelBuffer refill;
			
			int fuelValue = 0;
			if (!this.inventory.getStackInSlot(SLOT_FURNACE).isEmpty()){
				fuelValue = TileEntityFurnace.getItemBurnTime(this.inventory.getStackInSlot(SLOT_FURNACE));
			}
			
			//	if (FluidContainerRegistry.isContainer(this.content[10])){
			if (fuelValue > 0){
				ItemStack container = this.inventory.getStackInSlot(SLOT_FURNACE).getItem().getContainerItem(this.inventory.getStackInSlot(SLOT_FURNACE));//FluidContainerRegistry.drainFluidContainer(this.content[10]);
			
				if (!container.isEmpty()){
					//int leftover = Techguns.addItemToInventory(this.content,container, 1, 10);
					int leftover = InventoryUtil.addItemToInventory(inventory, container, SLOT_OUTPUT1, SLOT_OUTPUT1+SLOT_OUTPUT_COUNT);
					//if (!this.worldObj.isRemote){
					if (leftover >0 && !this.world.isRemote){
						ItemStack entityItem = container.copy();
						entityItem.setCount(leftover);
						this.world.spawnEntity(new EntityItem(this.world, this.pos.getX()+0.5, this.pos.getY()+0.5, this.pos.getZ()+0.5, entityItem));
					}
				}
				
				//consume 1 fuel
				this.inventory.extractItem(SLOT_FURNACE, 1, false);
				
				this.fuelBuffer=fuelValue;
				this.currentFuelBufferMax=fuelValue;
			//System.out.println("Fuel Value:"+fuelValue);
			} else {
				
				//check input tank
				float fuelvalue = this.getFuelValueFromInputTank();
				if (fuelvalue >0){
					
					FluidStack drainedFluid = this.inputTank.drain(Fluid.BUCKET_VOLUME, true);
					this.fuelBuffer=(int) (drainedFluid.amount*fuelvalue);					
					this.currentFuelBufferMax = (int) (drainedFluid.amount*fuelvalue);
					
				}
			}
		}
		
		if (this.fuelBuffer*FACTOR>=amount){
			this.fuelBuffer -= Math.max(amount/FACTOR,1);
			return true;
		}
		
		if (amount >0 && this.energy.getEnergyStored()>=amount){
			this.energy.extractEnergy(amount, false);
			return true;
		} else if (amount==0){
				return true;
		} else {
			return false;
		}
	}

	protected float getFuelValueFromInputTank(){
		if (this.inputTank.getFluid()!=null){
			
			if (this.inputTank.getFluid().getFluid() == TGFluids.LAVA){
				return 20F; //Same as LAVA BUCKET /1000
			} /*else if (this.inputTank.getFluid().getFluid() == TGFluids.FUEL){
				return TGConfig.oreDrillFuelValueFuel;
			} else if (this.inputTank.getFluid().getFluid() == TGFluids.BIOFUEL){
				return TGConfig.oreDrillFuelValueBioFuel;
			} */
			else if (TGFluids.fuels.contains(this.inputTank.getFluid().getFluid())){
				return TGConfig.oreDrillFuelValueFuel;
			}
		}
		
		return 0F;
	}
	
	protected boolean checkDrill(){
		if(this.world.getTotalWorldTime()%20==0){
			if (this.lastDrill.isEmpty() && this.inventory.getStackInSlot(SLOT_DRILL).isEmpty()) {
				//this.complete=false;
				this.hasDrill=false;
				this.needUpdate();
				return false;
			} else if (this.lastDrill.isEmpty()){
				this.lastDrill=this.inventory.getStackInSlot(SLOT_DRILL);
				return true;
			} else {
				if(this.inventory.getStackInSlot(SLOT_DRILL).isEmpty() || !this.lastDrill.isItemEqual(this.inventory.getStackInSlot(SLOT_DRILL))){
					//System.out.println("Drill no longer matches");
					this.lastDrill=this.inventory.getStackInSlot(SLOT_DRILL);
					this.currentOperation=null;
					this.progress=0;
					this.totaltime=0;
					this.needUpdate();
					return false;
				}
				
			}
		}
		return true;
	}
	
	@Override
	public void update() {
		if (this.isRedstoneEnabled()){
			if (this.currentOperation != null) {

				if (this.world.isRemote||this.checkDrill()) { //check drill on server
					if(this.consumePower(this.getNeededPower()*this.currentOperation.getStackMultiplier())) {
						this.progress++;
	
						playAmbientSound();
	
						if(!this.world.isRemote && !this.hasPower) {
							this.hasPower=true;
							this.needUpdate();
						}
						
						if (progress >= totaltime) {
	
							if (!this.world.isRemote) {
								this.finishedOperation();
							}
							this.progress = 0;
							this.totaltime = 0;
							this.currentOperation = null;
	
							if (!this.world.isRemote){
								checkAndStartOperation();
								this.needUpdate();
							}
						}
					} else {
						//power consume failed
						if(!this.world.isRemote && this.hasPower) {
							this.hasPower=false;
							this.needUpdate();
						}
					}
				}

			}  else {

				if(!this.world.isRemote && this.contentsChanged) {
					//System.out.println("CHECK AND START OPERATION");
					checkAndStartOperation();
				}

			}
		}
		
	}
	

	@Override
	protected void checkAndStartOperation() {
		if (this.isFormed()) {
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
							int effectivemininglevel = drillheadmininglevel+this.getDrillRadius()-cluster.getMininglevel();
							this.drillType = this.getDrillHeadType();
							
							if (effectivemininglevel >=0){
	
								int clustersize = this.getClusterSize(0, targetPos, new ArrayList<BlockPos>(), cluster, bs);
								int effectiveclustersize = Math.min(clustersize, this.getTotalDrillLength());
						
								double orePerHour = ((effectiveclustersize*3)+(effectiveclustersize*effectivemininglevel*0.5))*TGConfig.oreDrillMultiplierOres*cluster.getMultiplier_amount();
								
								this.currentOperation= cluster.getNewOperation(this.world, orePerHour,this.getDrillRadius());
								this.progress=0;
								
								
								this.totaltime=(int)(60.0/orePerHour*1200.0);
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
	}

	public int getTotalDrillLength(){
		return this.engines+this.rods;
	}
	
	public int getDrillItemMiningLevel() {
		ItemStack drill = this.inventory.getStackInSlot(SLOT_DRILL);
		if(!drill.isEmpty() && this.isFormed()){
			
			//ugly code
			
			int mininglevel=-1;
			if(this.getDrillRadius()==0){
				if (drill.getItem()==TGItems.SHARED_ITEM){
				
					if(drill.getItemDamage()==TGItems.OREDRILLHEAD_STEEL.getItemDamage()){
						mininglevel=1;
					} else if (drill.getItemDamage()==TGItems.OREDRILLHEAD_OBSIDIANSTEEL.getItemDamage()){
						mininglevel=2;
					} else if (drill.getItemDamage()==TGItems.OREDRILLHEAD_CARBON.getItemDamage()){
						mininglevel=3;
					}	
				}
			} else if (this.getDrillRadius()==1 || this.getDrillRadius()==2){
				if (drill.getItem()==TGItems.SHARED_ITEM){
					
					if(drill.getItemDamage()==TGItems.OREDRILLHEAD_MEDIUM_STEEL.getItemDamage()){
						mininglevel=1;
					} else if (drill.getItemDamage()==TGItems.OREDRILLHEAD_MEDIUM_OBSIDIANSTEEL.getItemDamage()){
						mininglevel=2;
					} else if (drill.getItemDamage()==TGItems.OREDRILLHEAD_MEDIUM_CARBON.getItemDamage()){
						mininglevel=3;
					}	
				}
			} else if (this.getDrillRadius()==3 || this.getDrillRadius()==4){
				if (drill.getItem()==TGItems.SHARED_ITEM){
					
					if(drill.getItemDamage()==TGItems.OREDRILLHEAD_LARGE_STEEL.getItemDamage()){
						mininglevel=1;
					} else if (drill.getItemDamage()==TGItems.OREDRILLHEAD_LARGE_OBSIDIANSTEEL.getItemDamage()){
						mininglevel=2;
					} else if (drill.getItemDamage()==TGItems.OREDRILLHEAD_LARGE_CARBON.getItemDamage()){
						mininglevel=3;
					}	
				}
			}
			
			return mininglevel;
		}
		
		return -1;
	}

	@Override
	protected void finishedOperation() {
		ItemStack output = this.currentOperation.getItemOutput0();
		if (!output.isEmpty()) {
			
			int leftover = output.getCount();
			int s  =SLOT_OUTPUT1;
			ItemStack out = ItemStack.EMPTY;
			while(leftover>0&& s<SLOT_OUTPUT1+SLOT_OUTPUT_COUNT) {
				out = this.inventory.insertItemNoCheck(s, output, false);
				if(!out.isEmpty()) {
					leftover=out.getCount();
					if(leftover>0) {
						output=out;
						s++;
					}
				} else {
					leftover=0;
				}
			}
			
			if(leftover>0) {
				this.world.spawnEntity(new EntityItem(this.world, this.pos.getX()+0.5d, this.pos.getY()+0.5d, this.pos.getZ()+0.5d, out));
			}
			
		}
		
		FluidStack fluidOut = this.currentOperation.getFluidOutput0();
		if(fluidOut!=null) {
			this.outputTank.fillInternal(fluidOut, true);
		}

	}

	@Override
	protected void playAmbientSound() {
		if (soundLoopDelay-- <= 0) {
			if (this.radius == 0) {
				world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), TGSounds.ORE_DRILL_WORK_SMALL, SoundCategory.BLOCKS, SOUND_VOLUME, 1.0F, true );		
				//worldplaySound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.oredrillSmallWork", 1.5F, 1.0F, true );
				soundLoopDelay = 61; //62 ticks = 3.1 sec
			}else if (this.radius <= 2) {
				//world.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.oredrillMediumWork", 2.25F, 1.0F, true );
				world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), TGSounds.ORE_DRILL_WORK_MEDIUM, SoundCategory.BLOCKS, SOUND_VOLUME, 1.0F, true );			
				soundLoopDelay = 61; //62 ticks = 3.1 sec
			}else {
				//world.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.oredrillLargeWork", 3.0F, 1.0F, true );
				world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), TGSounds.ORE_DRILL_WORK_LARGE, SoundCategory.BLOCKS, SOUND_VOLUME, 1.0F, true );		
				soundLoopDelay = 61; //62 ticks = 3.1 sec
			}
		}
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
	
	public int getCurrentFuelBufferMax() {
		return currentFuelBufferMax;
	}

	public void setCurrentFuelBufferMax(int currentFuelBufferMax) {
		this.currentFuelBufferMax = currentFuelBufferMax;
	}
	
    @SideOnly(Side.CLIENT)
    public int getFuelBufferScaled(int limit)
    {
    	if (this.currentFuelBufferMax>0){
    		return this.fuelBuffer * limit / this.currentFuelBufferMax;
    	} else {
    		return 0;
    	}
    }
    
	public int getFuelBuffer() {
		return fuelBuffer;
	}

	@Override
	public void saveTanksToNBT(NBTTagCompound tags) {
		NBTTagCompound inputTankTags = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankTags);
		tags.setTag("inputTank", inputTankTags);
		
		NBTTagCompound outputTankTags = new NBTTagCompound();
		this.outputTank.writeToNBT(outputTankTags);
		tags.setTag("outputTank", outputTankTags);
	}

	@Override
	public void loadTanksFromNBT(NBTTagCompound tags) {
		NBTTagCompound inputTank = tags.getCompoundTag("inputTank");
		this.inputTank.readFromNBT(inputTank);
		
		NBTTagCompound outputTank = tags.getCompoundTag("outputTank");
		this.outputTank.readFromNBT(outputTank);
	}

	public int getPowerPerTick() {
		if(this.currentOperation!=null) {
			return this.currentOperation.getPowerPerTick();
		}
		return 0;
	}

	public int getValidSlotForItemInMachine(ItemStack stack) {
		if(this.isItemValidForSlot(SLOT_DRILL, stack)) return SLOT_DRILL;
		if(this.isItemValidForSlot(SLOT_FURNACE, lastDrill)) return SLOT_FURNACE;
		return -1;
	}

}
