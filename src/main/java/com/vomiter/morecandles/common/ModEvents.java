package com.vomiter.morecandles.common;

import com.vomiter.morecandles.common.event.CreativeTabEvents;
import net.minecraftforge.eventbus.api.IEventBus;

public final class ModEvents {

    public static void init(IEventBus modBus){
        modBus.addListener(CreativeTabEvents::buildContents);
    }

}
