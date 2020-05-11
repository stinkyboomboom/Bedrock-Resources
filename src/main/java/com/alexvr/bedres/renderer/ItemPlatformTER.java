package com.alexvr.bedres.renderer;

import com.alexvr.bedres.blocks.tiles.ItemPlatformTile;
import com.alexvr.bedres.utils.RendererHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class ItemPlatformTER extends TileEntityRenderer<ItemPlatformTile> {


    public ItemPlatformTER(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(ItemPlatformTile te, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        GlStateManager.pushLightingAttributes();
        GlStateManager.pushMatrix();
        // Translate to the location of our tile entity
        GlStateManager.translated(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
        GlStateManager.disableRescaleNormal();

        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {

            long angle = (System.currentTimeMillis() / 10) %360;

            if (h.getStackInSlot(0) != ItemStack.EMPTY){
                switch(te.getBlockState().get(DirectionalBlock.FACING)) {
                    case DOWN:
                        RendererHelper.renderItem(te,h.getStackInSlot(0),.5 ,.2 ,.5,.25,.25,.25,0,1,0,angle,matrixStack,i,iRenderTypeBuffer);
                        break;
                    case UP:
                        RendererHelper.renderItem(te,h.getStackInSlot(0),.5 ,.8 ,.5,.25,.25,.25,0,1,0,angle,matrixStack,i,iRenderTypeBuffer);
                        break;
                    case NORTH:
                        RendererHelper.renderItem(te,h.getStackInSlot(0),.5 ,.5 ,.2,.25,.25,.25,0,0,1,angle,matrixStack,i,iRenderTypeBuffer);
                        break;
                    case SOUTH:
                        RendererHelper.renderItem(te,h.getStackInSlot(0),.5 ,.5 ,.8,.25,.25,.25,0,0,1,angle,matrixStack,i,iRenderTypeBuffer);
                        break;
                    case WEST:
                        RendererHelper.renderItem(te,h.getStackInSlot(0),.2 ,.5 ,.5,.25,.25,.25,1,0,0,angle,matrixStack,i,iRenderTypeBuffer);
                        break;
                    case EAST:
                        RendererHelper.renderItem(te,h.getStackInSlot(0),.8 ,.5 ,.5,.25,.25,.25,1,0,0,angle,matrixStack,i,iRenderTypeBuffer);
                        break;
                }
            }

        });



        GlStateManager.enableRescaleNormal();



        GlStateManager.popMatrix();

        GlStateManager.popAttributes();
    }


}
