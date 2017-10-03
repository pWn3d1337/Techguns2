package techguns.world.dungeon;

public class DungeonPreset {

	public int sizeXZ;
	public int sizeY;
	
	//List<TemplateEntry> templates;
	
	public DungeonPreset() {
		// TODO Auto-generated constructor stub
	}

	
	class TemplateEntry {
		int layerMin;
		int layerMax;
		float probability;
		String templateName;
	}
}
