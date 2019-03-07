package techguns.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.gui.containers.UpgradeBenchContainer;
import techguns.tileentities.UpgradeBenchTileEnt;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.util.TextUtil;

public class UpgradeBenchGui extends OwnedTileEntGui {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/upgrade_bench_gui.png");
	
	protected UpgradeBenchTileEnt tile;
	protected InventoryPlayer invplayer;
	protected UpgradeBenchContainer container;
	
	public UpgradeBenchGui(InventoryPlayer player, UpgradeBenchTileEnt tile) {
		super(new UpgradeBenchContainer(player,tile), tile);
		this.container = (UpgradeBenchContainer) this.inventorySlots;
		this.tile=tile;
		this.invplayer=player;
		this.tex=texture;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (!this.container.outputSlot.getStackInSlot(0).isEmpty()) {
			int i = 3-(int) (tile.getWorld().getTotalWorldTime()%4);
			float hue = (tile.getWorld().getTotalWorldTime()%360)/360f;
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			
			int x = i>=2 ? 0 : 89;
			int y = i%2==1 ? 0 : 31;
			
			Color c = new Color(Color.HSBtoRGB(hue, 1f, 1f));
			this.drawTexturedModalRect(k + 37, l + 40, 0+x, 167 +y, 89, 31, c.getRed(),c.getGreen(),c.getBlue(),255);
		}

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		int xp = this.container.getXp_needed();
		String s = TextUtil.transTG("gui.xpcost")+": "+xp;
		
		int color = (this.mc.player.capabilities.isCreativeMode || xp <= this.mc.player.experienceTotal) ? 8453920 : 16736352; //same colors as anvil.
		
		this.drawXPText(TextUtil.transTG("gui.xp")+": "+this.mc.player.experienceTotal, 95, 17, 8453920);
		this.drawXPText(s, 95, 17+10, color);
		
	}

	protected void drawXPText(String s, int x, int y, int color) {
		int j = -16777216 | (color & 16579836) >> 2 | color & -16777216;
		
        this.fontRenderer.drawString(s, x, y+1, j);
        this.fontRenderer.drawString(s, x + 1, y, j);
        this.fontRenderer.drawString(s, x + 1, y+1, j);
        
        this.fontRenderer.drawString(s, x, y, color);
	}
}
