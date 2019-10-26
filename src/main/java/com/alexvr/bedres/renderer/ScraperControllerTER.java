package com.alexvr.bedres.renderer;

import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScraperControllerTile;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScrapperControllerBlock;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.RendererHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

public class ScraperControllerTER extends TileEntityRenderer<BedrockScraperControllerTile> {

    @Override
    public void render(BedrockScraperControllerTile te, double x, double y, double z, float partialTicks, int destroyStage) {

         double CentreOffsetX = 0.05;
         double CentreOffsetY = .18;
         double CentreOffsetZ = 0.95;
        switch (te.getBlockState().get(BedrockScrapperControllerBlock.FACING_HORIZ).toString()){
            case "north":

                break;
            case "south":
                CentreOffsetX+=1;
                CentreOffsetZ-=1;
                break;
            case "east":
                CentreOffsetZ-=1;
                break;
            case "west":
                CentreOffsetX+=1;
                break;
        }


        double scallingMultiplier = 1;



        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translated(x + CentreOffsetX, y + CentreOffsetY, z + CentreOffsetZ);
        GlStateManager.disableRescaleNormal();


        this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.rotated(45,0,1,0);
        RendererHelper.drawAngleCuboidAt(ModBlocks.enderianBlock.getDefaultState(), te, -.6, 0.5, -0.5, 0.5*scallingMultiplier, (0.5625)*scallingMultiplier, 0.5*scallingMultiplier, false, 0, 0, 0, 35);
        RendererHelper.drawAngleCuboidAt(ModBlocks.enderianBlock.getDefaultState(), te, .125-0.5, 1.5, 0.125-0.5, 0.375*scallingMultiplier, (0.125)*scallingMultiplier, 0.375*scallingMultiplier, false, 0, 0, 0, 35);
        RendererHelper.drawAngleCuboidAt(ModBlocks.enderianBlock.getDefaultState(), te, 0.375-0.7, 0.9, 0.375-0.7, 0.25*scallingMultiplier, (0.125)*scallingMultiplier, 0.25*scallingMultiplier, false, 0, 0, 0, 35);
        RendererHelper.drawAngleCuboidAt(ModBlocks.enderianBlock.getDefaultState(), te, 1.2-1.4, -0.5, 1.2-1.4, 0.125*scallingMultiplier, (0.25)*scallingMultiplier, 0.125*scallingMultiplier, te.getUpdateTag().getBoolean("multi"), 0, 0, 0, 35);
        RendererHelper.drawAngleCuboidAt(ModBlocks.enderianBlock.getDefaultState(), te, -0.58, -4.34, -.41, 1.5*scallingMultiplier, (0.03125)*scallingMultiplier, 0.0625*scallingMultiplier, false, 0, 0, 0, 35);
        RendererHelper.drawAngleCuboidAt(ModBlocks.enderianBlock.getDefaultState(), te, -.41, -4.34, -.58, 0.0625*scallingMultiplier, (0.03125)*scallingMultiplier, 1.5*scallingMultiplier, false, 0, 0, 0, 35);
        RendererHelper.drawAngleCuboidAt(ModBlocks.scrapeTank.getDefaultState(), te, -0.52, -4.52, -0.52, 1.125*scallingMultiplier, (0.025)*scallingMultiplier, 1.125*scallingMultiplier, te.getUpdateTag().getBoolean("multi"), 0, 0, 0, 35);


        GlStateManager.enableRescaleNormal();

        GlStateManager.popMatrix();



    }

    public static double interpolate(double x, double x1, double x2, double y1, double y2){
        if (x1 > x2) {
            double temp = x1; x1 = x2; x2 = temp;
            temp = y1; y1 = y2; y2 = temp;
        }

        if (x <= x1) return y1;
        if (x >= x2) return y2;
        double xFraction = (x - x1) / (x2 - x1);
        return y1 + xFraction * (y2 - y1);
    }
}
