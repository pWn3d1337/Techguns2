package techguns.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class WorldGenTestTool extends GenericItem{

	static String[] modes = new String[]{"Structure", "Dungeon", "Smooth", "Flatten", "Remove Junk",/* "Sphere", "Cylinder"*/};
	
	public WorldGenTestTool(String name, boolean b) {
		super(name, true);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack item = player.getHeldItem(hand);
		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
		if (player.isSneaking()) {
			int mode = 1;
			if (item.getTagCompound().hasKey("mode")) {
				mode = (item.getTagCompound().getInteger("mode")+1) % modes.length;
			}
			item.getTagCompound().setInteger("mode", mode);
			if (world.isRemote) player.sendStatusMessage(new TextComponentString("WorldGen tool mode set to \""+modes[mode]+"\"."), false);
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
	
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		ItemStack item = player.getHeldItem(hand);
		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
		if (player.isSneaking()) {
			int mode = 1;
			if (item.getTagCompound().hasKey("mode")) {
				mode = (item.getTagCompound().getInteger("mode")+1) % modes.length;
			}
			item.getTagCompound().setInteger("mode", mode);
			if (world.isRemote) player.sendStatusMessage(new TextComponentString("WorldGen tool mode set to \""+modes[mode]+"\"."), false);
		}else {
			if (item.getTagCompound().hasKey("x1") && item.getTagCompound().hasKey("y1") && item.getTagCompound().hasKey("z1")) {
				
				if (world.isRemote) player.sendStatusMessage(new TextComponentString("Position2 set ("+x+"/"+y+"/"+z+")."), false);
				int x1 = item.getTagCompound().getInteger("x1");
				int y1 = item.getTagCompound().getInteger("y1");
				int z1 = item.getTagCompound().getInteger("z1");
				int sizeX = Math.abs(x1-x)+1;
				int sizeY = Math.abs(y1-y)+1;
				int sizeZ = Math.abs(z1-z)+1;
				
				if (world.isRemote) player.sendStatusMessage(new TextComponentString("Size: ("+sizeX+"/"+sizeY+"/"+sizeZ+")."), false);
				
				if (!world.isRemote) {
					int mode = 0;
					if (item.getTagCompound().hasKey("mode")) {
						mode = item.getTagCompound().getInteger("mode");
					}
					doOperation(world, Math.min(x, x1), Math.min(y, y1), Math.min(z, z1), sizeX, sizeY, sizeZ, mode);

				}
	
				item.getTagCompound().removeTag("x1");
				item.getTagCompound().removeTag("y1");
				item.getTagCompound().removeTag("z1");
				
			}else {
				item.getTagCompound().setInteger("x1",x);
				item.getTagCompound().setInteger("y1",y);
				item.getTagCompound().setInteger("z1",z);
				if (world.isRemote) player.sendStatusMessage(new TextComponentString("Position1 set ("+x+"/"+y+"/"+z+")."), false);
			}
		}
		return EnumActionResult.SUCCESS;
    }
	
	
	private void doOperation(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, int mode) {
		
		switch (mode) {
		case 0: //structure
			
			break;
		case 1: //dungeon
			
			break;
		}

	}


}
