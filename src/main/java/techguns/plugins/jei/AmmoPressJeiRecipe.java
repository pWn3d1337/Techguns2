package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.client.Minecraft;
import techguns.TGItems;
import techguns.Techguns;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.operation.AmmoPressBuildPlans;
import techguns.tileentities.operation.IMachineRecipe;
import techguns.util.TextUtil;

public class AmmoPressJeiRecipe extends BasicRecipeWrapper {
	int type;
	
	public AmmoPressJeiRecipe(IMachineRecipe recipe, int type) {
		super(recipe);
		this.type=type;
	}

	@Override
	public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		int x = 0 + BasicRecipeCategory.JEI_OFFSET_X;
		int y = 0 + BasicRecipeCategory.JEI_OFFSET_Y;
		
		int color = 4210752;
		
        String s2="";
        switch(type){
        	case 0:
        		s2=TextUtil.trans(TGItems.PISTOL_ROUNDS.getUnlocalizedName()+".name");
        		break;
        	case 1:
        		s2=TextUtil.trans(TGItems.SHOTGUN_ROUNDS.getUnlocalizedName()+".name");
        		break;
        	case 2:
        		s2=TextUtil.trans(TGItems.RIFLE_ROUNDS.getUnlocalizedName()+".name");
        		break;
        	case 3:
        		s2=TextUtil.trans(TGItems.SNIPER_ROUNDS.getUnlocalizedName()+".name");
        		break;
        }
        
        mc.fontRenderer.drawString(TextUtil.trans(Techguns.MODID+".gui.ammopress.build")+":", x+20, y+30, color);
        mc.fontRenderer.drawString(s2, x+20, y+40, color);
	}


	public static List<AmmoPressJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<AmmoPressJeiRecipe> recipes = new ArrayList<AmmoPressJeiRecipe>();
		
		for (int i=0;i<4;i++) {
			recipes.add(new AmmoPressJeiRecipe(AmmoPressBuildPlans.getRecipeForType(i),i));
		}
		
		return recipes;
	}

	@Override
	protected int getRFperTick() {
		return AmmoPressTileEnt.POWER_PER_TICK;
	}
}
