package com.vomiter.morecandles.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTables extends LootTableProvider {

    public ModLootTables(
            PackOutput packOutput,
            CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(
                packOutput,
                Set.of(),
                List.of(
                        new SubProviderEntry(ModBlockLootTable::new, LootContextParamSets.BLOCK)
                ),
                lookupProvider
        );
    }

    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {}

}