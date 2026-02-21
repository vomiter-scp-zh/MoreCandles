package com.vomiter.morecandles.data.loot;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public final class LootTableHelpers {
    private LootTableHelpers() {}

    /**
     * Vanilla Candle loot 行為：
     * - candles=1 -> 掉 1
     * - candles=2 -> 掉 2
     * - candles=3 -> 掉 3
     * - candles=4 -> 掉 4
     * - 並套用 explosion_decay
     *
     * @param candleBlock 用來做 block_state_property 判斷的 block（通常就是自己）
     * @param dropItem    實際掉落的 item（通常也是自己）
     * @param candlesProp state 上的蠟燭數量屬性（通常 BlockStateProperties.CANDLES）
     */
    public static LootTable.Builder candleLikeDrops(Block candleBlock, ItemLike dropItem, IntegerProperty candlesProp) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                                LootItem.lootTableItem(dropItem)
                                        // candles=2 -> count=2
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                                                .when(LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(candleBlock)
                                                        .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(candlesProp, 2))))
                                        // candles=3 -> count=3
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))
                                                .when(LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(candleBlock)
                                                        .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(candlesProp, 3))))
                                        // candles=4 -> count=4
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))
                                                .when(LootItemBlockStatePropertyCondition
                                                        .hasBlockStateProperties(candleBlock)
                                                        .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(candlesProp, 4))))
                                        // vanilla: explosion_decay
                                        .apply(ApplyExplosionDecay.explosionDecay())
                        )
        );
    }

    // 如果你所有 candle 都是標準 CandleBlock（有 BlockStateProperties.CANDLES），可以再包一層簡化呼叫：
    public static LootTable.Builder vanillaCandleLikeDrops(Block candleBlock) {
        return candleLikeDrops(candleBlock, candleBlock, BlockStateProperties.CANDLES);
    }
}