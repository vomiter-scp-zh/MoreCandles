package com.vomiter.morecandles.compat.supp;

import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;

public class SupplementariesCompat {
    public static DeferredHolder<Block, Block> getSoulCandleHolder(){
        return ModSuppRegistry.SOUL_CANDLE_HOLDER;
    }
    public static DeferredHolder<Block, Block> getEndCandleHolder(){
        return ModSuppRegistry.END_CANDLE_HOLDER;
    }
    public static DeferredHolder<Block, Block> getCopperCandleHolder(){
        return ModSuppRegistry.COPPER_CANDLE_HOLDER;
    }

    public static Map<ModBlocks.Scented, DeferredHolder<Block, Block>> getScentedCandleHolders(){ return ModSuppRegistry.SCENTED_CANDLE_HOLDERS;}
}
