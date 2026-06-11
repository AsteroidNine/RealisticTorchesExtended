package com.asteroidnine.realistictorchesextended.item;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RealisticTorchesExtended.MOD_ID);

    public static final RegistryObject<CreativeModeTab> REALISTIC_TORCHES_EXTENDED_TAB = CREATIVE_MODE_TABS.register("realistic_torches_extended_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(RealisticTorchesRegistry.UNLIT_TORCH_ITEM.get()))
                    .title(Component.translatable("creativetab.realistic_torches_extended_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        // Base Realistic Torches
                        pOutput.accept(RealisticTorchesRegistry.MATCHBOX_ITEM.get());
                        pOutput.accept(RealisticTorchesRegistry.UNLIT_TORCH_ITEM.get());
                        pOutput.accept(RealisticTorchesRegistry.LIT_TORCH_ITEM.get());
                        pOutput.accept(RealisticTorchesRegistry.GLOWSTONE_CRYSTAL_ITEM.get());
                        pOutput.accept(RealisticTorchesRegistry.GLOWSTONE_PASTE_ITEM.get());

                        // Realistic Torches Extended
                        pOutput.accept(ModItems.UNLIT_REDSTONE_TORCH.get());
                        pOutput.accept(ModItems.LIT_REDSTONE_TORCH.get());
                        pOutput.accept(ModItems.GLOWING_REDSTONE_CRYSTAL.get());
                        pOutput.accept(ModItems.GLOWING_REDSTONE_PASTE.get());

                        pOutput.accept(ModItems.UNLIT_SOUL_TORCH.get());
                        pOutput.accept(ModItems.LIT_SOUL_TORCH.get());
                        pOutput.accept(ModItems.GLOWING_SOUL_CRYSTAL.get());
                        pOutput.accept(ModItems.GLOWING_SOUL_PASTE.get());

                        pOutput.accept(ModItems.UNLIT_LANTERN.get());
                        pOutput.accept(ModItems.LIT_LANTERN.get());

                        pOutput.accept(ModItems.UNLIT_SOUL_LANTERN.get());
                        pOutput.accept(ModItems.LIT_SOUL_LANTERN.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
