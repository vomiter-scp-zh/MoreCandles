package com.vomiter.morecandles.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static DeferredRegister<Item> ITEMS
            = ModRegistries.createRegistry(BuiltInRegistries.ITEM);
}
