package com.vomiter.morecandles.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EndCandle extends CandleBlock implements IEndCandle {
    public EndCandle(Properties p_152801_) {
        super(p_152801_);
    }

    @Override
    public Iterable<Vec3> getEndCandleParticleOffsets(BlockState p_152812_) {
        return getParticleOffsets(p_152812_);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        animateEndTick(state, level, pos, random);
    }
}
