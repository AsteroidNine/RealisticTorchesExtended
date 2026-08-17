package com.asteroidnine.realistictorchesextended.block;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import com.asteroidnine.realistictorchesextended.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RealisticTorchesExtended.MOD_ID);

    public static final RegistryObject<Block> REALISTIC_REDSTONE_TORCH = registerBlock("realistic_redstone_torch", RealisticRedstoneTorchBlock::new, false);
    public static final RegistryObject<Block> REALISTIC_REDSTONE_TORCH_WALL = registerBlock("realistic_redstone_torch_wall", RealisticRedstoneWallTorchBlock::new, false);

    public static final RegistryObject<Block> REALISTIC_SOUL_TORCH = registerBlock("realistic_soul_torch", RealisticSoulTorchBlock::new, false);
    public static final RegistryObject<Block> REALISTIC_SOUL_TORCH_WALL = registerBlock("realistic_soul_torch_wall", RealisticSoulWallTorchBlock::new, false);

    public static final RegistryObject<Block> REALISTIC_LANTERN = registerBlock("realistic_lantern", RealisticLanternBlock::new, false);
    public static final RegistryObject<Block> REALISTIC_SOUL_LANTERN = registerBlock("realistic_soul_lantern", RealisticLanternBlock::new, false);

    public static final RegistryObject<Block> REALISTIC_CAMPFIRE = registerBlock("realistic_campfire", () -> new RealisticCampfireBlock(true, 1, Block.Properties.copy(Blocks.CAMPFIRE), 14, 12), false);
    public static final RegistryObject<Block> REALISTIC_SOUL_CAMPFIRE = registerBlock("realistic_soul_campfire", () -> new RealisticCampfireBlock(true, 2, Block.Properties.copy(Blocks.SOUL_CAMPFIRE), 10, 8), false);

    public static final RegistryObject<Block> REALISTIC_CANDLE = registerBlock("realistic_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_WHITE_CANDLE = registerBlock("realistic_white_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.WHITE_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_LIGHT_GRAY_CANDLE= registerBlock("realistic_light_gray_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.LIGHT_GRAY_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_GRAY_CANDLE = registerBlock("realistic_gray_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.GRAY_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_BLACK_CANDLE = registerBlock("realistic_black_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.BLACK_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_BROWN_CANDLE = registerBlock("realistic_brown_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.BROWN_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_RED_CANDLE = registerBlock("realistic_red_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.RED_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_ORANGE_CANDLE = registerBlock("realistic_orange_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.ORANGE_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_YELLOW_CANDLE = registerBlock("realistic_yellow_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.YELLOW_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_LIME_CANDLE = registerBlock("realistic_lime_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.LIME_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_GREEN_CANDLE = registerBlock("realistic_green_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.GREEN_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_CYAN_CANDLE = registerBlock("realistic_cyan_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.CYAN_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_LIGHT_BLUE_CANDLE = registerBlock("realistic_light_blue_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.LIGHT_BLUE_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_BLUE_CANDLE = registerBlock("realistic_blue_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.BLUE_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_PURPLE_CANDLE = registerBlock("realistic_purple_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.PURPLE_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_MAGENTA_CANDLE = registerBlock("realistic_magenta_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.MAGENTA_CANDLE)), false);
    public static final RegistryObject<Block> REALISTIC_PINK_CANDLE = registerBlock("realistic_pink_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.PINK_CANDLE)), false);

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, boolean needsItem) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        if (needsItem) {
            registerBlockItem(name, toReturn);
        }
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
