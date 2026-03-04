package com.vomiter.morecandles.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ScentedCandle  extends CandleBlock {
    public final MobEffect effect;
    private double r;
    private double g;
    private double b;

    private final int particleUntilCooldownMax = 32;
    private final int ticksUntilNextParticleMax = 10;

    private int ticksUntilNextParticle = 0;
    private int particleUntilCooldown = particleUntilCooldownMax;

    public ScentedCandle(Properties p_152801_, MobEffect effect) {
        super(p_152801_);
        this.effect = effect;
        int color = effect.getColor(); // 例如 SAEffects.XXX.get().getColor()
        r = (color >> 16 & 255) / 255.0;
        g = (color >> 8 & 255) / 255.0;
        b = (color & 255) / 255.0;
    }

    @Override
    public void animateTick(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.getValue(LIT)) return;
        for (Vec3 off : this.getParticleOffsets(state)) {
            Vec3 p = off.add(pos.getX(), pos.getY(), pos.getZ());
            addParticlesAndSoundCustom(level, p, random, state.getValue(CANDLES));
        }
        if(ticksUntilNextParticle > 0) ticksUntilNextParticle--;
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

            if (f < 1 - 0.3 * (candleNumber - 1) * 0.1 && ticksUntilNextParticle <= 0) {
                level.addParticle(ParticleTypes.ENTITY_EFFECT,
                        p.x,
                        p.y + 0.3,
                        p.z,
                        r, g, b);
                particleUntilCooldown--;
                if(particleUntilCooldown <= 0){
                    ticksUntilNextParticle = ticksUntilNextParticleMax;
                    particleUntilCooldown = particleUntilCooldownMax;
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
