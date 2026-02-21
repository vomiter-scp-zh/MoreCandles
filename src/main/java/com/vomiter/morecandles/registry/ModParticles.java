package com.vomiter.morecandles.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.vomiter.morecandles.MoreCandles.MOD_ID;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);

    public static final RegistryObject<SimpleParticleType> SMALL_SOUL_FLAME =
            PARTICLES.register("small_soul_flame",
                    () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SMALL_REDSTONE_FLAME =
            PARTICLES.register("small_redstone_flame",
                    () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SMALL_COPPER_FLAME =
            PARTICLES.register("small_copper_flame",
                    () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SMALL_END_FLAME =
            PARTICLES.register("small_end_flame",
                    () -> new SimpleParticleType(false));


}
