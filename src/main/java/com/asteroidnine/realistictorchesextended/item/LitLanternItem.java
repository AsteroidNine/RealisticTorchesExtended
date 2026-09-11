package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.block.RealisticLanternBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class LitLanternItem extends BlockItem {

    public LitLanternItem(RegistryObject<Block> block, Properties properties) {
        super(block.get(), properties);
    }

    @Override
    public BlockState getPlacementState(BlockItemUseContext context) {
        World level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        boolean isWater = level.getFluidState(pos).getType() == Fluids.WATER;

        BlockState state = super.getPlacementState(context);

        if (state != null) {
            return state
                    .setValue(LanternBlock.WATERLOGGED, isWater)
                    .setValue(RealisticLanternBlock.getLitState(), RealisticLanternBlock.LIT)
                    .setValue(RealisticLanternBlock.getBurnTime(), RealisticLanternBlock.getInitialBurnTime());
        }

        return null;
    }
}