package techguns.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.entities.projectiles.GenericProjectile;
import techguns.items.ItemTGDoor2x1;

public class BlockTGDoor2x1 extends BlockDoor implements IGenericBlock {

	ItemTGDoor2x1 placer;
	
	public BlockTGDoor2x1(String name, ItemTGDoor2x1 placer) {
		super(Material.IRON);
		this.init(this, name, true);
		this.setSoundType(SoundType.METAL);
		this.placer=placer;
		this.placer.setBlock(this);
	}
	
    /**
     * Called when the block is right clicked by a player.
     */
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
       
        BlockPos blockpos = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
        IBlockState temp = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);
        IBlockState iblockstate = temp.getActualState(worldIn, blockpos);
        
        if (iblockstate.getBlock() != this)
        {
            return false;
        }
        else
        {
        	
            state = iblockstate.cycleProperty(OPEN);
        	
        	//check if double door
        	EnumFacing doorfacing = iblockstate.getValue(FACING);
        	EnumFacing offsetfacing;

        	if(iblockstate.getValue(HINGE)==EnumHingePosition.LEFT) {
        		offsetfacing = doorfacing.rotateY();
        	} else {
        		offsetfacing = doorfacing.rotateYCCW();
        	}

        	BlockPos offsetPos = blockpos.offset(offsetfacing);
        	IBlockState offsetState = worldIn.getBlockState(offsetPos);
        	if(offsetState.getBlock()==iblockstate.getBlock() && offsetState.getValue(OPEN)==iblockstate.getValue(OPEN)) {
                worldIn.setBlockState(offsetPos, offsetState.withProperty(OPEN, state.getValue(OPEN)), 10);
                worldIn.markBlockRangeForRenderUpdate(offsetPos, pos.offset(offsetfacing));
        	}
        	
            worldIn.setBlockState(blockpos, state, 10);
            worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
            //worldIn.playEvent(playerIn, ((Boolean)state.getValue(OPEN)).booleanValue() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), TGSounds.BUNKER_DOOR_OPEN, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            return true;
        }
        
    }
	
    /**
     * Gets the localized name of this block. Used for the statistics page.
     */
	@Override
    public String getLocalizedName()
    {
        return I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.BLOCK;
    }
	
    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this.getItem());
    }

    private Item getItem()
    {
        return this.placer;
    }

	@Override
	public ItemBlock createItemBlock() {
		return new GenericItemBlock(this);
	}

	@Override
	public void registerBlock(Register<Block> event) {
		event.getRegistry().register(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {

	}
    
    
}
