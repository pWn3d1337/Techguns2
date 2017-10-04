package techguns.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import techguns.TGBlocks;
import techguns.TGConfig;
import techguns.blocks.EnumOreType;

public class OreGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
		//If OVERWORLD!
			if (world.provider.getDimension() == 0) {
				if (TGConfig.doOreGenCopper){
					this.addOreSpawn(TGBlocks.TG_ORE.getDefaultState().withProperty(TGBlocks.TG_ORE.ORE_TYPE, EnumOreType.ORE_COPPER), world, random, chunkX*16, chunkZ*16, 16, 16, 5+random.nextInt(3), 12, 5, 80);
				}
				if(TGConfig.doOreGenTin) {
					this.addOreSpawn(TGBlocks.TG_ORE.getDefaultState().withProperty(TGBlocks.TG_ORE.ORE_TYPE, EnumOreType.ORE_TIN), world, random, chunkX*16, chunkZ*16, 16, 16, 4+random.nextInt(3), 10, 5, 60);
				}
				if (TGConfig.doOreGenLead) {
					this.addOreSpawn(TGBlocks.TG_ORE.getDefaultState().withProperty(TGBlocks.TG_ORE.ORE_TYPE, EnumOreType.ORE_LEAD), world, random, chunkX*16, chunkZ*16, 16, 16, 4+random.nextInt(2), 8, 5, 50);
				}
				if (TGConfig.doOreGenUranium){
					this.addOreSpawn(TGBlocks.TG_ORE.getDefaultState().withProperty(TGBlocks.TG_ORE.ORE_TYPE, EnumOreType.ORE_URANIUM), world, random, chunkX*16, chunkZ*16, 16, 16, 4+random.nextInt(2), 4, 4, 24);
				}
				if (TGConfig.doOreGenTitanium){
					this.addOreSpawn(TGBlocks.TG_ORE.getDefaultState().withProperty(TGBlocks.TG_ORE.ORE_TYPE, EnumOreType.ORE_TITANIUM), world, random, chunkX*16, chunkZ*16, 16, 16, 4+random.nextInt(4), 5, 4, 32);
				}
			}

	}

	public void addOreSpawn(IBlockState blockstate, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY)
	{

		int diffBtwnMinMaxY = maxY - minY;
		for (int x = 0; x < chancesToSpawn; x++)
		{
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(diffBtwnMinMaxY);
			int posZ = blockZPos + random.nextInt(maxZ);
			new WorldGenMinable(blockstate, maxVeinSize).generate(world, random, new BlockPos(posX, posY,posZ));
		}
	}
}
