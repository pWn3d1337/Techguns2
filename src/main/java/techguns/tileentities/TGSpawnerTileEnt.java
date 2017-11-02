package techguns.tileentities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import techguns.blocks.machines.BasicMachine;
import techguns.capabilities.TGGenericNPCData;
import techguns.entities.npcs.GenericNPC;
import techguns.entities.npcs.ZombieSoldier;

public class TGSpawnerTileEnt extends BasicTGTileEntity implements ITickable {

	protected Random rand = new Random();
	protected int delay=200;
	protected int spawndelay = 200;
	protected int mobsLeft=5;
	protected int maxActive=3;
	
	protected static final int retrydelay = 40;
	protected static final double spawnrange=2d;
	
	protected ArrayList<WeightedSpawnerEntity> mobtypes = new ArrayList<>();
	
	protected LinkedList<GenericNPC> activeMobs = new LinkedList<>();
	
	public TGSpawnerTileEnt() {
		super(false);
		//this.addMobType(ZombieSoldier.class, 100);
	}
	
	public void addMobType(Class<? extends GenericNPC> clazz, int weight) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("id", EntityRegistry.getEntry(clazz).getRegistryName().toString());
		
		WeightedSpawnerEntity ent = new WeightedSpawnerEntity(weight, nbt);
		this.mobtypes.add(ent);
	}

	public void despawnedEntity(GenericNPC ent) {
		this.activeMobs.remove(ent);
	}
	
	public void killedEntity(GenericNPC ent) {
		if(this.activeMobs.remove(ent)) {
			this.mobsLeft--;
			this.markDirty();
		}
	}
	
	public void relinkNPC(GenericNPC ent) {
		if(!this.activeMobs.contains(ent)) {
			this.activeMobs.add(ent);
		}
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
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setByte("mobsLeft", (byte) this.mobsLeft);
		compound.setShort("delay", (short) this.delay);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.mobsLeft = compound.getByte("mobsLeft");
		this.delay = compound.getShort("delay");
		super.readFromNBT(compound);
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
	}

	protected boolean hasMobTypes() {
		return this.mobtypes.size()>0;
	}
	
	@Override
	public void update() {
		if (this.world.isRemote) return;
		this.delay--;
		
		if(this.delay<=0 && activeMobs.size() < Math.min(maxActive, mobsLeft) && this.hasMobTypes()) { 
			
		
			WeightedSpawnerEntity entdata = WeightedRandom.<WeightedSpawnerEntity>getRandomItem(this.world.rand, this.mobtypes);
			
			BlockPos blockpos = this.getPos();
			
            double d0 = (double)blockpos.getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * this.spawnrange + 0.5D;
            double d1 = (double)(blockpos.getY() + 1);
            double d2 = (double)blockpos.getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * this.spawnrange + 0.5D;
            Entity entity = AnvilChunkLoader.readWorldEntityPos(entdata.getNbt(), world, d0, d1, d2, false);
            
            if (entity !=null && entity instanceof GenericNPC) {
            	GenericNPC npc = (GenericNPC) entity;

	          //  if (net.minecraftforge.event.ForgeEventFactory.canEntitySpawnSpawner(npc, this.world, (float)entity.posX, (float)entity.posY, (float)entity.posZ))
	          //  {
	            	if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(npc, this.world, (float)entity.posX, (float)entity.posY, (float)entity.posZ)) {
	                    npc.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
	            
		                AnvilChunkLoader.spawnEntity(entity, world);
		                world.playEvent(2004, blockpos, 0);
		
		                npc.spawnExplosionParticle();
		                
		                this.activeMobs.add(npc);
		            	this.delay= this.spawndelay;
		            	TGGenericNPCData dat = TGGenericNPCData.get(npc);
		            	dat.setSpawnerPos(blockpos);
	            	}
	            //}
            	
            } else {
            	
        		this.delay= this.retrydelay;
            
            }
            
		} else {
			if(this.delay<=0) {
				this.delay= retrydelay;
			
        		Iterator<GenericNPC> it = this.activeMobs.iterator();
    			while(it.hasNext()) {
    				GenericNPC npc = it.next();
    				if(!npc.isEntityAlive() || npc.world.provider.getDimension()!= this.world.provider.getDimension()) {
    					it.remove();
    				}
    			}
			}
		}
		
		
		if(this.mobsLeft<=0) {
			if(!this.world.isRemote) {
				this.world.setBlockToAir(this.getPos());
			}
		}

	}

	
	
	
}
