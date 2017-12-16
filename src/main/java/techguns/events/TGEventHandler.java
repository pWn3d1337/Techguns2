package techguns.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import elucent.albedo.event.GatherLightsEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGBlocks;
import techguns.TGConfig;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.api.guns.GunHandType;
import techguns.api.guns.GunManager;
import techguns.api.guns.IGenericGun;
import techguns.api.npc.INpcTGDamageSystem;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.ClientProxy;
import techguns.client.ShooterValues;
import techguns.client.render.entities.npcs.RenderAttackHelicopter;
import techguns.client.render.entities.projectiles.DeathEffectEntityRenderer;
import techguns.client.render.entities.projectiles.RenderGrenade40mmProjectile;
import techguns.client.render.tileentities.RenderDoor3x3Fast;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.entities.npcs.TGDummySpawn;
import techguns.entities.spawn.TGSpawnManager;
import techguns.gui.player.TGPlayerInventory;
import techguns.gui.widgets.SlotFabricator;
import techguns.gui.widgets.SlotTG;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.TGArmorBonus;
import techguns.items.guns.GenericGrenade;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunCharge;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.items.guns.MiningDrill;
import techguns.packets.PacketEntityDeathType;
import techguns.packets.PacketNotifyAmbientEffectChange;
import techguns.packets.PacketRequestTGPlayerSync;
import techguns.packets.PacketTGExtendedPlayerSync;
import techguns.util.BlockUtils;
import techguns.util.InventoryUtil;

@Mod.EventBusSubscriber(modid = Techguns.MODID)
public class TGEventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onMouseEvent(MouseEvent event) {
		if (event.getButton()!=-1){
		
			EntityPlayerSP ply = Minecraft.getMinecraft().player;
			
			if (Minecraft.getMinecraft().inGameHasFocus) {
				// System.out.println("MOUSE EVENT LMB");
				if (event.getButton() == 0 && !ply.getHeldItemMainhand().isEmpty() && ply.getHeldItemMainhand().getItem() instanceof IGenericGun) {
	
					ClientProxy cp = ClientProxy.get();
					
					if (((IGenericGun) ply.getHeldItemMainhand().getItem()).isShootWithLeftClick()) {
						cp.keyFirePressedMainhand = event.isButtonstate();
						event.setCanceled(true);
		
						// can't mine/attack while reloading
					} else if (ShooterValues.getReloadtime(ClientProxy.get().getPlayerClient(), false) > 0) {
						long diff = ShooterValues.getReloadtime(ClientProxy.get().getPlayerClient(), false) - System.currentTimeMillis();
						if (diff > 0) {
							if (event.isButtonstate()) {
								event.setCanceled(true);
							}
						}
		
					}
				} /*else if (event.getButton() == 1 && !GunManager.canUseOffhand(ply)) {
					if(!ply.getHeldItemMainhand().isEmpty()&&ply.getHeldItemMainhand().getItem() instanceof GenericGunCharge) {
						//Charging gun is allowed
					} else if (!ply.getHeldItemMainhand().isEmpty() && ply.getHeldItemMainhand().getItem() instanceof GenericGun){
						GenericGun g = (GenericGun) ply.getHeldItemMainhand().getItem();
						
						//Cancel and call secondary action
						if (!ply.isSneaking() && event.isButtonstate()) {
							boolean use = g.gunSecondaryAction(ply, ply.getHeldItemMainhand());
							event.setCanceled(use);
						}
					
					}
					
				} */ else if (event.getButton() == 1 && !ply.isSneaking() && !ply.getHeldItemOffhand().isEmpty() && ply.getHeldItemOffhand().getItem() instanceof IGenericGun && GunManager.canUseOffhand(ply)) {
					
					ClientProxy cp = ClientProxy.get();
					
					if (((IGenericGun) ply.getHeldItemOffhand().getItem()).isShootWithLeftClick()) {
						cp.keyFirePressedOffhand = event.isButtonstate();
						event.setCanceled(true);
		
						// can't mine/attack while reloading
					} else if (ShooterValues.getReloadtime(ClientProxy.get().getPlayerClient(), true) > 0) {
						long diff = ShooterValues.getReloadtime(ClientProxy.get().getPlayerClient(), true) - System.currentTimeMillis();
						if (diff > 0) {
							if (event.isButtonstate()) {
								event.setCanceled(true);
							}
						}
		
					}
					
				//Lock On Weapon
				}else if (event.getButton() == 1 && ply.getHeldItemMainhand().getItem() instanceof GenericGunCharge && ((GenericGunCharge)ply.getHeldItemMainhand().getItem()).getLockOnTicks() > 0) {
					//System.out.println("Start/Stop LockOn: RMB = "+event.isButtonstate());
					ClientProxy cp = ClientProxy.get();
					cp.keyFirePressedOffhand = event.isButtonstate();
					
					TGExtendedPlayer props = TGExtendedPlayer.get(ply);
					props.lockOnEntity = null;
					props.lockOnTicks = -1;
					System.out.println("reset lock.");
				}
				
				//System.out.println("EVENT CANCELLED: "+event.isCanceled());
				
			}
		}
	}
	
	
	protected static boolean allowOffhandUse(EntityPlayer player, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) return true;
		if(!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof IGenericGun) {
			IGenericGun g = (IGenericGun) player.getHeldItemMainhand().getItem();
			if(g.getGunHandType()==GunHandType.TWO_HANDED) {
				return false;
			}
		}
		if(!player.getHeldItemOffhand().isEmpty() && player.getHeldItemOffhand().getItem() instanceof IGenericGun) {
			IGenericGun g = (IGenericGun) player.getHeldItemOffhand().getItem();
			if(g.getGunHandType()==GunHandType.TWO_HANDED) {
				return false;
			}
		}
		return true;
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=false)
	public static void rightClickEvent(PlayerInteractEvent.RightClickItem event) {
		boolean cancel = !allowOffhandUse(event.getEntityPlayer(), event.getHand());
		if (cancel) {
			event.setCanceled(cancel);
			event.setCancellationResult(EnumActionResult.PASS);
		}
		//System.out.println("Right Click Item:"+event.getEntityPlayer()+" "+event.getHand());
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=false)
	public static void rightClickEvent(PlayerInteractEvent.RightClickBlock event) {
		boolean cancel = !allowOffhandUse(event.getEntityPlayer(), event.getHand());
		if (cancel) {
			event.setCanceled(cancel);
			event.setUseBlock(Result.ALLOW);
			event.setUseItem(Result.DENY);
			event.setCancellationResult(EnumActionResult.PASS);
		} else if (event.getHand() == EnumHand.MAIN_HAND){
			
			EntityPlayer ply = event.getEntityPlayer();
			if(ply.isSneaking() && !ply.getHeldItemOffhand().isEmpty() && (ply.getHeldItemOffhand().getItem() instanceof GenericGun) && (!event.getItemStack().isEmpty() && !(event.getItemStack().getItem() instanceof GenericGun)) ) {
				event.setUseBlock(Result.ALLOW);
			}
			
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=false)
	public static void rightClickEvent(PlayerInteractEvent.EntityInteract event) {
		boolean cancel = !allowOffhandUse(event.getEntityPlayer(), event.getHand());
		if (cancel) {
			event.setCanceled(cancel);
			event.setCancellationResult(EnumActionResult.PASS);
		}
		//System.out.println("EntityInteract:"+event.getEntityPlayer()+" "+event.getHand());
	}
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.LOW) //set to low so other mods don't accidentally destroy it easily
	public static void handleFovEvent(FOVUpdateEvent event){

		float f = 1.0f;
		if ( TGConfig.cl_lockSpeedFov){
			IAttributeInstance iattributeinstance = event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        	f = 1/ ((float)(((iattributeinstance.getAttributeValue() / (double)event.getEntity().capabilities.getWalkSpeed() + 1.0D) / 2.0D)));
        	
        	if(ClientProxy.get().getPlayerClient().isSprinting()){
        		f*=TGConfig.cl_fixedSprintFov;
        	}
		}
		event.setNewfov(event.getNewfov()*ClientProxy.get().player_zoom*f);//*speedFOV;
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onRenderLivingEventPre(RenderLivingEvent.Pre event){
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer ply = (EntityPlayer) event.getEntity();
			
		 	ItemStack stack =ply.getHeldItemMainhand();
		 	if(!stack.isEmpty() && stack.getItem() instanceof GenericGun && ((GenericGun) stack.getItem()).hasBowAnim()){
		 		ModelPlayer model = (ModelPlayer) event.getRenderer().getMainModel();
		 		if (ply.getPrimaryHand()==EnumHandSide.RIGHT) {
		 			model.rightArmPose = ArmPose.BOW_AND_ARROW;
		 		} else {
		 			model.leftArmPose = ArmPose.BOW_AND_ARROW;
		 		}
		 	} else {
		 	
			 	ItemStack stack2 =ply.getHeldItemOffhand();
			 	if(!stack2.isEmpty() && stack2.getItem() instanceof GenericGun && ((GenericGun) stack2.getItem()).hasBowAnim()){
			 		ModelPlayer model =  (ModelPlayer) event.getRenderer().getMainModel();
			 		
			 		if (ShooterValues.getIsCurrentlyUsingGun(ply,true)){
				 		
				 		if (ply.getPrimaryHand()==EnumHandSide.RIGHT) {
				 			model.leftArmPose = ArmPose.BOW_AND_ARROW;
				 		} else {
				 			model.rightArmPose = ArmPose.BOW_AND_ARROW;
				 		}
			 		}
			 	}
		 	}
		}
		
		/*
		 * ENTITY DEATH EFFECTS
		 */
		ClientProxy cp = ClientProxy.get();
		DeathType dt = cp.getEntityDeathType(event.getEntity());
		switch (dt) {
		case GORE:
			event.setCanceled(true);
			break;
		case DISMEMBER:
		case BIO:
		case LASER:
			//TODO
			event.setCanceled(true);
			DeathEffectEntityRenderer.doRender(event.getRenderer(), event.getEntity(), event.getX(), event.getY(), event.getZ(), 0f, dt);
			break;
		case DEFAULT:
		default:
			break;
		}
		
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=false)
	public static void OnLivingAttack(LivingAttackEvent event){
		if (event.getSource() instanceof TGDamageSource) {
			event.setCanceled(true);
			try {
				DamageSystem.attackEntityFrom(event.getEntityLiving(), event.getSource(), event.getAmount());
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=false)
	public static void onLivingHurt(LivingHurtEvent event) {
		if(event.getEntity() instanceof INpcTGDamageSystem) {
			event.setCanceled(true);
			try {
				DamageSystem.livingHurt(event.getEntityLiving(), event.getSource(), event.getAmount());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onLivingJumpEvent(LivingJumpEvent event)
	{
	    if (event.getEntity() instanceof EntityPlayer)
	    {
	        EntityPlayer ply = (EntityPlayer) event.getEntity();
	        float jumpbonus = GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.JUMP,true);
	        
	        /*if (ply.onGround && ply.isSneaking()){
	        	jumpbonus*=5;
	        }*/
	        
	        ply.motionY+=jumpbonus;
	    }  
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onLivingFallEvent(LivingFallEvent event)
	{
	    if (event.getEntity() instanceof EntityPlayer)
	    {	
	    	boolean consume= event.getDistance()>3.0f;
	    	
	        EntityPlayer ply = (EntityPlayer) event.getEntity();
	        float fallbonus = GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.FALLDMG,consume);
	        float reduction = (fallbonus <1 ? 1-fallbonus : 0.0f);
	        float freeheight = GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.FREEHEIGHT,false);
	        if(freeheight<event.getDistance()){
	        	event.setDistance(event.getDistance() - freeheight);
	        } else {
	        	event.setDistance(0.0f);
	        }
	        
	        event.setDistance(event.getDistance() * reduction);
	    }  
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=false)
	public static void onBreakEventHigh(BreakSpeed event){
		EntityPlayer ply = event.getEntityPlayer();
		ItemStack item = ply.getHeldItemMainhand();
		if(!item.isEmpty() && item.getItem() instanceof GenericGunMeleeCharge) {
			GenericGunMeleeCharge g = (GenericGunMeleeCharge) item.getItem();
			if(g.getMiningRadius(item)>0) {	
				EnumFacing sidehit = g.getSideHitMining(ply.world, ply);
				
				if (sidehit!=null) {
					IBlockState state = event.getState();
					float mainHardness = state.getBlockHardness(ply.world, event.getPos());
					
					List<BlockPos> blocks = BlockUtils.getBlockPlaneAroundAxisForMining(ply.world,ply, event.getPos(), sidehit.getAxis(), g.getMiningRadius(item), false, g, item);
					float maxHardness = 0f;
					for (BlockPos p: blocks) {
						IBlockState s = ply.world.getBlockState(p);
						float h = s.getBlockHardness(ply.world, p);
						if(h>maxHardness) {
							maxHardness=h;
						}
					}
					
					if (maxHardness>mainHardness) {
						event.setNewSpeed(event.getNewSpeed()*mainHardness/maxHardness);
					}
				}
			}
		}
	}
	
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onBreakEvent(BreakSpeed event){
		EntityPlayer ply = event.getEntityPlayer();
		float bonus = 1.0f+GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.BREAKSPEED,true);
		float waterbonus=1.0f;
		if(ply.isInsideOfMaterial(Material.WATER)  || ply.isInsideOfMaterial(Material.LAVA)){
			waterbonus += GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.BREAKSPEED_WATER,true);
		}
		
		event.setNewSpeed(event.getNewSpeed()*bonus*waterbonus);
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onLivingDeathEvent(LivingDeathEvent event){
		EntityLivingBase entity = event.getEntityLiving();
		if(!entity.world.isRemote){

			if(entity instanceof EntityPlayer){
				TGExtendedPlayer tgplayer = TGExtendedPlayer.get((EntityPlayer) event.getEntityLiving());
				tgplayer.foodleft=0;
				tgplayer.lastSaturation=0;
			}
			
			if (event.getSource() instanceof TGDamageSource) {
				TGDamageSource tgs = (TGDamageSource)event.getSource();
				if (tgs.deathType != DeathType.DEFAULT) {
					if (EntityDeathUtils.hasSpecialDeathAnim(entity, tgs.deathType)) {
						//System.out.println("Send packet!");
						TGPackets.network.sendToAllAround(new PacketEntityDeathType(entity, tgs.deathType), TGPackets.targetPointAroundEnt(entity, 100.0f));
					}
				}
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onEntityJoinWorld(EntityJoinWorldEvent event){
		if (!event.getEntity().world.isRemote){
			if(event.getEntity() instanceof EntityPlayer){
				EntityPlayer ply = (EntityPlayer) event.getEntity();
				TGExtendedPlayer props = TGExtendedPlayer.get(ply);
				if (props!=null){
					//System.out.println("SENT EXTENDED PLAYER SYNC");
					//TGPackets.network.sendToDimension(new PacketTGExtendedPlayerSync(props, false), event.entity.dimension);
					TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP) ply);
					
					ply.getDataManager().set(TGExtendedPlayer.DATA_FACE_SLOT, props.tg_inventory.getStackInSlot(TGPlayerInventory.SLOT_FACE));
					ply.getDataManager().set(TGExtendedPlayer.DATA_BACK_SLOT, props.tg_inventory.getStackInSlot(TGPlayerInventory.SLOT_BACK));
					ply.getDataManager().set(TGExtendedPlayer.DATA_HAND_SLOT, props.tg_inventory.getStackInSlot(TGPlayerInventory.SLOT_HAND));
					//ply.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_FACESLOT, props.TG_inventory.inventory[TGPlayerInventory.SLOT_FACE]);
					//ply.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_BACKSLOT, props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK]);
				
				}

			} else if (event.getEntity() instanceof TGDummySpawn){
				//
				TGSpawnManager.handleSpawn(event.getWorld(), event.getEntity());
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public static void onStartTracking(PlayerEvent.StartTracking event){
		if(event.getEntityPlayer().world.isRemote){
			TGPackets.network.sendToServer(new PacketRequestTGPlayerSync(event.getEntityPlayer()));			
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public static void onStopTracking(PlayerEvent.StopTracking event){
		if(event.getEntityPlayer().world.isRemote){
			TGExtendedPlayer props = TGExtendedPlayer.get(event.getEntityPlayer());
			if(props!=null){
				props.setJumpkeyPressed(false);
				props.isGliding=false;
			}
		}
	}
		
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent event) {
		event.getMap().registerSprite(SlotTG.BACKSLOT_TEX);
		event.getMap().registerSprite(SlotTG.FACESLOT_TEX);
		event.getMap().registerSprite(SlotTG.HANDSLOT_TEX);
		event.getMap().registerSprite(SlotTG.FOODSLOT_TEX);
		event.getMap().registerSprite(SlotTG.HEALSLOT_TEX);
		event.getMap().registerSprite(SlotTG.AMMOSLOT_TEX);
		
		event.getMap().registerSprite(SlotTG.AMMOEMPTYSLOT_TEX);
		event.getMap().registerSprite(SlotTG.BOTTLESLOT_TEX);
		event.getMap().registerSprite(SlotTG.TURRETGUNSLOT_TEX);
		event.getMap().registerSprite(SlotTG.TURTETARMORSLOT_TEX);

		event.getMap().registerSprite(SlotFabricator.FABRICATOR_SLOTTEX_WIRES);
		event.getMap().registerSprite(SlotFabricator.FABRICATOR_SLOTTEX_POWDER);
		event.getMap().registerSprite(SlotFabricator.FABRICATOR_SLOTTEX_PLATE);
		
		event.getMap().registerSprite(SlotTG.INGOTSLOT_TEX);
		event.getMap().registerSprite(SlotTG.INGOTDARKSLOT_TEX);
		
		RenderDoor3x3Fast.stitchTextures(event.getMap());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent event) {
		RenderGrenade40mmProjectile.initModel();
		RenderAttackHelicopter.initModels();
		RenderDoor3x3Fast.initModels();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		float t = 1.0f;
		EntityPlayer ply = ClientProxy.get().getPlayerClient();
		ItemStack stack = ply.getActiveItemStack();
		
		if(!stack.isEmpty() && ((stack.getItem() instanceof GenericGunCharge && ((GenericGunCharge)stack.getItem()).hasRightClickAction()) || stack.getItem() instanceof GenericGrenade)) {
			EnumHand hand = ply.getActiveHand();

			ItemRenderer itemrenderer = Minecraft.getMinecraft().getItemRenderer();
			try {
				ClientProxy cp = ClientProxy.get();
				if(hand==EnumHand.MAIN_HAND) {
					if(cp.Field_ItemRenderer_equippedProgressMainhand.getFloat(itemrenderer)<t) {
						cp.Field_ItemRenderer_equippedProgressMainhand.setFloat(itemrenderer, t);
						cp.Field_ItemRenderer_prevEquippedProgressMainhand.setFloat(itemrenderer, t);
					}
				} else {
					if(cp.Field_ItemRenderer_equippedProgressOffhand.getFloat(itemrenderer)<t) {
						cp.Field_ItemRenderer_equippedProgressOffhand.setFloat(itemrenderer, t);
						cp.Field_ItemRenderer_prevEquippedProgressOffhand.setFloat(itemrenderer, t);
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		} /*else {
			
			if(!ply.getHeldItemMainhand().isEmpty() && ply.getHeldItemMainhand().getItem() instanceof IGenericGunMelee) {
				ItemRenderer itemrenderer = Minecraft.getMinecraft().getItemRenderer();
				float f = ply.getCooledAttackStrength(1.0f);
				System.out.println("f:"+f);
				if (f<1f) {
					try {
						System.out.println("Set to 1");
						Field_ItemRenderer_equippedProgressMainhand.setFloat(itemrenderer, 1.0f);
						Field_ItemRenderer_prevEquippedProgressMainhand.setFloat(itemrenderer, 1.0f);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			
		}*/

	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRenderWorldLast(RenderWorldLastEvent event) {
		ClientProxy.get().particleManager.renderParticles(Minecraft.getMinecraft().getRenderViewEntity(), event.getPartialTicks());
	}
	
	@SubscribeEvent
	public static void onCraftEvent(ItemCraftedEvent event) {
		if(event.crafting.getItem() instanceof GenericGun) {
			boolean hasGun=false;
			boolean hasAmmo=false;
			boolean hasInvalid=false;
			
			ItemStack gun=ItemStack.EMPTY;
			
			for(int i=0; i<event.craftMatrix.getSizeInventory();i++) {
				ItemStack stack = event.craftMatrix.getStackInSlot(i);
				
				if(!stack.isEmpty()) {
					if( stack.getItem() instanceof GenericGun) {
						if(!hasGun) {
							hasGun=true;
							gun=stack;
						} else {
							hasInvalid=true;
							break;
						}
					} else if (stack.getItem() instanceof ITGSpecialSlot && ((ITGSpecialSlot)stack.getItem()).getSlot(stack)==TGSlotType.AMMOSLOT){
						if(!hasAmmo) {
							hasAmmo=true;
						} else {
							hasInvalid=true;
							break;
						}
						
					} else {
						hasInvalid=true;
						break;
					}
				}
			}
			if(!hasInvalid && hasGun && hasAmmo) {
				//Was an Ammo change recipe!
				GenericGun g = (GenericGun) gun.getItem();
				List<ItemStack> items = g.getAmmoOnUnload(gun);
				items.forEach(i -> {
					int amount = InventoryUtil.addAmmoToPlayerInventory(event.player, i);
					if(amount>0 && !event.player.world.isRemote) {
						ItemStack it = i.copy();
						it.setCount(amount);
						event.player.world.spawnEntity(new EntityItem(event.player.world, event.player.posX, event.player.posY, event.player.posZ, it));
					}		
					});
			}
			
		}

		
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public static void onPlayerDrops(PlayerDropsEvent event){
		EntityPlayer ply = event.getEntityPlayer();
		TGExtendedPlayer props = TGExtendedPlayer.get(ply);
		
		if(props!=null){
			
			ply.captureDrops=true;
			
			props.dropInventory(ply);
			
			ply.captureDrops=false;
					
		}	
		
	}
	
	public static Method Block_getSilkTouchDrop = ReflectionHelper.findMethod(Block.class, "getSilkTouchDrop", "func_180643_i", IBlockState.class);

	@SubscribeEvent
	public static void onBlockDrops(HarvestDropsEvent event) {
		EntityPlayer ply = event.getHarvester();
		if(ply!=null) {
			ItemStack stack = ply.getHeldItemMainhand();
			if(!stack.isEmpty() && stack.getItem() instanceof MiningDrill && ply.isSneaking()) {
				IBlockState state = event.getState();
				if (state.getBlock().canSilkHarvest(ply.world, event.getPos(), state, ply)){
				
					MiningDrill md = (MiningDrill) stack.getItem();
					if (md.getAmmoLeft(stack)>0) {
						
						List drops = event.getDrops();
						drops.clear();
						
						try {
							drops.add(Block_getSilkTouchDrop.invoke(state.getBlock(), state));
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						
						event.setDropChance(1.0f);
					}
				}
			}
		}
		
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH) //run before regular drop events
	public static void MilitaryCrateDrops(HarvestDropsEvent event) {
		IBlockState state = event.getState();

		if(state.getBlock()==TGBlocks.MILITARY_CRATE && !event.isSilkTouching() && !event.getWorld().isRemote) {
			BlockPos pos = event.getPos();
			EntityPlayer ply = event.getHarvester();
			if (ply!=null) {
				int fortune = event.getFortuneLevel();
						
				LootTable loottable = ply.world.getLootTableManager().getLootTableFromLocation(TGBlocks.MILITARY_CRATE.getLootableForState(state));
				LootContext lootcontext = new LootContext.Builder((WorldServer) ply.world).withLuck(fortune).withPlayer(ply).build();
				
				event.getDrops().clear();
				for (ItemStack itemstack : loottable.generateLootForPools(ply.world.rand, lootcontext))
	            {
					event.getDrops().add(itemstack);
	            }
			}
		}
	}
	
	/*@SubscribeEvent
	public static void damageTest(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			System.out.println("Attacking"+event.getEntityLiving()+" for "+event.getAmount() +" with "+event.getSource());
		}
	}*/
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onBlockHighlight(DrawBlockHighlightEvent event) {
		EntityPlayer ply = event.getPlayer();
		ItemStack item = ply.getHeldItemMainhand();
		if(!ply.isSneaking() && !item.isEmpty() && item.getItem() instanceof GenericGunMeleeCharge) {
			GenericGunMeleeCharge g = (GenericGunMeleeCharge) item.getItem();
			if (g.getMiningRadius(item)>0 && g.getAmmoLeft(item)>0) {
				RayTraceResult target = event.getTarget();
				if(target!=null && target.typeOfHit == Type.BLOCK) {
					BlockPos p = target.getBlockPos();
					List<BlockPos> otherblocks = BlockUtils.getBlockPlaneAroundAxisForMining(ply.world, ply, p, target.sideHit.getAxis(), g.getMiningRadius(item), false, g, item);
					otherblocks.forEach(b -> {
						RayTraceResult result = new RayTraceResult(new Vec3d(b.getX()+0.5d,  b.getY()+0.5d,  b.getZ()+0.5d),target.sideHit,b);
						event.getContext().drawSelectionBox(ply, result, 0, event.getPartialTicks());
					});
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onItemSwitch(LivingEquipmentChangeEvent event) {
		if (event.getSlot()==EntityEquipmentSlot.MAINHAND || event.getSlot()==EntityEquipmentSlot.OFFHAND) {
			if(!event.getTo().isEmpty() && event.getTo().getItem() instanceof GenericGun) {
				if(((GenericGun)event.getTo().getItem()).hasAmbientEffect()){
					EnumHand hand = EnumHand.MAIN_HAND;
					if (event.getSlot()==EntityEquipmentSlot.OFFHAND) {
						hand=EnumHand.OFF_HAND;
					}
					TGPackets.network.sendToDimension(new PacketNotifyAmbientEffectChange(event.getEntityLiving(), hand), event.getEntityLiving().world.provider.getDimension());
				}
			} else if (!event.getFrom().isEmpty() && event.getFrom().getItem() instanceof GenericGun) {
				if(((GenericGun)event.getFrom().getItem()).hasAmbientEffect()){
					EnumHand hand = EnumHand.MAIN_HAND;
					if (event.getSlot()==EntityEquipmentSlot.OFFHAND) {
						hand=EnumHand.OFF_HAND;
					}
					TGPackets.network.sendToDimension(new PacketNotifyAmbientEffectChange(event.getEntityLiving(), hand), event.getEntityLiving().world.provider.getDimension());
				}
			}
		}
	}
	
	@Optional.Method(modid="albedo")
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onGatherLightsEvent(GatherLightsEvent event) {
		ClientProxy cp = ClientProxy.get();
		for (int i=0;i<cp.activeLightPulses.size();i++) {
			event.getLightList().add(cp.activeLightPulses.get(i).provideLight());
		}
		//ClientProxy.get().activeLightPulses.forEach(l -> event.getLightList().add(l.provideLight()));
	}
	
	
}
