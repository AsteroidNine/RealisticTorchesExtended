package com.asteroidnine.realistictorchesextended.item;

import com.chaosthedude.realistictorches.items.RealisticTorchesItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModCreativeModeTabs {

    // In 1.16.5, we instantiate an anonymous subclass of ItemGroup directly
    public static final ItemGroup REALISTIC_TORCHES_EXTENDED_TAB = new ItemGroup("realistic_torches_extended_tab") {

        @Override
        public ItemStack makeIcon() {
            // This replaces the .icon() builder method
            return new ItemStack(RealisticTorchesItems.UNLIT_TORCH);
        }
    };
}