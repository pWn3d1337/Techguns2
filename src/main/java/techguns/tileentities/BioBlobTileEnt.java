package techguns.tileentities;

import java.util.UUID;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.api.machines.ITGTileEntSecurity;
import techguns.damagesystem.TGDamageSource;
import techguns.damagesystem.TGExplosion;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.packets.PacketSpawnParticle;

public class BioBlobTileEnt extends TileEntity implements ITGTileEntSecurity, ITickable {

	protected UUID owner;
	
	protected static final int NUMTICKS=60;
	protected int ticks=NUMTICKS;
	
	public byte size =1;
	
	@Override
	public void setOwner(EntityPlayer ply) {
		UUID id = ply.getGameProfile().getId();
		if (id != null) {
			this.owner = id;
		}
	}

	@Override
	public boolean isOwnedByPlayer(EntityPlayer ply) {
		if (this.owner == null) {
			return false;
		}
		return this.owner.equals(ply.getGameProfile().getId());
	}

	@Override
	public UUID getOwner() {
		return owner;
	}

	@Override
	public byte getSecurity() {
		return 0;
	}

	/**
	 * Return the bioblob size [0-2]
	 * @return
	 */
	public int getBlobSize(){
		return size-1;
	}
	
	@Override
	public void update() {
		if(!this.world.isRemote) {
			this.ticks--;
			if (ticks <=0){
			
				if (this.size>1){
					size--;
					this.ticks = NUMTICKS;
					
					this.needUpdate();
					
				} else {
					//remove blob
					if(!this.world.isRemote) {
						this.world.setBlockState(this.pos,Blocks.AIR.getDefaultState());
					}
				}
				
			}
		}
	}
	
	public void needUpdate(){
		if(!this.world.isRemote) {	
			this.world.markBlockRangeForRenderUpdate(getPos(), getPos());
			ChunkPos cp = this.world.getChunkFromBlockCoords(getPos()).getPos();
			PlayerChunkMapEntry entry = ((WorldServer)this.world).getPlayerChunkMap().getEntry(cp.x, cp.z);
			if (entry!=null) {
				entry.sendPacket(this.getUpdatePacket());
			}
			this.markDirty();
		}
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tags = new NBTTagCompound();
		this.writeToNBT(tags);
		return new SPacketUpdateTileEntity(pos, 1, tags);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tags= super.getUpdateTag();
		this.writeToNBT(tags);
		return tags;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		this.readFromNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		byte oldSize = size;
		ticks=tags.getInteger("BlobTicks");
		size= tags.getByte("size");
		if (this.world!=null && this.world.isRemote && size!=oldSize){
			this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound tags= super.writeToNBT(compound);
		tags.setInteger("BlobTicks", ticks);
		tags.setByte("size", size);
		return tags;
	}

	public void hitBlob(int power, EntityLivingBase attacker){
		if (!this.world.isRemote) {
			this.size+=power;
			if(size>3){
				size=3;
				//kaboom
				float radius = 3.0f;
				
				TGDamageSource dmgSrc = TGDamageSource.causePoisonDamage(null, attacker, DeathType.BIO);
				dmgSrc.goreChance=1.0f;
				dmgSrc.armorPenetration=0.35f;
				
				TGExplosion explosion = new TGExplosion(world, attacker, null, this.pos.getX()+0.5, this.pos.getY()+0.5, this.pos.getZ()+0.5, 30, 15, radius, radius*1.5f,0.0f);
				explosion.setDmgSrc(dmgSrc);
				//	explosion.setDamageSource(dmgSrc);
			//	explosion.setExplosionSound("techguns:effects.biodeath");
				
				this.world.setBlockState(this.getPos(),Blocks.AIR.getDefaultState());
				//explosion.doExplosion(false, attacker);
				explosion.doExplosion(false);
				this.world.playSound((EntityPlayer)null, this.pos, TGSounds.DEATH_BIO, SoundCategory.BLOCKS, 4.0F, 1.0F);   
				
				if(!this.world.isRemote){
					TGPackets.network.sendToAllAround(new PacketSpawnParticle("bioblobExplosion", this.pos.getX()+0.5,this.pos.getY()+0.5, this.pos.getZ()+0.5), new TargetPoint(this.world.provider.getDimension(), this.pos.getX()+0.5,this.pos.getY()+0.5, this.pos.getZ()+0.5, 50.0D));
				}
				
			}
			this.ticks=NUMTICKS;
			this.needUpdate();
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return (oldState.getBlock()!=newState.getBlock());
	}
}
