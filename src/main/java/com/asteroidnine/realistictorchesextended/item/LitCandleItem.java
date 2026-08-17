package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.RealisticCandleBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class LitCandleItem extends BlockItem {

    public LitCandleItem(RegistryObject<Block> block, Properties properties) {
        super(block.get(), properties);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext context) {
        BlockState state = super.getPlacementState(context);

        if (state != null) {
            // Check if this is the first candle being placed in this block space
            if (state.getValue(net.minecraft.world.level.block.CandleBlock.CANDLES) == 1) {
                return state
                        .setValue(net.minecraft.world.level.block.CandleBlock.LIT, true)
                        .setValue(RealisticCandleBlock.getLitState(), RealisticCandleBlock.LIT)
                        .setValue(RealisticCandleBlock.getBurnTime(), 0);
            }

            return state;
        }

        return null;
    }
}