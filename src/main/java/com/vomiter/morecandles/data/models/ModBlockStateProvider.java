package com.vomiter.morecandles.data.models;

import com.vomiter.morecandles.Helpers;
import com.vomiter.morecandles.MoreCandles;
import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.level.block.CandleBlock.CANDLES;
import static net.minecraft.world.level.block.CandleBlock.LIT;

public class ModBlockStateProvider extends BlockStateProvider {

    private final ExistingFileHelper existingFileHelper;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MoreCandles.MOD_ID, exFileHelper);
        this.existingFileHelper = exFileHelper;
    }

    private void trackTexture(String namespace, String pathNoExt) {
        existingFileHelper.trackGenerated(
                Helpers.id(namespace, pathNoExt),
                PackType.CLIENT_RESOURCES,
                ".png",
                "textures"
        );
    }
    private void trackTexture(String pathNoExt){
        trackTexture(MoreCandles.MOD_ID, pathNoExt);
    }

    private void trackModel(String namespace, String pathNoExt) {
        existingFileHelper.trackGenerated(
                Helpers.id(namespace, pathNoExt),
                PackType.CLIENT_RESOURCES,
                ".json",
                "models"
        );
    }
    private void trackModel(String pathNoExt){
        trackModel(MoreCandles.MOD_ID, pathNoExt);
    }

    @Override
    protected void registerStatesAndModels() {
        customCandle(ModBlocks.REDSTONE_CANDLE);
        customCandle(ModBlocks.SOUL_CANDLE);
        customCandle(ModBlocks.END_CANDLE);
    }

    private void customCandle(RegistryObject<Block> block){
        customCandle(block.get(), block.getId().getPath());
    }

    /**
     * 會生成：
     * - assets/<modid>/models/block/<name>_one_candle.json
     * - assets/<modid>/models/block/<name>_one_candle_lit.json
     * - ... two/three/four ...
     * - assets/<modid>/blockstates/<name>.json（variants candles + lit）
     * - assets/<modid>/models/item/<name>.json（指向 one_candle）
     *
     * 以及會用 trackTexture/trackModel 做 ExistingFileHelper 註冊（避免 missing existing file 問題）
     *
     * 貼圖路徑預設為：
     * - textures/block/<name>.png
     * - textures/block/<name>_lit.png
     */
    private void customCandle(Block block, String name) {
        // 你想要的貼圖位置（可依你專案習慣調整）
        ResourceLocation texUnlit = modLoc("block/" + name);
        ResourceLocation texLit   = modLoc("block/" + name + "_lit");

        // 讓 ExistingFileHelper 知道 datagen 會產這些貼圖/模型
        trackTexture("block/" + name);
        trackTexture("block/" + name + "_lit");

        // 生成 1~4 candle models（unlit/lit 各一）
        ModelFile one      = candleModel(name + "_one_candle",       mcLoc("block/template_candle"),        texUnlit);
        ModelFile oneLit   = candleModel(name + "_one_candle_lit",   mcLoc("block/template_candle"),        texLit);

        ModelFile two      = candleModel(name + "_two_candles",      mcLoc("block/template_two_candles"),   texUnlit);
        ModelFile twoLit   = candleModel(name + "_two_candles_lit",  mcLoc("block/template_two_candles"),   texLit);

        ModelFile three    = candleModel(name + "_three_candles",    mcLoc("block/template_three_candles"), texUnlit);
        ModelFile threeLit = candleModel(name + "_three_candles_lit",mcLoc("block/template_three_candles"), texLit);

        ModelFile four     = candleModel(name + "_four_candles",     mcLoc("block/template_four_candles"),  texUnlit);
        ModelFile fourLit  = candleModel(name + "_four_candles_lit", mcLoc("block/template_four_candles"),  texLit);

        // 記錄模型輸出（block + item）
        trackModel("block/" + name + "_one_candle");
        trackModel("block/" + name + "_one_candle_lit");
        trackModel("block/" + name + "_two_candles");
        trackModel("block/" + name + "_two_candles_lit");
        trackModel("block/" + name + "_three_candles");
        trackModel("block/" + name + "_three_candles_lit");
        trackModel("block/" + name + "_four_candles");
        trackModel("block/" + name + "_four_candles_lit");
        trackModel("item/" + name);

        // 生成 blockstate variants（只看 candles + lit；waterlogged 不影響模型所以不需要寫進 key）
        getVariantBuilder(block)
                .partialState().with(CANDLES, 1).with(LIT, false).modelForState().modelFile(one).addModel()
                .partialState().with(CANDLES, 1).with(LIT, true ).modelForState().modelFile(oneLit).addModel()

                .partialState().with(CANDLES, 2).with(LIT, false).modelForState().modelFile(two).addModel()
                .partialState().with(CANDLES, 2).with(LIT, true ).modelForState().modelFile(twoLit).addModel()

                .partialState().with(CANDLES, 3).with(LIT, false).modelForState().modelFile(three).addModel()
                .partialState().with(CANDLES, 3).with(LIT, true ).modelForState().modelFile(threeLit).addModel()

                .partialState().with(CANDLES, 4).with(LIT, false).modelForState().modelFile(four).addModel()
                .partialState().with(CANDLES, 4).with(LIT, true ).modelForState().modelFile(fourLit).addModel();

        // item model：通常指向 one_candle block model 就行（原版 candle 也類似概念）
        itemModels().withExistingParent(name, mcLoc("item/candle")).texture("layer0", modLoc("item/" + name));
    }

    private ModelFile candleModel(String modelName, ResourceLocation parent, ResourceLocation allTexture) {
        return models()
                .withExistingParent("block/" + modelName, parent)
                .texture("all", allTexture)
                .texture("particle", allTexture);
    }

    private static int yFromHorizontal(Direction dir) {
        return switch (dir) {
            case NORTH -> 0;
            case EAST  -> 90;
            case SOUTH -> 180;
            case WEST  -> 270;
            default -> 0;
        };
    }
}
