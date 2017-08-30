package techguns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import techguns.init.ITGInitializer;
import techguns.packets.*;

/**
 * Class for dealing with packets
*
*/
public class TGPackets implements ITGInitializer {
	public static SimpleNetworkWrapper network;
	
	public static EntityPlayer getPlayerFromContext(MessageContext ctx){
		EntityPlayer thePlayer = (ctx.side.isClient() ? Techguns.proxy.getPlayerClient() : ctx.getServerHandler().player);
		return thePlayer;
	}

	public static TargetPoint targetPointAroundEnt(Entity ent, double distance){
		return new TargetPoint(ent.dimension, ent.posX, ent.posY, ent.posZ, distance);
	}
	
	public static TargetPoint targetPointAroundEnt(TileEntity ent, double distance){
		return new TargetPoint(ent.getWorld().provider.getDimension(), ent.getPos().getX(), ent.getPos().getY(), ent.getPos().getZ(), distance);
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
	}

	@Override
	public void init(FMLInitializationEvent event) {

		network = NetworkRegistry.INSTANCE.newSimpleChannel(Techguns.MODID);
		int packetid=0;
		
		network.registerMessage(PacketShootGun.Handler.class, PacketShootGun.class, packetid++, Side.SERVER);
		network.registerMessage(GunFiredMessage.Handler.class, GunFiredMessage.class, packetid++, Side.CLIENT);
		network.registerMessage(ReloadStartedMessage.Handler.class, ReloadStartedMessage.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketPlaySound.Handler.class, PacketPlaySound.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketSpawnParticle.Handler.class, PacketSpawnParticle.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketSpawnParticleOnEntity.Handler.class, PacketSpawnParticleOnEntity.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketSwapWeapon.Handler.class, PacketSwapWeapon.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketEntityDeathType.Handler.class, PacketEntityDeathType.class,  packetid++, Side.CLIENT);
		network.registerMessage(PacketOpenPlayerGUI.Handler.class, PacketOpenPlayerGUI.class,  packetid++, Side.SERVER);
		network.registerMessage(PacketTGKeybindPress.Handler.class, PacketTGKeybindPress.class, packetid++, Side.SERVER);
		network.registerMessage(PacketTGExtendedPlayerSync.Handler.class, PacketTGExtendedPlayerSync.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketTGPlayerFieldSync.Handler.class, PacketTGPlayerFieldSync.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketRequestTGPlayerSync.Handler.class, PacketRequestTGPlayerSync.class, packetid++, Side.SERVER);
		network.registerMessage(PacketGuiButtonClick.Handler.class, PacketGuiButtonClick.class, packetid++, Side.SERVER);
		network.registerMessage(PacketUpdateTileEntTanks.Handler.class, PacketUpdateTileEntTanks.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketRequestTileEntitySync.Handler.class, PacketRequestTileEntitySync.class, packetid++, Side.SERVER);
		network.registerMessage(PacketMultiBlockFormInvalidBlockMessage.Handler.class, PacketMultiBlockFormInvalidBlockMessage.class, packetid++, Side.CLIENT);
		
		/*
		network.registerMessage(PacketRequestTurretSync.Handler.class, PacketRequestTurretSync.class, packetid++, Side.SERVER);
		network.registerMessage(PacketTGMessage.Handler.class, PacketTGMessage.class, packetid++, Side.CLIENT);*/
		//network.registerMessage(MyMessage.Handler.class, MyMessage.class, 0, Side.SERVER);
	       // network.registerMessage(SecondMessage.Handler.class, SecondMessage.class, 1, Side.CLIENT);
	       // ...
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}