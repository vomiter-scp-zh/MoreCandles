package com.vomiter.morecandles.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.ToIntFunction;

public class SoulCandle extends CandleBlock implements ISoulCandle{
    public SoulCandle(Properties p_152801_) {
        super(p_152801_);
    }

    public static final ToIntFunction<BlockState> LIGHT_EMISSION = (p_152848_) -> {
        return p_152848_.getValue(LIT) ? (int) Math.floor(2.5 * p_152848_.getValue(CANDLES)) : 0;
    };

    private int ticksUntilNExtPossibleSoulParticle = 0;

    @Override
    public int getTicksUntilNExtPossibleSoulParticle() {
        return ticksUntilNExtPossibleSoulParticle;
    }

    @Override
    public void setTicksUntilNExtPossibleSoulParticle(int tick) {
        ticksUntilNExtPossibleSoulParticle = tick;
    }

    @Override
    public @NotNull Iterable<Vec3> getSoulCandleParticleOffsets(@NotNull BlockState p_152812_) {
        return super.getParticleOffsets(p_152812_);
    }

    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        animateSoulCandleTick(state, level, pos, random);
    }

}
