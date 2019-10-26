package com.alexvr.bedres.renderer;

import com.alexvr.bedres.tiles.ScrapeTankTile;
import com.alexvr.bedres.utils.RendererHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.items.CapabilityItemHandler;

public class ScrapeTankTER extends TileEntityRenderer<ScrapeTankTile> {

    @Override
    public void render(ScrapeTankTile te, double x, double y, double z, float partialTicks, int destroyStage) {

        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translated(x, y, z);
        GlStateManager.disableRescaleNormal();


        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            if (h.getStackInSlot(0).getCount()>0) {
                this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
                RendererHelper.drawCuboidAt(Blocks.BEDROCK.getDefaultState(), te, 0.1, 0.1, .1, 0.8, (0.8 * (h.getStackInSlot(0).getCount() / 64.0)), 0.8, false, 0, 0, 0, 10);
            }
        });

        GlStateManager.enableRescaleNormal();

        GlStateManager.popMatrix();


    }
}
