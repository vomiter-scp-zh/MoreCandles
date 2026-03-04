package com.vomiter.morecandles.common.compat.supp;

import com.vomiter.morecandles.common.block.ISoulCandle;
import com.vomiter.morecandles.registry.ModParticles;
import net.mehvahdjukaar.supplementaries.common.block.blocks.CandleHolderBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SoulCandleHolderBlock extends CandleHolderBlock implements ISoulCandle {
    public SoulCandleHolderBlock(@Nullable DyeColor color, Properties properties) {
        super(color, properties, ModParticles.SMALL_SOUL_FLAME::get, CandleHolderBlock::getParticleOffsets);
    }

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
        return CandleHolderBlock.getParticleOffsets(p_152812_);
    }

    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        animateSoulCandleTick(state, level, pos, random);
    }

}
