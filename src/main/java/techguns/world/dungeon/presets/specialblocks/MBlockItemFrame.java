package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.util.BlockUtils;
import techguns.util.MBlock;

public class MBlockItemFrame extends MBlock {

	ItemStack itemToDisplay = ItemStack.EMPTY;
	
	EnumFacing face = EnumFacing.SOUTH;
	
	public MBlockItemFrame(ItemStack itemToDisplay, EnumFacing facing) {
		super(Blocks.AIR.getDefaultState());
		this.hasTileEntity=true;
		this.layer=1;
		this.itemToDisplay = itemToDisplay;
		this.face = facing;
	}

	@Override
	protected int getPlacementFlags() {
		return 3;
	}

	@Override
	public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
		
		if(!w.isRemote) {
			EntityItemFrame frame = new EntityItemFrame(w, p, rotate(face,rotation));
			w.spawnEntity(frame);
	
			if(!itemToDisplay.isEmpty()) {
				frame.setDisplayedItem(itemToDisplay.copy());
			}
		}
	}

	private EnumFacing rotate (EnumFacing facing, int rotations) {
		for(int i=0;i<rotations;i++) {
			facing = facing.rotateYCCW();
		}
		return facing;
	}
}
