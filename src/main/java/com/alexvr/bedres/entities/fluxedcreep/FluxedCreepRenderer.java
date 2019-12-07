package com.alexvr.bedres.entities.fluxedcreep;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.entities.effectball.EffectBallRenderer;
import com.alexvr.bedres.utils.References;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FluxedCreepRenderer extends MobRenderer<FluxedCreepEntity, FluxedCreepModel<FluxedCreepEntity>> {
    private static final ResourceLocation GHAST_TEXTURES =  new ResourceLocation(BedrockResources.MODID, "textures/entity/"+ References.FLUXED_CREEP_REGNAME +".png");
    private static final ResourceLocation GHAST_SHOOTING_TEXTURES = new ResourceLocation(BedrockResources.MODID, "textures/entity/"+ References.FLUXED_CREEP_REGNAME +".png");
    public FluxedCreepRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FluxedCreepModel<>(), 0.5F);
    }

    protected ResourceLocation getEntityTexture(FluxedCreepEntity entity) {
        return entity.isAttacking() ? GHAST_SHOOTING_TEXTURES : GHAST_TEXTURES;
    }

    protected void preRenderCallback(FluxedCreepEntity entitylivingbaseIn, float partialTickTime) {
        EffectBallRenderer.preRenderCallbackTransparentEntity();
    }
}
