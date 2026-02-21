package com.vomiter.morecandles.common.block;

import com.vomiter.morecandles.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EndCandle extends CandleBlock {
    public EndCandle(Properties p_152801_) {
        super(p_152801_);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT)) return;

        // CandleBlock 會依 CANDLES 回傳對應的 offsets
        for (Vec3 off : this.getParticleOffsets(state)) {
            Vec3 p = off.add(pos.getX(), pos.getY(), pos.getZ());
            addParticlesAndSoundCustom(level, p, random, state.getValue(CANDLES));
        }
    }

    private void addParticlesAndSoundCustom(Level level, Vec3 p, RandomSource random, int candleNumber) {
        float f = random.nextFloat();

        if (f < 0.5F) {
            if (f < 1 - 0.3 * (candleNumber - 1)) {
                level.addParticle(ParticleTypes.END_ROD,
                        p.x + (random.nextFloat() - 0.5) * 0.6,
                        p.y + random.nextFloat() * 0.3,
                        p.z + (random.nextFloat() - 0.5) * 0.6,
                        0.0D, 0.0D, 0.0D);
                level.playLocalSound(
                        p.x + 0.5D, p.y + 0.5D, p.z + 0.5D,
                        SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS,
                        1.0F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.3F,
                        false
                );
            }
        }
        if(f < 1 - 0.1 * candleNumber) level.addParticle(ModParticles.SMALL_END_FLAME.get(), p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
    }

}
