package com.asteroidnine.realistictorchesextended.block;

import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class RealisticSoulTorchBlock extends RealisticTorchBlock {

    public RealisticSoulTorchBlock() {
        super();
    }

    @Override
    public void animateTick(BlockState state, World level, BlockPos pos, Random random) {
        int litState = state.getValue(LITSTATE);

        if (litState == 2 || (litState == 1 && random.nextInt(2) == 1)) {
            double x = (double)pos.getX() + 0.5D;
            double y = (double)pos.getY() + 0.7D;
            double z = (double)pos.getZ() + 0.5D;

            level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void changeToLit(World level, BlockPos pos, BlockState state) {
        level.setBlock(pos, (BlockState)((BlockState)((Block)ModBlocks.REALISTIC_SOUL_TORCH.get()).defaultBlockState()
                .setValue(LITSTATE, 2))
                .setValue(BURNTIME, getInitialBurnTime()), 3);

        level.updateNeighborsAt(pos, this);
        if (SHOULD_BURN_OUT) {
            level.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    @Override
    public void changeToSmoldering(World level, BlockPos pos, BlockState state, int newBurnTime) {
        if (SHOULD_BURN_OUT) {
            level.setBlock(pos, (BlockState)((BlockState)((Block)ModBlocks.REALISTIC_SOUL_TORCH.get()).defaultBlockState()
                    .setValue(LITSTATE, 1))
                    .setValue(BURNTIME, newBurnTime), 3);

            level.updateNeighborsAt(pos, this);
            level.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    @Override
    public void changeToUnlit(World level, BlockPos pos, BlockState state) {
        if (SHOULD_BURN_OUT) {
            if ((Boolean) ConfigHandler.noRelightEnabled.get()) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            } else {
                level.setBlock(pos, ((Block)ModBlocks.REALISTIC_SOUL_TORCH.get()).defaultBlockState(), 3);
                level.getBlockTicks().scheduleTick(pos, this, 1200);
            }
            level.updateNeighborsAt(pos, this);
        }
    }
}