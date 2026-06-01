package com.vomiter.morecandles.common.event;

import com.vomiter.morecandles.Helpers;
import com.vomiter.morecandles.common.block.IScentedCandle;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.CandleBlock;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.*;

public class LivingTickAroundCandleEvent {
    static int INTERVAL = 20;
    static int MIN_EXPOSE_TICKS = INTERVAL * 10;
    static Map<UUID, Integer> EXPOSED_TO_SCENT = new HashMap<>();
    private static final ResourceLocation ADV_MIXED_SCENTS =
            Helpers.id("morecandles", "misc/mixed_scents");

    private static void awardMixedScents(ServerPlayer player) {
        AdvancementHolder adv = player.server.getAdvancements().get(ADV_MIXED_SCENTS);
        if (adv == null) return;
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(adv);
        if (progress.isDone()) return;

        for (String criterion : progress.getRemainingCriteria()) {
            player.getAdvancements().award(adv, criterion);
        }
    }

    public static void onLivingTick(EntityTickEvent.Post event){
        if(!(event.getEntity() instanceof LivingEntity entity)) return;
        if((entity.level().isClientSide())) return;
        if(!(entity instanceof Player)) return;
        if(entity.tickCount % INTERVAL != 0) return;
        BlockPos center = entity.blockPosition();
        Set<Holder<MobEffect>> mobEffects = new HashSet<>();
        int r = 5;
        for (int dx = -r; dx <= r; dx++) {for (int dy = -r; dy <= 2; dy++) {for (int dz = -r; dz <= r; dz++) {
            BlockPos pos = center.offset(dx, dy, dz);
            var state = entity.level().getBlockState(pos);
            if (state.getBlock() instanceof IScentedCandle sc) {
                if(!state.getValue(CandleBlock.LIT)) continue;
                var radius2 = (state.getValue(CandleBlock.CANDLES) + 1) * (state.getValue(CandleBlock.CANDLES) + 1);
                if(radius2 < pos.distToCenterSqr(entity.position())) continue;
                mobEffects.add(sc.effect());
            }
        }}}
        if((long) mobEffects.size() <= 0) {
            EXPOSED_TO_SCENT.remove(entity.getUUID());
            return;
        }
        else {
            UUID id = entity.getUUID();
            int exposedTicks = EXPOSED_TO_SCENT.merge(id, INTERVAL, (oldVal, inc) -> {
                if (oldVal >= MIN_EXPOSE_TICKS) return oldVal;
                return Math.min(MIN_EXPOSE_TICKS, oldVal + inc);
            });
            if(exposedTicks < MIN_EXPOSE_TICKS) {}
            else if((long) mobEffects.size() > 1){
                entity.addEffect(
                        new MobEffectInstance(
                                MobEffects.CONFUSION,
                                100,
                                0,
                                true,
                                false,
                                true
                        )
                );
                if (entity instanceof ServerPlayer sp) {
                    awardMixedScents(sp);
                }
            }
            else entity.addEffect(
                    new MobEffectInstance(
                            mobEffects.iterator().next(),
                            240, //NIGHT VISION FLICKERS WHEN BELOW 200, 220 could cause 1 tick flickering
                            0,
                            true,
                            false,
                            true
                    )
                );
        }
    }
}
