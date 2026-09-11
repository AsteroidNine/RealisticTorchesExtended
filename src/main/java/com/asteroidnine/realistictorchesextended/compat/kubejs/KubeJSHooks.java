package com.asteroidnine.realistictorchesextended.compat.kubejs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class KubeJSHooks {
    public static void fireBurnoutEvent(Level level, BlockPos pos) {
        RealisticTorchesExtendedKubeJSPlugin.BURNOUT.post(new TorchBurnoutEventJS(level, pos));
    }
}
