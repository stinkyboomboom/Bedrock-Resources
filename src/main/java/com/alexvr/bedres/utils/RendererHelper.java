package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RendererHelper {
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

    public static void drawCuboidAt(BlockState blockTexture,TileEntity te,double xtranslate,double ytranslate, double ztranslate,double xScale,double yScale,double zScale,boolean rotate,double xrotation,double yrotation, double zrotation, int rotationSpeed) {

        GlStateManager.pushMatrix();
        GlStateManager.translated(xtranslate,ytranslate,ztranslate);
        GlStateManager.scaled(xScale,yScale,zScale);
        if(rotate) {

            long angle = (System.currentTimeMillis() / rotationSpeed) %360;
            GlStateManager.rotated(angle, xrotation, yrotation, zrotation);
        }
        GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)

        World world = te.getWorld();
        // Translate back to local view coordinates so that we can do the acual rendering here
        GlStateManager.translated(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(blockTexture);
        net.minecraft.world.IEnviromentBlockReader worldreader = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), te.getPos());
        long i = blockTexture.getPositionRandom(te.getPos());
        dispatcher.getBlockModelRenderer().renderModel(worldreader, model, blockTexture, te.getPos(), bufferBuilder, true,new Random(),i);
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)

        GlStateManager.popMatrix();
    }



        public static void drawAngleCuboidAt(BlockState blockTexture,TileEntity te,double xtranslate,double ytranslate, double ztranslate,double xScale,double yScale,double zScale,boolean rotate,double xrotation,double yrotation, double zrotation, int rotationSpeed) {

        GlStateManager.pushMatrix();
        GlStateManager.scaled(xScale,yScale,zScale);
        if(rotate) {

            double angle = ((System.currentTimeMillis() / rotationSpeed) %720 )*(2*Math.PI);
            GlStateManager.rotated(angle, xrotation, 1, zrotation);


        }
        GlStateManager.translated(xtranslate,ytranslate,ztranslate);

        GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)

        World world = te.getWorld();
        // Translate back to local view coordinates so that we can do the acual rendering here
        GlStateManager.translated(-(te.getPos().getX()), -(te.getPos().getY()), -(te.getPos().getZ()));


        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(blockTexture);
        net.minecraft.world.IEnviromentBlockReader worldreader = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), te.getPos());
        long i = blockTexture.getPositionRandom(te.getPos());
        dispatcher.getBlockModelRenderer().renderModel(worldreader, model, blockTexture, te.getPos(), bufferBuilder, true,new Random(),i);
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)

        GlStateManager.popMatrix();
    }

    public static void renderItem(TileEntity te,ItemStack itemStack,double xTranslate,double yTranslate,double zTranslate,double xScalee,double yScale,double zScale,double xRotate,double yRotate,double zRotate,double angle){

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();

        GlStateManager.pushMatrix();


        GlStateManager.translated(xTranslate,yTranslate,zTranslate);
        GlStateManager.scaled(xScalee,yScale,zScale);
        GlStateManager.rotated(angle,xRotate,yRotate,zRotate);



        Minecraft.getInstance().getItemRenderer().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();

    }

}
