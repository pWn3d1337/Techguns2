package techguns.gui;

import static techguns.gui.ButtonConstants.BUTTON_ID_SECURITY;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import techguns.Techguns;
import techguns.capabilities.TGExtendedPlayer;
import techguns.gui.containers.CamoBenchContainer;
import techguns.gui.player.TGPlayerInventory;
import techguns.items.armors.ICamoChangeable;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.tileentities.operation.CamoBenchRecipes;
import techguns.tileentities.operation.CamoBenchRecipes.CamoBenchRecipe;
import techguns.util.TextUtil;

public class CamoBenchGui extends OwnedTileEntGui {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/camo_bench_gui.png");// new
																													// ResourceLocation("textures/gui/container/ammopressgui.png");
	protected CamoBenchTileEnt tileent;

	public CamoBenchGui(InventoryPlayer ply, CamoBenchTileEnt tileent) {
		super(new CamoBenchContainer(ply, tileent),tileent);
		this.tileent = tileent;
		this.tex = texture;
		this.heightSecurityButton=95+25;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		//security texture still bound (from super.draw....)
		this.drawTexturedModalRect(k-22, l+90, 195, 0, 22, 20);
		this.drawTexturedModalRect(k-22, l+90+20, 195, 25, 22, 5);
		
		this.mc.getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect(k - 45, l + 6, 178, 0, 45, 84);

		//draw offhand slotbg
		this.drawTexturedModalRect(k-18, l+93, 7, 83, 18, 18);
		
		int left = this.guiLeft;
		int top = this.guiTop;

		int mx = mouseX - (this.width - this.xSize) / 2;
		int my = mouseY - (this.height - this.ySize) / 2;

		GuiInventory.drawEntityOnScreen(left - 20, top + 77, 30, -mx, -my, this.mc.player);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		int x = 0;
        int y = 0;
        int color = 4210752; //0xff101010;
        
        String s2=TextUtil.trans("techguns.item.invalidcamo");
        String s3= "";
        
        ItemStack item = tileent.getItem();
        if(!item.isEmpty()){
        	if(item.getItem() instanceof ICamoChangeable) {
	        	ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
	        	s2=TextUtil.trans(camoitem.getCurrentCamoName(item));
	        	s3+=(camoitem.getCurrentCamoIndex(item)+1)+"/"+(camoitem.getCamoCount())+":";
        	} else if (item.getItem() instanceof ItemBlock) {
        		Block b = ((ItemBlock) item.getItem()).getBlock();
        		CamoBenchRecipe r = CamoBenchRecipes.getRecipeFor(b);
        		if(r!=null) {
        			if(!r.hasCustomMeta()) {
	        			int i = item.getMetadata()+1;
	        			s3+=(i>9?"":" ")+i+"/"+r.getCamoCount();
	        			s2="";
        			} else {
        				ICamoChangeable camoblock = r.getCamoblock();
        				int i = camoblock.getCurrentCamoIndex(item)+1;
        				s3+=(i>9?"":" ")+i+"/"+(camoblock.getCamoCount()+1);
        				s2=TextUtil.trans(camoblock.getCurrentCamoName(item));
        			}
        		}
        	}
        }
        
        this.fontRenderer.drawString(s3, x+10, y+40, color);
        this.fontRenderer.drawString(s2, x+10, y+50, color);
        
        for(int i=0;i<4;i++){
        	this.fontRenderer.drawString(""+getArmorCamo(3-i), 104+(i*18), 38, color);
        }
        
        this.fontRenderer.drawString(""+getExtendedInvItemCamo(TGPlayerInventory.SLOT_FACE), 49, 38, color);
        this.fontRenderer.drawString(""+getExtendedInvItemCamo(TGPlayerInventory.SLOT_BACK), 68, 38, color);
        this.fontRenderer.drawString(""+getExtendedInvItemCamo(TGPlayerInventory.SLOT_HAND), 86, 38, color);
	}
	
	public void initGui() {
		super.initGui();

		int id=BUTTON_ID_SECURITY+1;
										//id, x, y, width, height, text
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+14+12, this.guiTop+59, 12, 12, ">"));
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+14, this.guiTop+59, 12, 12, "<"));
		
		int posX=102;
		int offsetX=18;
		int offsetY=59;
		
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+posX, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+posX, this.guiTop+offsetY+10, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+posX+offsetX, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+posX+offsetX, this.guiTop+offsetY+10, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+posX+offsetX*2, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+posX+offsetX*2, this.guiTop+offsetY+10, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+posX+offsetX*3, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(id++, this.guiLeft+posX+offsetX*3, this.guiTop+offsetY+10, 10, 10, "-"));
		
		
		//Extended inventory face and back slots
		/*this.buttonList.add(new GuiButtonExt(11, this.guiLeft+posX-offsetX-2, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(12, this.guiLeft+posX-offsetX-2, this.guiTop+offsetY+10, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(13, this.guiLeft+posX-offsetX*2-2, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(14, this.guiLeft+posX-offsetX*2-2, this.guiTop+offsetY+10, 10, 10, "-"));*/
		
		
	}

	private int getArmorCamo(int slot){
		EntityPlayer ply = Minecraft.getMinecraft().player;
		
		ItemStack armor = ply.inventory.armorInventory.get(slot);
		if(!armor.isEmpty() && armor.getItem() instanceof ICamoChangeable){
			ICamoChangeable camoArmor = (ICamoChangeable) armor.getItem();
			return camoArmor.getCurrentCamoIndex(armor)+1;
		}
		
		return 0;
	}
	
	private int getExtendedInvItemCamo(int slot){
		EntityPlayer ply = Minecraft.getMinecraft().player;
		
		TGExtendedPlayer props = TGExtendedPlayer.get(ply);
		if (props !=null){
		
			ItemStack item = props.tg_inventory.inventory.get(slot);
			
			if(!item.isEmpty() && item.getItem() instanceof ICamoChangeable){
				ICamoChangeable camoItem = (ICamoChangeable) item.getItem();
				return camoItem.getCurrentCamoIndex(item)+1;
			}
		}
		return 0;
	}
	
}
