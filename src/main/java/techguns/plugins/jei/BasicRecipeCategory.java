package techguns.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.gui.AmmoPressGui;
import techguns.util.TextUtil;

public abstract class BasicRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	protected IDrawable background;
	protected final String localizedName;
	
	protected final IGuiHelper guiHelper;
	
	public static final int JEI_OFFSET_X=-8;
	public static final int JEI_OFFSET_Y=-16;
	
	protected final String uid;
	
	protected IDrawableAnimated powerbar;
	protected IDrawableStatic powerbar_static;
	
	public BasicRecipeCategory(IGuiHelper guiHelper, ResourceLocation guiTexture, String name, String UID) {
		background = guiHelper.createDrawable(guiTexture, 7, 15, 162, 63);
		localizedName = TextUtil.trans(Techguns.MODID+".container."+name);
		this.guiHelper=guiHelper;
		this.uid = UID;
		this.powerbar_static = guiHelper.createDrawable(AmmoPressGui.texture, 250, 0, 6, 50);
		this.powerbar = guiHelper.createAnimatedDrawable(powerbar_static, 100, IDrawableAnimated.StartDirection.TOP, true);
	}
	
	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public String getModName() {
		return Techguns.MODID;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

}
