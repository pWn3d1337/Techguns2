package techguns.recipes;

import net.minecraftforge.common.crafting.IIngredientFactory;

public class IngredientTypeFactory {
	protected String factoryname;
	protected String typename;
	
	public IngredientTypeFactory(String typename, Class<? extends IIngredientFactory> clazz) {
		this.factoryname=clazz.getName();
		this.typename=typename;
	}

	public String getFactoryname() {
		return factoryname;
	}

	public String getTypename() {
		return typename;
	}
	
}