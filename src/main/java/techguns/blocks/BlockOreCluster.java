package techguns.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import techguns.TGOreClusters.OreCluster;
import techguns.util.TextUtil;
import techguns.Techguns;

public class BlockOreCluster<T extends Enum<T> & IEnumOreClusterType> extends GenericBlockMetaEnum<T> {

	//public static java.lang.reflect.Field chunkCacheWorld = ObfuscationReflectionHelper.findField(ChunkCache.class, "field_72815_e");
	
	public BlockOreCluster(String name, Material mat, Class<T> clazz) {
		super(name, mat, clazz);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
	}

	public BlockOreCluster(String name, Material mat, MapColor mc, SoundType soundType, Class<T> clazz) {
		super(name, mat, mc, soundType, clazz);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }

	@Override
	public boolean shouldAutoGenerateJsonForEnum() {
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		
		IBlockState state = this.getStateFromMeta(stack.getItemDamage());
		
		OreCluster ore = Techguns.orecluster.getClusterForType(state.getValue(TYPE));
		if(ore!=null) {
			tooltip.add(TextUtil.trans("techguns.orecluster.mininglevel")+": "+ore.getMininglevel());
			tooltip.add(TextUtil.trans("techguns.orecluster.powermult")+": x"+String.format("%.1f",ore.getMultiplier_power()));
			tooltip.add(TextUtil.trans("techguns.orecluster.amountmult")+": x"+String.format("%.1f",ore.getMultiplier_amount()));
		}
		
	}

	
	
	/*@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		World w=null;
		if(worldIn instanceof World) {
			w = (World) worldIn;
		} else if (worldIn instanceof ChunkCache) {
			try {
				w = (World) chunkCacheWorld.get((ChunkCache)worldIn);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(w!=null) {
			int id = w.provider.getDimension();
		}
		return super.getActualState(state, worldIn, pos);
	}*/

	
}
