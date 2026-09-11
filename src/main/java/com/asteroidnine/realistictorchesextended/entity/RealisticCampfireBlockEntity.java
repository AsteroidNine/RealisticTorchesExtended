package com.asteroidnine.realistictorchesextended.entity;

import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RealisticCampfireBlockEntity extends CampfireTileEntity {

    public RealisticCampfireBlockEntity() {
        super();
    }

    @Override
    public TileEntityType<?> getType() {
        return ModBlockEntities.REALISTIC_CAMPFIRE_ENTITY.get();
    }
}