package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
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
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param, GlStateManager.DestFactor.ZERO.param);
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();

        BedrockResources.proxy.getMinecraft().getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//botl    bufferbuilder.pos(0.0D, (double)BedrockResources.proxy.getMinecraft().mainWindow.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos(leftSideX, bottomY, -90.0D).tex(0.0f, 1.0f).endVertex();

//botr    bufferbuilder.pos((double)BedrockResources.proxy.getMinecraft().mainWindow.getScaledWidth(), (double)BedrockResources.proxy.getMinecraft().mainWindow.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(rightSideX, bottomY, -90.0D).tex(1.0f, 1.0f).endVertex();

//topr      bufferbuilder.pos((double)BedrockResources.proxy.getMinecraft().mainWindow.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(rightSideX, topY, -90.0D).tex(1.0f, 0.0f).endVertex();

//topl    bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        bufferbuilder.pos(leftSideX, topY, -90.0D).tex(0.0f, 0.0f).endVertex();

        tessellator.draw();
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }

    public static void drawCuboidAt(BlockState blockTexture, TileEntity te, double xtranslate, double ytranslate, double ztranslate, double xScale, double yScale, double zScale, boolean rotate, float xrotation, float yrotation, float zrotation, int rotationSpeed, MatrixStack mx,int light) {

        GlStateManager.pushMatrix();
        GlStateManager.translated(xtranslate,ytranslate,ztranslate);
        GlStateManager.scaled(xScale,yScale,zScale);
        if(rotate) {

            long angle = (System.currentTimeMillis() / rotationSpeed) %360;
            GlStateManager.rotatef(angle, xrotation, yrotation, zrotation);
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
        net.minecraft.world.ILightReader worldreader = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), te.getPos());
        long i = blockTexture.getPositionRandom(te.getPos());
        dispatcher.getBlockModelRenderer().renderModel(worldreader, model, blockTexture, te.getPos(),mx, bufferBuilder.getVertexBuilder(), true,new Random(),i,light);
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)

        GlStateManager.popMatrix();
    }

    public static void drawCubeAt(BlockState blockTexture, World world, LivingEntity entity, double xtranslate, double ytranslate, double ztranslate, double xScale, double yScale, double zScale, boolean rotate, float xrotation, float yrotation, float zrotation, int rotationSpeed, MatrixStack mx,int light) {

        GlStateManager.pushMatrix();
        GlStateManager.translated(xtranslate,ytranslate,ztranslate);
        GlStateManager.scaled(xScale,yScale,zScale);
        if(rotate) {

            long angle = (System.currentTimeMillis() / rotationSpeed) %360;
            GlStateManager.rotatef(angle, xrotation, yrotation, zrotation);
        }
        GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)

        // Translate back to local view coordinates so that we can do the acual rendering here
        GlStateManager.translated(-entity.getPosition().getX(), -entity.getPosition().getY(), -entity.getPosition().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(blockTexture);
        net.minecraft.world.ILightReader worldreader = MinecraftForgeClient.getRegionRenderCache(world, entity.getPosition());
        long i = blockTexture.getPositionRandom(entity.getPosition());
        dispatcher.getBlockModelRenderer().renderModel(worldreader, model, blockTexture, entity.getPosition(),mx, bufferBuilder.getVertexBuilder(), true,new Random(),i,light);
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)

        GlStateManager.popMatrix();
    }



        public static void drawAngleCuboidAt(BlockState blockTexture,TileEntity te,double xtranslate,double ytranslate, double ztranslate,double xScale,double yScale,double zScale,boolean rotate,float xrotation,float yrotation, float zrotation, int rotationSpeed, MatrixStack mx,int light) {

        GlStateManager.pushMatrix();
        GlStateManager.scaled(xScale,yScale,zScale);
        if(rotate) {

            float angle = (float) (((System.currentTimeMillis() / rotationSpeed) %720 )*(2*Math.PI));
            GlStateManager.rotatef(angle, xrotation, 1, zrotation);


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
        net.minecraft.world.ILightReader worldreader = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), te.getPos());
        long i = blockTexture.getPositionRandom(te.getPos());
            dispatcher.getBlockModelRenderer().renderModel(worldreader, model, blockTexture, te.getPos(),mx, bufferBuilder.getVertexBuilder(), true,new Random(),i,light);
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)

        GlStateManager.popMatrix();
    }

    public static void renderItem(TileEntity te,ItemStack itemStack,double xTranslate,double yTranslate,double zTranslate,double xScalee,double yScale,double zScale,float xRotate,float yRotate,float zRotate,float angle, MatrixStack mx,int light, IRenderTypeBuffer bufferIn){

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();

        GlStateManager.pushMatrix();


        GlStateManager.translated(xTranslate,yTranslate,zTranslate);
        GlStateManager.scaled(xScalee,yScale,zScale);
        GlStateManager.rotatef(angle,xRotate,yRotate,zRotate);



        Minecraft.getInstance().getItemRenderer().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE,0,0,mx,bufferIn);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();

    }

}
