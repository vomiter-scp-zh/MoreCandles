package com.vomiter.morecandles.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;

public class GenericFlameParticle extends AbstractScaledFlameParticle {
    public GenericFlameParticle(ClientLevel level, double x, double y, double z,
                                double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
    }
}
