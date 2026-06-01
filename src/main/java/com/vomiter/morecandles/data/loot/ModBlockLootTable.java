package com.vomiter.morecandles.data.loot;

import com.vomiter.morecandles.MoreCandles;
import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.BiConsumer;

public class ModBlockLootTable implements LootTableSubProvider {
    public ModBlockLootTable(HolderLookup.Provider provider) {
    }

    private void addCandleLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> b, DeferredHolder<Block, Block> block){
        b.accept(
                ResourceKey.create(Registries.LOOT_TABLE, MoreCandles.modLoc("blocks/" + block.getId().getPath())),
                LootTableHelpers.vanillaCandleLikeDrops(block.get())
        );
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> b) {
        addCandleLoot(b, ModBlocks.REDSTONE_CANDLE);
        addCandleLoot(b, ModBlocks.SOUL_CANDLE);
        addCandleLoot(b, ModBlocks.END_CANDLE);
        addCandleLoot(b, ModBlocks.COPPER_CANDLE);
        ModBlocks.SCENTED_CANDLES.values().forEach(robj -> addCandleLoot(b, robj));

    }
}