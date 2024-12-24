package techguns2.block;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import techguns2.block.entity.BioBlobBlockEntity;

public final class BioBlobBlock extends BaseEntityBlock
{
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 2);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private final Map<BlockState, VoxelShape> _shapes;

    public BioBlobBlock(Properties properties)
    {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SIZE, 0)
                .setValue(FACING, Direction.UP));

        this._shapes = makeShapes(this.defaultBlockState());
    }

    @Override
    public @NotNull VoxelShape getShape(
            @NotNull BlockState blockState,
            @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos,
            @NotNull CollisionContext collisionContext
    )
    {
        return this._shapes.get(blockState);
    }

    @Override
    public VoxelShape getCollisionShape(
            @NotNull BlockState blockState,
            @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos,
            @NotNull CollisionContext collisionContext
    )
    {
        return this._shapes.get(blockState);
    }

    @Override
    public boolean isPathfindable(
            @NotNull BlockState blockState,
            @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos,
            @NotNull PathComputationType type
    )
    {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
    {
        builder.add(SIZE, FACING);
    }

    @Override
    public BioBlobBlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
    {
        return new BioBlobBlockEntity(blockPos, blockState);
    }

    private static Map<BlockState, VoxelShape> makeShapes(BlockState defaultState)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> result = ImmutableMap.builder();

        int size = 0;
        while (size < 3)
        {
            int w = 5 - (2 * size);
            int h = 3 + size;

            BlockState sizedState = defaultState.setValue(SIZE, size);

            result.put(sizedState.setValue(FACING, Direction.DOWN),
                    Block.box(w, 0, w, 16 - w, h, 16 - w));
            result.put(sizedState.setValue(FACING, Direction.WEST),
                    Block.box(0, w, w, h, 16 - w, 16 - w));
            result.put(sizedState.setValue(FACING, Direction.NORTH),
                    Block.box(w, w, 0, 16 - w, 16 - w, h));
            result.put(sizedState.setValue(FACING, Direction.SOUTH),
                    //Block.box(w, w, 16 - h, 16 - w, h, 1)); fixed error
                    Block.box(w, w, 16 - h, 16 - w, 16-w, 16));
            result.put(sizedState.setValue(FACING, Direction.UP),
                    Block.box(w, 16-h, w, 16 - w, 16, 16 - w));
            result.put(sizedState.setValue(FACING, Direction.EAST),
                    Block.box(16 - h, w, w, 16, 16 - w, 16 - w));

            size++;
        }

        return result.build();
    }

}
