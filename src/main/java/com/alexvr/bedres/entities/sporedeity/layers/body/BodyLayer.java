package com.alexvr.bedres.entities.sporedeity.layers.body;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BodyLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("bedres:textures/entity/aura1.png");
    private final BodyModel<T> modelElytra = new BodyModel<>(0.0F, false);

    public BodyLayer(IEntityRenderer<T, M> p_i50942_1_) {
        super(p_i50942_1_);
    }

    public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {

            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.bindTexture(TEXTURE_ELYTRA);
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.0F, 0.0F, 0.0F);
            this.modelElytra.setRotationAngles(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
            this.modelElytra.render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();

    }

    public boolean shouldCombineTextures() {
        return false;
    }
}