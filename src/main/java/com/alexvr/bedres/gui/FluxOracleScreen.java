package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.bedrock_flux.IBedrockFlux;
import com.alexvr.bedres.utils.WorldEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.alexvr.bedres.utils.RendererHelper.drawModalRectWithCustomSizedTexture;


@OnlyIn(Dist.CLIENT)
public class FluxOracleScreen extends Screen {

    public IBedrockFlux flux;

    public FluxOracleScreen() {
        super(NarratorChatListener.EMPTY);

    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        if ( WorldEventHandler.displayOverLay) {
            drawModalRectWithCustomSizedTexture(0,32,Minecraft.getInstance().getMainWindow().getScaledHeight(),Minecraft.getInstance().getMainWindow().getScaledHeight()-64,new ResourceLocation("bedres","textures/gui/flux_oracle_gui.png"));
            drawString(BedrockResources.proxy.getMinecraft().fontRenderer,flux.getBedrockFluxString(),1,Minecraft.getInstance().getMainWindow().getScaledHeight()-72,16777215);
            float ratio = (float) (flux.getBedrockFlux()/flux.getMaxBedrockFlux());
            minecraft.getTextureManager().bindTexture(ScrapeTankScreen.SCRAPE_TANK_GUI_TEXTURE);
            this.blit(5, (Minecraft.getInstance().getMainWindow().getScaledHeight()-(59)), 179, 16, 42, (int)(59*ratio));
            this.blit(11, (Minecraft.getInstance().getMainWindow().getScaledHeight()-(59)), 179, 16, 42, (int)(59*ratio));
        }
    }




}
