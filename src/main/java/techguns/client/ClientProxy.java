package techguns.client;

import java.awt.image.renderable.RenderableImageOp;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.config.RegistryBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import techguns.CommonProxy;
import techguns.TGArmors;
import techguns.TGBlocks;
import techguns.TGConfig;
import techguns.TGEntities;
import techguns.TGFluids;
import techguns.TGItems;
import techguns.TGuns;
import techguns.Techguns;
import techguns.api.capabilities.ITGExtendedPlayer;
import techguns.api.render.IItemTGRenderer;
import techguns.blocks.machines.SimpleMachine;
import techguns.capabilities.TGDeathTypeCap;
import techguns.capabilities.TGDeathTypeCapStorage;
import techguns.capabilities.TGExtendedPlayer;
import techguns.capabilities.TGExtendedPlayerStorage;
import techguns.client.audio.TGSound;
import techguns.client.audio.TGSoundCategory;
import techguns.client.models.armor.*;
import techguns.client.models.guns.*;
import techguns.client.models.items.*;
import techguns.client.models.machines.ModelAmmoPress;
import techguns.client.models.machines.ModelChemLab;
import techguns.client.models.machines.ModelMetalPress;
import techguns.client.models.machines.ModelTurretBase;
import techguns.client.models.projectiles.ModelRocket;
import techguns.client.particle.LightPulse;
import techguns.client.particle.TGFX;
import techguns.client.particle.TGParticleSystem;
import techguns.client.render.AdditionalSlotRenderRegistry;
import techguns.client.render.ItemRenderHack;
import techguns.client.render.RenderAdditionalSlotItem;
import techguns.client.render.RenderAdditionalSlotSharedItem;
import techguns.client.render.entities.TGLayerRendererer;
import techguns.client.render.entities.npcs.*;
import techguns.client.render.entities.projectiles.*;
import techguns.client.render.fx.ScreenEffect;
import techguns.client.render.item.*;
import techguns.client.render.tileentities.RenderChargingStation;
import techguns.client.render.tileentities.RenderDungeonScanner;
import techguns.client.render.tileentities.RenderFabricator;
import techguns.client.render.tileentities.RenderMachine;
import techguns.client.render.tileentities.RenderReactionChamber;
import techguns.client.render.tileentities.RenderTurret;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.debug.Keybinds;
import techguns.entities.npcs.NPCTurret;
import techguns.entities.npcs.ZombieFarmer;
import techguns.entities.npcs.ZombieSoldier;
import techguns.entities.projectiles.*;
import techguns.events.TGGuiEvents;
import techguns.entities.projectiles.FlyingGibs;
import techguns.items.guns.GenericGun;
import techguns.keybind.TGKeybinds;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.ChemLabTileEnt;
import techguns.tileentities.DungeonScannerTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.MetalPressTileEnt;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.tileentities.TurretTileEnt;
import techguns.util.MathUtil;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	//No longer needed after forge build 2412
	//protected static Field ItemRender_field_itemStackMainHand = ReflectionHelper.findField(ItemRenderer.class, "itemStackMainHand", "field_187467_d");
	//protected static Field ItemRender_field_itemStackOffHand = ReflectionHelper.findField(ItemRenderer.class, "itemStackOffHand", "field_187468_e");
	
	protected static Field RenderPlayer_LayerRenderers = ReflectionHelper.findField(RenderLivingBase.class, "layerRenderers", "field_177097_h");
	
	public LinkedList<LightPulse> activeLightPulses;
	
	private boolean lightPulsesEnabled=true;
	
	/**
	 * if local player has pressed the fire key this tick
	 */
	public boolean keyFirePressedMainhand;
	public boolean keyFirePressedOffhand;

	@SideOnly(Side.CLIENT)
	public float player_zoom=1.0f;
	
	//client side safeguard to prevent multiple reloadsounds overlapping caused by deyncs
	public long lastReloadsoundPlayed=0L;
	
	//local player First person muzzle flash timing
	protected long player_muzzleFlashtime_right=0;
	protected long player_muzzleFlashtime_total_right=0;
	protected long player_muzzleFlashtime_left=0;
	protected long player_muzzleFlashtime_total_left=0;
	
	public static float PARTIAL_TICK_TIME;
	//local muzzle flash jitter offsets
	public float muzzleFlashJitterX = 0; //-1.0 to 1.0
	public float muzzleFlashJitterY = 0; //-1.0 to 1.0
	public float muzzleFlashJitterAngle = 0; //-1.0 to 1.0
	public float muzzleFlashJitterScale = 0; //-1.0 to 1.0
	
	public boolean hasStepassist=false;
	
	public boolean hasNightvision=false;
	
	public static ModelBiped[] armorModels = {new ModelSteamArmor(0), new ModelSteamArmor(1), new ModelT3PowerArmor(0),new ModelT3PowerArmor(1), new ModelExoSuit(0,1.0f), new ModelExoSuit(1,0.5f),new ModelExoSuit(0,0.75f), new ModelBeret(), new ModelArmorCoat(0,1.0f), new ModelArmorCoat(1,0.51f), new ModelArmorCoat(2,0.49f), new ModelArmorCoat(3,0.75f) };
	
	private boolean isLeft(EnumHand handIn){
		if(this.getPlayerClient().getPrimaryHand()==EnumHandSide.RIGHT){
			return handIn == EnumHand.OFF_HAND;
		} else {
			return handIn == EnumHand.MAIN_HAND;
		}
	}
	
	private boolean isLeft(boolean left){
		if(this.getPlayerClient().getPrimaryHand()==EnumHandSide.RIGHT){
			return left;
		} else {
			return !left;
		}
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		TGFX.loadFXList();
		if (!Loader.isModLoaded("albedo")){
			this.lightPulsesEnabled=false;
		} else {
			this.activeLightPulses=new LinkedList<>();
		}
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		if(TGConfig.debug) {
			Keybinds.init(); //Debuging Keybinds
		} 
		TGKeybinds.init();
		
		MinecraftForge.EVENT_BUS.register(new TGGuiEvents());
		MinecraftForge.EVENT_BUS.register(new TGKeybinds());
		
		RenderGrenade40mmProjectile.initModel();
		
		Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
		
		RenderPlayer slim =skinMap.get("slim");
		//slim.addLayer(new TGLayerRendererer(slim,true));
		insertLayerAfterArmor(slim, new TGLayerRendererer(slim,true));
		
		RenderPlayer def = skinMap.get("default");
		//def.addLayer(new TGLayerRendererer(def,false));
		insertLayerAfterArmor(def, new TGLayerRendererer(def,false));
		
		ClientRegistry.bindTileEntitySpecialRenderer(AmmoPressTileEnt.class,
				new RenderMachine(new ModelAmmoPress(), new ResourceLocation(Techguns.MODID, "textures/blocks/ammopress.png")));
		
		ClientRegistry.bindTileEntitySpecialRenderer(MetalPressTileEnt.class,
				new RenderMachine(new ModelMetalPress(), new ResourceLocation(Techguns.MODID, "textures/blocks/metalpress.png")));
		
		ClientRegistry.bindTileEntitySpecialRenderer(ChemLabTileEnt.class,
				new RenderMachine(new ModelChemLab(), new ResourceLocation(Techguns.MODID, "textures/blocks/chemlab.png")));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TurretTileEnt.class,
				new RenderTurret(new ModelTurretBase()));
		
		ClientRegistry.bindTileEntitySpecialRenderer(FabricatorTileEntMaster.class, new RenderFabricator());
		
		ClientRegistry.bindTileEntitySpecialRenderer(ChargingStationTileEnt.class, new RenderChargingStation());
		
		ClientRegistry.bindTileEntitySpecialRenderer(ReactionChamberTileEntMaster.class, new RenderReactionChamber());
		
		ClientRegistry.bindTileEntitySpecialRenderer(DungeonScannerTileEnt.class, new RenderDungeonScanner());
	}
	
	private void insertLayerAfterArmor(RenderPlayer r, TGLayerRendererer tglayer) {
		try {
			List<LayerRenderer> layers = (List<LayerRenderer>) RenderPlayer_LayerRenderers.get(r);

			for(int i=0;i<layers.size();i++) {
				LayerRenderer layer = layers.get(i);
				if(layer instanceof LayerBipedArmor){
					layers.add(i+1, tglayer);
					break;
				}				
			}
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		TGItems.initModels();
		TGArmors.initModels();
		TGuns.initModels();
		TGEntities.initModels();
		TGBlocks.initModels();
		TGFluids.initModels();
	}

	@Override
	public void registerItemRenderers() {
		RenderGenericSharedItem3D sharedRenderer = new RenderGenericSharedItem3D();
		
		float[][] m4magTranslations = {
				{0,0.25f,0}, //First Person
				{0f,0.25f,0.05f}, //Third Person
				{0.1f,0.25f,0}, //GUI
				{0,-0.1f,0}, //Ground
				{0,0,-0.05f} //frame
		};
		
		float[][] as50magTranslations = {
				{0,0f,0}, //First Person
				{0f,0f,0f}, //Third Person
				{0f,0.05f,0}, //GUI
				{0,-0.1f,0}, //Ground
				{0,0,0f} //frame
		};
		
		float[][] lmgmagTranslations = {
				{0,0.35f,0}, //First Person
				{0f,0.15f,0.0f}, //Third Person
				{0.1f,0.25f,0}, //GUI
				{0,0f,0}, //Ground
				{0,0.25f,-0.05f} //frame
		};
		/*
		  DEFAULT
		 
		.setTransformTranslations(new float[][]{
			{0f,0f,0f}, //First Person
			{0f,0f,0f}, //Third Person
			{0f,0f,0f}, //GUI
			{0f,0f,0f}, //Ground
			{0f,0f,-0.05f} //frame
		})
		
		*/
		sharedRenderer.addRenderForType("assaultriflemagazine", new RenderItemBase(new ModelARMagazine(false), new ResourceLocation(Techguns.MODID,"textures/guns/m4texture.png")).setBaseScale(1.25f).setGUIScale(0.85f).setBaseTranslation(0, -0.2f, 0).setTransformTranslations(m4magTranslations));
		sharedRenderer.addRenderForType("assaultriflemagazineempty", new RenderItemBase(new ModelARMagazine(true), new ResourceLocation(Techguns.MODID,"textures/guns/m4texture.png")).setBaseScale(1.25f).setGUIScale(0.85f).setBaseTranslation(0, -0.2f, 0).setTransformTranslations(m4magTranslations));
		
		sharedRenderer.addRenderForType("lmgmagazine", new RenderItemLMGMag(new ModelLmgMag(false), new ResourceLocation(Techguns.MODID,"textures/guns/mg2_texture.png")).setBaseScale(1.25f).setGUIScale(0.75f).setBaseTranslation(0, 0f, 0.2f).setTransformTranslations(lmgmagTranslations));
		sharedRenderer.addRenderForType("lmgmagazineempty", new RenderItemLMGMag(new ModelLmgMag(true), new ResourceLocation(Techguns.MODID,"textures/guns/mg2_texture.png")).setBaseScale(1.25f).setGUIScale(0.75f).setBaseTranslation(0, 0f, 0.2f).setTransformTranslations(lmgmagTranslations));	
		
		sharedRenderer.addRenderForType("as50magazine", new RenderItemBase(new ModelAS50Mag(false), new ResourceLocation(Techguns.MODID,"textures/guns/as50texture.png")).setBaseScale(1.5f).setGUIScale(0.75f).setBaseTranslation(0.0325f, -0.2f, 0.33f).setTransformTranslations(as50magTranslations));
		sharedRenderer.addRenderForType("as50magazineempty", new RenderItemBase(new ModelAS50Mag(true), new ResourceLocation(Techguns.MODID,"textures/guns/as50texture.png")).setBaseScale(1.5f).setGUIScale(0.75f).setBaseTranslation(0.0325f, -0.2f, 0.33f).setTransformTranslations(as50magTranslations));
		
		
		sharedRenderer.addRenderForType("rocket", new RenderItemBaseRocketItem(new ModelRocket(), new ResourceLocation(Techguns.MODID,"textures/guns/rocketlauncher.png")).setBaseScale(1.5f).setGUIScale(0.5f).setBaseTranslation(0, 0, 0.1f).setTransformTranslations(new float[][]{
			{0,0f,0f}, //First Person
			{0f,-0.1f,0.02f}, //Third Person
			{0.0f,0.0f,0}, //GUI
			{0.0f,0.0f,0}, //Ground
			{0,0,0f} //frame
		}).setFirstPersonScale(0.35f));
		
		ItemRenderHack.registerItemRenderer(TGItems.SHARED_ITEM, sharedRenderer);
		
		ItemRenderHack.registerItemRenderer(TGuns.m4,new RenderGunBase(new ModelM4(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.18f, -1.29f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0f,-0.05f}, //First Person
					{0f,0.01f,-0.1f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.13f, -1f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f));
		
		ItemRenderHack.registerItemRenderer(TGuns.ak47,new RenderGunBase(new ModelAK(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0).setBaseScale(0.75f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.18f, -1.36f, 0.8f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.06f,-0.02f}, //First Person
					{0f,0f,-0.08f}, //Third Person
					{0.06f,-0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.08f, -1.02f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f));
		
		ItemRenderHack.registerItemRenderer(TGuns.lmg,new RenderGunBase(new ModelLMG(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.21f, -1.5f, 0.78f,0).setMuzzleFXPos3P(0.13f, -0.98f).setRecoilAnim(GunAnimation.genericRecoil, 0.01f, 2.0f).setTransformTranslations(new float[][]{
					{0f,0.02f,-0.09f}, //First Person
					{0f,0f,-0.06f}, //Third Person
					{0.05f,-0.03f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -1.02f).setMuzzleFlashJitter(0.03f, 0.03f, 5.0f, 0.1f));
		
		ItemRenderHack.registerItemRenderer(TGuns.handcannon,new RenderGunBase90(new ModelHandgun(),1).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.16f, -0.75f, 0.9f,0).setReloadAnim(GunAnimation.breechReload, -0.15f, 55.0f).setReloadAnim3p(GunAnimation.breechReload, 0f, 55.0f).setTransformTranslations(new float[][]{
					{0,0.03f,-0.12f}, //First Person
					{0.0f,-0.05f,-0.09f}, //Third Person
					{0.0f,0.0f,0}, //GUI
					{0.0f,0.0f,0}, //Ground
					{0,0,0f} //frame
				}).setMuzzleFXPos3P(0.03f, -0.59f).setRecoilAnim(GunAnimation.genericRecoil, 0.2f, 25.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.sawedoff,new RenderGunBase90(new ModelSawedOff(),1).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.16f, -0.75f, 1.05f,0).setReloadAnim(GunAnimation.breechReload, -0.15f, 55.0f).setReloadAnim3p(GunAnimation.breechReload, 0f, 55.0f).setTransformTranslations(new float[][]{
					{0f,0f,0f}, //First Person
					{0f,-0.04f,0f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.06f, -0.49f).setRecoilAnim(GunAnimation.genericRecoil, 0.2f, 20.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.thompson,new RenderGunBase90(new ModelThompson(),1).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE*0.5f-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.14f, -0.75f, 0.55f,0).setMuzzleFXPos3P(0.1f, -0.59f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f));
		
		ItemRenderHack.registerItemRenderer(TGuns.boltaction,new RenderGunBase90(new ModelBoltaction(),1).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f).setBaseScale(1.35f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.21f, -1.48f, 0.9f,0).setScope(ScreenEffect.sniperScope).setTransformTranslations(new float[][]{
					{0f,-0.02f,-0.09f}, //First Person
					{0f,-0.04f,-0.11f}, //Third Person
					{0.1f,-0.08f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.025f} //frame
				}).setMuzzleFXPos3P(0.12f, -1.13f).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.20f, 2.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.biogun,new RenderGunBase90(new ModelBiogun(),1).setBaseTranslation(0.35f, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleGreenFlare, 0, 0.23f, -0.51f, 0.55f,0).setTransformTranslations(new float[][]{
					{0f,0.16f,0.05f}, //First Person
					{0f,0.07f,-0.05f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.1f, -0.51f));
	
		ItemRenderHack.registerItemRenderer(TGuns.flamethrower,new RenderGunFlamethrower(new ModelFlamethrower(),1).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.FlamethrowerMuzzleFlash, 0, 0.16f, -0.9f, 0.45f,0).setTransformTranslations(new float[][]{
					{0f,0f,0f}, //First Person
					{0f,0f,-0.08f}, //Third Person
					{0.05f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.1f, -0.63f).setRecoilAnim(GunAnimation.swayRecoil, 0.025f, 2.5f));
	
		
		ItemRenderHack.registerItemRenderer(TGuns.pistol,new RenderGunBase(new ModelPistol(),2).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.3f, -0.4f)
				.setBaseScale(1.2f).setGUIScale(0.9f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0.03f, 0.2f, -0.5f, 0.55f,-0.03f).setTransformTranslations(new float[][]{
					{0,0.09f,-0.02f}, //First Person
					{0.0f,-0.03f,0.0f}, //Third Person
					{0.02f,-0.08f,0}, //GUI
					{0.02f,-0.08f,0}, //Ground
					{0,0,0f} //frame
				}).setRecoilAnim(GunAnimation.genericRecoil, 0.025f, 12.0f).setMuzzleFlashJitter(0.01f, 0.01f, 5.0f, 0.05f).setMuzzleFXPos3P(0.07f, -0.26f));
		
		ItemRenderHack.registerItemRenderer(TGuns.rocketlauncher,new RenderGunBase90(new ModelRocketLauncher(),1).setBaseTranslation(-0.4f, -0.2f, -RenderItemBase.SCALE*0.5f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.39f, -0.6f, 0.87f, 0).setTransformTranslations(new float[][]{
					{-0.13f,0.3f,0.32f}, //First Person
					{0,0.02f,0.07f}, //Third Person
					{-0.06f,0.0f,0.0f}, //GUI
					{0.0f,0.0f,0}, //Ground
					{0,0,0f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.26f));
		
		ItemRenderHack.registerItemRenderer(TGuns.minigun,new RenderGunBase90(new ModelMinigun(),2).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE*0.5f).setBaseScale(1.2f)
				.setGUIScale(0.3f).setMuzzleFx(ScreenEffect.muzzleFlash_minigun, -0.04f, 0.05f, -1.25f, 0.8f,0.04f).setTransformTranslations(new float[][]{
					{0f,-0.16f,0.1f}, //First Person
					{0f,-0.53f,0.2f}, //Third Person
					{0.12f,-0.02f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(-0.38f, -0.78f).setRecoilAnim(GunAnimation.genericRecoil, 0.05f, 3.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.combatshotgun,new RenderGunBase90(new ModelCombatShotgun(),2).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.21f, -0.91f, 0.75f,0).setTransformTranslations(new float[][]{
					{0f,0.03f,0f}, //First Person
					{0f,-0.01f,-0.10f}, //Third Person
					{0.05f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.12f, -0.81f).setRecoilAnim(GunAnimation.genericRecoil, 0.3f, 15.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.revolver,new RenderGunBase90(new ModelRevolver(),1).setBaseTranslation(-0.35f, -0.2f, RenderItemBase.SCALE*0.5f-0.1f)
				.setBaseScale(0.75f).setGUIScale(0.75f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.25f, -0.41f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.14f,0.01f}, //First Person
					{0f,0.0f,0.0f}, //Third Person
					{0.05f,0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,0f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.30f).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 10.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.grimreaper,new RenderGunBase90(new ModelGrimReaper(),1).setBaseTranslation(0.3f, -0.2f, RenderItemBase.SCALE*0.5f)
				.setGUIScale(0.3f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.39f, -0.61f, 0.75f,0).setTransformTranslations(new float[][]{
					{0f,0.25f,0.18f}, //First Person
					{0,0.13f,0.1f}, //Third Person
					{-0.02f,0.01f,0.0f}, //GUI
					{0.0f,0.1f,0}, //Ground
					{0,0,0f} //frame
				}).setMuzzleFXPos3P(0.24f, -0.56f).setBaseScale(1.25f).setFirstPersonScale(0.4f).setGroundAndFrameScale(0.35f));
		
		ItemRenderHack.registerItemRenderer(TGuns.pdw,new RenderGunBase90(new ModelPDW(),1).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE*1.5f-0.1f)
				.setGUIScale(0.55f).setMuzzleFx(ScreenEffect.muzzleFlash_blue, 0, 0.13f, -0.58f, 0.55f ,0).setTransformTranslations(new float[][]{
					{0.0f, 0.09f, -0.04f}, //First Person
					{0f, 0.06f, -0.02f}, //Third Person
					{0f, 0f, 0f}, //GUI
					{0f, 0f, 0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.11f, -0.41f).setMuzzleFlashJitter(0.01f, 0.01f, 5.0f, 0.1f).setRecoilAnim(GunAnimation.genericRecoil,  0.06f, 4.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.as50,new RenderGunBase(new ModelAS50(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setBaseScale(0.85f).setGUIScale(0.30f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.29f, -1.82f, 1.15f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.25f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.06f,-0.1f}, //First Person
					{0f,0.0f,-0.05f}, //Third Person
					{0.13f,-0.09f,-0.05f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,-0.2f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.17f, -1.29f).setScope(ScreenEffect.sniperScope).setRecoilAnim(GunAnimation.genericRecoil, 0.25f, 5.0f).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.2f, 2.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.m4_infiltrator,new RenderGunBase(new ModelM4Infiltrator(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setGUIScale(0.35f)/*.setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.18f, -1.5f, 0.5f,0)*/.setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0f,-0.05f}, //First Person
					{0f,0.01f,-0.1f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -1.15f).setScope(ScreenEffect.sniperScope).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.05f, 1.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.goldenrevolver,new RenderGunBase90(new ModelGoldenRevolver(),1).setBaseTranslation(-0.35f, -0.2f, RenderItemBase.SCALE*0.5f-0.1f)
				.setBaseScale(0.75f).setGUIScale(0.75f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.25f, -0.41f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.14f,0.01f}, //First Person
					{0f,0.0f,0.0f}, //Third Person
					{0.05f,0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,0f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.30f).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 15.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.pulserifle,new RenderGunBase90(new ModelPulseRifle(),1).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE*1.5f - 0.09f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_blue, 0, 0.22f, -0.76f, 0.6f,0).setTransformTranslations(new float[][]{
					{0f,0.16f,0.01f}, //First Person
					{0f,0.05f,0.08f}, //Third Person
					{0.05f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.1f, -0.50f).setScope(ScreenEffect.techScope,2.125f).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.10f, 1.5f).setRecoilAnim(GunAnimation.pulseRifleRecoil, 0.25f, 10.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.teslagun,new RenderGunBase90(new ModelTeslaGun(),1).setBaseTranslation(0.25f, -0.2f, RenderItemBase.SCALE-0.09f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlashLightning, 0, 0.26f, -0.67f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.15f,0.04f}, //First Person
					{0f,0.05f,-0.08f}, //Third Person
					{0.03f,0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.12f, -0.65f).setRecoilAnim(GunAnimation.genericRecoil, 0.2f, 5.0f));	
		
		ItemRenderHack.registerItemRenderer(TGuns.netherblaster,new RenderGunBase(new ModelNetherBlaster(),1).setBaseTranslation(0f, -0.2f, RenderItemBase.SCALE-0.09f)
				.setGUIScale(0.60f).setMuzzleFx(ScreenEffect.muzzleFlashFireball_alpha, 0, 0.29f, -0.33f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.15f,0.04f}, //First Person
					{0f,-0.16f,-0.24f}, //Third Person
					{-0.10f,0.01f,0f}, //GUI
					{0f,0f,-0.11f}, //Ground
					{0.16f,-0.07f,-0.16f} //frame
				}).setMuzzleFXPos3P(-0.06f, -0.45f));
		
		ItemRenderHack.registerItemRenderer(TGuns.lasergun,new RenderGunBase90(new ModelLasergun(),1).setBaseTranslation(0.25f, -0.2f, RenderItemBase.SCALE*0.5f-0.10f)
				.setBaseScale(1.1f).setGUIScale(0.40f).setMuzzleFx(ScreenEffect.muzzleFlashLaser, 0, 0.30f, -1.06f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.15f,0.04f}, //First Person
					{0f,0.02f,0.01f}, //Third Person
					{0.13f,0.01f,0f}, //GUI
					{0f,0f,0.15f}, //Ground
					{-0.18f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.11f, -0.83f).setRecoilAnim(GunAnimation.genericRecoil, 0.2f, 5.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.alienblaster,new RenderGunBase(new ModelAlienBlaster(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.3f, -0.2f)
				.setBaseScale(0.75f).setGUIScale(0.75f).setMuzzleFx(ScreenEffect.muzzleFlashAlienBlaster, 0f, 0.28f, -0.52f, 0.55f,0f).setTransformTranslations(new float[][]{
					{0,0.06f,-0.02f}, //First Person
					{0.0f,-0.07f,-0.01f}, //Third Person
					{0.02f,-0.08f,0}, //GUI
					{0.02f,-0.08f,0}, //Ground
					{-0.04f,-0.09f,0f} //frame
				}).setRecoilAnim(GunAnimation.genericRecoil, 0.025f, 10.0f).setMuzzleFXPos3P(0.07f, -0.36f).setReloadAnim(GunAnimation.breechReload, -0.15f, 55.0f).setReloadAnim3p(GunAnimation.breechReload, 0f, 55.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.blasterrifle,new RenderGunBase(new ModelBlasterRifle(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setBaseScale(0.9f).setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlashLaser, 0, 0.24f, -0.93f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.11f,-0.20f}, //First Person
					{0f,0.0f,-0.04f}, //Third Person
					{0f,0f,0.03f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.57f).setScope(ScreenEffect.sniperScope));
		
		ItemRenderHack.registerItemRenderer(TGuns.powerhammer,new RenderGunBase90(new ModelPowerHammer(),2).setBaseTranslation(0.15f, -0.2f, RenderItemBase.SCALE-0.09f)
				.setBaseScale(1.25f).setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlashSonic, 0, 0.26f, -0.67f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.18f,0.09f}, //First Person
					{0f,0.04f,0.04f}, //Third Person
					{0.03f,0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{-0.07f,-0.03f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.12f, -0.65f));	
		
		ItemRenderHack.registerItemRenderer(TGuns.grenadelauncher,new RenderGunBaseObj(new ModelBaseBakedGrenadeLauncher(
				new ResourceLocation(Techguns.MODID,"textures/guns/grenadelauncher.png"), new ModelResourceLocation(TGuns.grenadelauncher.getRegistryName(), "inventory"), new ModelResourceLocation(TGuns.grenadelauncher.getRegistryName()+"_1", "inventory")),1,90.0f)
				.setBaseTranslation(0f, 0f, 0f)
				.setBaseScale(0.125f).setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.22f, -0.63f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.19f,-0.09f}, //First Person
					{0f,0.06f,-0.20f}, //Third Person
					{-0.05f,0.08f,0f}, //GUI
					{0f,0.05f,-0.09f}, //Ground
					{0.11f,0.01f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.07f, -0.61f));	
		
		ItemRenderHack.registerItemRenderer(TGuns.aug,new RenderGunBase(new ModelAUG(),2).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0.1f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.19f, -1.45f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.01f,-0.15f}, //First Person
					{0f,0.0f,-0.01f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0.02f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.12f, -0.87f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f).setScope(ScreenEffect.sniperScope).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.075f, 1.0f));
		
		ItemRenderHack.registerItemRenderer(TGuns.sonicshotgun,new RenderGunBase90(new ModelSonicShotgun(),1).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE*0.5f-0.10f)
				.setBaseScale(1.0f).setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlashSonic, 0, 0.28f, -0.98f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.10f,0.04f}, //First Person
					{0f,-0.01f,-0.03f}, //Third Person
					{0.08f,0.0f,0f}, //GUI
					{0f,0.05f,0.15f}, //Ground
					{-0.18f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.11f, -0.81f));
		
		ItemRenderHack.registerItemRenderer(TGuns.chainsaw,new RenderGunBase90(new ModelChainsaw(),1).setBaseTranslation(-0.4f, -0.2f, RenderItemBase.SCALE-0.09f)
				.setBaseScale(0.95f).setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlashSonic, 0, 0.26f, -0.67f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,-0.08f,0.15f}, //First Person
					{0f,-0.5f,0.04f}, //Third Person
					{0.03f,0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{-0.07f,-0.03f,-0.11f} //frame
				}).setMuzzleFXPos3P(0.12f, -0.65f));	
		
		ItemRenderHack.registerItemRenderer(TGuns.scatterbeamrifle,new RenderGunBase(new ModelLasergun2(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0.1f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlashLaser, 0, 0.22f, -1.09f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.04f,-0.05f}, //First Person
					{0f,0.01f,-0.1f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -0.82f));
		
		ItemRenderHack.registerItemRenderer(TGuns.nucleardeathray,new RenderGunBase90(new ModelNDR(),1).setBaseTranslation(1f, -0.2f, RenderItemBase.SCALE*1.5f-0.09f)
				.setBaseScale(1.2f).setGUIScale(0.40f).setMuzzleFx(ScreenEffect.muzzleFlashNukeBeam, 0, 0.19f, -0.91f, 0.65f,0).setTransformTranslations(new float[][]{
					{0f,0.02f,0.09f}, //First Person
					{-0.01f,0.04f,0.3f}, //Third Person
					{0.11f,-0.08f,0f}, //GUI
					{0f,0f,0.15f}, //Ground
					{-0.23f,-0.08f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.11f, -0.83f).setRecoilAnim(GunAnimation.swayRecoil, 0.025f, 0.75f));
		
		ItemRenderHack.registerItemRenderer(TGuns.scar,new RenderGunBase(new ModelScar(),2).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0.1f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.23f, -1.48f, 0.78f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.04f,-0.15f}, //First Person
					{0f,0.02f,-0.11f}, //Third Person
					{0.05f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0.02f,-0.09f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -1.04f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f).setScope(ScreenEffect.sniperScope));
		
		ItemRenderHack.registerItemRenderer(TGuns.vector,new RenderGunBase(new ModelVector(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.57f, -0.2f)
				.setBaseScale(1.1f).setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.10f, -0.72f, 0.60f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.05f, 2.0f).setTransformTranslations(new float[][]{
					{0f,-0.17f,0.0f}, //First Person
					{0f,-0.2f,-0.04f}, //Third Person
					{-0.08f,-0.09f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0.05f,-0.17f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.0f, -0.53f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f));
		
		ItemRenderHack.registerItemRenderer(TGuns.mac10,new RenderGunBase(new ModelMac10(),1).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.45f, -0.3f)
				.setBaseScale(1.2f).setGUIScale(0.55f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.23f, -0.46f, 0.5f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.06f, 3.0f).setTransformTranslations(new float[][]{
					{0f,0f,-0.05f}, //First Person
					{0f,-0.10f,0.01f}, //Third Person
					{-0.02f,-0.02f,0f}, //GUI
					{0f,0.03f,0f}, //Ground
					{0f,-0.05f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.07f, -0.26f).setMuzzleFlashJitter(0.02f, 0.05f, 5.0f, 0.1f));
		
		ItemRenderHack.registerItemRenderer(TGuns.mibgun,new RenderGunBase(new ModelMibGun(),1).setBaseTranslation(/*RenderItemBase.SCALE*0.5f*/0f, -0.56f, -0.02f)
				.setBaseScale(1.2f).setGUIScale(0.75f).setMuzzleFx(ScreenEffect.muzzleFlashAlienBlaster, 0f, 0.26f, -0.42f, 0.55f,0f).setTransformTranslations(new float[][]{
					{0,0.10f,-0.02f}, //First Person
					{0.0f,-0.04f,-0.01f}, //Third Person
					{0.02f,-0.08f,0}, //GUI
					{0.02f,-0.08f,0}, //Ground
					{-0.04f,-0.09f,0f} //frame
				}).setRecoilAnim(GunAnimation.genericRecoil, 0.025f, 10.0f).setMuzzleFXPos3P(0.06f, -0.31f).setReloadAnim(GunAnimation.breechReload, -0.15f, 55.0f).setReloadAnim3p(GunAnimation.breechReload, 0f, 55.0f));
	
		
		ItemRenderHack.registerItemRenderer(TGuns.gaussrifle,new RenderGunBaseObj(
				new ModelBaseBaked(new ResourceLocation(Techguns.MODID,"textures/guns/gaussrifle.png"), new ModelResourceLocation(TGuns.gaussrifle.getRegistryName(), "inventory")),1,-90f)
				.setBaseTranslation(0.6f, 0f, RenderItemBase.SCALE*0.5f-0.09f)
				.setBaseScale(0.9f).setGUIScale(0.25f).setMuzzleFx(ScreenEffect.muzzleFlashSonic, 0, 0.21f, -1.56f, 1.0f,0).setTransformTranslations(new float[][]{
					{0f,0.12f,-0.1f}, //First Person
					{0f,0.05f,-0.17f}, //Third Person
					{0.f,0.06f,0.06f}, //GUI
					{0f,0f,0.f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.09f, -1.26f).setRecoilAnim(GunAnimation.genericRecoil, 0.25f, 7.5f).setScope(ScreenEffect.techScope,2.125f).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.15f, 1.0f));
	
		ItemRenderHack.registerItemRenderer(TGuns.guidedmissilelauncher,new RenderGunBase90(new ModelGuidedMissileLauncher(),1).setBaseTranslation(-0.4f, -0.2f, RenderItemBase.SCALE*0.5f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.39f, -0.6f, 0.87f, 0).setTransformTranslations(new float[][]{
					{-0.13f,0.3f,0.62f}, //First Person
					{0,0.09f,0.28f}, //Third Person
					{0.0f,0.03f,0.0f}, //GUI
					{0.0f,0.0f,0}, //Ground
					{0,0,-0.04f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.26f));
		
		ItemRenderHack.registerItemRenderer(TGArmors.steam_Helmet,  new RenderArmorItem(new ModelSteamArmor(0), new ResourceLocation(Techguns.MODID,"textures/models/armor/steam_armor.png"), EntityEquipmentSlot.HEAD) );
		ItemRenderHack.registerItemRenderer(TGArmors.steam_Chestplate,  new RenderArmorItem(new ModelSteamArmor(0), new ResourceLocation(Techguns.MODID,"textures/models/armor/steam_armor.png"), EntityEquipmentSlot.CHEST) );
		ItemRenderHack.registerItemRenderer(TGArmors.steam_Leggings,  new RenderArmorItem(new ModelSteamArmor(1), new ResourceLocation(Techguns.MODID,"textures/models/armor/steam_armor.png"), EntityEquipmentSlot.LEGS) );
		ItemRenderHack.registerItemRenderer(TGArmors.steam_Boots,  new RenderArmorItem(new ModelSteamArmor(0), new ResourceLocation(Techguns.MODID,"textures/models/armor/steam_armor.png"), EntityEquipmentSlot.FEET) );
	
		ItemRenderHack.registerItemRenderer(TGArmors.t3_power_Helmet,  new RenderArmorItem(new ModelT3PowerArmor(0), new ResourceLocation(Techguns.MODID,"textures/models/armor/powerarmor.png"), EntityEquipmentSlot.HEAD) );
		ItemRenderHack.registerItemRenderer(TGArmors.t3_power_Chestplate,  new RenderArmorItem(new ModelT3PowerArmor(0), new ResourceLocation(Techguns.MODID,"textures/models/armor/powerarmor.png"), EntityEquipmentSlot.CHEST) );
		ItemRenderHack.registerItemRenderer(TGArmors.t3_power_Leggings,  new RenderArmorItem(new ModelT3PowerArmor(1), new ResourceLocation(Techguns.MODID,"textures/models/armor/powerarmor.png"), EntityEquipmentSlot.LEGS) );
		ItemRenderHack.registerItemRenderer(TGArmors.t3_power_Boots,  new RenderArmorItem(new ModelT3PowerArmor(0), new ResourceLocation(Techguns.MODID,"textures/models/armor/powerarmor.png"), EntityEquipmentSlot.FEET) );
	
		
		ItemRenderHack.registerItemRenderer(TGuns.stielgranate,new RenderGrenade(new ModelStielgranate(), new ResourceLocation(Techguns.MODID,"textures/guns/stielgranate.png")).setBaseScale(1f).setGUIScale(0.8f).setBaseTranslation(-RenderItemBase.SCALE*0.5f, 0.48f, -RenderItemBase.SCALE).setTransformTranslations(new float[][]{
			{0f,-0.06f,0f}, //First Person
			{0f,-0.15f,0.06f}, //Third Person
			{-0.05f,-0.49f,0f}, //GUI
			{0f,-0.15f,0f}, //Ground
			{0f,0f,-0.05f} //frame
		}));	
		
		AdditionalSlotRenderRegistry.register(TGItems.GAS_MASK, new RenderAdditionalSlotItem(new ModelGasMask(), new ResourceLocation(Techguns.MODID, "textures/armors/gasmask.png")));
		AdditionalSlotRenderRegistry.register(TGItems.GLIDER, new RenderAdditionalSlotItem(new ModelGlider(), new ResourceLocation(Techguns.MODID, "textures/armors/glider.png")));
		AdditionalSlotRenderRegistry.register(TGItems.JUMPPACK, new RenderAdditionalSlotItem(new ModelJetPack(1), Techguns.MODID, "textures/armors/jetpack",4));
		AdditionalSlotRenderRegistry.register(TGItems.JETPACK, new RenderAdditionalSlotItem(new ModelJetPack(0), Techguns.MODID, "textures/armors/jetpack",4));
		AdditionalSlotRenderRegistry.register(TGItems.SCUBA_TANKS, new RenderAdditionalSlotItem(new ModelOxygenTanks(), new ResourceLocation(Techguns.MODID,"textures/armors/oxygentanks.png")));
		AdditionalSlotRenderRegistry.register(TGItems.NIGHTVISION_GOGGLES, new RenderAdditionalSlotItem(new ModelNightVisionGoggles(), new ResourceLocation(Techguns.MODID,"textures/armors/nightvisiongoggles.png")));
		AdditionalSlotRenderRegistry.register(TGItems.ANTI_GRAV_PACK, new RenderAdditionalSlotItem(new ModelAntiGravPack(), Techguns.MODID, "textures/armors/antigravpack",5));
		AdditionalSlotRenderRegistry.register(TGItems.TACTICAL_MASK, new RenderAdditionalSlotItem(new ModelFaceMask(true), Techguns.MODID, "textures/armors/tacticalmask",4));
		
		
		RenderAdditionalSlotSharedItem sharedItemRenderer = new RenderAdditionalSlotSharedItem();
		sharedItemRenderer.addRenderForSharedItem(TGItems.OXYGEN_MASK.getItemDamage(),new RenderAdditionalSlotItem(new ModelFaceMask(true), new ResourceLocation(Techguns.MODID,"textures/armors/oxygenmask.png")));
		
		AdditionalSlotRenderRegistry.register(TGItems.SHARED_ITEM, sharedItemRenderer);
		
	}
	
	@Override
	protected void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(GenericProjectile.class, RenderGenericProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(RocketProjectile.class, RenderRocketProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(StoneBulletProjectile.class, RenderStoneBulletProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(BioGunProjectile.class, RenderBioGunProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(FlamethrowerProjectile.class, RenderFlameThrowerProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(GrenadeProjectile.class, RenderGrenadeProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(FlyingGibs.class, RenderFlyingGibs::new);
		RenderingRegistry.registerEntityRenderingHandler(Grenade40mmProjectile.class, RenderGrenade40mmProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(LaserProjectile.class, RenderLaserProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(TeslaProjectile.class, RenderTeslaProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(NDRProjectile.class, RenderNDRProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(BlasterProjectile.class, RenderBlasterProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(GaussProjectile.class, RenderAdvancedBulletProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(AdvancedBulletProjectile.class, RenderAdvancedBulletProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(GuidedMissileProjectile.class, RenderRocketProjectile::new);
		
		//NPCS
		RenderingRegistry.registerEntityRenderingHandler(NPCTurret.class, RenderNPCTurret::new);
		
		RenderingRegistry.registerEntityRenderingHandler(ZombieSoldier.class, RenderZombieSoldier::new);
		RenderingRegistry.registerEntityRenderingHandler(ZombieFarmer.class, RenderZombieSoldier::new);
		
	}
	
	public static ClientProxy get(){
		return (ClientProxy) Techguns.proxy;
	}

	@Override
	public EntityPlayer getPlayerClient() {
		return Minecraft.getMinecraft().player;
	}

	
	@Override
	public void setGunTextures(GenericGun gun, String path, int variations) {
		gun.textures=new ArrayList<ResourceLocation>();
		for(int i=0;i<variations;i++){
			gun.textures.add(new ResourceLocation(Techguns.MODID,path+(i!=0?("_"+i):"")+".png"));
		}
	}
	
	@Override
	public void handleSoundEvent(EntityPlayer ply, int entityId, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving,
			boolean gunPosition,boolean playOnOwnPlayer, TGSoundCategory soundCategory) {
		Entity entity = null;
		if (entityId != -1) {
			entity = ply.world.getEntityByID(entityId);
		}

		if(entity!=null){
			if (entity != ply || playOnOwnPlayer ) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(soundname,entity,volume,pitch, repeat, moving, gunPosition,soundCategory));
					
			}
		} else {
			//System.out.println("Handle Sound entity null, NEED FIX!");
		}
	}

	@Override
	public void playSoundOnEntity(Entity ent, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category) {
		Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(soundname,ent, volume, pitch, repeat, moving, gunPosition, category));
	}
	
	
	@Override
	public void playSoundOnPosition(SoundEvent soundname, float posx, float posy, float posz, float volume, float pitch, boolean repeat, TGSoundCategory soundCategory) {
		Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(soundname,posx,posy,posz,volume,pitch, repeat,soundCategory));
	}
	
	@Override
	public void createFX(String name, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ){	
		List<TGParticleSystem> systems = TGFX.createFX(world, name, posX, posY, posZ, motionX, motionY, motionZ);
		if (systems!=null) {
			systems.forEach(s -> Minecraft.getMinecraft().effectRenderer.addEffect(s));
		}
	}
	
	@Override
	public void createFXOnEntity(String name, Entity ent) {
		List<TGParticleSystem> systems = TGFX.createFXOnEntity(ent, name);
		if (systems!=null) {
			systems.forEach(s -> Minecraft.getMinecraft().effectRenderer.addEffect(s));
		}
	}
	
	@Override
	public void setHasStepassist(boolean value) {
		this.hasStepassist=value;
	}

	@Override
	public void setHasNightvision(boolean value) {
		this.hasNightvision=value;
	}

	@Override
	public boolean getHasStepassist() {
		return this.hasStepassist;
	}

	@Override
	public boolean getHasNightvision() {
		return this.hasNightvision;
	}
	
	@Override
	public void setFlySpeed(float value) {
		EntityPlayer ply = getPlayerClient();
		ply.capabilities.setFlySpeed(value);
	}

	//NO LONGER NEEDED AFTER FORGE 2412
	/*@Override 
	public void fixReequipAnim(ItemStack from, ItemStack to) {
		EntityPlayer ply = Minecraft.getMinecraft().player;
		
		boolean mainhand = ply.getHeldItemMainhand() == to;
		ItemRenderer ir = Minecraft.getMinecraft().getItemRenderer();
		try {
			if (mainhand) {
				ItemRender_field_itemStackMainHand.set(ir, to);
			} else {
				ItemRender_field_itemStackOffHand.set(ir, to);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}*/

	@Override
	public void registerCapabilities() {
		super.registerCapabilities();
		CapabilityManager.INSTANCE.register(TGDeathTypeCap.class, new TGDeathTypeCapStorage(), () -> new TGDeathTypeCap(null));
	}
	
	/**
	 * EntityDeathType
	 */
	public void setEntityDeathType(EntityLivingBase entity, DeathType deathtype){
		TGDeathTypeCap cap = TGDeathTypeCap.get(entity);
		cap.setDeathType(deathtype);
	}
	
	public DeathType getEntityDeathType(EntityLivingBase entity) {
		return TGDeathTypeCap.get(entity).getDeathType();
	}
	
	@Deprecated
	public boolean hasDeathType(EntityLivingBase entity) {
		return TGDeathTypeCap.get(entity).getDeathType() == DeathType.DEFAULT;
	}
	
	@Deprecated
	public void clearEntityDeathType(EntityLivingBase entity) {
		setEntityDeathType(entity, DeathType.DEFAULT);
	}
		
	@Override
	public boolean isClientPlayerAndIn1stPerson(EntityLivingBase ent) {
		return ent == this.getPlayerClient() && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
	}

	@Override
	public void createLightPulse(double x, double y, double z, int lifetime, float rad_start, float rad_end, float r, float g, float b) {
		if (this.lightPulsesEnabled) {
			this.activeLightPulses.add(new LightPulse(x, y, z, lifetime, rad_start, rad_end, r, g, b));
		}
	}

	@Override
	public void createLightPulse(double x, double y, double z, int fadeIn, int fadeOut, float rad_large, float rad_small, float r, float g, float b) {
		if (this.lightPulsesEnabled) {
			this.activeLightPulses.add(new LightPulse(x, y, z, fadeIn, fadeOut, rad_large, rad_small, r, g, b));
		}
	}
	
	public String resolvePlayerNameFromUUID(UUID uuid){
		String name = UsernameCache.getLastKnownUsername(uuid);
		if (name!=null){
			return name;
		} else {
			return "UNKNOW_PLAYERNAME";
		}
	}
	
	@Override
	public boolean clientInRangeSquared(double posX, double posZ, double distSq) {
		EntityPlayer localPly = this.getPlayerClient();
		
		MathUtil.Vec2 posPly = new MathUtil.Vec2(localPly.posX, localPly.posZ);
		MathUtil.Vec2 pos = new MathUtil.Vec2(posX, posZ);
		
		return posPly.getVecTo(pos).lenSquared() <= distSq;
	}
}
