package com.alexvr.bedres.renderer;

import com.alexvr.bedres.blocks.tiles.ScrapeTankTile;
import com.alexvr.bedres.utils.RendererHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.items.CapabilityItemHandler;

public class ScrapeTankTER extends TileEntityRenderer<ScrapeTankTile> {


    public ScrapeTankTER(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(ScrapeTankTile scrapeTankTile, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translated(scrapeTankTile.getPos().getX(), scrapeTankTile.getPos().getY(), scrapeTankTile.getPos().getZ());
        GlStateManager.disableRescaleNormal();


        scrapeTankTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            if (h.getStackInSlot(0).getCount()>0) {
                RendererHelper.drawCuboidAt(Blocks.BEDROCK.getDefaultState(), scrapeTankTile, 0.1, 0.1, .1, 0.8, (0.8 * (h.getStackInSlot(0).getCount() / 64.0)), 0.8, false, 0, 0, 0, 10,matrixStack,i);
            }
        });

        GlStateManager.enableRescaleNormal();

        GlStateManager.popMatrix();

    }


}
