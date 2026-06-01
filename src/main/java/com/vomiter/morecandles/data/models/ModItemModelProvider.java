package com.vomiter.morecandles.data.models;

import com.vomiter.morecandles.MoreCandles;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreCandles.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }


    @SuppressWarnings("unused")
    private void simpleGenerated(DeferredHolder<? extends Item, ? extends Item> item, String texturePath) {
        ModelFile parent = new ModelFile.UncheckedModelFile(mcLoc("item/generated"));
        getBuilder(item.getId().getPath())
                .parent(parent)
                .texture("layer0", MoreCandles.modLoc( "item/" + texturePath));
    }
}
