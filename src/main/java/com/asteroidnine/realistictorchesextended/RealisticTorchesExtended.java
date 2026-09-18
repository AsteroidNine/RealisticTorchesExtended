package com.asteroidnine.realistictorchesextended;

import com.asteroidnine.realistictorchesextended.entity.ModBlockEntities;
import com.asteroidnine.realistictorchesextended.item.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.asteroidnine.realistictorchesextended.block.ModBlocks;

@Mod(RealisticTorchesExtended.MOD_ID)
public class RealisticTorchesExtended {
    public static final String MOD_ID = "realistictorchesextended";
    public static final Logger LOGGER = LogManager.getLogger();

    public RealisticTorchesExtended() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.REALISTIC_REDSTONE_TORCH.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.REALISTIC_REDSTONE_TORCH_WALL.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.REALISTIC_SOUL_TORCH.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.REALISTIC_SOUL_TORCH_WALL.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.REALISTIC_CAMPFIRE.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.REALISTIC_SOUL_CAMPFIRE.get(), RenderType.cutout());
        });
    }
}