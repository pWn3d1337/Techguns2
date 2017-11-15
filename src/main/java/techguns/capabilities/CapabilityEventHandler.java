package techguns.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.entities.npcs.ITGSpawnerNPC;

@Mod.EventBusSubscriber(modid = Techguns.MODID)
public class CapabilityEventHandler {
		
	@SubscribeEvent
	public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
		
		if (event.getObject() instanceof INPCTechgunsShooter) {
			
			final TGShooterValues cap = new TGShooterValues();
			event.addCapability(TGShooterValuesCapProvider.ID, new TGShooterValuesCapProvider(cap));
			
		} else if (event.getObject() instanceof EntityPlayer) {
			TGExtendedPlayer cap;
			if( event.getObject().world.isRemote) {
				cap = new TGExtendedPlayerClient((EntityPlayer) event.getObject());
			} else {
				cap = new TGExtendedPlayer((EntityPlayer) event.getObject());
			}
			event.addCapability(TGExtendedPlayerCapProvider.ID, new TGExtendedPlayerCapProvider(cap));
			
		} 
		if ( event.getObject() instanceof ITGSpawnerNPC) {
			final TGSpawnerNPCData cap = new TGSpawnerNPCData();
			event.addCapability(TGSpawnerNPCDataCapProvider.ID, new TGSpawnerNPCDataCapProvider(cap));
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void attachCapabilitiesClient(final AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityLivingBase) {
			final TGDeathTypeCap cap = new TGDeathTypeCap((EntityLivingBase) event.getObject());
			event.addCapability(TGDeathTypeCapProvider.ID, new TGDeathTypeCapProvider(cap));
		}
	}
	
	/**
	 * Copy the player's caps when they respawn after dying or dimension change.
	 *
	 * @param event The event
	 */
	@SubscribeEvent
	public static void playerClone(final PlayerEvent.Clone event) {
		final TGExtendedPlayer oldValues = TGExtendedPlayer.get(event.getOriginal());
		final TGExtendedPlayer newValues = TGExtendedPlayer.get(event.getEntityPlayer());
		
		if (oldValues != null && newValues != null) {
			newValues.copyFrom(oldValues);
		}
	}
	
}
