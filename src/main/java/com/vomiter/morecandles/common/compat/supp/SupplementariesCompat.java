package com.vomiter.morecandles.common.compat.supp;

import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class SupplementariesCompat {
    public static RegistryObject<Block> getSoulCandleHolder(){
        return ModSuppRegistry.SOUL_CANDLE_HOLDER;
    }
    public static RegistryObject<Block> getEndCandleHolder(){
        return ModSuppRegistry.END_CANDLE_HOLDER;
    }
    public static Map<ModBlocks.Scented, RegistryObject<Block>> getScentedCandleHolders(){ return ModSuppRegistry.SCENTED_CANDLE_HOLDERS;}
}
