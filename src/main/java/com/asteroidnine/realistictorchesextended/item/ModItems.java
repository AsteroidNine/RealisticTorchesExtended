package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RealisticTorchesExtended.MOD_ID);

    public static final RegistryObject<Item> GLOWING_REDSTONE_CRYSTAL = ITEMS.register("glowing_redstone_crystal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_REDSTONE_PASTE = ITEMS.register("glowing_redstone_paste",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_SOUL_CRYSTAL = ITEMS.register("glowing_soul_crystal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_SOUL_PASTE = ITEMS.register("glowing_soul_paste",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLOWING_HONEYCOMB = ITEMS.register("glowing_honeycomb",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> LIT_REDSTONE_TORCH = ITEMS.register("lit_redstone_torch",
            () -> new LitRedstoneTorchItem(new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> UNLIT_REDSTONE_TORCH = ITEMS.register("unlit_redstone_torch",
            () -> new UnlitRedstoneTorchItem(new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> LIT_SOUL_TORCH = ITEMS.register("lit_soul_torch",
            () -> new LitSoulTorchItem(new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> UNLIT_SOUL_TORCH = ITEMS.register("unlit_soul_torch",
            () -> new UnlitSoulTorchItem(new Item.Properties(), Direction.DOWN));

    public static final RegistryObject<Item> LIT_LANTERN = ITEMS.register("lit_lantern",
            () -> new LitLanternItem(ModBlocks.REALISTIC_LANTERN, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_LANTERN = ITEMS.register("unlit_lantern",
            () -> new UnlitLanternItem(ModBlocks.REALISTIC_LANTERN, new Item.Properties()));
    public static final RegistryObject<Item> LIT_SOUL_LANTERN = ITEMS.register("lit_soul_lantern",
            () -> new LitSoulLanternItem(new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_SOUL_LANTERN = ITEMS.register("unlit_soul_lantern",
            () -> new UnlitSoulLanternItem(new Item.Properties()));

    public static final RegistryObject<Item> LIT_CAMPFIRE = ITEMS.register("lit_campfire",
            () -> new LitCampfireItem(ModBlocks.REALISTIC_CAMPFIRE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_CAMPFIRE = ITEMS.register("unlit_campfire",
            () -> new UnlitCampfireItem(ModBlocks.REALISTIC_CAMPFIRE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_SOUL_CAMPFIRE = ITEMS.register("lit_soul_campfire",
            () -> new LitCampfireItem(ModBlocks.REALISTIC_SOUL_CAMPFIRE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_SOUL_CAMPFIRE = ITEMS.register("unlit_soul_campfire",
            () -> new UnlitCampfireItem(ModBlocks.REALISTIC_SOUL_CAMPFIRE, new Item.Properties()));

    public static final RegistryObject<Item> UNLIT_CANDLE = ITEMS.register("unlit_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_CANDLE = ITEMS.register("lit_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_WHITE_CANDLE = ITEMS.register("unlit_white_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_WHITE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_WHITE_CANDLE = ITEMS.register("lit_white_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_WHITE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_LIGHT_GRAY_CANDLE = ITEMS.register("unlit_light_gray_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_LIGHT_GRAY_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_LIGHT_GRAY_CANDLE = ITEMS.register("lit_light_gray_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_LIGHT_GRAY_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_GRAY_CANDLE = ITEMS.register("unlit_gray_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_GRAY_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_GRAY_CANDLE = ITEMS.register("lit_gray_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_GRAY_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_BLACK_CANDLE = ITEMS.register("unlit_black_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_BLACK_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_BLACK_CANDLE = ITEMS.register("lit_black_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_BLACK_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_BROWN_CANDLE = ITEMS.register("unlit_brown_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_BROWN_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_BROWN_CANDLE = ITEMS.register("lit_brown_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_BROWN_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_RED_CANDLE = ITEMS.register("unlit_red_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_RED_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_RED_CANDLE = ITEMS.register("lit_red_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_RED_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_ORANGE_CANDLE = ITEMS.register("unlit_orange_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_ORANGE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_ORANGE_CANDLE = ITEMS.register("lit_orange_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_ORANGE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_YELLOW_CANDLE = ITEMS.register("unlit_yellow_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_YELLOW_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_YELLOW_CANDLE = ITEMS.register("lit_yellow_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_YELLOW_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_LIME_CANDLE = ITEMS.register("unlit_lime_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_LIME_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_LIME_CANDLE = ITEMS.register("lit_lime_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_LIME_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_GREEN_CANDLE = ITEMS.register("unlit_green_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_GREEN_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_GREEN_CANDLE = ITEMS.register("lit_green_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_GREEN_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_CYAN_CANDLE = ITEMS.register("unlit_cyan_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_CYAN_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_CYAN_CANDLE = ITEMS.register("lit_cyan_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_CYAN_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_LIGHT_BLUE_CANDLE = ITEMS.register("unlit_light_blue_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_LIGHT_BLUE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_LIGHT_BLUE_CANDLE = ITEMS.register("lit_light_blue_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_LIGHT_BLUE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_BLUE_CANDLE = ITEMS.register("unlit_blue_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_BLUE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_BLUE_CANDLE = ITEMS.register("lit_blue_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_BLUE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_PURPLE_CANDLE = ITEMS.register("unlit_purple_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_PURPLE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_PURPLE_CANDLE = ITEMS.register("lit_purple_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_PURPLE_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_MAGENTA_CANDLE = ITEMS.register("unlit_magenta_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_MAGENTA_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_MAGENTA_CANDLE = ITEMS.register("lit_magenta_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_MAGENTA_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_PINK_CANDLE = ITEMS.register("unlit_pink_candle",
            () -> new UnlitCandleItem(ModBlocks.REALISTIC_PINK_CANDLE, new Item.Properties()));
    public static final RegistryObject<Item> LIT_PINK_CANDLE = ITEMS.register("lit_pink_candle",
            () -> new LitCandleItem(ModBlocks.REALISTIC_PINK_CANDLE, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
