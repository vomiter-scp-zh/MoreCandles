package com.vomiter.morecandles.registry;

import com.vomiter.morecandles.common.compat.supp.SupplementariesCompat;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
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
                        output.accept(ModBlocks.COPPER_CANDLE.get());
                        ModBlocks.SCENTED_CANDLES.values().forEach(
                                robj -> output.accept(robj.get())
                        );
                        if(ModList.get().isLoaded("supplementaries")){
                            output.accept(SupplementariesCompat.getSoulCandleHolder().get());
                            output.accept(SupplementariesCompat.getEndCandleHolder().get());
                            SupplementariesCompat.getScentedCandleHolders().values().forEach(b -> output.accept(b.get()));
                        }
                    })
                    .build()
    );
}
