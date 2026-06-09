package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import com.asteroidnine.realistictorchesextended.block.RealisticRedstoneTorchBlock;

import net.minecraft.core.Direction;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class LitRedstoneTorchItem extends StandingAndWallBlockItem {

    public LitRedstoneTorchItem(Properties properties, Direction direction) {
        super(ModBlocks.REALISTIC_REDSTONE_TORCH.get(), ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get(), properties, direction);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext context) {
        BlockState state = super.getPlacementState(context);
        if (state != null) {
            return state.setValue(
                    RealisticRedstoneTorchBlock.getLitState(),
                    RealisticRedstoneTorchBlock.LIT).setValue(RealisticRedstoneTorchBlock.getBurnTime(),
                    RealisticRedstoneTorchBlock.getInitialBurnTime()
            );
        }
        return null;
    }

}