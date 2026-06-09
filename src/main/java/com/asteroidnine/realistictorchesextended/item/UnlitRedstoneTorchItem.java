package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.ModBlocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.StandingAndWallBlockItem;

public class UnlitRedstoneTorchItem extends StandingAndWallBlockItem {

    public UnlitRedstoneTorchItem(Properties properties, Direction direction) {
        super(ModBlocks.REALISTIC_REDSTONE_TORCH.get(), ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get(), properties, direction);
    }
}