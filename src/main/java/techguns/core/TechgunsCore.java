package techguns.core;

import com.google.common.eventbus.EventBus;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class TechgunsCore extends DummyModContainer {

	public TechgunsCore(){
		super(new ModMetadata());
		ModMetadata meta = this.getMetadata();
		meta.modId = "techguns_core";
		meta.version = "1.12.2-1.0";
		meta.name = "Techguns Core";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
	
}
