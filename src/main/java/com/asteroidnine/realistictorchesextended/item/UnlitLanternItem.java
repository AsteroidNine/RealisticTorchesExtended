package com.asteroidnine.realistictorchesextended.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;

public class UnlitLanternItem extends BlockItem {

    public UnlitLanternItem(RegistryObject<Block> block, Properties properties) {
        super(block.get(), properties);
    }
}