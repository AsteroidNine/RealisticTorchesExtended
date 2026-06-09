package com.asteroidnine.realistictorchesextended.block;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import com.asteroidnine.realistictorchesextended.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RealisticTorchesExtended.MOD_ID);

    public static final RegistryObject<Block> REALISTIC_REDSTONE_TORCH = registerBlock("realistic_redstone_torch", RealisticRedstoneTorchBlock::new, false);
    public static final RegistryObject<Block> REALISTIC_REDSTONE_TORCH_WALL = registerBlock("realistic_redstone_torch_wall", RealisticRedstoneWallTorchBlock::new, false);
    public static final RegistryObject<Block> REALISTIC_LANTERN = registerBlock("realistic_lantern", RealisticLanternBlock::new, false);

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
