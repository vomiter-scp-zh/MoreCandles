package com.vomiter.morecandles.common.block;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public interface InvertingRedstoneEmitter {

    /* ========= burnout config ========= */

    Map<Level, List<Toggle>> RECENT_TOGGLES = new WeakHashMap<>();

    int RECENT_TOGGLE_TIMER = 60;
    int MAX_RECENT_TOGGLES = 8;
    int RESTART_DELAY = 160;
    int TOGGLE_DELAY = 2;

    /* ========= block 必須提供的 hook ========= */

    // 目前是否亮
    boolean emitterIsLit(BlockState state);

    // 設定亮 / 熄
    BlockState setLit(BlockState state, boolean lit);

    // 判定「輸入」：預設為下方供電
    default boolean hasInputSignal(Level level, BlockPos pos, BlockState state) {
        return level.hasSignal(pos.below(), net.minecraft.core.Direction.DOWN);
    }

    /* ========= neighbor update ========= */

    default void onNeighborChanged(BlockState state, Level level, BlockPos pos) {
        if (emitterIsLit(state) == hasInputSignal(level, pos, state)
                && !level.getBlockTicks().willTickThisTick(pos, (net.minecraft.world.level.block.Block) this)) {
            level.scheduleTick(pos, (net.minecraft.world.level.block.Block) this, TOGGLE_DELAY);
        }
    }

    /* ========= tick state machine ========= */

    default void tickEmitter(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        boolean input = hasInputSignal(level, pos, state);

        List<Toggle> list = RECENT_TOGGLES.get(level);

        // 清掉過期 toggle
        while (list != null && !list.isEmpty()
                && level.getGameTime() - list.get(0).when > RECENT_TOGGLE_TIMER) {
            list.remove(0);
        }

        if (emitterIsLit(state)) {
            if (input) {
                level.setBlock(pos, setLit(state, false), 3);

                if (isToggledTooFrequently(level, pos, true)) {
                    level.levelEvent(1502, pos, 0);
                    level.scheduleTick(pos,
                            level.getBlockState(pos).getBlock(),
                            RESTART_DELAY);
                }
            }
        } else {
            if (!input && !isToggledTooFrequently(level, pos, false)) {
                level.setBlock(pos, setLit(state, true), 3);
            }
        }
    }

    /* ========= burnout tracking ========= */

    default boolean isToggledTooFrequently(Level level, BlockPos pos, boolean add) {
        List<Toggle> list = RECENT_TOGGLES.computeIfAbsent(level, l -> Lists.newArrayList());

        if (add) {
            list.add(new Toggle(pos.immutable(), level.getGameTime()));
        }

        int count = 0;
        for (Toggle t : list) {
            if (t.pos.equals(pos)) {
                ++count;
                if (count >= MAX_RECENT_TOGGLES) return true;
            }
        }
        return false;
    }

    /* ========= data holder ========= */

    class Toggle {
        final BlockPos pos;
        final long when;

        public Toggle(BlockPos pos, long when) {
            this.pos = pos;
            this.when = when;
        }
    }
}
