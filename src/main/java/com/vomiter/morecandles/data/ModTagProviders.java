package com.vomiter.morecandles.data;

import com.vomiter.morecandles.MoreCandles;
import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModTagProviders {
    DataGenerator generator;
    PackOutput output;
    CompletableFuture<HolderLookup.Provider> lookupProvider;
    ExistingFileHelper helper;

    public ModTagProviders(GatherDataEvent event){
        generator = event.getGenerator();
        output = generator.getPackOutput();
        lookupProvider = event.getLookupProvider();
        helper = event.getExistingFileHelper();

        var blockTags = new BlockTags();
        var itemTags = new ItemTags(blockTags);
        var entityTags = new EntityTags();
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), itemTags);
        generator.addProvider(event.includeClient(), entityTags);

    }

    interface TagUtil<T> {
        ResourceKey<Registry<T>> key();

        default TagKey<T> create(ResourceLocation id){
            return TagKey.create(
                    key(),
                    id
            );
        }

    }

    class EntityTags extends EntityTypeTagsProvider implements TagUtil<EntityType<?>>{
        public EntityTags() {
            super(output, lookupProvider, MoreCandles.MOD_ID, helper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        }

        @Override
        public ResourceKey<Registry<EntityType<?>>> key() {
            return Registries.ENTITY_TYPE;
        }
    }


    class BlockTags extends BlockTagsProvider{

        public BlockTags() {
            super(output, lookupProvider, MoreCandles.MOD_ID, helper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
            tag(net.minecraft.tags.BlockTags.CANDLES)
                    .add(
                            ModBlocks.SOUL_CANDLE.get(),
                            ModBlocks.END_CANDLE.get()
                    );
        }
    }

    class ItemTags extends ItemTagsProvider {
        public ItemTags(BlockTags blockTags) {
            super(output, lookupProvider, blockTags.contentsGetter(), MoreCandles.MOD_ID, helper);
        }
        static TagKey<Item> create(ResourceLocation id){
            return TagKey.create(
                    Registries.ITEM,
                    id
            );
        }
        @Override
        protected void addTags(HolderLookup.@NotNull Provider p_256380_) {
            //tag(test).add(Items.ACACIA_BOAT);
        }
    }


}
