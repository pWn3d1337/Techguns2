package techguns.packets;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.capabilities.TGExtendedPlayer;
import techguns.gui.player.TGPlayerInventory;
import techguns.util.DataUtil;

public class PacketTGExtendedPlayerSync implements IMessage {
	public int entityId;
	public NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(TGPlayerInventory.NUMSLOTS, ItemStack.EMPTY);
	public int radlevel;
	public short foodLeft=0;
	public boolean nightVisionEnabled=false;
	public boolean safemodeEnabled=true;
	public float lastSaturation=0f;
	public boolean hovermodeEnabled=false;
	public byte size;
	public boolean stepAssist;
	public boolean showHUD;
	public boolean enableJetpack;
	private boolean allSlots=false;
	
	public PacketTGExtendedPlayerSync() {
	}
	
	public PacketTGExtendedPlayerSync(EntityPlayer ply, TGExtendedPlayer props,boolean allSlots) {
		super();
		this.entityId=ply.getEntityId();
		this.nightVisionEnabled=props.enableNightVision;
		this.safemodeEnabled=props.enableSafemode;
		this.hovermodeEnabled=props.enableHovermode;
		this.inv = props.tg_inventory.inventory;
	//	this.size=allSlots?(byte)inv.length:(byte)2;
		if (allSlots){
			this.radlevel=props.radlevel;
			this.foodLeft=props.foodleft;
			this.lastSaturation=props.lastSaturation;
			this.stepAssist=props.enableStepAssist;
			this.showHUD=props.showTGHudElements;
			this.enableJetpack=props.enableJetpack;
		}
		this.allSlots=allSlots;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId=buf.readInt();
		
		byte dat = buf.readByte();
		BitSet states = DataUtil.uncompress(dat);
		this.enableJetpack=states.get(0);
		this.nightVisionEnabled=states.get(1);
		this.safemodeEnabled=states.get(2);
		this.stepAssist=states.get(3);
		this.showHUD=states.get(4);
		this.hovermodeEnabled=states.get(5);
		
		this.allSlots=buf.readBoolean();
		if (allSlots) {
			this.radlevel=buf.readInt();
			this.foodLeft=buf.readShort();
			this.lastSaturation=buf.readFloat();
			NBTTagCompound tags = DataUtil.readCompoundTag(buf);
			if(tags!=null) {
				inv.clear();
				ItemStackHelper.loadAllItems(tags, inv);
			}
		} else {
			inv.set(TGPlayerInventory.SLOT_FACE, DataUtil.readItemStack(buf));
			inv.set(TGPlayerInventory.SLOT_BACK, DataUtil.readItemStack(buf));
			inv.set(TGPlayerInventory.SLOT_HAND, DataUtil.readItemStack(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
		byte states = DataUtil.compress(this.enableJetpack,this.nightVisionEnabled,this.safemodeEnabled,this.stepAssist,this.showHUD,this.hovermodeEnabled);
		buf.writeByte(states);
		buf.writeBoolean(this.allSlots);
		if(allSlots) {
			buf.writeInt(radlevel);
			buf.writeShort(foodLeft);
			buf.writeFloat(lastSaturation);
			NBTTagCompound tags = new NBTTagCompound();
			ItemStackHelper.saveAllItems(tags, inv, false);
			DataUtil.writeCompoundTag(buf, tags);
		} else {
			DataUtil.writeItemStack(buf, inv.get(TGPlayerInventory.SLOT_FACE));
			DataUtil.writeItemStack(buf, inv.get(TGPlayerInventory.SLOT_BACK));
			DataUtil.writeItemStack(buf, inv.get(TGPlayerInventory.SLOT_HAND));
		}
	}
	
	public static class Handler implements IMessageHandler<PacketTGExtendedPlayerSync, IMessage> {
		@Override
		public IMessage onMessage(PacketTGExtendedPlayerSync message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketTGExtendedPlayerSync message, MessageContext ctx) {
			EntityPlayer local_ply = TGPackets.getPlayerFromContext(ctx);
			
			Entity ent = local_ply.world.getEntityByID(message.entityId);
			
			if (ent !=null && ent instanceof EntityPlayer){
				EntityPlayer ply = (EntityPlayer) ent;

				TGExtendedPlayer props= TGExtendedPlayer.get(ply);
				
				props.radlevel=message.radlevel;
				props.lastSaturation=message.lastSaturation;
				props.foodleft=message.foodLeft;
				props.enableNightVision=message.nightVisionEnabled;
				props.enableSafemode=message.safemodeEnabled;
				props.enableHovermode=message.hovermodeEnabled;
				props.enableStepAssist=message.stepAssist;
				props.showTGHudElements = message.showHUD;
				props.enableJetpack = message.enableJetpack;
				
				int max = message.allSlots ? message.inv.size() : 3;
				for (int i=0;i<max;i++) {
					props.tg_inventory.inventory.set(i, message.inv.get(i));
				}
			}
		}
	}
}
