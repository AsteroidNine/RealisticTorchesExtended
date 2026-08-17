package com.asteroidnine.realistictorchesextended.datagen;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import com.asteroidnine.realistictorchesextended.block.RealisticCandleBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RealisticTorchesExtended.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Base uncolored candle
        makeCandleBlockState(getBlock("realistic_candle"), "candle");

        // 16 colored candles
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getName();
            makeCandleBlockState(getBlock("realistic_" + colorName + "_candle"), colorName + "_candle");
        }
    }

    private void makeCandleBlockState(Block block, String baseModelName) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);

        for (int candles = 1; candles <= 4; candles++) {
            for (int litstate = 0; litstate <= 2; litstate++) {

                String amountPrefix = switch (candles) {
                    case 1 -> "one_candle";
                    case 2 -> "two_candles";
                    case 3 -> "three_candles";
                    case 4 -> "four_candles";
                    default -> "one_candle";
                };

                // litstate 0 is unlit, litstate 1 and 2 use the lit model
                String litSuffix = (litstate > 0) ? "_lit" : "";

                String vanillaModelPath = "minecraft:block/" + baseModelName + "_" + amountPrefix + litSuffix;

                builder.partialState()
                        .with(CandleBlock.CANDLES, candles)
                        .with(RealisticCandleBlock.getLitState(), litstate)
                        .addModels(new ConfiguredModel(new ModelFile.UncheckedModelFile(vanillaModelPath)));
            }
        }
    }

    private Block getBlock(String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(RealisticTorchesExtended.MOD_ID, name));
    }
}