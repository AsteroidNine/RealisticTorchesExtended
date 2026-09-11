package com.asteroidnine.realistictorchesextended.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class TorchBurnoutEventJS extends EventJS {
    private final Level level;
    private final BlockPos pos;

    public TorchBurnoutEventJS(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getPos() {
        return pos;
    }
}
