package com.vomiter.morecandles.common.block;

import com.vomiter.morecandles.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.level.block.AbstractCandleBlock.LIT;

public interface ICopperCandle {
    Iterable<Vec3> getCopperCandleParticleOffsets(BlockState p_152812_);

    default void animateCopperTick(BlockState p_220697_, Level p_220698_, BlockPos p_220699_, RandomSource p_220700_) {
        if (p_220697_.getValue(LIT)) {
            getCopperCandleParticleOffsets(p_220697_).forEach((p_220695_) -> {
                addParticlesAndSoundCustom(p_220698_, p_220695_.add((double)p_220699_.getX(), (double)p_220699_.getY(), (double)p_220699_.getZ()), p_220700_);
            });
        }
    }

    private static void addParticlesAndSoundCustom(Level p_220688_, Vec3 p_220689_, RandomSource p_220690_) {
        float f = p_220690_.nextFloat();
        if (f < 0.3F) {
            p_220688_.addParticle(ParticleTypes.SMOKE, p_220689_.x, p_220689_.y, p_220689_.z, 0.0D, 0.0D, 0.0D);
            if (f < 0.17F) {
                p_220688_.playLocalSound(p_220689_.x + 0.5D, p_220689_.y + 0.5D, p_220689_.z + 0.5D, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + p_220690_.nextFloat(), p_220690_.nextFloat() * 0.7F + 0.3F, false);
            }
        }

        p_220688_.addParticle(ModParticles.SMALL_COPPER_FLAME.get(), p_220689_.x, p_220689_.y, p_220689_.z, 0.0D, 0.0D, 0.0D);
    }

}
