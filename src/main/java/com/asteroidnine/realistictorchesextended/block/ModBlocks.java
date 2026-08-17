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

    public static final RegistryObject<Block> REALISTIC_CANDLE = registerBlock("realistic_candle", () -> new RealisticCandleBlock(Block.Properties.copy(Blocks.CANDLE)), true);

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
