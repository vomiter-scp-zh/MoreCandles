package com.vomiter.morecandles.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.AbstractCandleBlock.LIT;
import static net.minecraft.world.level.block.CandleBlock.CANDLES;

public interface IScentedCandle {
    double r();
    double g();
    double b();
    Holder<MobEffect> effect();

    int particleUntilCooldownMax = 32;
    int ticksUntilNextParticleMax = 10;
    int getTicksUntilNextParticle();
    void setTicksUntilNextParticle(int i);
    int getParticleUntilCooldown();
    void setParticleUntilCooldown(int i);
    Iterable<Vec3> getScentedCandleParticleOffsets(BlockState p_152812_);

    default void animateScentedTick(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random){
        if (!state.getValue(LIT)) return;
        for (Vec3 off : getScentedCandleParticleOffsets(state)) {
            Vec3 p = off.add(pos.getX(), pos.getY(), pos.getZ());
            addParticlesAndSoundCustom(level, p, random, state.getValue(CANDLES));
        }
        if(getTicksUntilNextParticle() > 0) setTicksUntilNextParticle(getTicksUntilNextParticle() - 1);
    }

    private void addParticlesAndSoundCustom(Level level, Vec3 p, RandomSource random, int candleNumber) {
        float f = random.nextFloat();
        if (f < 0.5F) {
            if(f < 0.03){
                level.addParticle(
                        ParticleTypes.SMOKE,
                        p.x,
                        p.y,
                        p.z,
                        0.0D,
                        0.0D,
                        0.0D
                );
            }

            if (f < 1 - 0.3 * (candleNumber - 1) * 0.1 && getTicksUntilNextParticle() <= 0) {
                level.addParticle(
                        ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, (float) r(), (float) g(), (float) b()),
                        p.x,
                        p.y + 0.3,
                        p.z,
                        0, 0, 0);
                setParticleUntilCooldown(getParticleUntilCooldown() - 1);
                if(getParticleUntilCooldown() <= 0){
                    setTicksUntilNextParticle(ticksUntilNextParticleMax);
                    setParticleUntilCooldown(particleUntilCooldownMax);
                }
            }
            if(f < 0.17){
                level.playLocalSound(
                        p.x + 0.5D, p.y + 0.5D, p.z + 0.5D,
                        SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS,
                        1.0F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.3F,
                        false
                );
            }
        }
        if(f < 1 - 0.1 * candleNumber) level.addParticle(ParticleTypes.SMALL_FLAME, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
    }

}
