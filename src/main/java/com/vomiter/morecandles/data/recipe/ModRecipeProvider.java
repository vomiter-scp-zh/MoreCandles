package com.vomiter.morecandles.data.recipe;

import com.vomiter.morecandles.Helpers;
import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    private Criterion<?> unlockedBy(ItemPredicate predicate){
        return InventoryChangeTrigger.TriggerInstance.hasItems(predicate);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REDSTONE_CANDLE.get())
                .pattern("R")
                .pattern("H")
                .pattern("G")
                .define('R', Items.REDSTONE)
                .define('G', Items.GOLD_NUGGET)
                .define('H', Items.HONEYCOMB)
                .unlockedBy("get_redstone", unlockedBy(ItemPredicate.Builder.item().of(Items.REDSTONE).build()))
                .save(recipeOutput, ModBlocks.REDSTONE_CANDLE.getId());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_CANDLE.get())
                .pattern("s")
                .pattern("H")
                .pattern("S")
                .define('s', Tags.Items.STRINGS)
                .define('H', Items.HONEYCOMB)
                .define('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .unlockedBy("get_soul", unlockedBy(ItemPredicate.Builder.item().of(ItemTags.SOUL_FIRE_BASE_BLOCKS).build()))
                .save(recipeOutput, ModBlocks.SOUL_CANDLE.getId());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.END_CANDLE.get(), 4)
                .pattern("s")
                .pattern("H")
                .pattern("E")
                .define('s', Tags.Items.STRINGS)
                .define('H', Items.HONEYCOMB)
                .define('E', Items.END_ROD)
                .unlockedBy("get_end_rod", unlockedBy(ItemPredicate.Builder.item().of(Items.END_ROD).build()))
                .save(recipeOutput, ModBlocks.END_CANDLE.getId());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_CANDLE.get(), 1)
                .pattern("s")
                .pattern("H")
                .pattern("E")
                .define('s', Tags.Items.STRINGS)
                .define('H', Items.HONEYCOMB)
                .define('E', Items.COPPER_INGOT)
                .unlockedBy("get_copper", unlockedBy(ItemPredicate.Builder.item().of(Items.COPPER_INGOT).build()))
                .save(recipeOutput, ModBlocks.COPPER_CANDLE.getId());


        ModBlocks.SCENTED_CANDLES.forEach((scented, candle) -> {
            if(candle == null) return;
            Item flower = BuiltInRegistries.ITEM.get(Helpers.minecraftId(scented.name().toLowerCase(Locale.ROOT)));
            if(scented == ModBlocks.Scented.OXEYE) flower = Items.OXEYE_DAISY;
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, candle.get())
                    .pattern("FFF")
                    .pattern("FCF")
                    .pattern("FFF")
                    .define('C', Items.CANDLE)
                    .define('F', flower)
                    .unlockedBy("get_candle", unlockedBy(ItemPredicate.Builder.item().of(Items.CANDLE).build()))
                    .save(recipeOutput, candle.getId());
        });
    }
}
