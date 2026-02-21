package com.vomiter.morecandles.common.block;

import com.vomiter.morecandles.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.ToIntFunction;

public class RedStoneCandle extends CandleBlock implements InvertingRedstoneEmitter {

    public static final ToIntFunction<BlockState> LIGHT_EMISSION = (p_152848_) -> {
        return p_152848_.getValue(LIT) ? (int) (Math.ceil(1.5 * p_152848_.getValue(CANDLES)) + 1) : 0;
    };

    public RedStoneCandle(Properties p_152801_) {
        super(p_152801_);
        this.registerDefaultState(this.stateDefinition.any().setValue(CANDLES, 1).setValue(LIT, Boolean.TRUE).setValue(WATERLOGGED, Boolean.FALSE));
    }
    @Override
    public int getSignal(BlockState state, @NotNull BlockGetter p_55695_, @NotNull BlockPos p_55696_, @NotNull Direction p_55697_) {
        return state.getValue(LIT) && Direction.UP != p_55697_ ?
                ((state.getValue(CANDLES) * 4) - 1) : 0;
    }

    @Override
    public int getDirectSignal(@NotNull BlockState state, BlockGetter level, BlockPos pos, Direction dir) {
        return dir == Direction.DOWN ? state.getSignal(level, pos, dir) : 0;
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean emitterIsLit(BlockState state) {
        return state.getValue(LIT);
    }

    @Override
    public BlockState setLit(BlockState state, boolean lit) {
        return state.setValue(LIT, lit);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block block, BlockPos fromPos, boolean moving) {
        onNeighborChanged(state, level, pos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        tickEmitter(state, level, pos, random);
    }

    @Override
    public void animateTick(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.getValue(LIT)) return;

        // CandleBlock 會依 CANDLES 回傳對應的 offsets
        for (Vec3 off : this.getParticleOffsets(state)) {
            Vec3 p = off.add(pos.getX(), pos.getY(), pos.getZ());
            addParticlesAndSoundCustom(level, p, random);
        }
    }

    private static void addParticlesAndSoundCustom(Level level, Vec3 p, RandomSource random) {
        float f = random.nextFloat();

        if (f < 0.5F) {
            level.addParticle(DustParticleOptions.REDSTONE, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);

            if (f < 0.17F) {
                level.playLocalSound(
                        p.x + 0.5D, p.y + 0.5D, p.z + 0.5D,
                        SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS,
                        1.0F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.3F,
                        false
                );
            }
        }

        level.addParticle(ModParticles.SMALL_REDSTONE_FLAME.get(), p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
    }

}