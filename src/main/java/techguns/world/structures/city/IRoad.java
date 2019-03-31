package techguns.world.structures.city;

import net.minecraft.world.World;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public interface IRoad {
	
	public void setRoadBlocks(World world, int x, int y, int z, int roadWidth, int roadLength, int heightDif, boolean alignment, BiomeColorType colorType);

}