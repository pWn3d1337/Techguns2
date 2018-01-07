package techguns.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import techguns.TGConfig;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.blocks.machines.BlockExplosiveCharge;
import techguns.packets.PacketSpawnParticle;

public class ExplosiveChargeTileEnt extends BasicOwnedTileEnt implements ITickable {

	public ExplosiveChargeTileEnt() {
		super(0,false);
	}
	
	protected short maxBlastradius=3;
	protected short minBlastradius=3;
	protected short minBlastLength=2;
	protected short maxBlastLength=5;
	protected short minFuseTime=60;
	protected short maxFuseTime=200;
	
	protected short blastradius=3;
	protected short blastlength=3;	
	
	protected short fusetime=100;
	protected boolean armed=false;
		
	public SoundEvent getSoundPlant() {
		return TGSounds.TNT_PLANT;
	}
	
	public SoundEvent getTickSound() {
		return TGSounds.TNT_TICK;
	}
	
	public SoundEvent getInitSound() {
		return TGSounds.TNT_INIT;
	}

	protected int getBlastRadiusForCuboid(){
		return this.blastradius/2;
	}
	
	protected EnumFacing getOrientation() {
		IBlockState state = this.world.getBlockState(getPos());
		return state.getValue(BlockDirectional.FACING);
	}
	
	
	@Override
	public void update() {
		if(this.armed){
			this.fusetime--;
			
			if (!this.world.isRemote && this.fusetime>0 && (this.fusetime % 20 ==0) ){
				this.world.playSound((EntityPlayer)null,this.pos, getTickSound(),SoundCategory.BLOCKS, 4.0F, 1.0f);
			}
			
			if(this.fusetime<=0){
				
				this.explode();

			}

		}
	}

	protected void explode(){
		
		BlockPos pos = this.getPos();
		if(!this.world.isRemote){
			this.world.playSound((EntityPlayer)null,this.pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
			
			//pos.shift(this.getDirection(),1);
		
			//get the first slice of the explosion volume
			
			EnumFacing facing = this.getOrientation();
			
			EnumFacing side1;
			EnumFacing side2;
			
			if(facing.getAxis() == EnumFacing.Axis.X) {
				side1 = EnumFacing.NORTH;
				side2 = EnumFacing.UP;
			} else if (facing.getAxis() == EnumFacing.Axis.Z) {
				side1 = EnumFacing.EAST;
				side2 = EnumFacing.UP;
			} else {
				side1 = EnumFacing.NORTH;
				side2 = EnumFacing.EAST;
			}
			
			//remove self
			world.setBlockToAir(pos);
			
			int rad = this.getBlastRadiusForCuboid();
			this.getPos().getAllInBoxMutable(pos.offset(side1,rad).offset(side2, rad), pos.offset(side1.getOpposite(),rad).offset(side2.getOpposite(), rad)).forEach(p -> {
				explodeLine(p,facing);
			});

		}
		
	}
	
	protected void explodeLine(MutableBlockPos p, EnumFacing direction){
		MutableBlockPos pos = new MutableBlockPos(p);
		int i =0;
		boolean blocked=false;
		
		while (!blocked && i < this.blastlength){
			pos.move(direction);	
			blocked = explodeBlock(pos);
			i++;
		}		
	}
	
	/**
	 * Returns false when explosion is blocked
	 * @param coords
	 * @return
	 */
	protected boolean explodeBlock(MutableBlockPos coords){
		IBlockState bs = this.world.getBlockState(coords);
		Block block = bs.getBlock();

		if(this.canBreakBlockHardness(bs.getBlockHardness(this.world,coords))){
			
			//post blockBreakEvent
			EntityPlayer ply = this.world.getPlayerEntityByUUID(getOwner());
			if (ply!=null) {
				final BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(this.world,coords,bs,ply);
				
				MinecraftForge.EVENT_BUS.post(breakEvent);
					
				if (!breakEvent.isCanceled()){
					
					block.dropBlockAsItem(world,coords, bs,0);
					world.setBlockToAir(coords);
		
					if (world.rand.nextFloat()>0.5f){
						TGPackets.network.sendToAllAround(new PacketSpawnParticle("MiningChargeBlockExplosion", coords.getX()+0.5d, coords.getY()+0.5d, coords.getZ()+0.5d), TGPackets.targetPointAroundBlockPos(this.world.provider.getDimension(),coords,50));
					}
					
					return false;
				} 
			}
		}
		return true;
	}
	
	protected boolean canBreakBlockHardness(float hardness){
		return hardness >=0 && hardness < TGConfig.explosiveChargeMaxBlockHardness;
	}
	
	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		blastradius=tags.getShort("blastradius");
		blastlength=tags.getShort("blastlength");
		fusetime=tags.getShort("fusetime");
		boolean armedNew = tags.getBoolean("armed");
		if(armedNew!=armed && this.world!=null && this.world.isRemote) {
			this.world.markBlockRangeForRenderUpdate(getPos(), getPos().add(1, 1, 1));
		}
		armed=armedNew;
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setShort("blastradius",blastradius);
		tags.setShort("blastlength",blastlength);
		tags.setShort("fusetime",fusetime);
		tags.setBoolean("armed",armed);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(Techguns.MODID+".inventory.explosivecharge");
	}

	public short getBlastradius() {
		return blastradius;
	}

	public short getBlastlength() {
		return blastlength;
	}

	public short getFusetime() {
		return fusetime;
	}

	public boolean isArmed() {
		return armed;
	}


	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		if (!this.armed){
			if ((id==0) && (this.isUseableByPlayer(ply))){
				this.armed=true;
				if(!this.world.isRemote){
					this.world.playSound(null,this.getPos(), this.getInitSound(), SoundCategory.BLOCKS, 4.0F, 1.0f);
					IBlockState state= world.getBlockState(getPos());
					IBlockState newState = state.withProperty(BlockExplosiveCharge.ARMED, this.armed);
					this.world.notifyBlockUpdate(getPos(), state, newState, 3);
					this.markDirty();
				}
			}else if(id==1){
				if(this.blastradius<this.maxBlastradius){
					this.blastradius++;
					this.needUpdate();
				}
			}
			else if(id==2){
				if(this.blastradius>this.minBlastradius){
					this.blastradius--;
					this.needUpdate();
				}
			}
			
			else if(id==3){
				if(this.blastlength< this.maxBlastLength){
					this.blastlength++;
					this.needUpdate();
				}
			}
			
			else if(id==4){
				if(this.blastlength > this.minBlastLength){
					this.blastlength--;
					this.needUpdate();
				}
			}
		
			else if(id==5){
				if(this.fusetime< this.maxFuseTime){
					this.fusetime+=20;
					this.needUpdate();
				}
			}
			else if(id==6){
				if(this.fusetime> this.minFuseTime){
					this.fusetime-=20;
					this.needUpdate();
				}
			}
		}
	}
	
}