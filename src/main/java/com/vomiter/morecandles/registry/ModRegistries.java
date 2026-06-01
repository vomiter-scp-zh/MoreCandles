package com.vomiter.morecandles.registry;

import com.vomiter.morecandles.MoreCandles;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class ModRegistries {
    static List<DeferredRegister<?>> REGISTRIES = new ArrayList<>();
    public static <B> DeferredRegister<B> createRegistry(Registry<B> b){
        return DeferredRegister.create(b, MoreCandles.MOD_ID);
    }
    public static <B> DeferredRegister<B> createRegistry(ResourceKey b){
        return DeferredRegister.create(b, MoreCandles.MOD_ID);
    }


    static void add(DeferredRegister<?> r){
        REGISTRIES.add(r);
    }

    public static void register(IEventBus modBus){
        add(ModItems.ITEMS);
        add(ModBlocks.BLOCKS);
        add(ModParticles.PARTICLES);
        add(ModCreativeTabs.TABS);
        for (DeferredRegister<?> registry : REGISTRIES) {
            registry.register(modBus);
        }
    }
}
