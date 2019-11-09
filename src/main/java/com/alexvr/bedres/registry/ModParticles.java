package com.alexvr.bedres.registry;

import com.alexvr.bedres.particles.BedrockDust;
import com.alexvr.bedres.particles.IParticle;
import com.alexvr.bedres.particles.RitualParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModParticles {
    BEDROCK_DUST,
    RITUALPARTICLE;

    ModParticles() {}

    @OnlyIn(Dist.CLIENT)
    public Particle create(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... params) {
        return getFactory().makeParticle(world, x, y, z, velocityX, velocityY, velocityZ, params);
    }

    @OnlyIn(Dist.CLIENT)
    public IParticle getFactory() {
        switch (this) {
            case BEDROCK_DUST:
                return new BedrockDust.Factory();
            case RITUALPARTICLE:
                return new RitualParticle.Factory();
        }
        return this.getDefaultParticle().getFactory();
    }

    public ModParticles getDefaultParticle() {
        return BEDROCK_DUST;
    }

    public void spawn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        if (world.isRemote) {
            spawn(create(world, x, y, z, velocityX, velocityY, velocityZ, parameters));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void spawn(Particle particle) {
        Minecraft.getInstance().particles.addEffect(particle);
    }
}
