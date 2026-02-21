package com.vomiter.morecandles.mixin;

import com.vomiter.mobcivics.api.common.block.IPiglinRepellentBlock;
import com.vomiter.morecandles.common.block.SoulCandle;
import com.vomiter.morecandles.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoulCandle.class)
public class SoulCandlePiglinRepellentMixin implements IPiglinRepellentBlock {
    @Override
    public boolean isRepellent(Level level, BlockPos pos) {
        var state = level.getBlockState(pos);
        if(state.is(ModBlocks.SOUL_CANDLE.get())){
            return state.getValue(SoulCandle.LIT);
        }
        return false;
    }
}
