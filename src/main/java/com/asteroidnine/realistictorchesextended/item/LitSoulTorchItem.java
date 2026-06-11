package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import com.asteroidnine.realistictorchesextended.block.RealisticRedstoneTorchBlock;

import com.asteroidnine.realistictorchesextended.block.RealisticSoulTorchBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class LitSoulTorchItem extends StandingAndWallBlockItem {

    public LitSoulTorchItem(Properties properties, Direction direction) {
        super(ModBlocks.REALISTIC_SOUL_TORCH.get(), ModBlocks.REALISTIC_SOUL_TORCH_WALL.get(), properties, direction);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext context) {
        BlockState state = super.getPlacementState(context);
        if (state != null) {
            return state.setValue(RealisticSoulTorchBlock.getLitState(), RealisticSoulTorchBlock.LIT)
                    .setValue(RealisticSoulTorchBlock.getBurnTime(), RealisticSoulTorchBlock.getInitialBurnTime());
        }
        return null;
    }

}