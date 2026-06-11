package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.RealisticLanternBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class LitLanternItem extends BlockItem {

    public LitLanternItem(RegistryObject<Block> block, Properties properties) {
        super(block.get(), properties);
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