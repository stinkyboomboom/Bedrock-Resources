package com.alexvr.bedres.entities.effectball;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.References;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class EffectBallRenderer extends EntityRenderer<EffectBallEntity> {

    private static final ResourceLocation EFFECT_BALL_TEXTURES =  new ResourceLocation(BedrockResources.MODID, "textures/entity/"+ References.EFFECT_BALL_REGNAME +".png");

    public EffectBallRenderer(EntityRendererManager renderManager) {
        super(renderManager);
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

    @Override
    public void doRender(EffectBallEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        ItemStack stack = entity.func_213896_l();
        if (!stack.isEmpty()) {
            System.out.println("rendering: " + stack.getDisplayName());
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableLighting();

            GlStateManager.pushMatrix();

            GlStateManager.translated(x,y,z);

            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }
}
