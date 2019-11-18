package com.alexvr.bedres.entities.fluxedcreep;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.References;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FluxedCreepRenderer extends MobRenderer<FluxedCreepEntity, FluxedCreepModel<FluxedCreepEntity>> {
    private static final ResourceLocation GHAST_TEXTURES =  new ResourceLocation(BedrockResources.MODID, "textures/entity/"+ References.FLUXED_CREEP_REGNAME +".png");
    private static final ResourceLocation GHAST_SHOOTING_TEXTURES = new ResourceLocation(BedrockResources.MODID, "textures/entity/"+ References.FLUXED_CREEP_REGNAME +".png");
    public FluxedCreepRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FluxedCreepModel<>(), 1.5F);
    }

    protected ResourceLocation getEntityTexture(FluxedCreepEntity entity) {
        return entity.isAttacking() ? GHAST_SHOOTING_TEXTURES : GHAST_TEXTURES;
    }

    protected void preRenderCallback(FluxedCreepEntity entitylivingbaseIn, float partialTickTime) {
        float f = 1.0F;
        float f1 = 4.5F;
        float f2 = 4.5F;
        GlStateManager.scalef(1, 1, 1);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
