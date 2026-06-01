package com.vomiter.morecandles.compat.supp;

import com.vomiter.morecandles.common.block.IScentedCandle;
import net.mehvahdjukaar.supplementaries.common.block.blocks.CandleHolderBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ScentedCandleHolderBlock  extends CandleHolderBlock implements IScentedCandle {
    public final Holder<MobEffect> effect;
    private final double r;
    private final double g;
    private final double b;

    private int ticksUntilNextParticle = 0;
    private int particleUntilCooldown = particleUntilCooldownMax;

    public ScentedCandleHolderBlock(DyeColor color, BlockBehaviour.Properties properties, Holder<MobEffect> effect) {
        super(color, properties, () -> ParticleTypes.SMALL_FLAME, CandleHolderBlock::getDefaultParticleOffsets);
        this.effect = effect;
        int ecolor = effect.value().getColor(); // 例如 SAEffects.XXX.get().getColor()
        r = (ecolor >> 16 & 255) / 255.0;
        g = (ecolor >> 8 & 255) / 255.0;
        b = (ecolor & 255) / 255.0;

    }

    @Override
    public void animateTick(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        animateScentedTick(state, level, pos, random);
    }

    @Override
    public double r() {
        return r;
    }

    @Override
    public double g() {
        return g;
    }

    @Override
    public double b() {
        return b;
    }

    @Override
    public Holder<MobEffect> effect() {
        return effect;
    }

    @Override
    public int getTicksUntilNextParticle() {
        return ticksUntilNextParticle;
    }

    @Override
    public void setTicksUntilNextParticle(int i) {
        ticksUntilNextParticle = i;
    }

    @Override
    public int getParticleUntilCooldown() {
        return particleUntilCooldown;
    }

    @Override
    public void setParticleUntilCooldown(int i) {
        particleUntilCooldown = i;
    }

    @Override
    public Iterable<Vec3> getScentedCandleParticleOffsets(BlockState p_152812_) {
        return CandleHolderBlock.getDefaultParticleOffsets(p_152812_);
    }
}
