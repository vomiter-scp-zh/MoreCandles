package com.vomiter.morecandles.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static DeferredRegister<Item> ITEMS
            = ModRegistries.createRegistry(ForgeRegistries.ITEMS);
}
