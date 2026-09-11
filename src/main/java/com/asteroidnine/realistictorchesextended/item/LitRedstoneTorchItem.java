package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import com.asteroidnine.realistictorchesextended.block.RealisticRedstoneTorchBlock;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.WallOrFloorItem;

public class LitRedstoneTorchItem extends WallOrFloorItem {

    public LitRedstoneTorchItem(Properties properties) {
        super(ModBlocks.REALISTIC_REDSTONE_TORCH.get(), ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get(), properties);
    }

    @Override
    public BlockState getPlacementState(BlockItemUseContext context) {
        BlockState state = super.getPlacementState(context);
        if (state != null) {
            return state.setValue(RealisticRedstoneTorchBlock.getLitState(), RealisticRedstoneTorchBlock.LIT)
                    .setValue(RealisticRedstoneTorchBlock.getBurnTime(), RealisticRedstoneTorchBlock.getInitialBurnTime());
        }
        return null;
    }
}