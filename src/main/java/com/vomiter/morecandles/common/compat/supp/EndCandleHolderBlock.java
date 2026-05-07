package com.vomiter.morecandles.common.compat.supp;

import com.vomiter.morecandles.common.block.IEndCandle;
import com.vomiter.morecandles.registry.ModParticles;
import net.mehvahdjukaar.supplementaries.common.block.blocks.CandleHolderBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EndCandleHolderBlock extends CandleHolderBlock implements IEndCandle {
    public EndCandleHolderBlock(DyeColor color, Properties properties) {
        super(color, properties, ModParticles.SMALL_END_FLAME::get, CandleHolderBlock::getParticleOffsets);
    }

    @Override
    public Iterable<Vec3> getEndCandleParticleOffsets(BlockState p_152812_) {
        return CandleHolderBlock.getParticleOffsets(p_152812_);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        animateEndTick(state, level, pos, random);
    }

}
