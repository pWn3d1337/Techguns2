package techguns.plugins.chisel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import techguns.blocks.GenericBlockMetaEnum;

public class ChiselIMCHelper {
	
	public static <T extends Enum<T> & IStringSerializable>void addChiselVariants(String group, GenericBlockMetaEnum<T> block, Class<T> clazz) {
		
		for(T t: clazz.getEnumConstants()) {
			addChiselVariation(group, block.getRegistryName(), block.getMetaFromState(block.getDefaultState().withProperty(block.TYPE, t)));
		}
	}
	
	public static void addChiselVariation(String group, ResourceLocation registryName, int meta) {
		NBTTagCompound tags = new NBTTagCompound();
		tags.setString("group", group);
		tags.setString("block", registryName.toString());
		tags.setInteger("meta", meta);
		
		FMLInterModComms.sendMessage("chisel", "add_variation", tags);
	}
	
}
