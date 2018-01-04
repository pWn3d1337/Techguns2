package techguns;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.init.ITGInitializer;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.plugins.chisel.TGChiselBlocks;
import techguns.plugins.crafttweaker.TGCraftTweakerIntegration;
import techguns.world.OreGenerator;
import techguns.world.WorldGenTGStructureSpawn;
import techguns.world.dungeon.DungeonTemplate;

@Mod(modid = Techguns.MODID, version = Techguns.VERSION, name=Techguns.NAME, acceptedMinecraftVersions=Techguns.MCVERSION, guiFactory=Techguns.GUI_FACTORY, updateJSON=Techguns.UPDATEURL, dependencies=Techguns.DEPENDENCIES)
public class Techguns
{
    public static final String MODID = "techguns";
    public static final String MCVERSION = "1.12.2";
    public static final String VERSION = "2.0.1.0";
    public static final String NAME = "Techguns";
    public static final String GUI_FACTORY = "techguns.gui.config.GuiFactoryTechguns";
    public static final String UPDATEURL = "https://raw.githubusercontent.com/pWn3d1337/Techguns2/master/update.json";
    
    public static final String DEPENDENCIES = "after:ftbl;after:chisel;";
    
    @Mod.Instance
    public static Techguns instance;
    
    @SidedProxy(clientSide = "techguns.client.ClientProxy", serverSide = "techguns.server.ServerProxy")
    public static CommonProxy proxy;
    
    public static TGItems items = new TGItems();
    public static TGBlocks blocks = new TGBlocks();
    public static TGuns guns = new TGuns();
    public static TGEntities entities = new TGEntities();
    public static TGPackets packets = new TGPackets();
    public static AmmoTypes ammoTypes = new AmmoTypes();
    public static TGArmors armors = new TGArmors();
    public static TGFluids fluids = new TGFluids();
    
    //Mod integration
    public boolean FTBLIB_ENABLED=false;
    public boolean CHISEL_ENABLED=false;
    
    protected ITGInitializer[] initializers = {
    	items,
    	armors,
    	ammoTypes,
    	guns,
    	fluids,
    	blocks,
    	entities,
    	packets
    };
    
    
	public static CreativeTabs tabTechgun = new CreativeTabs(Techguns.MODID) {
		
	    @Override
	    @SideOnly(Side.CLIENT)
	    public ItemStack getTabIconItem() {
	        return TGItems.newStack(TGItems.PISTOL_ROUNDS,1);
	    }

		@Override
		public String getTranslatedTabLabel() {
			return Techguns.MODID+"."+super.getTranslatedTabLabel();
		}

		@Override
		public boolean hasSearchBar() {
			return true;
		}
	};
	static {
		tabTechgun.setBackgroundImageName("item_search.png");
	};
	
	public static int modEntityID=-1;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	for (ITGInitializer init : initializers){
    		init.init(event);
    	}
    	proxy.init(event);
    	
    	if(Loader.isModLoaded("ftbl")) {
    		FTBLIB_ENABLED=true;
    	}
    	if(Loader.isModLoaded("chisel")) {
    		CHISEL_ENABLED=true;
    	}
    }
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	TGConfig.init(event);
    	for (ITGInitializer init : initializers){
    		init.preInit(event);
    	}
    	proxy.preInit(event);
    	
    	if(Loader.isModLoaded("crafttweaker")){
    		TGCraftTweakerIntegration.init();
    	}
    	
    	if(TGConfig.doOreGenTitanium||TGConfig.doOreGenUranium||TGConfig.doOreGenLead||TGConfig.doOreGenTin||TGConfig.doOreGenCopper) {
    		GameRegistry.registerWorldGenerator(new OreGenerator(), 1);
    	}
    	
    	 if(TGConfig.doWorldspawn){
         	GameRegistry.registerWorldGenerator(new WorldGenTGStructureSpawn(), 6);
         }
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
    	for (ITGInitializer init : initializers){
    		init.postInit(event);
    	}
    	proxy.postInit(event);
    	TGMachineRecipes.addRecipes();
    	
    	if(this.CHISEL_ENABLED) {
    		TGChiselBlocks.postInit();
    	}
    	DungeonTemplate.init();
    }
    
    
}