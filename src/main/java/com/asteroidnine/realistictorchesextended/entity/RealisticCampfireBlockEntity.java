package com.asteroidnine.realistictorchesextended.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RealisticCampfireBlockEntity extends CampfireBlockEntity {
    public RealisticCampfireBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.REALISTIC_CAMPFIRE_ENTITY.get();
    }
}


