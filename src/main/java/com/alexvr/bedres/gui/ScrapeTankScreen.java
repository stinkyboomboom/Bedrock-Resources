package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.containers.ScrapeTankContainer;
import com.alexvr.bedres.utils.References;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

public class ScrapeTankScreen extends ContainerScreen<ScrapeTankContainer> implements IHasContainer<ScrapeTankContainer> {
    static final ResourceLocation SCRAPE_TANK_GUI_TEXTURE = new ResourceLocation(BedrockResources.MODID, References.SCRAPE_TANK_GUI_TEXTURE_RESOURCE);

    private ScrapeTankContainer scrapeTankContainer;

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
        scrapeTankContainer.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> this.blit(relX+(int)(176*0.8068181819), relY+(int)(166*0.0843373493975904), 179, 14, 16, (int)(52*(h.getStackInSlot(0).getCount()/64.0))));

    }
}
