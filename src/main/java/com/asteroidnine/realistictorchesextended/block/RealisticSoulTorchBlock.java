package com.asteroidnine.realistictorchesextended.block;

import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RealisticSoulTorchBlock extends RealisticTorchBlock {

    public RealisticSoulTorchBlock() {
        super();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
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
    public void changeToLit(Level level, BlockPos pos, BlockState state) {
        level.setBlock(pos, (BlockState)((BlockState)((Block)ModBlocks.REALISTIC_SOUL_TORCH.get()).defaultBlockState()
                .setValue(LITSTATE, 2))
                .setValue(BURNTIME, getInitialBurnTime()), 3);

        level.updateNeighborsAt(pos, this);
        if (SHOULD_BURN_OUT) {
            level.scheduleTick(pos, this, 1200);
        }

    }

    @Override
    public void changeToSmoldering(Level level, BlockPos pos, BlockState state, int newBurnTime) {
        if (SHOULD_BURN_OUT) {
            level.setBlock(pos, (BlockState)((BlockState)((Block)ModBlocks.REALISTIC_SOUL_TORCH.get()).defaultBlockState()
                    .setValue(LITSTATE, 1))
                    .setValue(BURNTIME, newBurnTime), 3);

            level.updateNeighborsAt(pos, this);
            level.scheduleTick(pos, this, 1200);
        }

    }

    @Override
    public void changeToUnlit(Level level, BlockPos pos, BlockState state) {
        if (SHOULD_BURN_OUT) {
            if ((Boolean) ConfigHandler.noRelightEnabled.get()) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            } else {
                level.setBlock(pos, ((Block)ModBlocks.REALISTIC_SOUL_TORCH.get()).defaultBlockState(), 3);
                level.scheduleTick(pos, this, 1200);
            }
            level.updateNeighborsAt(pos, this);
        }

    }
}
