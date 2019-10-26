package com.alexvr.bedres.renderer;

import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.tiles.BedrockiumTowerTile;
import com.alexvr.bedres.utils.RendererHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class BedrockiumTowerTER extends TileEntityRenderer<BedrockiumTowerTile> {

    @Override
    public void render(BedrockiumTowerTile te, double x, double y, double z, float partialTicks, int destroyStage) {

        GlStateManager.pushLightingAttributes();
        GlStateManager.pushMatrix();
        // Translate to the location of our tile entity
        GlStateManager.translated(x, y, z);
        GlStateManager.disableRescaleNormal();

        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {


            double total = 0;

            if (h.getStackInSlot(0) != ItemStack.EMPTY){
                total++;
                RendererHelper.renderItem(te,h.getStackInSlot(0),.5,.25,.13,.22,.22,.22,0,0,0,0);
            }
            if (h.getStackInSlot(1) != ItemStack.EMPTY){
                total++;
                RendererHelper.renderItem(te,h.getStackInSlot(1),.5,.75,.245,.22,.22,.22,0,0,0,0);
            }
            if (h.getStackInSlot(2) != ItemStack.EMPTY){
                total++;
                RendererHelper.renderItem(te,h.getStackInSlot(2),.87,.25,.5,.22,.22,.22,0,1,0,90);
            }
            if (h.getStackInSlot(3) != ItemStack.EMPTY){
                total++;
                RendererHelper.renderItem(te,h.getStackInSlot(3),0.755,.75,.5,.22,.22,.22,0,1,0,90);
            }
            if (h.getStackInSlot(4) != ItemStack.EMPTY){
                total++;
                RendererHelper.renderItem(te,h.getStackInSlot(4),.13,.25,.5,.22,.22,.22,0,1,0,90);

            }
            if (h.getStackInSlot(5) != ItemStack.EMPTY){
                RendererHelper.renderItem(te,h.getStackInSlot(5),.245,.75,.5,0.22,.22,.22,0,1,0,90);

                total++;
            }
            if (h.getStackInSlot(6) != ItemStack.EMPTY){
                total++;
                RendererHelper.renderItem(te,h.getStackInSlot(6),.5,.25,.87,.22,.22,.22,0,0,0,0);
            }
            if (h.getStackInSlot(7) != ItemStack.EMPTY){
                total++;
                RendererHelper.renderItem(te,h.getStackInSlot(7),.5,.75,0.755,.22,.22,.22,0,0,0,0);
            }
            if (total>0) {
                this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
                double ratio = total / 8.0;
                RendererHelper.drawCuboidAt(ModBlocks.bedrociumSpike.getDefaultState(), te, 0, 1, 0, 1, (ratio), 1, false, 0, 0, 0, 10);
            }

        });



        GlStateManager.enableRescaleNormal();



        GlStateManager.popMatrix();

        GlStateManager.popAttributes();

    }
}
