package com.asteroidnine.realistictorchesplus.block;

import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RealisticRedstoneTorchBlock extends RealisticTorchBlock {

    public RealisticRedstoneTorchBlock() {
        super();
    }

    @Override
    public void changeToLit(Level level, BlockPos pos, BlockState state) {
        level.setBlock(pos, ModBlocks.REALISTIC_REDSTONE_TORCH.get().defaultBlockState()
                .setValue(LITSTATE, LIT)
                .setValue(BURNTIME, getInitialBurnTime()), 2);

        this.updateNeighbors(level, pos);

        if (SHOULD_BURN_OUT) {
            level.scheduleTick(pos, this, TICK_INTERVAL);
        }
    }

    private void updateNeighbors(Level level, BlockPos pos) {
        level.updateNeighborsAt(pos, this);
        level.updateNeighborsAt(pos.above(), this);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getValue(LITSTATE) > 0 && Direction.UP != pSide ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return direction == Direction.DOWN ? state.getSignal(level, pos, direction) : 0;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        if (!level.isClientSide) {
            if (state.getValue(LITSTATE) > 0 && (level.hasSignal(pos.below(), Direction.DOWN) || level.getBestNeighborSignal(pos.below()) > 0)) {
                level.setBlock(pos, state.setValue(LITSTATE, 0), 2);
                this.updateNeighbors(level, pos);
            }
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        for(Direction direction : Direction.values()) {
            pLevel.updateNeighborsAt(pPos.relative(direction), this);
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving) {
            for(Direction direction : Direction.values()) {
                pLevel.updateNeighborsAt(pPos.relative(direction), this);
            }
        }
    }
}
