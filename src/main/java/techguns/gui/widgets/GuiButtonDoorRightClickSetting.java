package techguns.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import techguns.Techguns;
import techguns.tileentities.Door3x3TileEntity;
import techguns.util.TextUtil;

public class GuiButtonDoorRightClickSetting extends GuiButtonExt {
	protected Door3x3TileEntity tile;
	protected int type;
	
	public GuiButtonDoorRightClickSetting(int id, int xPos, int yPos, int width, int height, Door3x3TileEntity tile, int type) {
		super(id, xPos, yPos, width, height, "");
		this.tile=tile;
		this.type=type;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partial) {
		if (this.visible)
        {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            this.mouseDragged(mc, mouseX, mouseY);
            int color = 14737632;

            if (packedFGColour != 0)
            {
                color = packedFGColour;
            }
            else if (!this.enabled)
            {
                color = 10526880;
            }
            else if (this.hovered)
            {
                color = 16777120;
            }

            String buttonText = getDisplayString();
            int strWidth = mc.fontRenderer.getStringWidth(buttonText);
            int ellipsisWidth = mc.fontRenderer.getStringWidth("...");

            if (strWidth > width - 6 && strWidth > ellipsisWidth)
                buttonText = mc.fontRenderer.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";

            this.drawCenteredString(mc.fontRenderer, buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
        }
	}

	protected String getDisplayString() {
		if (type==1) {
			return getStateString(tile.isAutoClose());
		}
		return "";
	}
	protected String getStateString(boolean yes) {
		if (yes) {
			return TextUtil.trans(Techguns.MODID+".yes");
		} else {
			return TextUtil.trans(Techguns.MODID+".no");
		}
	}
}
