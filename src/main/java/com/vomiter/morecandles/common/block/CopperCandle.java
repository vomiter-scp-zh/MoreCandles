package com.vomiter.morecandles.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class CopperCandle extends CandleBlock implements ICopperCandle {
    public CopperCandle(Properties p_152801_) {
        super(p_152801_);
    }

    @Override
    public Iterable<Vec3> getCopperCandleParticleOffsets(BlockState p_152812_) {
        return getParticleOffsets(p_152812_);
    }

    @Override
    public void animateTick(@NotNull BlockState p_220697_, @NotNull Level p_220698_, @NotNull BlockPos p_220699_, @NotNull RandomSource p_220700_){
        animateCopperTick(p_220697_, p_220698_, p_220699_, p_220700_);
    }
}
