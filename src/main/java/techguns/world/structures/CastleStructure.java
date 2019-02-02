package techguns.world.structures;

import java.util.Random;

import net.minecraft.world.World;
import techguns.world.dungeon.Dungeon;
import techguns.world.dungeon.DungeonTemplate;
import techguns.world.dungeon.presets.IDungeonPreset;
import techguns.world.dungeon.presets.PresetTemplateTest;

public class CastleStructure extends WorldgenStructure{

	public CastleStructure() {
		this.heightdiffLimit=10;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction,
			BiomeColorType colorType, Random rnd) {

		Dungeon dungeon = new Dungeon(IDungeonPreset.PRESET_CASTLE);
		 
		dungeon.generate(world, posX, posY+1-IDungeonPreset.PRESET_CASTLE.getSizeY(), posZ, sizeX, sizeY, sizeZ);
	}

	
	@Override
	public int getSizeX(Random rnd) {
		return 32+rnd.nextInt(16);
	}

	@Override
	public int getSizeZ(Random rnd) {
		return 32+rnd.nextInt(16);
	}
	
	@Override
	public int getSizeY(Random rnd) {
		return 24+rnd.nextInt(16);
	}
}
