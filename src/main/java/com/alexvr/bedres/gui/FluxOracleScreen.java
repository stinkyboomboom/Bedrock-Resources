package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.bedrock_flux.IBedrockFlux;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeIngameGui;

import static com.alexvr.bedres.utils.RendererHelper.drawModalRectWithCustomSizedTexture;


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
        drawString(BedrockResources.proxy.getMinecraft().fontRenderer,flux.getBedrockFluxString(),1,Minecraft.getInstance().mainWindow.getScaledHeight()-72,16777215);
        float ratio = (float) (flux.getBedrockFlux()/flux.getMaxBedrockFlux());
        mc.getTextureManager().bindTexture(ScrapeTankScreen.SCRAPE_TANK_GUI_TEXTURE);
        this.blit(5, (Minecraft.getInstance().mainWindow.getScaledHeight()-(59)), 179, 16, 42, (int)(59*ratio));
        this.blit(11, (Minecraft.getInstance().mainWindow.getScaledHeight()-(59)), 179, 16, 42, (int)(59*ratio));


    }


}
