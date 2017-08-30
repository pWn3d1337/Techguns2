package techguns.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.gui.containers.RepairBenchContainer;
import techguns.gui.widgets.GuiButtonRepair;
import techguns.items.armors.GenericArmor;
import techguns.packets.PacketGuiButtonClick;
import techguns.tileentities.BasicOwnedTileEnt;
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
						tooltips.add(prefix+mats.get(i-1).getCount()+"x "+TextUtil.trans(mats.get(i-1).getUnlocalizedName()+".name"));
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
		this.buttonList.add(new GuiButtonRepair(1, this.guiLeft+90, this.guiTop+39, 14, 14));
		this.buttonList.add(new GuiButtonRepair(2, this.guiLeft+90+20, this.guiTop+39, 14, 14));
		this.buttonList.add(new GuiButtonRepair(3, this.guiLeft+90+40, this.guiTop+39, 14, 14));
		this.buttonList.add(new GuiButtonRepair(4, this.guiLeft+90+60, this.guiTop+39, 14, 14));
	}
}
