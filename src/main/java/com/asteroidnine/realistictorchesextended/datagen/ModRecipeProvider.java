package com.asteroidnine.realistictorchesextended.datagen;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        Item unlitCandle = getItem("unlit_candle");
        Item litCandle = getItem("lit_candle");
        Item matchbox = getItemFromMod("realistictorches", "matchbox");

        // Base Unlit Candle: String + Honeycomb
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, unlitCandle)
                .pattern("S")
                .pattern("H")
                .define('S', Items.STRING)
                .define('H', Items.HONEYCOMB)
                .unlockedBy("has_string", has(Items.STRING))
                .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .save(writer);

        // Base Lit Candle: Unlit Candle + Matchbox
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, litCandle)
                .requires(unlitCandle)
                .requires(matchbox)
                .unlockedBy("has_unlit_candle", has(unlitCandle))
                .save(writer, new ResourceLocation(RealisticTorchesExtended.MOD_ID, "lit_candle_match"));

        // Generate recipes for all 16 dye colors
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getName();
            Item dyeItem = DyeItem.byColor(color);
            Item unlitColoredCandle = getItem("unlit_" + colorName + "_candle");
            Item litColoredCandle = getItem("lit_" + colorName + "_candle");

            // 1. Dye Unlit Candle: Unlit Base Candle + Dye -> Unlit Colored Candle
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, unlitColoredCandle)
                    .requires(unlitCandle)
                    .requires(dyeItem)
                    .unlockedBy("has_unlit_candle", has(unlitCandle))
                    .save(writer, new ResourceLocation(RealisticTorchesExtended.MOD_ID, "unlit_" + colorName + "_candle"));

            // 2. Dye Lit Candle: Lit Base Candle + Dye -> Lit Colored Candle
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, litColoredCandle)
                    .requires(litCandle)
                    .requires(dyeItem)
                    .unlockedBy("has_lit_candle", has(litCandle))
                    .save(writer, new ResourceLocation(RealisticTorchesExtended.MOD_ID, "lit_" + colorName + "_candle"));

            // 3. Light Colored Candle: Unlit Colored Candle + Matchbox -> Lit Colored Candle
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, litColoredCandle)
                    .requires(unlitColoredCandle)
                    .requires(matchbox)
                    .unlockedBy("has_unlit_" + colorName + "_candle", has(unlitColoredCandle))
                    .save(writer, new ResourceLocation(RealisticTorchesExtended.MOD_ID, "lit_" + colorName + "_candle_match"));
        }
    }

    private Item getItem(String name) {
        return getItemFromMod(RealisticTorchesExtended.MOD_ID, name);
    }

    private Item getItemFromMod(String modId, String name) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(modId, name));
    }
}