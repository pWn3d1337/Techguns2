package techguns.gui;

import static techguns.gui.ButtonConstants.BUTTON_ID_SECURITY;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.gui.containers.RepairBenchContainer;
import techguns.gui.widgets.GuiButtonRepair;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.GenericShield;
import techguns.tileentities.RepairBenchTileEnt;
import techguns.util.InventoryUtil;
import techguns.util.TextUtil;

public class RepairBenchGui extends OwnedTileEntGui {
	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/repair_bench_gui.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");

	protected RepairBenchTileEnt tileent;
	protected InventoryPlayer invplayer;
	
	public RepairBenchGui(InventoryPlayer player,RepairBenchTileEnt ent) {
		super(new RepairBenchContainer(player, ent),ent);
		this.tileent=ent;
		this.invplayer=player;
		this.tex=texture;
		this.heightSecurityButton=10;
		this.showInventoryText=false;
	}

	
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int x = 0;
        int y = 0;
        int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        int color = 4210752; //0xff101010;
          
        for(int i=0;i<4;i++){
	        if (this.isInRect(mx, my, 90+i*20, 39, 14, 14)){
	        	ItemStack armor = this.invplayer.armorInventory.get(3-i);  	
	        	this.drawTooltipRepairMats(armor, mx, my);
	        }
        }
        
        if(this.isInRect(mx, my, 65, 39, 14,14)) {
        	ItemStack offhand = this.invplayer.offHandInventory.get(0);
        	this.drawTooltipRepairMats(offhand, mx, my);
        }
        
        if(this.isInRect(mx, my, 12, 39, 14,14)) {
        	ItemStack stack = this.tileent.getInventory().getStackInSlot(9);
        	this.drawTooltipRepairMats(stack, mx, my);
        }
	}



	private void drawTooltipRepairMats(ItemStack item, int mx, int my){
		if(!item.isEmpty()){
			
			if(item.getItem() instanceof GenericArmor && ((GenericArmor)item.getItem()).canRepairOnRepairBench(item)){
				
				if (item.getItemDamage()>0){
					
					List<ItemStack> mats =((GenericArmor)item.getItem()).getRepairMats(item);

					List<String> tooltips = new ArrayList<String>();
					tooltips.add(TextUtil.trans("techguns.repairBench.requiredMats"));
					for(int i=1;i<mats.size()+1;i++){
						String prefix="";
						if(InventoryUtil.canConsumeItem(this.tileent.getInventory(), mats.get(i-1), 0, this.tileent.getInventory().getSlots())>0){
							prefix="\u00A7c";
						}
						tooltips.add(prefix+mats.get(i-1).getCount()+"x "+TextUtil.trans(mats.get(i-1).getTranslationKey()+".name"));
					}
					
					this.drawHoveringText(tooltips, mx, my);
					
				} else {
					this.drawHoveringText(TextUtil.trans("techguns.repairBench.fullcondition"),mx, my);
				}
				
			} else if (item.getItem() instanceof GenericShield && ((GenericShield)item.getItem()).canRepairOnRepairBench(item)) {
			
				if (item.getItemDamage()>0){
					
					List<ItemStack> mats =((GenericShield)item.getItem()).getRepairMats(item);

					List<String> tooltips = new ArrayList<String>();
					tooltips.add(TextUtil.trans("techguns.repairBench.requiredMats"));
					for(int i=1;i<mats.size()+1;i++){
						String prefix="";
						if(InventoryUtil.canConsumeItem(this.tileent.getInventory(), mats.get(i-1), 0, this.tileent.getInventory().getSlots())>0){
							prefix="\u00A7c";
						}
						tooltips.add(prefix+mats.get(i-1).getCount()+"x "+TextUtil.trans(mats.get(i-1).getTranslationKey()+".name"));
					}
					
					this.drawHoveringText(tooltips, mx, my);
					
				} else {
					this.drawHoveringText(TextUtil.trans("techguns.repairBench.fullcondition"),mx, my);
				}
				
		    } else {
				this.drawHoveringText(TextUtil.trans("techguns.repairBench.cantrepair"),mx, my);
			}
			
		} else {
			this.drawHoveringText(TextUtil.trans("techguns.repairBench.noarmor"),mx, my);
		}
		
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int id = BUTTON_ID_SECURITY+1;
		this.buttonList.add(new GuiButtonRepair(id++, this.guiLeft+90, this.guiTop+39, 14, 14));
		this.buttonList.add(new GuiButtonRepair(id++, this.guiLeft+90+20, this.guiTop+39, 14, 14));
		this.buttonList.add(new GuiButtonRepair(id++, this.guiLeft+90+40, this.guiTop+39, 14, 14));
		this.buttonList.add(new GuiButtonRepair(id++, this.guiLeft+90+60, this.guiTop+39, 14, 14));
		
		//offhand
		this.buttonList.add(new GuiButtonRepair(id++, this.guiLeft+65, this.guiTop+39, 14, 14));
		//general repair slot
		this.buttonList.add(new GuiButtonRepair(id++, this.guiLeft+9, this.guiTop+39, 14, 14));
		
	}
}
