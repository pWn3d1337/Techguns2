package techguns.gui;

import java.awt.Button;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import techguns.Techguns;
import techguns.gui.containers.Door3x3Container;
import techguns.gui.player.TGPlayerInventory;
import techguns.gui.widgets.GuiButtonDoorRightClickSetting;
import techguns.items.armors.ICamoChangeable;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.Door3x3TileEntity;
import techguns.tileentities.operation.CamoBenchRecipes;
import techguns.tileentities.operation.CamoBenchRecipes.CamoBenchRecipe;
import techguns.util.TextUtil;

public class Door3x3Gui extends RedstoneTileEntGui {
	Door3x3TileEntity tile;
	
	public Door3x3Gui(InventoryPlayer player, Door3x3TileEntity tile) {
		super(new Door3x3Container(player, tile), tile);
		this.tile=tile;
		this.tex=DungeonGeneratorGui.texture;
		this.showInventoryText=false;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		int x = 0;
        int y = 0;
        int color = 4210752; //0xff101010;
        
        byte dm = tile.getDoormode();
        
        this.fontRenderer.drawString(TextUtil.trans(Techguns.MODID+".container.door3x3.mode."+dm), 44, 20, color);
        
        this.fontRenderer.drawString((tile.getDoormode()==0?"":ChatFormatting.STRIKETHROUGH)+TextUtil.trans(Techguns.MODID+".container.door3x3.autoclose"), 44, 20+20, color);
       
        if(tile.getDoormode()==1 && tile.getRedstoneBehaviour()==0) {
        	this.fontRenderer.drawString(ChatFormatting.RED+TextUtil.trans(Techguns.MODID+".container.door3x3.redstonewarning"), 44, 20+40, color);
        }
        
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	public void initGui() {
		super.initGui();
		//id, x, y, width, height, text
		this.buttonList.add(new GuiButtonExt(Door3x3TileEntity.BUTTON_ID_DOORMODE, this.guiLeft+8, this.guiTop+16, 32, 16, TextUtil.trans(Techguns.MODID+".mode")));
		this.buttonList.add(new GuiButtonDoorRightClickSetting(Door3x3TileEntity.BUTTON_ID_AUTOCLOSE, this.guiLeft+8, this.guiTop+16+20, 32, 16, tile,1));
		
	}
}
