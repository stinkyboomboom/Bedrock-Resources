package com.alexvr.bedres.renderer;

import com.alexvr.bedres.blocks.tiles.BedrockiumPedestalTile;
import com.alexvr.bedres.blocks.tiles.BedrockiumTowerTile;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.RendererHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class BedrockiumPedestalTER extends TileEntityRenderer<BedrockiumPedestalTile> {

    public BedrockiumPedestalTER(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(BedrockiumPedestalTile te, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        GlStateManager.pushLightingAttributes();
        GlStateManager.pushMatrix();
        // Translate to the location of our tile entity
        GlStateManager.translated(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
        GlStateManager.disableRescaleNormal();

        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {

            long angle = (System.currentTimeMillis() / 10) %360;

            if (h.getStackInSlot(0) != ItemStack.EMPTY){
                RendererHelper.renderItem(te,h.getStackInSlot(0),.5,.5,.5,.5,.5,.5,0,1,0,angle,matrixStack,i,iRenderTypeBuffer);
            }
            if (h.getStackInSlot(1) != ItemStack.EMPTY){
                RendererHelper.renderItem(te,h.getStackInSlot(1),.5,2,.5,.75,.75,.75,0,1,0,angle,matrixStack,i,iRenderTypeBuffer);
                if((te.getWorld().getBlockState(te.towerpos1).getBlock().hasTileEntity( te.getWorld().getBlockState(te.towerpos1)) && te.getWorld().getTileEntity(te.towerpos1) instanceof BedrockiumTowerTile)&&
                        (te.getWorld().getBlockState(te.towerpos2).getBlock().hasTileEntity( te.getWorld().getBlockState(te.towerpos2)) && te.getWorld().getTileEntity(te.towerpos2) instanceof BedrockiumTowerTile)&&
                        (te.getWorld().getBlockState(te.towerpos3).getBlock().hasTileEntity( te.getWorld().getBlockState(te.towerpos3)) && te.getWorld().getTileEntity(te.towerpos3) instanceof BedrockiumTowerTile)&&
                        (te.getWorld().getBlockState(te.towerpos4).getBlock().hasTileEntity( te.getWorld().getBlockState(te.towerpos4)) && te.getWorld().getTileEntity(te.towerpos4) instanceof BedrockiumTowerTile)) {

                    RendererHelper.drawCuboidAt(ModBlocks.bedrociumSpike.getDefaultState(), te.getWorld().getTileEntity(te.towerpos1), te.towerpos1.getX()-te.getPos().getX(), 2, te.towerpos1.getZ()-te.getPos().getZ(), 1, 1, 1, false, 0, 0, 0, 10,matrixStack,i);
                    RendererHelper.drawCuboidAt(ModBlocks.bedrociumSpike.getDefaultState(), te.getWorld().getTileEntity(te.towerpos2), te.towerpos2.getX()-te.getPos().getX(), 2, te.towerpos2.getZ()-te.getPos().getZ(), 1, 1, 1, false, 0, 0, 0, 10,matrixStack,i);
                    RendererHelper.drawCuboidAt(ModBlocks.bedrociumSpike.getDefaultState(), te.getWorld().getTileEntity(te.towerpos3), te.towerpos3.getX()-te.getPos().getX(), 2, te.towerpos3.getZ()-te.getPos().getZ(), 1, 1, 1, false, 0, 0, 0, 10,matrixStack,i);
                    RendererHelper.drawCuboidAt(ModBlocks.bedrociumSpike.getDefaultState(), te.getWorld().getTileEntity(te.towerpos4), te.towerpos4.getX()-te.getPos().getX(), 2, te.towerpos4.getZ()-te.getPos().getZ(), 1, 1, 1, false, 0, 0, 0, 10,matrixStack,i);

                }
            }

        });


        //System.out.println(te.towerpos1.toString());




        GlStateManager.enableRescaleNormal();



        GlStateManager.popMatrix();

        GlStateManager.popAttributes();
    }


}
