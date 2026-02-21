package com.vomiter.morecandles.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS =
            ModRegistries.createRegistry(Registries.CREATIVE_MODE_TAB);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register(
            "more_candles",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.more_candles"))
                    .icon(() -> new ItemStack(ModBlocks.SOUL_CANDLE.get()))
                    .displayItems((params, output) -> {
                        output.accept(ModBlocks.SOUL_CANDLE.get());
                        output.accept(ModBlocks.REDSTONE_CANDLE.get());
                        output.accept(ModBlocks.END_CANDLE.get());
                    })
                    .build()
    );
}
