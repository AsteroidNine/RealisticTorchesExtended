package com.asteroidnine.realistictorchesextended.block;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.ToIntFunction;

public class RealisticRedstoneTorchBlock extends RedstoneTorchBlock {
    protected static final int INITIAL_BURN_TIME;
    protected static final boolean SHOULD_BURN_OUT;
    protected static final IntegerProperty BURNTIME;
    protected static final IntegerProperty LITSTATE;
    public static final int LIT = 2;
    public static final int SMOLDERING = 1;
    public static final int UNLIT = 0;

    public RealisticRedstoneTorchBlock() {
        super(Properties.copy(Blocks.TORCH).lightLevel(getLightValueFromState()));
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(LITSTATE, 0)).setValue(BURNTIME, 0));
    }

    @Override
    public void animateTick(BlockState state, World level, BlockPos pos, Random random) {
        if ((Integer)state.getValue(LITSTATE) == 2 || (Integer)state.getValue(LITSTATE) == 1 && level.getRandom().nextInt(2) == 1) {
            super.animateTick(state, level, pos, random);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (level.isClientSide()) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() != Items.FLINT_AND_STEEL && stack.getItem() != RealisticTorchesItems.MATCHBOX) {
                return super.use(state, level, pos, player, hand, hit);
            } else {
                this.playLightingSound(level, pos);
                if (!player.isCreative() && (stack.getItem() != RealisticTorchesItems.MATCHBOX || (Integer)ConfigHandler.matchboxDurability.get() > 0)) {
                    stack.hurtAndBreak(1, player, (playerEntity) -> playerEntity.broadcastBreakEvent(hand));
                }

                if (level.isRainingAt(pos)) {
                    this.playExtinguishSound(level, pos);
                } else {
                    this.changeToLit(level, pos, state);
                }

                return ActionResultType.SUCCESS;
            }
        }
    }

    public void tick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        if (level.isClientSide()) return;

        if (this.hasNeighborSignal(level, pos, state)) {
            if ((Integer)state.getValue(LITSTATE) > 0) {
                this.playExtinguishSound(level, pos);
                this.changeToUnlit(level, pos, state);
            }
            return;
        }

        if (SHOULD_BURN_OUT && (Integer)state.getValue(LITSTATE) > 0) {
            if (level.isRainingAt(pos)) {
                this.playExtinguishSound(level, pos);
                this.changeToUnlit(level, pos, state);
                return;
            }

            int newBurnTime = (Integer)state.getValue(BURNTIME) - 1;
            if (newBurnTime <= 0) {
                this.playExtinguishSound(level, pos);
                this.changeToUnlit(level, pos, state);
                level.updateNeighborsAt(pos, this);
            } else if ((Integer)state.getValue(LITSTATE) != 2 || newBurnTime > INITIAL_BURN_TIME / 10 && newBurnTime > 1) {
                level.setBlock(pos, (BlockState)state.setValue(BURNTIME, newBurnTime), 2);
                level.getBlockTicks().scheduleTick(pos, this, 1200);
            } else {
                this.changeToSmoldering(level, pos, state, newBurnTime);
                level.updateNeighborsAt(pos, this);
            }
        }
    }

    @Override
    public void setPlacedBy(World level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        level.getBlockTicks().scheduleTick(pos, this, 1200);
    }

    @Override
    public void onPlace(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            this.defaultBlockState().updateNeighbourShapes(level, pos, 3);
        }
        super.onPlace(state, level, pos, newState, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{BURNTIME});
        builder.add(new Property[]{LITSTATE});
    }

    @Override
    public int getSignal(BlockState pBlockState, IBlockReader pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getValue(LITSTATE) > 0 && pSide != Direction.UP ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState pBlockState, IBlockReader pBlockAccess, BlockPos pPos, Direction pSide) {
        return pSide == Direction.DOWN ? pBlockState.getSignal(pBlockAccess, pPos, pSide) : 0;
    }

    @Override
    public void neighborChanged(BlockState state, World level, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        if (!level.isClientSide) {
            if ((Integer)state.getValue(LITSTATE) > 0 && this.hasNeighborSignal(level, pos, state)) {
                this.changeToUnlit(level, pos, state);
            }
        }
        super.neighborChanged(state, level, pos, block, neighborPos, isMoving);
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
        level.setBlock(pos, (BlockState)((BlockState)((Block)ModBlocks.REALISTIC_REDSTONE_TORCH.get()).defaultBlockState()
                .setValue(LITSTATE, 2))
                .setValue(BURNTIME, getInitialBurnTime()), 3);

        level.updateNeighborsAt(pos, this);
        if (SHOULD_BURN_OUT) {
            level.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    public void changeToSmoldering(World level, BlockPos pos, BlockState state, int newBurnTime) {
        if (SHOULD_BURN_OUT) {
            level.setBlock(pos, (BlockState)((BlockState)((Block)ModBlocks.REALISTIC_REDSTONE_TORCH.get()).defaultBlockState()
                    .setValue(LITSTATE, 1))
                    .setValue(BURNTIME, newBurnTime), 3);

            level.updateNeighborsAt(pos, this);
            level.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    public void changeToUnlit(World level, BlockPos pos, BlockState state) {
        if (SHOULD_BURN_OUT) {
            if ((Boolean)ConfigHandler.noRelightEnabled.get()) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            } else {
                level.setBlock(pos, ((Block)ModBlocks.REALISTIC_REDSTONE_TORCH.get()).defaultBlockState(), 3);
                level.getBlockTicks().scheduleTick(pos, this, 1200);
            }
            level.updateNeighborsAt(pos, this);
        }
    }

    public void playLightingSound(World level, BlockPos pos) {
        level.playSound((PlayerEntity)null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    public void playExtinguishSound(World level, BlockPos pos) {
        level.playSound((PlayerEntity)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    private static ToIntFunction<BlockState> getLightValueFromState() {
        return (state) -> {
            if ((Integer)state.getValue(LITSTATE) == 2) {
                return 14;
            } else {
                return (Integer)state.getValue(LITSTATE) == 1 ? 12 : 0;
            }
        };
    }

    static {
        INITIAL_BURN_TIME = (Integer)ConfigHandler.torchBurnoutTime.get();
        SHOULD_BURN_OUT = INITIAL_BURN_TIME > 0;
        BURNTIME = IntegerProperty.create("burntime", 0, SHOULD_BURN_OUT ? INITIAL_BURN_TIME : 1);
        LITSTATE = IntegerProperty.create("litstate", 0, 2);
    }
}