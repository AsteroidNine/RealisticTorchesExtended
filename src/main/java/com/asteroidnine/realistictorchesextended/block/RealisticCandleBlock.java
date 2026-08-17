package com.asteroidnine.realistictorchesextended.block;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;

public class RealisticCandleBlock extends CandleBlock {
    public static final int TICK_INTERVAL = 1200;
    protected static final int INITIAL_BURN_TIME = ConfigHandler.torchBurnoutTime.get();
    protected static final boolean SHOULD_BURN_OUT = INITIAL_BURN_TIME > 0;
    protected static final IntegerProperty BURNTIME = IntegerProperty.create("burntime", 0, SHOULD_BURN_OUT ? INITIAL_BURN_TIME : 1);
    protected static final IntegerProperty LITSTATE = IntegerProperty.create("litstate", 0, 2);

    public static final int LIT = 2;
    public static final int SMOLDERING = 1;
    public static final int UNLIT = 0;

    public RealisticCandleBlock(Block.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(CANDLES, 1)
                .setValue(CandleBlock.LIT, false)
                .setValue(WATERLOGGED, false)
                .setValue(LITSTATE, UNLIT)
                .setValue(BURNTIME, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        // Handle lighting the candle cluster
        if (stack.getItem() == Items.FLINT_AND_STEEL || stack.getItem() == RealisticTorchesRegistry.MATCHBOX_ITEM.get() || ConfigHandler.lightTorchItems.get().contains(ForgeRegistries.ITEMS.getKey(stack.getItem()).toString())) {
            if (state.getValue(LITSTATE) == UNLIT && !state.getValue(WATERLOGGED)) {
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                if (!level.isClientSide()) {
                    if (!player.isCreative() && (stack.getItem() != RealisticTorchesRegistry.MATCHBOX_ITEM.get() || ConfigHandler.matchboxDurability.get() > 0)) {
                        stack.hurtAndBreak(1, player, playerEntity -> {
                            playerEntity.broadcastBreakEvent(hand);
                        });
                    }
                    changeToLit(level, pos, state);
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        // Handle extinguishing with an empty hand (overriding vanilla behavior to pause the timer)
        if (stack.isEmpty() && state.getValue(LITSTATE) > UNLIT) {
            if (!level.isClientSide()) {
                level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                changeToUnlit(level, pos, state);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide() && SHOULD_BURN_OUT && state.getValue(LITSTATE) > UNLIT) {
            // Instantly extinguish if the cluster becomes waterlogged
            if (state.getValue(WATERLOGGED)) {
                level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                changeToUnlit(level, pos, state);
                return;
            }

            int newBurnTime = state.getValue(BURNTIME) - 1;
            if (newBurnTime <= 0) {
                level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                changeToUnlit(level, pos, state);
            } else if (state.getValue(LITSTATE) == LIT && (newBurnTime <= INITIAL_BURN_TIME / 10 || newBurnTime <= 1)) {
                changeToSmoldering(level, pos, state, newBurnTime);
            } else {
                level.setBlock(pos, state.setValue(BURNTIME, newBurnTime), 2);
                level.scheduleTick(pos, this, TICK_INTERVAL);
            }
        }
    }

    public static IntegerProperty getBurnTime() {
        return BURNTIME;
    }

    public static IntegerProperty getLitState() {
        return LITSTATE;
    }

    public void changeToLit(Level level, BlockPos pos, BlockState state) {
        BlockState litState = state
                .setValue(LITSTATE, LIT)
                .setValue(BURNTIME, getInitialBurnTime())
                .setValue(CandleBlock.LIT, true);
        level.setBlock(pos, litState, 3);
        if (SHOULD_BURN_OUT) {
            level.scheduleTick(pos, this, TICK_INTERVAL);
        }
    }

    public void changeToSmoldering(Level level, BlockPos pos, BlockState state, int newBurnTime) {
        BlockState smolderingState = state
                .setValue(LITSTATE, SMOLDERING)
                .setValue(BURNTIME, newBurnTime)
                .setValue(CandleBlock.LIT, true);
        level.setBlock(pos, smolderingState, 3);
        if (SHOULD_BURN_OUT) {
            level.scheduleTick(pos, this, TICK_INTERVAL);
        }
    }

    public void changeToUnlit(Level level, BlockPos pos, BlockState state) {
        // Preserves the remaining burn time when extinguished so players can re-light it later
        BlockState unlitState = state
                .setValue(LITSTATE, UNLIT)
                .setValue(CandleBlock.LIT, false);
        level.setBlock(pos, unlitState, 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BURNTIME);
        builder.add(LITSTATE);
    }

    public static int getInitialBurnTime() {
        return SHOULD_BURN_OUT ? INITIAL_BURN_TIME : 0;
    }
}
