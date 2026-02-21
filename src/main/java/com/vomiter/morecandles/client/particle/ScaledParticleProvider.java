package com.vomiter.morecandles.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class ScaledParticleProvider implements ParticleProvider<SimpleParticleType> {

    @FunctionalInterface
    public interface ParticleFactory {
        AbstractScaledFlameParticle create(ClientLevel level,
                                           double x, double y, double z,
                                           double xd, double yd, double zd);
    }

    private final SpriteSet sprite;
    private final ParticleFactory factory;
    private final float scale;

    public ScaledParticleProvider(SpriteSet sprite, ParticleFactory factory, float scale) {
        this.sprite = sprite;
        this.factory = factory;
        this.scale = scale;
    }

    @Override
    public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level,
                                   double x, double y, double z,
                                   double xd, double yd, double zd) {
        AbstractScaledFlameParticle p = factory.create(level, x, y, z, xd, yd, zd);
        p.pickSprite(this.sprite);
        if (scale != 1.0F) p.scale(scale);
        return p;
    }
}
