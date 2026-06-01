package com.vomiter.morecandles.client;

import com.vomiter.morecandles.client.particle.GenericFlameParticle;
import com.vomiter.morecandles.client.particle.ScaledParticleProvider;
import com.vomiter.morecandles.registry.ModParticles;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public final class ClientModEvents {
    public static void init(IEventBus modBus){
        modBus.addListener(ClientModEvents::onRegisterRenderers);
        modBus.addListener(ClientModEvents::onRegisterParticles);
    }

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
    }

    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SMALL_SOUL_FLAME.get(),
                sprite -> new ScaledParticleProvider(sprite, GenericFlameParticle::new, 0.5F));
        event.registerSpriteSet(ModParticles.SMALL_REDSTONE_FLAME.get(),
                sprite -> new ScaledParticleProvider(sprite, GenericFlameParticle::new, 0.5F));
        event.registerSpriteSet(ModParticles.SMALL_COPPER_FLAME.get(),
                sprite -> new ScaledParticleProvider(sprite, GenericFlameParticle::new, 0.5F));
        event.registerSpriteSet(ModParticles.SMALL_END_FLAME.get(),
                sprite -> new ScaledParticleProvider(sprite, GenericFlameParticle::new, 0.5F));
    }

}
