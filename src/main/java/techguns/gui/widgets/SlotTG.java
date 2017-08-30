package techguns.gui.widgets;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;

public class SlotTG extends Slot {
	private final TGSlotType type;
	public static final ResourceLocation BACKSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_back");
	public static final ResourceLocation FACESLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_face");
	public static final ResourceLocation HANDSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_hand");
	public static final ResourceLocation AMMOSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_bullet");
	public static final ResourceLocation FOODSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_food");
	public static final ResourceLocation HEALSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_heal");
	
	public static final ResourceLocation BOTTLESLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_bottle");
	public static final ResourceLocation AMMOEMPTYSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_bullet_empty");
	public static final ResourceLocation TURTETARMORSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_turretarmor");
	public static final ResourceLocation TURRETGUNSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_turretgun");
	
	public static final ResourceLocation INGOTSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_ingot");
	public static final ResourceLocation INGOTDARKSLOT_TEX = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_ingot_dark");
	
	public SlotTG(IInventory inv, int index, int posx, int posy, TGSlotType type) {
		super(inv, index, posx, posy);
		this.type=type;
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (!itemstack.isEmpty() && itemstack.getItem() instanceof ITGSpecialSlot){
			ITGSpecialSlot slot = (ITGSpecialSlot) itemstack.getItem();
			return slot.getSlot(itemstack)==type;
		}
		return TGSlotType.NORMAL==type;
	}

	public TGSlotType getType() {
		return type;
	}

	@Override
	public String getSlotTexture() {
		switch(type) {
		case AMMOSLOT:
			return AMMOSLOT_TEX.toString();
		case BACKSLOT:
			return BACKSLOT_TEX.toString();
		case FACESLOT:
			return FACESLOT_TEX.toString();
		case FOODSLOT:
			return FOODSLOT_TEX.toString();
		case HANDSLOT:
			return HANDSLOT_TEX.toString();
		case HEALSLOT:
			return HEALSLOT_TEX.toString();
		default:
			break;
		}
		return super.getSlotTexture();
	}

	
}
