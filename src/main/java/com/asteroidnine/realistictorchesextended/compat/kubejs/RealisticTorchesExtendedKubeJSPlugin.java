package com.asteroidnine.realistictorchesextended.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public class RealisticTorchesExtendedKubeJSPlugin extends KubeJSPlugin {
    // Create an event group inside KubeJS called "RealisticTorchesEvents"
    public static final EventGroup GROUP = EventGroup.of("RealisticTorchesEvents");

    // Expose a specific event called "burnout"
    public static final EventHandler BURNOUT = GROUP.server("burnout", () -> TorchBurnoutEventJS.class);

    @Override
    public void registerEvents() {
        GROUP.register();
    }
}
