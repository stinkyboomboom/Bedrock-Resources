package com.alexvr.bedres.multiblocks.bedrockscraper;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.GeneralHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BedrockScraperScreen extends ContainerScreen<BedrockScraperContainer> implements IHasContainer<BedrockScraperContainer> {
    public static final ResourceLocation BEDROCK_SCRAPER_GUI_TEXTURE = new ResourceLocation(BedrockResources.MODID,"textures/gui/bedrock_scraper_gui.png");

    public BedrockScraperContainer scraperContainer;

    public BedrockScraperScreen(BedrockScraperContainer scraperContainer, PlayerInventory playerInventory, ITextComponent name) {
        super(scraperContainer, playerInventory, name);
        this.passEvents = false;
        this.scraperContainer = scraperContainer;


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
        this.font.drawString("Progress: " + String.valueOf(GeneralHelper.get2DEcimalPointString((scraperContainer.tileEntity.getUpdateTag().getInt("tick")/(20.0*60.0))*100))+ "%", 8.0F, (float)(this.ySize - 120), 4210752);
        World world = scraperContainer.tileEntity.getWorld();
        if(!world.getBlockState(scraperContainer.tileEntity.getPos().offset(Direction.DOWN)).getBlock().getRegistryName().equals(Blocks.BEDROCK.getRegistryName())
                || !world.getBlockState(((BedrockScraperControllerTile)scraperContainer.tileEntity).pos1.offset(Direction.DOWN)).getBlock().getRegistryName().equals(Blocks.BEDROCK.getRegistryName())
                || !world.getBlockState(((BedrockScraperControllerTile)scraperContainer.tileEntity).pos2.offset(Direction.DOWN)).getBlock().getRegistryName().equals(Blocks.BEDROCK.getRegistryName())
                || !world.getBlockState(((BedrockScraperControllerTile)scraperContainer.tileEntity).pos3.offset(Direction.DOWN)).getBlock().getRegistryName().equals(Blocks.BEDROCK.getRegistryName())) {
            this.font.drawString(new StringTextComponent(TextFormatting.RED + new TranslationTextComponent("bedrock_scraper.invalid_floor").getUnformattedComponentText()).getText(), 6.0F, (float)(this.ySize - 96 + 2),16711680);

        }
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BEDROCK_SCRAPER_GUI_TEXTURE);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        this.blit(relX+(int)(176*0.55681819)+30, relY+(int)(166*0.216867470)+3, 178, 1, 16, (int)(16*((scraperContainer.tileEntity.getUpdateTag().getInt("tick")/(20.0*60.0)))));


    }
}
