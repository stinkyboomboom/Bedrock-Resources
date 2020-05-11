package com.alexvr.bedres.renderer;

import com.alexvr.bedres.blocks.tiles.FluxedGravityBubbleTile;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.RendererHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import org.lwjgl.opengl.GL11;

public class FluxedGravityBubbleTER extends TileEntityRenderer<FluxedGravityBubbleTile> {


    public FluxedGravityBubbleTER(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(FluxedGravityBubbleTile te, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translated(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
        GlStateManager.disableRescaleNormal();
        if (te.render) {
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param, GlStateManager.DestFactor.ZERO.param);
            GL11.glDepthMask(false);               // gem is hidden behind other objects

            RendererHelper.drawCuboidAt(ModBlocks.rangeView.getDefaultState(), te, -te.xDist, -te.yDist, -te.zDist, (te.xDist*2) + 1, (te.yDist * 2) + 1, (te.zDist * 2) + 1, false, 0, 0, 0, 10,matrixStack,i);

        }
        GlStateManager.enableRescaleNormal();

        GlStateManager.popMatrix();
    }


}
