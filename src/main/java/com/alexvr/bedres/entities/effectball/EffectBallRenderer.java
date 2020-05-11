package com.alexvr.bedres.entities.effectball;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.References;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
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
    public ResourceLocation getEntityTexture(EffectBallEntity entity) {
        return EFFECT_BALL_TEXTURES;
    }

    @Override
    public boolean shouldRender(EffectBallEntity livingEntity, ClippingHelperImpl camera, double camX, double camY, double camZ) {
        return true;
    }


    protected void preRenderCallback(EffectBallEntity entity, float partialTickTime) {
        preRenderCallbackTransparentEntity();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param, GlStateManager.DestFactor.ZERO.param);

        if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.WHITE){
            GL11.glColor4d(0,0,0,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.ORANGE){
            GL11.glColor4d(255,128,0,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.MAGENTA){
            GL11.glColor4d(128,0,128,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.LIGHT_BLUE){
            GL11.glColor4d(153,204,255,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.YELLOW){
            GL11.glColor4d(255,255,0,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.LIME){
            GL11.glColor4d(128,255,0,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.PINK){
            GL11.glColor4d(255,204,204,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.GRAY){
            GL11.glColor4d(128,128,128,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.LIGHT_GRAY){
            GL11.glColor4d(192,192,192,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.CYAN){
            GL11.glColor4d(153,255,255,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.PURPLE){
            GL11.glColor4d(127,0,255,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.BLUE){
            GL11.glColor4d(51,51,255,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.BROWN){
            GL11.glColor4d(102,51,0,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.GREEN){
            GL11.glColor4d(0,102,0,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.RED){
            GL11.glColor4d(255,0,0,.3);
        }else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.BLACK){
            GL11.glColor4d(0,0,0,.3);
        }

    }

    public static void preRenderCallbackTransparentEntity() {
        float f = 1.0F;
        float f1 = 4.5F;
        float f2 = 4.5F;
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param, GlStateManager.DestFactor.ZERO.param);
        GL11.glDepthMask(true);               // is hidden behind other objects

    }

    @Override
    public void render(EffectBallEntity entity, float x, float y, MatrixStack matrix, IRenderTypeBuffer buffer, int l) {
        super.render(entity, x, y, matrix, buffer, l);
        ItemStack stack = entity.func_213896_l();
        if (!stack.isEmpty()) {


            GlStateManager.enableLighting();

            GlStateManager.pushMatrix();

            GlStateManager.translated(x+0,y+.5,entity.getPosZ());
            GlStateManager.scalef(.5f,.5f,.5f);

            GlStateManager.rotatef(-80,274,180,180);
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE,l,l,matrix,buffer);
            GlStateManager.rotatef(180,274,-80,180);
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE,l,l,matrix,buffer);

            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }






}
