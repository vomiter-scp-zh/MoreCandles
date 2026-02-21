package com.vomiter.morecandles.data.recipe;

import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    private InventoryChangeTrigger.TriggerInstance unlockedBy(ItemPredicate predicate){
        return InventoryChangeTrigger.TriggerInstance.hasItems(predicate);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> c) {
        assert ModBlocks.REDSTONE_CANDLE.getId() != null;
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REDSTONE_CANDLE.get())
                .pattern("R")
                .pattern("H")
                .pattern("G")
                .define('R', Items.REDSTONE)
                .define('G', Items.GOLD_NUGGET)
                .define('H', Items.HONEYCOMB)
                .unlockedBy("get_redstone", unlockedBy(ItemPredicate.Builder.item().of(Items.REDSTONE).build()))
                .save(c, ModBlocks.REDSTONE_CANDLE.getId());

        assert ModBlocks.SOUL_CANDLE.getId() != null;
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUL_CANDLE.get())
                .pattern("s")
                .pattern("H")
                .pattern("S")
                .define('s', Tags.Items.STRING)
                .define('H', Items.HONEYCOMB)
                .define('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .unlockedBy("get_soul", unlockedBy(ItemPredicate.Builder.item().of(ItemTags.SOUL_FIRE_BASE_BLOCKS).build()))
                .save(c, ModBlocks.SOUL_CANDLE.getId());

        assert ModBlocks.END_CANDLE.getId() != null;
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.END_CANDLE.get())
                .pattern("s")
                .pattern("H")
                .pattern("E")
                .define('s', Tags.Items.STRING)
                .define('H', Items.HONEYCOMB)
                .define('E', Items.END_ROD)
                .unlockedBy("get_end_rod", unlockedBy(ItemPredicate.Builder.item().of(Items.END_ROD).build()))
                .save(c, ModBlocks.END_CANDLE.getId());
    }
}
