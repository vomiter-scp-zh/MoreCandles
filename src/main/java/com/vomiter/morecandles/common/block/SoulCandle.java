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

import java.util.function.ToIntFunction;

public class SoulCandle extends CandleBlock {
    public SoulCandle(Properties p_152801_) {
        super(p_152801_);
    }

    public static final ToIntFunction<BlockState> LIGHT_EMISSION = (p_152848_) -> {
        return p_152848_.getValue(LIT) ? (int) Math.floor(2.5 * p_152848_.getValue(CANDLES)) : 0;
    };

    private int ticksUntilNExtPossibleSoulParticle = 0;

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT)) return;

        // CandleBlock 會依 CANDLES 回傳對應的 offsets
        for (Vec3 off : this.getParticleOffsets(state)) {
            Vec3 p = off.add(pos.getX(), pos.getY(), pos.getZ());
            addParticlesAndSoundCustom(level, p, random);
        }
        if(ticksUntilNExtPossibleSoulParticle > 0) ticksUntilNExtPossibleSoulParticle -= 1;
    }

    private void addParticlesAndSoundCustom(Level level, Vec3 p, RandomSource random) {
        float f = random.nextFloat();

        if (f < 0.5F) {
            if (f < 0.17F) {
                level.playLocalSound(
                        p.x + 0.5D, p.y + 0.5D, p.z + 0.5D,
                        SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS,
                        1.0F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.3F,
                        false
                );
                if(f < 0.08F && ticksUntilNExtPossibleSoulParticle <= 0){
                    level.addParticle(ParticleTypes.SCULK_SOUL, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
                    ticksUntilNExtPossibleSoulParticle += 20;
                }
            }
        }

        level.addParticle(ModParticles.SMALL_SOUL_FLAME.get(), p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);

        // 紅石粒子：通常用 DustParticleOptions（REDSTONE 是常數）
        // 注意：不同版本 DustParticleOptions 建構子/常數略有差異

        //level.addParticle(DustParticleOptions.REDSTONE, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
    }
}
