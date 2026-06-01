package com.vomiter.morecandles.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.vomiter.morecandles.MoreCandles.MOD_ID;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALL_SOUL_FLAME =
            PARTICLES.register("small_soul_flame",
                    () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALL_REDSTONE_FLAME =
            PARTICLES.register("small_redstone_flame",
                    () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALL_COPPER_FLAME =
            PARTICLES.register("small_copper_flame",
                    () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALL_END_FLAME =
            PARTICLES.register("small_end_flame",
                    () -> new SimpleParticleType(false));


}
