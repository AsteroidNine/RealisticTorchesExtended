package com.asteroidnine.realistictorchesextended.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class UnlitLanternItem extends BlockItem {

    public UnlitLanternItem(RegistryObject<Block> block, Properties properties) {
        super(block.get(), properties);
    }

}