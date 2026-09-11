package com.asteroidnine.realistictorchesextended.block;

import com.asteroidnine.realistictorchesextended.entity.RealisticCampfireBlockEntity;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;
import java.util.function.ToIntFunction;

public class RealisticCampfireBlock extends CampfireBlock {

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
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new RealisticCampfireBlockEntity();
    }

    @Override
    public void animateTick(BlockState state, World level, BlockPos pos, Random random) {
        if (state.getValue(LITSTATE) == LIT || (state.getValue(LITSTATE) == SMOLDERING && level.getRandom().nextInt(2) == 1)) {
            super.animateTick(state, level, pos, random);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() == Items.FLINT_AND_STEEL || stack.getItem() == RealisticTorchesItems.MATCHBOX) {
            if (state.getValue(LITSTATE) == UNLIT) {
                playLightingSound(level, pos);
                if (!level.isClientSide()) {
                    if (!player.isCreative() && (stack.getItem() != RealisticTorchesItems.MATCHBOX || ConfigHandler.matchboxDurability.get() > 0)) {
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
                return ActionResultType.sidedSuccess(level.isClientSide());
            }
        }

        if (stack.getItem() instanceof net.minecraft.item.ShovelItem && state.getValue(LITSTATE) > UNLIT) {
            if (!level.isClientSide()) {
                playExtinguishSound(level, pos);
                CampfireBlock.dowse(level, pos, state);
                changeToUnlit(level, pos, state);
                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, playerEntity -> {
                        playerEntity.broadcastBreakEvent(hand);
                    });
                }
            }
            return ActionResultType.sidedSuccess(level.isClientSide());
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    public void tick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        if (!level.isClientSide() && SHOULD_BURN_OUT && state.getValue(LITSTATE) > UNLIT) {
            if (level.isRainingAt(pos)) {
                playExtinguishSound(level, pos);
                CampfireBlock.dowse(level, pos, state);
                changeToUnlit(level, pos, state);
                return;
            }
            int newBurnTime = state.getValue(BURNTIME) - 1;
            if (newBurnTime <= 0) {
                playExtinguishSound(level, pos);
                CampfireBlock.dowse(level, pos, state);
                changeToUnlit(level, pos, state);
                level.updateNeighborsAt(pos, this);
            } else if (state.getValue(LITSTATE) == LIT && (newBurnTime <= INITIAL_BURN_TIME / 10 || newBurnTime <= 1)) {
                changeToSmoldering(level, pos, state, newBurnTime);
                level.updateNeighborsAt(pos, this);
            } else {
                level.setBlock(pos, state.setValue(BURNTIME, newBurnTime), 2);
                level.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
            }
        }
    }

    @Override
    public void setPlacedBy(World level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        level.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
    }

    @Override
    public void onPlace(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            defaultBlockState().updateNeighbourShapes(level, pos, 3);
        }
        super.onPlace(state, level, pos, newState, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
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

    public void changeToLit(World level, BlockPos pos, BlockState state) {
        BlockState litState = state
                .setValue(RealisticCampfireBlock.getLitState(), RealisticCampfireBlock.LIT)
                .setValue(RealisticCampfireBlock.getBurnTime(), getInitialBurnTime())
                .setValue(CampfireBlock.LIT, true);

        level.setBlock(pos, litState, 3);

        if (SHOULD_BURN_OUT) {
            level.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
        }
    }

    public void changeToSmoldering(World level, BlockPos pos, BlockState state, int newBurnTime) {
        BlockState smolderingState = state
                .setValue(RealisticCampfireBlock.getLitState(), RealisticCampfireBlock.SMOLDERING)
                .setValue(RealisticCampfireBlock.getBurnTime(), newBurnTime)
                .setValue(CampfireBlock.LIT, true);

        level.setBlock(pos, smolderingState, 3);

        if (SHOULD_BURN_OUT) {
            level.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
        }
    }

    public void changeToUnlit(World level, BlockPos pos, BlockState state) {
        BlockState unlitState = state
                .setValue(RealisticCampfireBlock.getLitState(), RealisticCampfireBlock.UNLIT)
                .setValue(RealisticCampfireBlock.getBurnTime(), 0)
                .setValue(CampfireBlock.LIT, false);

        level.setBlock(pos, unlitState, 3);
    }

    public void playLightingSound(World level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    public void playExtinguishSound(World level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
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
}