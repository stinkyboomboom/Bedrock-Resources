package com.alexvr.bedres.entities.effectball;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.References;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class EffectBallRenderer extends MobRenderer<EffectBallEntity, EffectBallModel<EffectBallEntity>> {

    private static final ResourceLocation EFFECT_BALL_TEXTURES =  new ResourceLocation(BedrockResources.MODID, "textures/entity/"+ References.EFFECT_BALL_REGNAME +".png");

    public EffectBallRenderer(EntityRendererManager renderManager) {
        super(renderManager, new EffectBallModel<>(),0.3f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EffectBallEntity entity) {
        return EFFECT_BALL_TEXTURES;
    }

    @Override
    public boolean shouldRender(EffectBallEntity livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }

    protected void preRenderCallback(EffectBallEntity entitylivingbaseIn, float partialTickTime) {
        preRenderCallbackTransparentEntity();
    }

    public static void preRenderCallbackTransparentEntity() {
        float f = 1.0F;
        float f1 = 4.5F;
        float f2 = 4.5F;
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glDepthMask(true);               // gem is hidden behind other objects
    }

    @Override
    public void doRender(EffectBallEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        ItemStack stack = entity.func_213896_l();
        if (!stack.isEmpty()) {


            GlStateManager.enableLighting();

            GlStateManager.pushMatrix();

            GlStateManager.translated(x+0,y+.5,z+0);
            GlStateManager.scalef(.5f,.5f,.5f);

            GlStateManager.rotated(-80,274,180,180);
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
            GlStateManager.rotated(180,274,-80,180);
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }


}
