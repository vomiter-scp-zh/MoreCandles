package com.vomiter.morecandles.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractScaledFlameParticle extends RisingParticle {

    protected AbstractScaledFlameParticle(ClientLevel level, double x, double y, double z,
                                          double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().move(dx, dy, dz));
        this.setLocationFromBoundingbox();
    }

    @Override
    public float getQuadSize(float partialTick) {
        float f = ((float) this.age + partialTick) / (float) this.lifetime;
        return this.quadSize * (1.0F - f * f * 0.5F);
    }

    // 如果想讓不同火焰類型有不同亮度曲線，就留 hook
    protected int applyLightRamp(int baseLight, float partialTick) {
        return baseLight;
    }

    @Override
    public int getLightColor(float partialTick) {
        return applyLightRamp(super.getLightColor(partialTick), partialTick);
    }
}
