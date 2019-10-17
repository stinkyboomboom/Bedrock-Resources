package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.IBedrockFlux;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeIngameGui;

import static com.alexvr.bedres.utils.RenderHelper.drawModalRectWithCustomSizedTexture;


@OnlyIn(Dist.CLIENT)
public class FluxOracleScreen extends ForgeIngameGui {

    public IBedrockFlux flux;

    public FluxOracleScreen() {
        super(BedrockResources.proxy.getMinecraft());

    }


    @Override
    public void renderGameOverlay(float partialTicks) {
        super.renderGameOverlay(partialTicks);
        drawModalRectWithCustomSizedTexture(0,32,Minecraft.getInstance().mainWindow.getScaledHeight(),Minecraft.getInstance().mainWindow.getScaledHeight()-64,new ResourceLocation("bedres","textures/gui/flux_oracle_gui.png"));
        drawString(BedrockResources.proxy.getMinecraft().fontRenderer,flux.getBedrockFluxString(),5,Minecraft.getInstance().mainWindow.getScaledHeight()-72,136324);
        float ratio = flux.getBedrockFlux()/flux.getMaxBedrockFlux();
        drawModalRectWithCustomSizedTexture(5,27,Minecraft.getInstance().mainWindow.getScaledHeight()-5,(Minecraft.getInstance().mainWindow.getScaledHeight()-(54* ratio))  ,new ResourceLocation("bedres","textures/gui/flux_oracle_fill_gui.png"));


    }


}
