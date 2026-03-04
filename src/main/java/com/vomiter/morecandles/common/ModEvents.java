package com.vomiter.morecandles.common;

import com.vomiter.morecandles.common.event.CreativeTabEvents;
import com.vomiter.morecandles.common.event.LivingTickAroundCandleEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public final class ModEvents {

    public static void init(IEventBus modBus){
        modBus.addListener(CreativeTabEvents::buildContents);
        MinecraftForge.EVENT_BUS.addListener(LivingTickAroundCandleEvent::onLivingTick);
    }

}
