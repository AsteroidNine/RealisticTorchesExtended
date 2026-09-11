package com.asteroidnine.realistictorchesextended.entity;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockEntities {
    public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, RealisticTorchesExtended.MOD_ID);

    public static final RegistryObject<TileEntityType<RealisticCampfireBlockEntity>> REALISTIC_CAMPFIRE_ENTITY =
            BLOCK_ENTITIES.register("realistic_campfire", () ->
                    TileEntityType.Builder.of(RealisticCampfireBlockEntity::new,
                            ModBlocks.REALISTIC_CAMPFIRE.get(),
                            ModBlocks.REALISTIC_SOUL_CAMPFIRE.get()
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}