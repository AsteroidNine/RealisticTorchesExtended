package com.asteroidnine.realistictorchesplus.item;

import com.asteroidnine.realistictorchesplus.block.ModBlocks;
import com.asteroidnine.realistictorchesplus.block.RealisticLanternBlock;
import com.asteroidnine.realistictorchesplus.block.RealisticRedstoneTorchBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class LitLanternItem extends BlockItem {

    public LitLanternItem(Properties properties) {
        super(ModBlocks.REALISTIC_LANTERN.get(), properties);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        net.minecraft.world.level.material.FluidState fluidState = level.getFluidState(pos);
        boolean isWater = fluidState.getType() == net.minecraft.world.level.material.Fluids.WATER;

        BlockState state = super.getPlacementState(context);

        if (state != null) {
            return state
                    .setValue(net.minecraft.world.level.block.LanternBlock.WATERLOGGED, isWater)
                    .setValue(RealisticLanternBlock.getLitState(), RealisticLanternBlock.LIT)
                    .setValue(RealisticLanternBlock.getBurnTime(), RealisticLanternBlock.getInitialBurnTime());
        }

        return null;
    }
}