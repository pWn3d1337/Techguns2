package techguns.items.armors;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGArmors;
import techguns.TGItems;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.ShieldStats;
import techguns.util.ItemStackOreDict;
import techguns.util.ItemUtil;
import techguns.util.MathUtil;
import techguns.util.TextUtil;

public class GenericShield extends ItemShield implements ICamoChangeable {

	protected int camocount =1;
	
	protected ItemStackOreDict repairMat = ItemStackOreDict.EMPTY;
	protected int repairMatCount=0;
	
	protected static DecimalFormat formatReduction = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	
	
	public GenericShield(String modid, String name, int durability, boolean addToList, int camocount) {
		super();
		setMaxStackSize(1);
		setCreativeTab(Techguns.tabTechgun);
		setRegistryName(name);
		setUnlocalizedName(modid+"."+name);
		this.camocount=Math.max(camocount,1);
		this.setMaxDamage(durability);
		if(addToList) {
			TGArmors.shields.add(this);
		}
	}
	
	public GenericShield(String modid, String name, int durability, boolean addToList) {
		this(modid, name, durability, addToList, 1);
	}

	public GenericShield(String name, int durability) {
		this(Techguns.MODID,name,durability, true,1);
	}
	
	public GenericShield(String name, int durability, int camocount) {
		this(Techguns.MODID,name, durability, true,camocount);
	}
	
	public GenericShield setRepairMat(ItemStackOreDict mats, int amount) {
		this.repairMat = mats;
		this.repairMatCount = amount;
		return this;
	}
	
	public GenericShield setRepairMat(ItemStackOreDict mats) {
		this.repairMat = mats;
		this.repairMatCount = mats.stackSize;
		return this;
	}
	
	public ArrayList<ItemStack> getRepairMats(ItemStack item){
		ArrayList<ItemStack> mats = new ArrayList<ItemStack>();
		
		if (item.getItemDamage()>0){
			GenericShield armor = (GenericShield) item.getItem();
			
			float dmgpercent = (item.getItemDamage()*1.0f)/((item.getMaxDamage()-1)*1.0f);
				
			int count =  (int) Math.ceil(armor.repairMatCount*dmgpercent);
						
			if(!armor.repairMat.isEmpty() && count>0){
				mats.add(armor.repairMat.getItemStacks(count).get(0));
			}
		
		}
		return mats;
	}
	
	public boolean canRepairOnRepairBench(ItemStack item){
		GenericShield shield = (GenericShield) item.getItem();
		return shield.repairMatCount>0;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
    {
		//same as in Item
		return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }

	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation("minecraft:shield"), "inventory"));
    }

	@Override
	public int getCamoCount() {
		return this.camocount;
	}
	
	@Override
	public boolean isShield(ItemStack stack, EntityLivingBase entity) {
		return (stack.getItemDamage()<stack.getMaxDamage());
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=0;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		if(camoID>0){
			return TextUtil.trans(this.getUnlocalizedName()+".camoname."+camoID);
		} else {
			return TextUtil.trans("techguns.item.defaultcamo");
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.repairMat.isEqualWithOreDict(repair) ? true : super.getIsRepairable(toRepair, repair);
	}

	@Override
	public void addInformation(ItemStack item, World worldIn, List<String> list, ITooltipFlag flagIn) {
		super.addInformation(item, worldIn, list, flagIn);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
			
			list.add(trans("armorTooltip.durability")+": "+(item.getMaxDamage()-item.getItemDamage())+"/"+(item.getMaxDamage()));
			//list.add(trans("armorTooltip.armorvalue")+": " +this.armorValue);
			
			ShieldStats s = ShieldStats.getStats(item, null);
			list.add(ChatFormatting.BLUE+trans("armorTooltip.resistances")+":");
			
			list.add(ChatFormatting.DARK_GRAY+" AR: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.PHYSICAL))+"%");
			list.add(ChatFormatting.GRAY+" PR: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.PROJECTILE))+"%");
			list.add(ChatFormatting.DARK_RED+" EX: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.EXPLOSION))+"%");
			list.add(ChatFormatting.DARK_AQUA+" E: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.ENERGY))+"%");
					
			list.add(ChatFormatting.RED+" F: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.FIRE))+"%");
			list.add(ChatFormatting.AQUA+" I: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.ICE))+"%");
			list.add(ChatFormatting.YELLOW+" L: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.LIGHTNING))+"%");
			list.add(ChatFormatting.DARK_GREEN+" P: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.POISON))+"%");
			list.add(ChatFormatting.DARK_GRAY+" D: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.DARK))+"%");
			list.add(ChatFormatting.GREEN+" RAD: "+formatReduction.format(s.getReductionPercentAgainstType(DamageType.RADIATION))+"%");	
		
		} else {
			//this.addMinimalInformation(item, list);
			list.add(TextUtil.trans("techguns.gun.tooltip.shift1")+" "+ChatFormatting.GREEN+TextUtil.trans("techguns.gun.tooltip.shift2")+" "+ChatFormatting.GRAY+TextUtil.trans("techguns.gun.tooltip.shift3"));
		}
	}
	
	protected String trans(String text){
		return TextUtil.trans(Techguns.MODID+"."+text);
	}
}
