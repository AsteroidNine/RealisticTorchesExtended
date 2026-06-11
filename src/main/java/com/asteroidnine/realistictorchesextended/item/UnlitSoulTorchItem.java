package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.ModBlocks;

import com.asteroidnine.realistictorchesextended.block.RealisticRedstoneTorchBlock;
import com.asteroidnine.realistictorchesextended.block.RealisticSoulTorchBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class UnlitSoulTorchItem extends StandingAndWallBlockItem {

    public UnlitSoulTorchItem(Properties properties, Direction direction) {
        super(ModBlocks.REALISTIC_SOUL_TORCH.get(), ModBlocks.REALISTIC_SOUL_TORCH_WALL.get(), properties, direction);
    }
}