package com.asteroidnine.realistictorchesextended.datagen;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        // Register recipes for remaining mod items
    }

    private Item getItem(String name) {
        return getItemFromMod(RealisticTorchesExtended.MOD_ID, name);
    }

    private Item getItemFromMod(String modId, String name) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(modId, name));
    }
}