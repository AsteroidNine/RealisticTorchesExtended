package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import net.minecraft.item.WallOrFloorItem;

public class UnlitRedstoneTorchItem extends WallOrFloorItem {

    public UnlitRedstoneTorchItem(Properties properties) {
        super(ModBlocks.REALISTIC_REDSTONE_TORCH.get(), ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get(), properties);
    }
}