package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import com.asteroidnine.realistictorchesextended.block.RealisticSoulTorchBlock;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.WallOrFloorItem;

public class LitSoulTorchItem extends WallOrFloorItem {

    public LitSoulTorchItem(Properties properties) {
        super(ModBlocks.REALISTIC_SOUL_TORCH.get(), ModBlocks.REALISTIC_SOUL_TORCH_WALL.get(), properties);
    }

    @Override
    public BlockState getPlacementState(BlockItemUseContext context) {
        BlockState state = super.getPlacementState(context);
        if (state != null) {
            return state.setValue(RealisticSoulTorchBlock.getLitState(), RealisticSoulTorchBlock.LIT)
                    .setValue(RealisticSoulTorchBlock.getBurnTime(), RealisticSoulTorchBlock.getInitialBurnTime());
        }
        return null;
    }
}