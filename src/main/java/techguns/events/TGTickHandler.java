package techguns.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Predicate;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGConfig;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.api.guns.IGenericGun;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.capabilities.TGExtendedPlayer;
import techguns.capabilities.TGExtendedPlayerClient;
import techguns.capabilities.TGShooterValues;
import techguns.client.ClientProxy;
import techguns.client.audio.TGSoundCategory;
import techguns.client.particle.LightPulse;
import techguns.entities.npcs.GenericNPC;
import techguns.gui.player.TGPlayerInventory;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.PoweredArmor;
import techguns.items.armors.TGArmorBonus;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunCharge;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketShootGun;
import techguns.packets.PacketShootGunTarget;
import techguns.packets.PacketSwapWeapon;
import techguns.packets.PacketTGExtendedPlayerSync;
import techguns.util.InventoryUtil;

@Mod.EventBusSubscriber(modid = Techguns.MODID)
public class TGTickHandler {
	private static final UUID UUID_SPEED = UUID.fromString("5D8E53EB-DCFA-4121-B4DB-99BCAFA6B70B");
	private static final UUID UUID_HEALTH = UUID.fromString("4CFA49EB-D215-498B-9CC9-4BD0D1350B1F");
	private static final UUID UUID_KNOCKBACK_RESISTANCE = UUID.fromString("3441FC5D-F0B6-47F4-AFBB-DC5005670254");
	
	private static Method ITEMFOOD_onFoodEaten;
	static {
		ITEMFOOD_onFoodEaten = ReflectionHelper.findMethod(ItemFood.class,"onFoodEaten","func_77849_c", ItemStack.class, World.class, EntityPlayer.class);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void localClientPlayerTick(PlayerTickEvent event) {

		 TGExtendedPlayer props = TGExtendedPlayer.get(event.player);
		
		if (event.phase == Phase.START) {

			ClientProxy cp = ClientProxy.get();
			

			// ONLY DO FOR OWN PLAYER!
			if (event.player == cp.getPlayerClient()) {
				//TGExtendedPlayer props = TGExtendedPlayer.get(cp.getPlayerClient());
				
				if (Minecraft.getMinecraft().inGameHasFocus) {
					ItemStack stack = event.player.getHeldItemMainhand();
					ItemStack stackOff = event.player.getHeldItemOffhand();
					
					if (!stack.isEmpty() && stack.getItem() instanceof IGenericGun && ((IGenericGun) stack.getItem()).isShootWithLeftClick()) {
						if (cp.keyFirePressedMainhand) {
							// event.player.swingItem();
							
							IGenericGun gun = (IGenericGun) stack.getItem();
							if (props.getFireDelay(EnumHand.MAIN_HAND) <= 0) {
								if (gun instanceof GenericGunCharge && ((GenericGunCharge)gun).getLockOnTicks() > 0 && props.lockOnEntity != null && props.lockOnTicks > ((GenericGunCharge)gun).getLockOnTicks()) {
									TGPackets.network.sendToServer(new PacketShootGunTarget(gun.isZooming(),EnumHand.MAIN_HAND, props.lockOnEntity));
									gun.shootGunPrimary(stack, event.player.world, event.player, gun.isZooming(), EnumHand.MAIN_HAND, props.lockOnEntity);
								}else {
									TGPackets.network.sendToServer(new PacketShootGun(gun.isZooming(),EnumHand.MAIN_HAND));
									gun.shootGunPrimary(stack, event.player.world, event.player, gun.isZooming(), EnumHand.MAIN_HAND, null);
								}
								
							}
							if (gun.isSemiAuto()) {
								cp.keyFirePressedMainhand = false;
							}
						}
		
					} else {
						cp.keyFirePressedMainhand = false;
					}
					
					if (!stackOff.isEmpty() && stackOff.getItem() instanceof IGenericGun && ((IGenericGun) stackOff.getItem()).isShootWithLeftClick()) {
						if (cp.keyFirePressedOffhand) {
							// event.player.swingItem();
							IGenericGun gun = (IGenericGun) stackOff.getItem();
							if (props.getFireDelay(EnumHand.OFF_HAND) <= 0) {

								TGPackets.network.sendToServer(new PacketShootGun(gun.isZooming(),EnumHand.OFF_HAND));
								gun.shootGunPrimary(stackOff, event.player.world, event.player, gun.isZooming(), EnumHand.OFF_HAND, null);
							}
							if (gun.isSemiAuto()) {
								cp.keyFirePressedOffhand = false;
							}
						}

					} else {
						cp.keyFirePressedOffhand = false;
					}
					
					//Reset lock if out of ammo
					if (!stack.isEmpty() && stack.getItem() instanceof GenericGunCharge && ((GenericGunCharge) stack.getItem()).getLockOnTicks() > 0) {
						//System.out.println("RMB: "+cp.keyFirePressedOffhand);
						if (((GenericGunCharge)stack.getItem()).getAmmoLeft(stack) <= 0 && props.lockOnEntity != null) {
							props.lockOnEntity = null;
							props.lockOnTicks = -1;
							//System.out.println("reset lock.");
						}
					}
					
				} else {
					cp.keyFirePressedMainhand = false;
					cp.keyFirePressedOffhand = false;
				}
			}

		} else {
			
			
		}

	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		TGExtendedPlayer props = TGExtendedPlayer.get(event.player);
		 
		 //System.out.println(event.phase);
		 if (event.phase == Phase.START){
			
			 //reduce fire delays on both sides
			 if(props.fireDelayMainhand>0){
				 props.fireDelayMainhand--;
			 }
			 if(props.loopSoundDelayMainhand>0){
				 props.loopSoundDelayMainhand--;
			 }
			 if(props.fireDelayOffhand>0){
				 props.fireDelayOffhand--;
			 }
			 if(props.loopSoundDelayOffhand>0){
				 props.loopSoundDelayOffhand--;
			 }
			 
			 if (event.side ==Side.SERVER){
				 //SERVER ONLY CODE
				 if (props.swingSoundDelay>0){
					 props.swingSoundDelay--;
				 }
			 }
			 
			 
		 } else if (event.phase == Phase.END){
		 
		 	 if(!event.player.world.isRemote) {
				 event.player.getDataManager().set(TGExtendedPlayer.DATA_FACE_SLOT, props.tg_inventory.getStackInSlot(TGPlayerInventory.SLOT_FACE));
				 event.player.getDataManager().set(TGExtendedPlayer.DATA_BACK_SLOT, props.tg_inventory.getStackInSlot(TGPlayerInventory.SLOT_BACK));
				 event.player.getDataManager().set(TGExtendedPlayer.DATA_HAND_SLOT, props.tg_inventory.getStackInSlot(TGPlayerInventory.SLOT_HAND));
			 } else {
				props.tg_inventory.setInventorySlotContents(TGPlayerInventory.SLOT_FACE, event.player.getDataManager().get(TGExtendedPlayer.DATA_FACE_SLOT));
				props.tg_inventory.setInventorySlotContents(TGPlayerInventory.SLOT_BACK, event.player.getDataManager().get(TGExtendedPlayer.DATA_BACK_SLOT));
				props.tg_inventory.setInventorySlotContents(TGPlayerInventory.SLOT_HAND, event.player.getDataManager().get(TGExtendedPlayer.DATA_HAND_SLOT));
			 }
			 
			 boolean wearingTechgunsArmor = false;
			 for (int i =0;i<4;i++){
				 ItemStack istack = event.player.inventory.armorInventory.get(i);
				 if (GenericArmor.isTechgunArmor(istack)){
					 wearingTechgunsArmor = true;
					 break;
				 }
			 }
			 
	         IAttributeInstance attributeMovespeed = event.player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
	         if (attributeMovespeed!=null){
	        	 AttributeModifier mod_speed = attributeMovespeed.getModifier(UUID_SPEED);
	        	 if (mod_speed!=null){
	        		 attributeMovespeed.removeModifier(mod_speed);
	        	 }
	         }
	         
	         IAttributeInstance attributeMaxHealth = event.player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
	         if (attributeMaxHealth!=null){
	        	 AttributeModifier mod_health = attributeMaxHealth.getModifier(UUID_HEALTH);
	        	 if (mod_health !=null){
	        		 attributeMaxHealth.removeModifier(mod_health);
	        	 }
	         }
	         
	         IAttributeInstance attributeKnockbackResist = event.player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
	         if (attributeKnockbackResist!=null){
	        	 AttributeModifier mod_knockback_resistance = attributeKnockbackResist.getModifier(UUID_KNOCKBACK_RESISTANCE);
	        	 if (mod_knockback_resistance  !=null){
	        		 attributeKnockbackResist.removeModifier(mod_knockback_resistance);
	        	 }
	         }
	         
	         float speed=0.0f;
			 if (wearingTechgunsArmor){

				 PoweredArmor.calculateConsumptionTick(event.player);
				 
				 if (event.player.isBurning()){
					 float cooling = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.COOLING_SYSTEM, false);
					 if (cooling >=1.0f){
						 event.player.extinguish();
					 }
				 }
				 
				 //Check for stats
				 speed = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.SPEED,false);
				 
				 if (event.player.isInsideOfMaterial(Material.WATER) || event.player.isInsideOfMaterial(Material.LAVA)){
					 speed += GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.SPEED_WATER,false);
				 }
				 
				 if ((speed) >0.0f){
					 //event.player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,2,0,true));
					 if(event.player.isSprinting()){
						 speed=speed*2.0f;
					 }
					 
					 /*if(!event.player.getActiveItemStack().isEmpty()){
						 ItemStack item = event.player.getHeldItemMainhand();
						 if (item.getItem() instanceof GenericGun){
							 speed+=5.0f;
						 }
					 }*/
					 
		             attributeMovespeed.applyModifier(new AttributeModifier(UUID_SPEED, "TechgunsSpeedboost", speed, 2));
				 }
				 float healthBonus = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.EXTRA_HEART,false);
				 if (healthBonus >0.0f){
					 
		             attributeMaxHealth.applyModifier(new AttributeModifier(UUID_HEALTH, "TechgunsHealthbonus", healthBonus, 0));
					 
				 }
				 
				 float knockbackresistance = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.KNOCKBACK_RESISTANCE,false);
				 if (knockbackresistance >0.0f){
					 
		             attributeKnockbackResist.applyModifier(new AttributeModifier(UUID_KNOCKBACK_RESISTANCE, "TechgunsKnockbackresistbonus", knockbackresistance, 0));
					 
				 }
				 
			 } else {

				 if (speed>0.0f){
					 attributeMovespeed.applyModifier(new AttributeModifier(UUID_SPEED, "TechgunsSpeedboost", speed, 2));
				 }
			 }
			
			 //Step height is client only
			 if(event.player.world.isRemote){
				//ONLY DO FOR OWN PLAYER!
				 if (event.player==ClientProxy.get().getPlayerClient()){
					 float stepassist = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.STEPASSIST,false);
					 
					 Techguns.proxy.setHasStepassist(stepassist>0.0f);
					 
					 if(props.enableStepAssist && (stepassist>0)){
						 
						 if(event.player.stepHeight<1.1f){
							 event.player.stepHeight=1.1f;
						 } 
						 
					 } else {
						 
						 if(event.player.stepHeight>0.6f){
							 event.player.stepHeight=0.6f;
						 }
						 
					 }
				 }
			 }
			 
			 boolean enabled=false;
			 if(props!=null){
				 enabled= props.enableNightVision;
			 }
			 float nightvision=GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.NIGHTVISION, enabled);
			 Techguns.proxy.setHasNightvision(nightvision>0.0f);
			 if(nightvision>0.0f && enabled){
				 if(!event.player.world.isRemote){
					 event.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 220, 0,false,false));
				 }
			 }
			 
			 //flying
			 float flightbonus = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.CREATIVE_FLIGHT, false);
			 if (flightbonus >0){
				 event.player.capabilities.allowFlying = true;
				 props.gotCreativeFlightLastTick=true;
				 
				 if (event.player.capabilities.isFlying){
					 if (!props.wasFlying){
						 
						 if (event.player.world.isRemote) {
							 Techguns.proxy.createFXOnEntity("AntiGravRing", event.player);
						 } else {
							 TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.ANTI_GRAV_START, event.player, 1.0f, 1.0f, false, true, false, true, TGSoundCategory.PLAYER_EFFECT), TGPackets.targetPointAroundEnt(event.player,50.0f));
						 }
						 
					 } 
					 props.wasFlying=true;
					 
				 } else {
					 props.wasFlying=false;
				 }
				 
				 
			 } else {
				 if (props.gotCreativeFlightLastTick && !event.player.capabilities.isCreativeMode){
					 event.player.capabilities.allowFlying=false;
					 event.player.capabilities.isFlying=false;
				 }
				 props.gotCreativeFlightLastTick=false;
				 props.wasFlying=false;
			 }
			 
			 //Fly speed is client only
			 if(event.player.world.isRemote){
				 final float DEFAULT_FLYSPEED=0.05f;
				//ONLY DO FOR OWN PLAYER!
				 if (event.player==ClientProxy.get().getPlayerClient()){
					 float flyspeedBonus = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.FLYSPEED,false);
					 if (flyspeedBonus>0){
						 if (props.enableJetpack){
							 Techguns.proxy.setFlySpeed((1.0f+flyspeedBonus)*DEFAULT_FLYSPEED);
						 } else {
							 Techguns.proxy.setFlySpeed(DEFAULT_FLYSPEED);
						 }
					 } else {
						 if (props.gotCreativeFlightLastTick){
							 Techguns.proxy.setFlySpeed(DEFAULT_FLYSPEED);
						 }
					 }
				 }
			 }
			 
			 /** 
			  * Auto feeder
			  */
			 if (!TGConfig.disableAutofeeder && event.player.getFoodStats().getFoodLevel()<=19){
				 
				 if (props!=null){
					 int needed = 20-event.player.getFoodStats().getFoodLevel();
					 if (props.foodleft>0){
						 if (props.foodleft<=needed){
							 event.player.getFoodStats().addStats(props.foodleft,props.lastSaturation);
							 props.foodleft=0;
							 props.lastSaturation=0.0f;
						 } else {
							 event.player.getFoodStats().addStats(needed,props.lastSaturation);
							 props.foodleft-=needed;
						 }
						 if (!event.player.world.isRemote){
							 TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(event.player,props,true), (EntityPlayerMP) event.player);
						 }
					 } else {
						 ItemStack stack = InventoryUtil.consumeFood(props.tg_inventory.inventory, props.tg_inventory.SLOTS_AUTOFOOD_START, props.tg_inventory.SLOTS_AUTOFOOD_END+1);
						 if (!stack.isEmpty()){
							 ItemFood food = (ItemFood) stack.getItem();
							 
							 //check potion effect.
							 try {
								ITEMFOOD_onFoodEaten.invoke(food, stack, event.player.world,event.player);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
							 
							 if (!event.player.world.isRemote){
								 //event.player.world.playSoundAtEntity(event.player, SoundEvents.ENTITY_PLAYER_BURP, 1.0f, 1.0f);
								 event.player.world.playSound(null, event.player.posX, event.player.posY, event.player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1f, 1f);
							 }
							 
							 short left = (short) (food.getHealAmount(stack)-needed);
							 if (left >0){
								 event.player.getFoodStats().addStats(needed, food.getSaturationModifier(stack));
								 props.foodleft=left;
								 props.lastSaturation=food.getSaturationModifier(stack);
							 } else {
								 event.player.getFoodStats().addStats(food.getHealAmount(stack), food.getSaturationModifier(stack));
								 props.foodleft=0;
								 props.lastSaturation=0.0f;
							 }
							 if (!event.player.world.isRemote){
								 TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(event.player,props,true), (EntityPlayerMP) event.player);
							 }
						 }
					 }
				 }
			 }
			 
			 /**
			  * Tick addition slots
			  */
			 props.isGliding=false;
			 
			 tickSlot(props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_FACE), event);
			 tickSlot(props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_BACK), event);
			 tickSlot(props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_HAND), event);
			 
			 Techguns.proxy.handlePlayerGliding(event.player);
			 
			 /**
			  * detect weapon swaps
			  */
			 if (event.side==Side.SERVER) { //does only work server side
				 ItemStack gunMH = ItemStack.EMPTY;
				 ItemStack gunOH = ItemStack.EMPTY;
				 if (!event.player.getHeldItemMainhand().isEmpty() && event.player.getHeldItemMainhand().getItem() instanceof GenericGun) {
					 gunMH=event.player.getHeldItemMainhand();
				 }
				 if (!event.player.getHeldItemOffhand().isEmpty() && event.player.getHeldItemOffhand().getItem() instanceof GenericGun) {
					 gunOH=event.player.getHeldItemOffhand();
				 }
				 
				 if (!gunOH.isEmpty() && props.gunMainHand==gunOH || !gunMH.isEmpty() && props.gunOffHand==gunMH ) {
					 TGPackets.network.sendToAllAround(new PacketSwapWeapon(event.player), TGPackets.targetPointAroundEnt(event.player, 50.0f));
					 
					 int i = props.fireDelayMainhand;
					 props.fireDelayMainhand=props.fireDelayOffhand;
					 props.fireDelayOffhand=i;
				 }
				 
				 props.gunMainHand=gunMH;
				 props.gunOffHand=gunOH;
			 }
			 
//TODO: Remove this
//			 if (props.isChargingWeapon() && (event.player.getItemInUseCount() <= 0)) { //  || !(event.player.getHeldItemMainhand().getItem() instanceof GenericGunCharge)) {		
//				 if (event.player.getHeldItemMainhand().getItem() instanceof GenericGunCharge) {
//					 GenericGunCharge gun = (GenericGunCharge)event.player.getHeldItemMainhand().getItem();
//					 if (gun.canFireWhileCharging && gun.getAmmoLeft(event.player.getHeldItemMainhand()) > 0) {
//						 //System.out.println("Keep Lock!");
//					 }else {
//							props.lockOnEntity = null;
//							props.lockOnTicks = 0;
//					 }
//				 }
//				//if (((GenericGunCharge)event.player.getHeldItemMainhand().getItem()).getLockOnTicks() > 0) {
//				//}
//			 }
			
			 /*
			  * Charging weapon
			  */
			 if (event.side == Side.SERVER) {
				 if (props.isChargingWeapon()) {
					if (event.player.getItemInUseCount() <= 0  || !(event.player.getHeldItemMainhand().getItem() instanceof GenericGunCharge)) {
						props.setChargingWeapon(false);
					}
				 }
			 }
		 }
	}
	
	protected static void tickSlot(ItemStack slot, PlayerTickEvent event) {
		if (!slot.isEmpty() && slot.getItem() instanceof ITGSpecialSlot) {
			ITGSpecialSlot item = (ITGSpecialSlot) slot.getItem();
			item.onPlayerTick(slot, event);
		}
	}
	
	@Optional.Method(modid="albedo")
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void clientGameTick(ClientTickEvent event) {
		if(event.phase==Phase.END) {
			Iterator<LightPulse> iter = ClientProxy.get().activeLightPulses.iterator();
			while(iter.hasNext()) {
				if (!iter.next().updateGameTick()) {
					iter.remove();
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void TickParticleSystems(ClientTickEvent event) {
		if(event.phase==Phase.END) {
			ClientProxy.get().particleManager.tickParticles();
			//System.out.println("TGParticleCount:"+ClientProxy.get().particleManager.getList().getSizeDebug()+ " :: "+ClientProxy.get().particleManager.getList().getSize());
		} else {
			World w = Minecraft.getMinecraft().world;
			if(w!=null) {
				w.<EntityLivingBase>getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
					@Override
					public boolean apply(EntityLivingBase input) {
						return input instanceof INPCTechgunsShooter;
					}
				}).forEach(e -> {
					TGShooterValues shooter_values = TGShooterValues.get(e);
					//Techguns.proxy.tickWeaponParticleSystems(e, shooter_values);
					shooter_values.tickParticles();
					//System.out.println("Tick for :"+e);
				});
				
				w.getPlayers(EntityPlayer.class, new Predicate<EntityPlayer>() {
					@Override
					public boolean apply(EntityPlayer input) {
						return true;
					}
				}).forEach(p -> {
					TGExtendedPlayerClient props = TGExtendedPlayerClient.get(p);
					props.tickParticles();
				});
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRenderTick(RenderTickEvent event) {

		if (event.phase==Phase.START) {
		
			ClientProxy cp = ClientProxy.get();
			cp.PARTIAL_TICK_TIME = event.renderTickTime;
			
			EntityPlayer player = cp.getPlayerClient();
			if (player != null){
				ItemStack current_item = player.getHeldItemMainhand();
				
				if (current_item != null) {
		
					if(current_item.getItem() instanceof IGenericGun){
						
						
						if ((((IGenericGun) current_item.getItem())).isHoldZoom()){
							
							if (player.isSneaking()) {
		
								cp.player_zoom = ((IGenericGun) current_item.getItem()).getZoomMult();
							} else {
								cp.player_zoom=1.0f;
							}
							
						} else {
							if(!((IGenericGun)current_item.getItem()).isZooming()) {
								cp.player_zoom = 1.0f;
							}
						}			
						
					} else {
						cp.player_zoom=1.0f;
					}
				} else {
					cp.player_zoom=1.0f;
				}
			}
		//}
		}
	}

}
