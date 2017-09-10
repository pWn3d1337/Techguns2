package techguns;

import java.util.logging.Level;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import techguns.init.ITGInitializer;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.util.TGLogger;

@Mod(modid = Techguns.MODID, version = Techguns.VERSION, name=Techguns.NAME, acceptedMinecraftVersions=Techguns.MCVERSION)
public class Techguns
{
    public static final String MODID = "techguns";
    public static final String MCVERSION = "1.12.1";
    public static final String VERSION = MCVERSION+"-2.0.1.0";
    public static final String NAME = "Techguns";

    
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
    }
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	TGConfig.init(event);
    	for (ITGInitializer init : initializers){
    		//TGLogger.logger_both.log(Level.WARNING, "PRE_INIT:"+init);
    		init.preInit(event);
    	}
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
    	for (ITGInitializer init : initializers){
    		init.postInit(event);
    	}
    	proxy.postInit(event);
    	TGMachineRecipes.addRecipes();
    }
    
    
}