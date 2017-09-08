package techguns.util;

import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

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
}
