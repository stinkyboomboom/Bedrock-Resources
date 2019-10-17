package com.alexvr.bedres.gui;

import com.alexvr.bedres.capability.BedrockFluxProvider;
import com.alexvr.bedres.capability.IBedrockFlux;
import com.alexvr.bedres.items.FluxOracle;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;


@OnlyIn(Dist.CLIENT)
public class FluxOracleScreen extends Screen {


    public FluxOracleScreen(ITextComponent titleIn) {
        super(titleIn);

    }


    @Override
    public boolean isMouseOver(double p_isMouseOver_1_, double p_isMouseOver_3_) {
        return false;
    }

    @Nullable
    @Override
    public IGuiEventListener getFocused() {
        return super.getFocused();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        if(mc.player.getHeldItemMainhand().getItem() instanceof FluxOracle && ((FluxOracle)mc.player.getHeldItemMainhand().getItem()).beingUsed) {
            ((FluxOracle)mc.player.getHeldItemMainhand().getItem()).beingUsed = false;
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

        drawModalRectWithCustomSizedTexture(0,32,Minecraft.getInstance().mainWindow.getScaledHeight(),Minecraft.getInstance().mainWindow.getScaledHeight()-64,new ResourceLocation("bedres","textures/gui/flux_oracle_gui.png"));
        LazyOptional<IBedrockFlux> bedrockFlux = mc.player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {

            drawString(minecraft.fontRenderer,h.getBedrockFluxString(),5,Minecraft.getInstance().mainWindow.getScaledHeight()-72,136324);
            float ratio = h.getBedrockFlux()/h.getMaxBedrockFlux();
            drawModalRectWithCustomSizedTexture(5,27,Minecraft.getInstance().mainWindow.getScaledHeight()-5,(Minecraft.getInstance().mainWindow.getScaledHeight()-(54* ratio))  ,new ResourceLocation("bedres","textures/gui/flux_oracle_fill_gui.png"));

        });
    }

    public static final Minecraft mc = Minecraft.getInstance();
    public static void drawModalRectWithCustomSizedTexture(double leftSideX, double rightSideX, double bottomY, double topY, ResourceLocation texture) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.enableGUIStandardItemLighting();

        mc.getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//botl    bufferbuilder.pos(0.0D, (double)mc.mainWindow.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos(leftSideX, bottomY, -90.0D).tex(0.0D, 1.0D).endVertex();

//botr    bufferbuilder.pos((double)mc.mainWindow.getScaledWidth(), (double)mc.mainWindow.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(rightSideX, bottomY, -90.0D).tex(1.0D, 1.0D).endVertex();

//topr      bufferbuilder.pos((double)mc.mainWindow.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(rightSideX, topY, -90.0D).tex(1.0D, 0.0D).endVertex();

//topl    bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        bufferbuilder.pos(leftSideX, topY, -90.0D).tex(0.0D, 0.0D).endVertex();

        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }
}
