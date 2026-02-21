package com.vomiter.morecandles.common.event;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class CreativeTabEvents {

    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        // 原版紅石 tab
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
        }
    }
}