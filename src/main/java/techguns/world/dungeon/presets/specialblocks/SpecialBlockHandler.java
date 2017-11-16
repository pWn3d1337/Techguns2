package techguns.world.dungeon.presets.specialblocks;

import java.util.List;

import net.minecraft.init.Blocks;
import techguns.TGBlocks;
import techguns.blocks.EnumMilitaryCrateType;
import techguns.util.MBlock;

public class SpecialBlockHandler {

	
	public static void applySpecialMBlocks(List<MBlock> list) {
		
		for(int i=0; i< list.size(); i++) {
			
			MBlock mb = list.get(i);
			
			if(mb.block == TGBlocks.MONSTER_SPAWNER) {
				list.set(i,new MBlockTGSpawner(mb));
			} else if (mb.block == Blocks.SKULL) {
				list.set(i, new MBlockSkullBlock(mb));
			} else if (mb.block == TGBlocks.MILITARY_CRATE) {
				list.set(i, new RandomStateMBlock(mb, EnumMilitaryCrateType.values().length));
			}
			
		}
		
	}
	
}
