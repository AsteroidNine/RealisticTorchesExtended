package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import net.minecraft.item.WallOrFloorItem;

public class UnlitSoulTorchItem extends WallOrFloorItem {

    public UnlitSoulTorchItem(Properties properties) {
        super(ModBlocks.REALISTIC_SOUL_TORCH.get(), ModBlocks.REALISTIC_SOUL_TORCH_WALL.get(), properties);
    }
}