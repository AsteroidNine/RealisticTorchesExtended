package com.asteroidnine.realistictorchesextended.datagen;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper exFileHelper) {
        super(generator, RealisticTorchesExtended.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Register blockstates for your remaining blocks (Torches, Lanterns, Campfires)
    }

    private Block getBlock(String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(RealisticTorchesExtended.MOD_ID, name));
    }
}