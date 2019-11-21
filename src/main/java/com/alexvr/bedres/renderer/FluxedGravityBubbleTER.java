package com.alexvr.bedres.renderer;

import com.alexvr.bedres.blocks.tiles.FluxedGravityBubbleTile;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.RendererHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import org.lwjgl.opengl.GL11;

public class FluxedGravityBubbleTER extends TileEntityRenderer<FluxedGravityBubbleTile> {

    @Override
    public void render(FluxedGravityBubbleTile te, double x, double y, double z, float partialTicks, int destroyStage) {

        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translated(x, y, z);
        GlStateManager.disableRescaleNormal();
        if (te.render) {
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GL11.glDepthMask(false);               // gem is hidden behind other objects

            this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            RendererHelper.drawCuboidAt(ModBlocks.rangeView.getDefaultState(), te, -te.xDist, -te.yDist, -te.zDist, (te.xDist*2) + 1, (te.yDist * 2) + 1, (te.zDist * 2) + 1, false, 0, 0, 0, 10);

            }
        GlStateManager.enableRescaleNormal();

        GlStateManager.popMatrix();


    }
}
