package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.containers.ScrapeTankContainer;
import com.alexvr.bedres.utils.RenderHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ScrapeTankScreen extends ContainerScreen<ScrapeTankContainer> implements IHasContainer<ScrapeTankContainer> {
    private static final ResourceLocation SCRAPE_TANK_GUI_TEXTURE = new ResourceLocation(BedrockResources.MODID,"textures/gui/scrape_tank_gui.png");

    public ScrapeTankContainer scrapeTankContainer;

    public ScrapeTankScreen(ScrapeTankContainer scrapeTankContainer, PlayerInventory playerInventory, ITextComponent name) {
        super(scrapeTankContainer, playerInventory, name);
        this.passEvents = false;
        this.scrapeTankContainer = scrapeTankContainer;

    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(new StringTextComponent(TextFormatting.DARK_GRAY + new TranslationTextComponent(this.title.getFormattedText()).getUnformattedComponentText()).getText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(SCRAPE_TANK_GUI_TEXTURE);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
    }
}
