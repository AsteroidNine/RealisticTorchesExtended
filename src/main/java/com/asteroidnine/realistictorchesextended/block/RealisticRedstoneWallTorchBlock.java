package com.asteroidnine.realistictorchesextended.block;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class RealisticRedstoneWallTorchBlock extends RealisticRedstoneTorchBlock {
    public static final DirectionProperty HORIZONTAL_FACING;

    public RealisticRedstoneWallTorchBlock() {
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public void animateTick(BlockState state, World level, BlockPos pos, Random random) {
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

    public void tick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
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

        super.tick(state, level, pos, random);
    }

    @Override
    public int getSignal(BlockState pBlockState, IBlockReader pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getValue(LITSTATE) > 0 && pBlockState.getValue(HORIZONTAL_FACING) != pSide ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState pBlockState, IBlockReader pBlockAccess, BlockPos pPos, Direction pSide) {
        return pSide == Direction.DOWN ? this.getSignal(pBlockState, pBlockAccess, pPos, pSide) : 0;
    }

    @Override
    public void neighborChanged(BlockState state, World level, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
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
    public void changeToLit(World level, BlockPos pos, BlockState state) {
        level.setBlock(pos, ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get().defaultBlockState()
                .setValue(LITSTATE, LIT)
                .setValue(BURNTIME, getInitialBurnTime())
                .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 3);

        level.updateNeighborsAt(pos, this);
        if (SHOULD_BURN_OUT) {
            level.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    @Override
    public void changeToSmoldering(World level, BlockPos pos, BlockState state, int newBurnTime) {
        if (SHOULD_BURN_OUT) {
            level.setBlock(pos, (BlockState)((BlockState)((BlockState)((Block)ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get()).defaultBlockState()
                    .setValue(LITSTATE, 1))
                    .setValue(BURNTIME, newBurnTime))
                    .setValue(HORIZONTAL_FACING, (Direction)state.getValue(HORIZONTAL_FACING)), 3);

            level.updateNeighborsAt(pos, this);
            level.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    @Override
    public void changeToUnlit(World level, BlockPos pos, BlockState state) {
        if (SHOULD_BURN_OUT) {
            if ((Boolean) ConfigHandler.noRelightEnabled.get()) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            } else {
                level.setBlock(pos, (BlockState)((Block)ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get()).defaultBlockState()
                        .setValue(HORIZONTAL_FACING, (Direction)state.getValue(HORIZONTAL_FACING)), 3);

                level.getBlockTicks().scheduleTick(pos, this, 1200);
            }
            level.updateNeighborsAt(pos, this);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{HORIZONTAL_FACING});
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader blockGetter, BlockPos pos, ISelectionContext context) {
        return WallTorchBlock.getShape(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState otherState, IWorld level, BlockPos pos, BlockPos otherPos) {
        return facing.getOpposite() == state.getValue(HORIZONTAL_FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader level, BlockPos pos) {
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = Blocks.WALL_TORCH.getStateForPlacement(context);
        return blockstate == null ? null : (BlockState)this.defaultBlockState().setValue(HORIZONTAL_FACING, (Direction)blockstate.getValue(HorizontalBlock.FACING));
    }

    static {
        HORIZONTAL_FACING = HorizontalBlock.FACING;
    }
}