package techguns.items;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import techguns.TGItems;
import techguns.Techguns;
import techguns.api.render.IItemTGRenderer;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.items.armors.TGArmorBonus;
import techguns.util.TextUtil;

public class GenericItemShared extends GenericItem implements IItemTGRenderer, ITGSpecialSlot{
	protected ArrayList<SharedItemEntry> sharedItems = new ArrayList<>();
	
	public GenericItemShared() {
		super("itemshared", false);
	}

	public ItemStack addsharedVariant(String name){
		return addsharedVariant(name, false);
	}
	
	public ItemStack addsharedVariant(String name, boolean useRenderHack){
		return addsharedVariant(name, useRenderHack, TGSlotType.NORMAL);
	}
	
	public ItemStack addsharedVariant(String name, TGSlotType slottype){
		return addsharedVariant(name, false, slottype);
	}
	
	public ItemStack addsharedVariant(String name, boolean useRenderHack, TGSlotType slottype){
		return addsharedVariant(name, useRenderHack,slottype,64,true);
	}
	
	public ItemStack addsharedVariantOptional(String name, boolean enabled) {
		return this.addsharedVariant(name, false, TGSlotType.NORMAL, 64, enabled);
	}
	
	public ItemStack addsharedVariant(String name, boolean useRenderHack, TGSlotType slottype, int maxStackSize, boolean enabled){
		int newMeta = sharedItems.size();
		sharedItems.add(new SharedItemEntry(name, newMeta, slottype, (short)maxStackSize, useRenderHack, enabled));
		return new ItemStack(this,1,newMeta);
	}
	
	
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		int dmg = stack.getItemDamage();
		if (dmg <this.sharedItems.size()) {
			return this.sharedItems.get(dmg).maxStackSize;
		}
		return super.getItemStackLimit(stack);
	}

	public SharedItemEntry get(int damageValue){
		return this.sharedItems.get(damageValue);
	}
	
	public static class SharedItemEntry {
		protected String name;
		protected TGSlotType slottype;
		protected short maxStackSize;
		protected int meta;
		protected boolean useRenderHack;
		protected boolean enabled;
		
		public SharedItemEntry(String name, int meta, TGSlotType slottype, short maxStackSize, boolean useRenderHack, boolean enabled) {
			super();
			this.name = name;
			this.slottype = slottype;
			this.maxStackSize = maxStackSize;
			this.meta = meta;
			this.useRenderHack=useRenderHack;
			this.enabled = enabled;
		}

		public String getName() {
			return name;
		}

		public TGSlotType getSlottype() {
			return slottype;
		}

		public short getMaxStackSize() {
			return maxStackSize;
		}

		public int getMeta() {
			return meta;
		}

		public boolean isEnabled() {
			return enabled;
		}
		
	}

	@Override
	public void initModel() {
		for (int i=0; i<this.sharedItems.size();i++){
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(new ResourceLocation(Techguns.MODID,this.sharedItems.get(i).name), "inventory"));
		}
	}

	public ArrayList<SharedItemEntry> getSharedItems() {
		return sharedItems;
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(this.isInCreativeTab(tab)){
			for (int i=0;i<this.sharedItems.size();i++){
				if ( this.sharedItems.get(i).isEnabled()) {
					items.add(new ItemStack(this,1,i));
				}
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item."+Techguns.MODID+"."+getSharedItems().get(stack.getItemDamage()).name;
	}

	@Override
	public boolean shouldUseRenderHack(ItemStack stack) {
		return this.getSharedItems().get(stack.getItemDamage()).useRenderHack;
	}

	@Override
	public TGSlotType getSlot(ItemStack item) {
		int dmg = item.getItemDamage();
		if (dmg <this.sharedItems.size()) {
			return this.sharedItems.get(dmg).slottype;
		}
		return TGSlotType.NORMAL;
	}

	@Override
	public float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, EntityPlayer player) {
		if(stack.getItemDamage()==TGItems.OXYGEN_MASK.getItemDamage()) {
			if(type == TGArmorBonus.OXYGEN_GEAR){
				return 1.0f;
			}
		} else if (stack.getItemDamage() ==TGItems.WORKING_GLOVES.getItemDamage()) {
			if(type==TGArmorBonus.BREAKSPEED) {
				return 0.10f;
			}
		}
		return 0f;
	}

	private float getBonus(TGArmorBonus type, ItemStack stack) {
		return getBonus(type, stack, false, null);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(this.getSlot(stack)!=TGSlotType.NORMAL) {
			tooltip.add(ChatFormatting.GRAY+TextUtil.trans(Techguns.MODID+".tooltip.slot")+": "+this.getSlot(stack));
		}
		if(this.getBonus(TGArmorBonus.EXTRA_HEART, stack)>0.0f){
			tooltip.add(trans("armorTooltip.healthbonus")+": +"+(int)this.getBonus(TGArmorBonus.EXTRA_HEART, stack)+" "+trans("armorTooltip.hearts"));
		} else if (this.getBonus(TGArmorBonus.SPEED, stack)>0.0f){
			tooltip.add(trans("armorTooltip.movespeed")+": +"+this.getBonus(TGArmorBonus.SPEED, stack)*100.0f+"%");
		} else if(this.getBonus(TGArmorBonus.JUMP, stack)>0.0f){
			tooltip.add(trans("armorTooltip.jumpheight")+": +"+this.getBonus(TGArmorBonus.JUMP, stack));
		} if(this.getBonus(TGArmorBonus.FALLDMG, stack)>0.0f){
			tooltip.add(trans("armorTooltip.falldamage")+": -"+this.getBonus(TGArmorBonus.FALLDMG, stack)*100.0f+"%");
		} if(this.getBonus(TGArmorBonus.FREEHEIGHT, stack)>0.0f){
			tooltip.add(trans("armorTooltip.fallheight")+": -"+this.getBonus(TGArmorBonus.FREEHEIGHT, stack));
		} if(this.getBonus(TGArmorBonus.BREAKSPEED, stack)>0.0f){
			tooltip.add(trans("armorTooltip.miningspeed")+": +"+this.getBonus(TGArmorBonus.BREAKSPEED, stack)*100.0f+"%");
		} if(this.getBonus(TGArmorBonus.BREAKSPEED_WATER, stack)>0.0f){
			tooltip.add(trans("armorTooltip.underwatermining")+": +"+this.getBonus(TGArmorBonus.BREAKSPEED_WATER, stack)*100.0f+"%");
		} if(this.getBonus(TGArmorBonus.KNOCKBACK_RESISTANCE, stack)>0.0f){
			tooltip.add(trans("armorTooltip.knockbackresistance")+": +"+this.getBonus(TGArmorBonus.KNOCKBACK_RESISTANCE, stack)*100.0f+"%");
		} if(this.getBonus(TGArmorBonus.NIGHTVISION, stack)>0.0f){
			tooltip.add(trans("armorTooltip.nightvision"));
		} if(this.getBonus(TGArmorBonus.STEPASSIST, stack)>0.0f){
			tooltip.add(trans("armorTooltip.stepassist"));
		} if(this.getBonus(TGArmorBonus.OXYGEN_GEAR, stack)>0.0f){
			tooltip.add(trans("armorTooltip.oxygengear"));
		} if(this.getBonus(TGArmorBonus.COOLING_SYSTEM, stack)>0.0f){
			tooltip.add(trans("armorTooltip.coolingsystem"));
		}	
	}
	
	private String trans(String text){
		return TextUtil.trans(Techguns.MODID+"."+text);
	}
	
	
}
