package techguns.packets;

import com.mojang.realmsclient.gui.ChatFormatting;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.keybind.TGKeybindsID;
import techguns.util.TextUtil;

public class PacketShowKeybindConfirmMessage implements IMessage {
	
	byte messageID;
	boolean state;
	
	public PacketShowKeybindConfirmMessage() {
	}
	
	public PacketShowKeybindConfirmMessage(byte messageID, boolean state) {
		super();
		this.messageID = messageID;
		this.state = state;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		messageID=buf.readByte();
		state=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(messageID);
		buf.writeBoolean(state);
	}
	
	
	public static class Handler extends HandlerTemplate<PacketShowKeybindConfirmMessage> {

		private String getLangKeyForID(int id) {
			switch(id) {
				case TGKeybindsID.TOGGLE_NIGHTVISION:
					return TextUtil.trans("techguns.armorTooltip.nightvision");
				case TGKeybindsID.TOGGLE_JETPACK:
					return TextUtil.trans("techguns.msg.enablejetpack");
				case TGKeybindsID.TOGGLE_SAFEMODE:
					return TextUtil.trans("techguns.msg.safemode");
				case TGKeybindsID.TOGGLE_STEP_ASSIST:
					return TextUtil.trans("techguns.armorTooltip.stepassist");
				default:
					return "UNKNOWN";
			}
		}
		
		@Override
		protected void handle(PacketShowKeybindConfirmMessage message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			ply.sendStatusMessage(new TextComponentString(ChatFormatting.YELLOW+
					"[Techguns]: "+ChatFormatting.WHITE+
					TextUtil.trans("techguns.msg.keybindtogglechange")+ChatFormatting.YELLOW+" ["+
					getLangKeyForID(message.messageID)+"] "+ChatFormatting.WHITE+
					TextUtil.trans("techguns.msg.keybindtogglechange2")+
					" "+(message.state?ChatFormatting.GREEN:ChatFormatting.DARK_RED)+"["+TextUtil.trans("techguns.container.info."+(message.state?"on":"off"))+"]"), true);
		}
		
	}
	
}
