package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.server.permission.PermissionAPI;
import techguns.TGConfig;
import techguns.TGPackets;
import techguns.TGPermissions;
import techguns.TGSounds;
import techguns.capabilities.TGExtendedPlayer;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.TGArmorBonus;
import techguns.items.guns.GenericGun;
import techguns.keybind.TGKeybindsID;

public class PacketTGKeybindPress implements IMessage {
	public byte buttonID;
	public EnumHand hand=EnumHand.MAIN_HAND;
	public boolean showMsg=false;
	
	public PacketTGKeybindPress(){
	};
	
	public PacketTGKeybindPress(byte buttonID) {
		super();
		this.buttonID = buttonID;
	}
	
	public PacketTGKeybindPress(byte buttonID, EnumHand hand) {
		this(buttonID);
		this.hand=hand;
	}

	public PacketTGKeybindPress(byte buttonID, boolean showMsg) {
		this(buttonID);
		this.showMsg=showMsg;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		buttonID=buf.readByte();
		byte h=buf.readByte();
		this.hand=EnumHand.values()[h];
		this.showMsg=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(buttonID);
		buf.writeByte((byte)hand.ordinal());
		buf.writeBoolean(showMsg);
	}

	public static class Handler implements  IMessageHandler<PacketTGKeybindPress, IMessage> {

		@Override
		public IMessage onMessage(PacketTGKeybindPress message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PacketTGKeybindPress message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			
			TGExtendedPlayer props= TGExtendedPlayer.get(ply);
			
			//System.out.println("Button Pressed:"+message.buttonID);

			if (message.buttonID==TGKeybindsID.TOGGLE_NIGHTVISION){
				
				props.enableNightVision=!props.enableNightVision;
				if(!props.enableNightVision){
					if(ply.getActivePotionEffect(MobEffects.NIGHT_VISION)!=null){
						ply.removePotionEffect(MobEffects.NIGHT_VISION);
						//ply.world.playSoundAtEntity(ply, TGSounds.NIGHTVISION_OFF, 1.0f, 1.0f);
						ply.world.playSound(null, ply.posX, ply.posY+ply.getEyeHeight(), ply.posZ, TGSounds.NIGHTVISION_OFF, SoundCategory.PLAYERS, 1f, 1f);
						//Techguns.proxy.playSoundOnEntity(ply, TGSounds.NIGHTVISION_OFF, 1f, 1f, false, true, false, TGSoundCategory.PLAYER_EFFECT);
						
					}
				} else if (GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.NIGHTVISION, false)>0.0f){
					//ply.world.playSoundAtEntity(ply, TGSounds.NIGHTVISION_ON, 1.0f, 1.0f);
					ply.world.playSound(null, ply.posX, ply.posY+ply.getEyeHeight(), ply.posZ, TGSounds.NIGHTVISION_ON, SoundCategory.PLAYERS, 1f, 1f);
					
				}
				if(message.showMsg) {
					TGPackets.network.sendTo(new PacketShowKeybindConfirmMessage(message.buttonID, props.enableNightVision), (EntityPlayerMP)ply);
				}
				TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP)ply);
				
			} 
			else if (message.buttonID==TGKeybindsID.TOGGLE_SAFEMODE){
				
				props.enableSafemode=!props.enableSafemode;
				
				if (!props.enableSafemode && !( (!TGConfig.limitUnsafeModeToOP && !PermissionAPI.hasPermission(ply, TGPermissions.ALLOW_UNSAFE_MODE)) || (TGConfig.limitUnsafeModeToOP && isPlayerOp(ply)) )) {
					props.enableSafemode=true;
				} 
				if(message.showMsg) {
					TGPackets.network.sendTo(new PacketShowKeybindConfirmMessage(message.buttonID, props.enableSafemode), (EntityPlayerMP)ply);
				}
				TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP)ply);
			}	
			else if (message.buttonID==TGKeybindsID.TOGGLE_JETPACK){
								
				if (!props.isJumpkeyPressed()){
					props.enableJetpack=!props.enableJetpack;
					TGPackets.network.sendTo(new PacketTGPlayerFieldSync(ply,PacketTGPlayerFieldSync.FIELD_ENABLEJETPACK, props.enableJetpack), (EntityPlayerMP)ply);
					if(message.showMsg) {
						TGPackets.network.sendTo(new PacketShowKeybindConfirmMessage(message.buttonID, props.enableJetpack), (EntityPlayerMP)ply);
					}
					
					if (!props.enableJetpack && props.isJumpkeyPressed()){
						props.setJumpkeyPressed(false);
						TGPackets.network.sendToDimension(new PacketTGPlayerFieldSync(ply, PacketTGPlayerFieldSync.FIELD_JUMPBUTTONPRESSED, false), ply.world.provider.getDimension());
					}
				}
				
			}	
			else if (message.buttonID==TGKeybindsID.JETPACK_BOOST_START){
				
				props.setJumpkeyPressed(true);
				
				//send change to other player
				TGPackets.network.sendToDimension(new PacketTGPlayerFieldSync(ply, PacketTGPlayerFieldSync.FIELD_JUMPBUTTONPRESSED, true), ply.world.provider.getDimension());
				
				
			}
			else if (message.buttonID==TGKeybindsID.JETPACK_BOOST_STOP){
				
				props.setJumpkeyPressed(false);
				TGPackets.network.sendToDimension(new PacketTGPlayerFieldSync(ply, PacketTGPlayerFieldSync.FIELD_JUMPBUTTONPRESSED, false), ply.world.provider.getDimension());
				
			}
			else if (message.buttonID==TGKeybindsID.JETPACK_FORWARD_START){
				
				props.isForwardKeyPressed=true;
				
			}
			else if (message.buttonID==TGKeybindsID.JETPACK_FORWARD_STOP){
				
				props.isForwardKeyPressed=false;
				
			}
			else if (message.buttonID ==TGKeybindsID.FORCE_RELOAD){
				ItemStack item = ply.getHeldItem(message.hand);
				if (!item.isEmpty() && item.getItem() instanceof GenericGun){
					
					GenericGun gun = (GenericGun) item.getItem();
					gun.tryForcedReload(item, ply.world, ply, message.hand);
				}
				
			} else if (message.buttonID == TGKeybindsID.TOGGLE_HUD){
				props.showTGHudElements=!props.showTGHudElements;
				
				TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP)ply);
				
			} else if (message.buttonID == TGKeybindsID.TOGGLE_STEP_ASSIST){
				props.enableStepAssist=!props.enableStepAssist;
				if(message.showMsg) {
					TGPackets.network.sendTo(new PacketShowKeybindConfirmMessage(message.buttonID, props.enableStepAssist), (EntityPlayerMP)ply);
				}
				TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP)ply);
			}
			
		}

		
	}
	
	public static boolean isPlayerOp(EntityPlayer player){
		boolean op = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayers().getEntry(player.getGameProfile()) != null;
		return op;
	}
}
