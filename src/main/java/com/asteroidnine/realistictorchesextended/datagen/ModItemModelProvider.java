package com.asteroidnine.realistictorchesextended.datagen;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RealisticTorchesExtended.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Base uncolored candle items
        makeCandleItem("unlit_candle", "minecraft:item/candle");
        makeCandleItem("lit_candle", "minecraft:item/candle");

        // 16 colored candle items
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getName();
            makeCandleItem("unlit_" + colorName + "_candle", "minecraft:item/" + colorName + "_candle");
            makeCandleItem("lit_" + colorName + "_candle", "minecraft:item/" + colorName + "_candle");
        }
    }

    private void makeCandleItem(String customItemName, String vanillaTexturePath) {
        withExistingParent(customItemName, "minecraft:item/generated")
                .texture("layer0", vanillaTexturePath);
    }
}