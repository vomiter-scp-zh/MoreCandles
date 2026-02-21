package com.vomiter.morecandles.data.models;

import com.vomiter.morecandles.MoreCandles;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreCandles.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // ===== Spawn Eggs =====

        // 你如果還有一般物品也可以加：
        // basicItem(ModItems.SOME_ITEM.get());
    }

    private void spawnEgg(RegistryObject<? extends Item> item) {
        withExistingParent(item.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    @SuppressWarnings("unused")
    private void simpleGenerated(RegistryObject<? extends Item> item, String texturePath) {
        // 需要時可用：item/generated + 自訂貼圖
        ModelFile parent = new ModelFile.UncheckedModelFile(mcLoc("item/generated"));
        getBuilder(item.getId().getPath())
                .parent(parent)
                .texture("layer0", MoreCandles.modLoc( "item/" + texturePath));
    }
}
