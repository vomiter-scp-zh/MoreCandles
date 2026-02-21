package com.vomiter.morecandles.data.loot;

import com.vomiter.morecandles.MoreCandles;
import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiConsumer;

public class ModBlockLootTable implements LootTableSubProvider {
    private void addCandleLoot(BiConsumer<ResourceLocation, LootTable.Builder> b, RegistryObject<Block> block){
        b.accept(
                MoreCandles.modLoc("blocks/" + block.getId().getPath()),
                LootTableHelpers.vanillaCandleLikeDrops(block.get())
        );
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> b) {
        addCandleLoot(b, ModBlocks.REDSTONE_CANDLE);
        addCandleLoot(b, ModBlocks.SOUL_CANDLE);
    }
}