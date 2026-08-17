package com.asteroidnine.realistictorchesextended.block;

import com.asteroidnine.realistictorchesextended.entity.ModBlockEntities;
import com.asteroidnine.realistictorchesextended.entity.RealisticCampfireBlockEntity;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.ToIntFunction;

public class RealisticCampfireBlock extends CampfireBlock implements EntityBlock {

    public static final int TICK_INTERVAL = 1200;
    protected static final int INITIAL_BURN_TIME = ConfigHandler.torchBurnoutTime.get();
    protected static final boolean SHOULD_BURN_OUT = INITIAL_BURN_TIME > 0;
    protected static final IntegerProperty BURNTIME = IntegerProperty.create("burntime", 0, SHOULD_BURN_OUT ? INITIAL_BURN_TIME : 1);
    protected static final IntegerProperty LITSTATE = IntegerProperty.create("litstate", 0, 2);

    public static final int LIT = 2;
    public static final int SMOLDERING = 1;
    public static final int UNLIT = 0;

    public RealisticCampfireBlock(boolean spawnParticles, int fireDamage, Block.Properties properties, int litLight, int smolderingLight) {
        super(spawnParticles, fireDamage, properties.lightLevel(getLightValueFromState(litLight, smolderingLight)));
        registerDefaultState(stateDefinition.any().setValue(LITSTATE, 0).setValue(BURNTIME, 0));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RealisticCampfireBlockEntity(pPos, pState);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LITSTATE) == LIT || (state.getValue(LITSTATE) == SMOLDERING && level.getRandom().nextInt(2) == 1)) {
            super.animateTick(state, level, pos, random);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        // Handle lighting the campfire
        if (stack.getItem() == Items.FLINT_AND_STEEL || stack.getItem() == RealisticTorchesRegistry.MATCHBOX_ITEM.get() || ConfigHandler.lightTorchItems.get().contains(ForgeRegistries.ITEMS.getKey(stack.getItem()).toString())) {
            if (state.getValue(LITSTATE) == UNLIT) {
                playLightingSound(level, pos);
                if (!level.isClientSide()) {
                    if (!player.isCreative() && (stack.getItem() != RealisticTorchesRegistry.MATCHBOX_ITEM.get() || ConfigHandler.matchboxDurability.get() > 0)) {
                        stack.hurtAndBreak(1, player, playerEntity -> {
                            playerEntity.broadcastBreakEvent(hand);
                        });
                    }
                    if (level.isRainingAt(pos)) {
                        playExtinguishSound(level, pos);
                    } else {
                        changeToLit(level, pos, state);
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        // Intercept vanilla shovel extinguishing
        if (stack.getItem() instanceof net.minecraft.world.item.ShovelItem && state.getValue(LITSTATE) > UNLIT) {
            if (!level.isClientSide()) {
                playExtinguishSound(level, pos);
                CampfireBlock.dowse(player, level, pos, state); // Drop cooking items before extinguishing
                changeToUnlit(level, pos, state);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, playerEntity -> {
                        playerEntity.broadcastBreakEvent(hand);
                    });
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide() && SHOULD_BURN_OUT && state.getValue(LITSTATE) > UNLIT) {
            if (level.isRainingAt(pos)) {
                playExtinguishSound(level, pos);
                CampfireBlock.dowse(null, level, pos, state);
                changeToUnlit(level, pos, state);
                return;
            }
            int newBurnTime = state.getValue(BURNTIME) - 1;
            if (newBurnTime <= 0) {
                playExtinguishSound(level, pos);
                CampfireBlock.dowse(null, level, pos, state);
                changeToUnlit(level, pos, state);
                level.updateNeighborsAt(pos, this);
            } else if (state.getValue(LITSTATE) == LIT && (newBurnTime <= INITIAL_BURN_TIME / 10 || newBurnTime <= 1)) {
                changeToSmoldering(level, pos, state, newBurnTime);
                level.updateNeighborsAt(pos, this);
            } else {
                level.setBlock(pos, state.setValue(BURNTIME, newBurnTime), 2);
                level.scheduleTick(pos, this, TICK_INTERVAL);
            }
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        level.scheduleTick(pos, this, TICK_INTERVAL);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            defaultBlockState().updateNeighbourShapes(level, pos, 3);
        }
        super.onPlace(state, level, pos, newState, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BURNTIME);
        builder.add(LITSTATE);
    }

    public static IntegerProperty getBurnTime() {
        return BURNTIME;
    }

    public static IntegerProperty getLitState() {
        return LITSTATE;
    }

    public static int getInitialBurnTime() {
        return SHOULD_BURN_OUT ? INITIAL_BURN_TIME : 0;
    }

    public void changeToLit(Level level, BlockPos pos, BlockState state) {
        BlockState litState = state
                .setValue(RealisticCampfireBlock.getLitState(), RealisticCampfireBlock.LIT)
                .setValue(RealisticCampfireBlock.getBurnTime(), getInitialBurnTime())
                .setValue(CampfireBlock.LIT, true);

        level.setBlock(pos, litState, 3); // Changed from 2 to 3

        if (SHOULD_BURN_OUT) {
            level.scheduleTick(pos, this, TICK_INTERVAL);
        }
    }

    public void changeToSmoldering(Level level, BlockPos pos, BlockState state, int newBurnTime) {
        BlockState smolderingState = state
                .setValue(RealisticCampfireBlock.getLitState(), RealisticCampfireBlock.SMOLDERING)
                .setValue(RealisticCampfireBlock.getBurnTime(), newBurnTime)
                .setValue(CampfireBlock.LIT, true);

        level.setBlock(pos, smolderingState, 3); // Changed from 2 to 3

        if (SHOULD_BURN_OUT) {
            level.scheduleTick(pos, this, TICK_INTERVAL);
        }
    }

    public void changeToUnlit(Level level, BlockPos pos, BlockState state) {
        BlockState unlitState = state
                .setValue(RealisticCampfireBlock.getLitState(), RealisticCampfireBlock.UNLIT)
                .setValue(RealisticCampfireBlock.getBurnTime(), 0)
                .setValue(CampfireBlock.LIT, false);

        level.setBlock(pos, unlitState, 3); // Changed from 2 to 3
    }

    public void playLightingSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    public void playExtinguishSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    private static ToIntFunction<BlockState> getLightValueFromState(int litLight, int smolderingLight) {
        return (state) -> {
            if (state.getValue(RealisticCampfireBlock.LITSTATE) == RealisticCampfireBlock.LIT) {
                return litLight;
            } else if (state.getValue(RealisticCampfireBlock.LITSTATE) == RealisticCampfireBlock.SMOLDERING) {
                return smolderingLight;
            }
            return 0;
        };
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return state.getValue(LITSTATE) > UNLIT
                    ? createTickerHelper(type, ModBlockEntities.REALISTIC_CAMPFIRE_ENTITY.get(), CampfireBlockEntity::particleTick)
                    : null;
        } else {
            return state.getValue(LITSTATE) > UNLIT
                    ? createTickerHelper(type, ModBlockEntities.REALISTIC_CAMPFIRE_ENTITY.get(), CampfireBlockEntity::cookTick)
                    : createTickerHelper(type, ModBlockEntities.REALISTIC_CAMPFIRE_ENTITY.get(), CampfireBlockEntity::cooldownTick);
        }
    }
}