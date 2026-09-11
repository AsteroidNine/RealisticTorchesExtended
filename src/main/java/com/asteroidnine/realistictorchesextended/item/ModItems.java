package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import com.asteroidnine.realistictorchesextended.block.ModBlocks;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RealisticTorchesExtended.MOD_ID);

    private static Item.Properties defaultProperties() {
        return new Item.Properties().tab(ModCreativeModeTabs.REALISTIC_TORCHES_EXTENDED_TAB);
    }

    public static final RegistryObject<Item> GLOWING_REDSTONE_CRYSTAL = ITEMS.register("glowing_redstone_crystal",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> GLOWING_REDSTONE_PASTE = ITEMS.register("glowing_redstone_paste",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> GLOWING_SOUL_CRYSTAL = ITEMS.register("glowing_soul_crystal",
            () -> new Item(defaultProperties()));
    public static final RegistryObject<Item> GLOWING_SOUL_PASTE = ITEMS.register("glowing_soul_paste",
            () -> new Item(defaultProperties()));

    public static final RegistryObject<Item> REDSTONE_TORCH = ITEMS.register("unlit_redstone_torch",
            () -> new UnlitRedstoneTorchItem(defaultProperties()));
    public static final RegistryObject<Item> SOUL_TORCH = ITEMS.register("unlit_soul_torch",
            () -> new LitSoulTorchItem(defaultProperties()));

    public static final RegistryObject<Item> LANTERN = ITEMS.register("unlit_lantern",
            () -> new LitLanternItem(ModBlocks.REALISTIC_LANTERN, defaultProperties()));
    public static final RegistryObject<Item> SOUL_LANTERN = ITEMS.register("unlit_soul_lantern",
            () -> new UnlitSoulLanternItem(defaultProperties()));

    public static final RegistryObject<Item> CAMPFIRE = ITEMS.register("unlit_campfire",
            () -> new UnlitCampfireItem(ModBlocks.REALISTIC_CAMPFIRE, defaultProperties()));
    public static final RegistryObject<Item> SOUL_CAMPFIRE = ITEMS.register("unlit_soul_campfire",
            () -> new UnlitCampfireItem(ModBlocks.REALISTIC_SOUL_CAMPFIRE, defaultProperties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}