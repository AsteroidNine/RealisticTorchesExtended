package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
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
    public static final RegistryObject<Item> LIT_REDSTONE_TORCH = ITEMS.register("lit_redstone_torch",
            () -> new LitRedstoneTorchItem(new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> UNLIT_REDSTONE_TORCH = ITEMS.register("unlit_redstone_torch",
            () -> new UnlitRedstoneTorchItem(new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> LIT_LANTERN = ITEMS.register("lit_lantern",
            () -> new LitLanternItem(new Item.Properties()));
    public static final RegistryObject<Item> UNLIT_LANTERN = ITEMS.register("unlit_lantern",
            () -> new UnlitLanternItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
