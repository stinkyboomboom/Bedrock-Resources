package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderHelper {
    public static void drawModalRectWithCustomSizedTexture(double leftSideX, double rightSideX, double bottomY, double topY, ResourceLocation texture) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();

        BedrockResources.proxy.getMinecraft().getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//botl    bufferbuilder.pos(0.0D, (double)BedrockResources.proxy.getMinecraft().mainWindow.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos(leftSideX, bottomY, -90.0D).tex(0.0D, 1.0D).endVertex();

//botr    bufferbuilder.pos((double)BedrockResources.proxy.getMinecraft().mainWindow.getScaledWidth(), (double)BedrockResources.proxy.getMinecraft().mainWindow.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(rightSideX, bottomY, -90.0D).tex(1.0D, 1.0D).endVertex();

//topr      bufferbuilder.pos((double)BedrockResources.proxy.getMinecraft().mainWindow.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(rightSideX, topY, -90.0D).tex(1.0D, 0.0D).endVertex();

//topl    bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        bufferbuilder.pos(leftSideX, topY, -90.0D).tex(0.0D, 0.0D).endVertex();

        tessellator.draw();
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }
}
