package com.asteroidnine.realistictorchesextended.entity;

import com.asteroidnine.realistictorchesextended.RealisticTorchesExtended;
import com.asteroidnine.realistictorchesextended.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RealisticTorchesExtended.MOD_ID);

    public static final RegistryObject<BlockEntityType<RealisticCampfireBlockEntity>> REALISTIC_CAMPFIRE_ENTITY =
            BLOCK_ENTITIES.register("realistic_campfire", () ->
                    BlockEntityType.Builder.of(RealisticCampfireBlockEntity::new,
                            ModBlocks.REALISTIC_CAMPFIRE.get(),
                            ModBlocks.REALISTIC_SOUL_CAMPFIRE.get()
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}