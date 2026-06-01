package com.vomiter.morecandles.common;

import com.vomiter.morecandles.common.event.CreativeTabEvents;
import com.vomiter.morecandles.common.event.LivingTickAroundCandleEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

public final class ModEvents {

    public static void init(IEventBus modBus){
        modBus.addListener(CreativeTabEvents::buildContents);
        NeoForge.EVENT_BUS.addListener(LivingTickAroundCandleEvent::onLivingTick);
    }

}
