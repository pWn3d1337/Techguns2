package techguns.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.items.guns.MiningDrill;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockUtils {
	
	private static final Joiner COMMA_JOINER = Joiner.on(',');
	private static final Function<Entry<IProperty<?>, Comparable<?>>, String> MAP_ENTRY_TO_STRING = new Function<Entry<IProperty<?>, Comparable<?>>, String>() {
		@Nullable
		public String apply(@Nullable Entry<IProperty<?>, Comparable<?>> p_apply_1_) {
			if (p_apply_1_ == null) {
				return "<NULL>";
			} else {
				IProperty<?> iproperty = (IProperty) p_apply_1_.getKey();
				return iproperty.getName() + "=" + this.getPropertyName(iproperty, p_apply_1_.getValue());
			}
		}

		private <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> entry) {
			return property.getName((T) entry);
		}
	};
	
	public static String getBlockStateVariantString(IBlockState state) {
		StringBuilder sb = new StringBuilder();
		COMMA_JOINER.appendTo(sb, Iterables.transform(state.getProperties().entrySet(), MAP_ENTRY_TO_STRING));
		return sb.toString();
	}
	
	public static BlockPos rotateAroundY(BlockPos pos, BlockPos axis, int steps) {
		int offsetX = pos.getX()-axis.getX();
		int offsetZ = pos.getZ()-axis.getZ();
		
		int newOffsetX=offsetX;
		int newOffsetZ=offsetZ;
		
		for(int i=steps; i>0; i--) {
			int tmp = newOffsetX;
			
			newOffsetX=newOffsetZ;
			newOffsetZ=-tmp;
		}
		return new BlockPos(axis.getX()+newOffsetX, pos.getY(), axis.getZ()+newOffsetZ);
	}
	
	public static BlockPos rotateAroundY(BlockPos pos, Vec3d axis, int steps) {
		
		Vec3d v_pos = new Vec3d(pos.getX()+0.5d, 0d, pos.getZ()+0.5d);
		
		double offsetX = v_pos.x-axis.x;
		double offsetZ = v_pos.z-axis.z;
		
		double newOffsetX=offsetX;
		double newOffsetZ=offsetZ;
		
		for(int i=steps; i>0; i--) {
			double tmp = newOffsetX;
			
			newOffsetX=newOffsetZ;
			newOffsetZ=-tmp;
		}
		return new BlockPos(axis.x+newOffsetX, pos.getY(), axis.z+newOffsetZ);
	}
	
	public static MutableBlockPos rotateAroundY(MutableBlockPos pos, Vec3d axis, int steps) {
		
		Vec3d v_pos = new Vec3d(pos.getX()+0.5d, 0d, pos.getZ()+0.5d);
		
		double offsetX = v_pos.x-axis.x;
		double offsetZ = v_pos.z-axis.z;
		
		double newOffsetX=offsetX;
		double newOffsetZ=offsetZ;
		
		for(int i=steps; i>0; i--) {
			double tmp = newOffsetX;
			
			newOffsetX=newOffsetZ;
			newOffsetZ=-tmp;
		}
		pos.setPos(axis.x+newOffsetX, pos.getY(), axis.z+newOffsetZ);
		return pos;
	}
	
	protected static boolean checkMiningLevels(World world, EntityPlayer ply, BlockPos b, GenericGunMeleeCharge miningtool, ItemStack stack) {
		IBlockState state = world.getBlockState(b);
		String tool = state.getBlock().getHarvestTool(state);
		if (state.getBlock().canCollideCheck(state, false) && state.getBlock().canHarvestBlock(world, b, ply)) {
			if(tool==null) {
				return true;
			} else {
				return state.getBlock().getHarvestLevel(state) <= miningtool.getHarvestLevel(stack, tool, ply, state);
			}
		}
		return false;
	}
	
	public static List<BlockPos> getBlockPlaneAroundAxisForMining(World world, EntityPlayer ply, BlockPos center, EnumFacing.Axis axis, int radius, boolean includeCenter, @Nullable GenericGunMeleeCharge miningtool, ItemStack stack){
		List<BlockPos> entries = new ArrayList<BlockPos>();
		
		Iterable<BlockPos> blocks;
		switch(axis) {
			case X:
				blocks = BlockPos.getAllInBox(center.add(0, -radius, -radius), center.add(0, radius, radius));
				break;
			case Y:
				blocks = BlockPos.getAllInBox(center.add(-radius,0, -radius), center.add(radius,0, radius));
				break;
			case Z:
			default:
				blocks = BlockPos.getAllInBox(center.add(-radius, -radius,0), center.add(radius, radius,0));
				break;
		}

		blocks.forEach(b -> {
			if(includeCenter || !b.equals(center)) {
				if(miningtool==null || stack.isEmpty() || checkMiningLevels(world,ply, b, miningtool, stack)) {
					entries.add(b);
				}
			}
		});
		return entries;
	}
}
