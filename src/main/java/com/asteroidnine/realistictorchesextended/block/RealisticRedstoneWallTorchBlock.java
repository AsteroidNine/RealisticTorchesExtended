package com.asteroidnine.realistictorchesextended.block;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class RealisticRedstoneWallTorchBlock extends RealisticRedstoneTorchBlock {
    public static final DirectionProperty HORIZONTAL_FACING;

    public RealisticRedstoneWallTorchBlock() {
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if ((Integer)state.getValue(LITSTATE) == 2 || (Integer)state.getValue(LITSTATE) == 1 && level.getRandom().nextInt(2) == 1) {
            Direction direction = (Direction)state.getValue(HORIZONTAL_FACING);
            double d0 = (double)pos.getX() + (double)0.5F;
            double d1 = (double)pos.getY() + 0.7;
            double d2 = (double)pos.getZ() + (double)0.5F;
            Direction direction1 = direction.getOpposite();
            level.addParticle(ParticleTypes.SMOKE, d0 + 0.27 * (double)direction1.getStepX(), d1 + 0.22, d2 + 0.27 * (double)direction1.getStepZ(), (double)0.0F, (double)0.0F, (double)0.0F);
            level.addParticle(ParticleTypes.FLAME, d0 + 0.27 * (double)direction1.getStepX(), d1 + 0.22, d2 + 0.27 * (double)direction1.getStepZ(), (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isClientSide()) return;

        Direction attachDirection = ((Direction)state.getValue(HORIZONTAL_FACING)).getOpposite();
        BlockPos wallPos = pos.relative(attachDirection);
        boolean isWallPowered = level.hasSignal(wallPos, attachDirection);

        if (isWallPowered) {
            if ((Integer)state.getValue(LITSTATE) > 0) {
                this.playExtinguishSound(level, pos);
                this.changeToUnlit(level, pos, state);
            }
            return;
        }

        // Fallback to standard decay logic from parent if not turned off by redstone
        super.tick(state, level, pos, random);
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getValue(LITSTATE) > 0 && pBlockState.getValue(HORIZONTAL_FACING) != pSide ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pSide == Direction.DOWN ? this.getSignal(pBlockState, pBlockAccess, pPos, pSide) : 0;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        if (!level.isClientSide) {
            Direction attachDirection = ((Direction)state.getValue(HORIZONTAL_FACING)).getOpposite();
            BlockPos wallPos = pos.relative(attachDirection);

            boolean isWallPowered = level.hasSignal(wallPos, attachDirection);

            if ((Integer)state.getValue(LITSTATE) > 0 && isWallPowered) {
                this.changeToUnlit(level, pos, state);
            }
        }
        super.neighborChanged(state, level, pos, block, neighborPos, isMoving);
    }

    @Override
    public void changeToLit(Level level, BlockPos pos, BlockState state) {
        level.setBlock(pos, ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get().defaultBlockState()
                .setValue(LITSTATE, LIT)
                .setValue(BURNTIME, getInitialBurnTime())
                .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 3);

        level.updateNeighborsAt(pos, this);
        if (SHOULD_BURN_OUT) {
            level.scheduleTick(pos, this, 1200);
        }
    }

    @Override
    public void changeToSmoldering(Level level, BlockPos pos, BlockState state, int newBurnTime) {
        if (SHOULD_BURN_OUT) {
            level.setBlock(pos, (BlockState)((BlockState)((BlockState)((Block)ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get()).defaultBlockState()
                    .setValue(LITSTATE, 1))
                    .setValue(BURNTIME, newBurnTime))
                    .setValue(HORIZONTAL_FACING, (Direction)state.getValue(HORIZONTAL_FACING)), 3);

            level.updateNeighborsAt(pos, this);
            level.scheduleTick(pos, this, 1200);
        }
    }

    @Override
    public void changeToUnlit(Level level, BlockPos pos, BlockState state) {
        if (SHOULD_BURN_OUT) {
            if ((Boolean) ConfigHandler.noRelightEnabled.get()) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            } else {
                level.setBlock(pos, (BlockState)((Block)ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get()).defaultBlockState()
                        .setValue(HORIZONTAL_FACING, (Direction)state.getValue(HORIZONTAL_FACING)), 3);

                level.scheduleTick(pos, this, 1200);
            }
            level.updateNeighborsAt(pos, this);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{HORIZONTAL_FACING});
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return WallTorchBlock.getShape(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState otherState, LevelAccessor level, BlockPos pos, BlockPos otherPos) {
        return facing.getOpposite() == state.getValue(HORIZONTAL_FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = (Direction)state.getValue(HORIZONTAL_FACING);
        BlockPos onPos = pos.relative(direction.getOpposite());
        BlockState onState = level.getBlockState(onPos);
        return onState.isFaceSturdy(level, onPos, direction);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return Blocks.WALL_TORCH.rotate(state, rot);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return Blocks.WALL_TORCH.mirror(state, mirror);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = Blocks.WALL_TORCH.getStateForPlacement(context);
        return blockstate == null ? null : (BlockState)this.defaultBlockState().setValue(HORIZONTAL_FACING, (Direction)blockstate.getValue(HORIZONTAL_FACING));
    }

    static {
        HORIZONTAL_FACING = HorizontalDirectionalBlock.FACING;
    }
}