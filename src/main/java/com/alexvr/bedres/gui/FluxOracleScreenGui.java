package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.BedrockFluxProvider;
import com.alexvr.bedres.capability.IBedrockFlux;
import com.alexvr.bedres.items.FluxOracle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

import static com.alexvr.bedres.utils.RenderHelper.drawModalRectWithCustomSizedTexture;

public class FluxOracleScreenGui extends Screen {

    public FluxOracleScreenGui() {
        super(new StringTextComponent("flux_Gui"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        if(BedrockResources.proxy.getMinecraft().player.getHeldItemMainhand().getItem() instanceof FluxOracle && ((FluxOracle)BedrockResources.proxy.getMinecraft().player.getHeldItemMainhand().getItem()).beingUsed) {
            ((FluxOracle)BedrockResources.proxy.getMinecraft().player.getHeldItemMainhand().getItem()).beingUsed = false;
        }

            return true;
    }


    @Override
    public void init(Minecraft minecraft, int width, int height) {
        this.minecraft = minecraft;
        this.itemRenderer = minecraft.getItemRenderer();
        this.font = minecraft.fontRenderer;
        this.width = width;
        this.height = height;
        java.util.function.Consumer<Widget> remove = (b) -> { buttons.remove(b); children.remove(b); };
        if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Pre(this, this.buttons, this::addButton, remove))) {
            this.buttons.clear();
            this.children.clear();
            this.init();
        }
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Post(this, this.buttons, this::addButton, remove));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {

        drawModalRectWithCustomSizedTexture(0,Minecraft.getInstance().mainWindow.getScaledWidth(),Minecraft.getInstance().mainWindow.getScaledHeight(),Minecraft.getInstance().mainWindow.getScaledHeight()-64,new ResourceLocation("bedres","textures/gui/flux_oracle_gui.png"));

    }
}
