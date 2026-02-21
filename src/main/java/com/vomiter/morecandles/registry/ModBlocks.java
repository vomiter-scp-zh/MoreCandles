package com.vomiter.morecandles.registry;

import com.vomiter.morecandles.common.block.EndCandle;
import com.vomiter.morecandles.common.block.RedStoneCandle;
import com.vomiter.morecandles.common.block.SoulCandle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = ModRegistries.createRegistry(ForgeRegistries.BLOCKS);

    private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
        return (p_50763_) -> {
            return p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
        };
    }

    private static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return false;
    }

    private static Boolean always(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_, EntityType<?> p_50813_) {
        return true;
    }

    private static RedStoneCandle redStoneCandle(MapColor p_285034_) {
        return new RedStoneCandle(BlockBehaviour.Properties.of().mapColor(p_285034_).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(RedStoneCandle.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY));
    }
    private static SoulCandle soulCandle(MapColor p_285034_) {
        return new SoulCandle(BlockBehaviour.Properties.of().mapColor(p_285034_).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandle.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY));
    }
    private static EndCandle endCandle(MapColor p_285034_) {
        return new EndCandle(BlockBehaviour.Properties.of().mapColor(p_285034_).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(CandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY));
    }


    public static final RegistryObject<Block> REDSTONE_CANDLE = BLOCKS.register("redstone_candle", () -> redStoneCandle(MapColor.SAND));
    public static final RegistryObject<Block> SOUL_CANDLE = BLOCKS.register("soul_candle", () -> soulCandle(MapColor.SAND));
    public static final RegistryObject<Block> END_CANDLE = BLOCKS.register("end_candle", () -> endCandle(MapColor.TERRACOTTA_WHITE));

    private static void registerItem(RegistryObject<Block> block){
        ModItems.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    static {
        registerItem(REDSTONE_CANDLE);
        registerItem(SOUL_CANDLE);
        registerItem(END_CANDLE);
    }
}
