package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.items.FluxOracle;
import com.alexvr.bedres.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

import static com.alexvr.bedres.utils.RendererHelper.drawModalRectWithCustomSizedTexture;

public class FluxOracleScreenGui extends Screen {

    private boolean main = true;
    private boolean scrapes = false;
    private boolean knife = false;
    private boolean altar = false;
    private boolean altar2 = false;

    private boolean blazium = false;
    private boolean hush = false;
    private boolean daize = false;
    private boolean eOre = false;
    private boolean eIngot = false;
    private boolean tank = false;
    private boolean scraper = false;
    private boolean scraper2 = false;
    private boolean flux = false;

    double xOffset = 0, yOffset =0;
    double mouseX = 0, mouseY =0;
    double scaleX = 0, scaleY =0;


    private ImageButton back;
    private ImageButton next;

    public FluxOracleScreenGui() {
        super(new StringTextComponent(References.FLUX_GUI_TITLE_RESOURCE));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        mouseX = p_mouseClicked_1_;
        mouseY = p_mouseClicked_3_;
        return super.mouseClicked(p_mouseClicked_1_,p_mouseClicked_3_,p_mouseClicked_5_);
    }

    @Override
    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        if (xOffset + (p_mouseDragged_1_ - mouseX) / 32<478 && xOffset+ (p_mouseDragged_1_ - mouseX) / 32>-389) {
            xOffset += (p_mouseDragged_1_ - mouseX) / 32;
        }
        if (yOffset+(p_mouseDragged_3_ - mouseY) / 32< 191 && yOffset+(p_mouseDragged_3_ - mouseY) / 32>-109) {
            yOffset += (p_mouseDragged_3_ - mouseY) / 32;
        }


        return super.mouseDragged(p_mouseDragged_1_,p_mouseDragged_3_,p_mouseDragged_5_,p_mouseDragged_6_,p_mouseDragged_8_);
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {

        if (scaleX+ p_mouseScrolled_5_>-23 && scaleY+ p_mouseScrolled_5_<9) {
            scaleX += p_mouseScrolled_5_;
            scaleY += p_mouseScrolled_5_;
        }

        return super.mouseScrolled(p_mouseScrolled_1_,p_mouseScrolled_3_,p_mouseScrolled_5_);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        if(BedrockResources.proxy.getMinecraft().player.getHeldItemMainhand().getItem() instanceof FluxOracle && ((FluxOracle)BedrockResources.proxy.getMinecraft().player.getHeldItemMainhand().getItem()).beingUsed) {
            ((FluxOracle)BedrockResources.proxy.getMinecraft().player.getHeldItemMainhand().getItem()).beingUsed = false;
            changePage("main");
        }
        return true;
    }

    @Override
    protected void init() {

        back = new ImageButton(15+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-48)),32,32,0,0,0,
                new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/back.png"),32,32, (button) -> {
            xOffset=0;
            yOffset=5;
            scaleY=0;
            scaleX=0;
            if(altar2){
                changePage("altar");
            }else if(scraper2){
                changePage("scraper");
            }
            else {
                changePage("main");
            }
        });

        next = new ImageButton(76+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-48)),32,32,0,0,0,
                new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/next.png"),32,32, (button) -> {
            if (altar){
                changePage("altar2");
            }else if(scraper){
                changePage("scraper2");

            }
        });

        List<AbstractButton> buttons = new ArrayList<AbstractButton>() {{

            add(new ImageButton((int)xOffset+15+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), (int)yOffset+15+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/items/bedrock_scrapes.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("scrapes");
                }
            }));

            add(new ImageButton((int)xOffset+64+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+64+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/scrape_knife.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("knife");
                }
            }));

            add(new ImageButton((int)xOffset+164+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+46+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/altar.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("altar");
                }
            }));

            add(new ImageButton((int)xOffset+300+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+21+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/blazium.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("blazium");
                }
            }));

            add(new ImageButton((int)xOffset+106+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+76+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/ender_hush_base.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("hush");
                }
            }));

            add(new ImageButton((int)xOffset+186+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+66+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/sun_daize.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("daize");
                }
            }));

            add(new ImageButton((int)xOffset+243+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+83+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/enderian_ore.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("eOre");
                }
            }));

            add(new ImageButton((int)xOffset+243+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+23+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/items/enderian_ingot.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("eIngot");
                }
            }));

            add(new ImageButton((int)xOffset+106+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+28+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/tank.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("tank");
                }
            }));

            add(new ImageButton((int)xOffset+164+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+97+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/scraper.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("scraper");
                }
            }));

            add(new ImageButton((int)xOffset+32+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+97+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/flux.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("flux");
                }
            }));


            add(next);
            add(back);


        }};

        buttons.forEach(this::addButton);
    }

    public void changePage(String name){

        main = false;
        scrapes = false;
        knife = false;
        altar = false;
        altar2 = false;
        blazium = false;
        hush = false;
        daize = false;
        eOre = false;
        eIngot = false;
        tank = false;
        flux = false;
        scraper = false;
        scraper2 = false;

        switch(name){
            case "main":
                main=true;
                break;
            case "scrapes":
                scrapes=true;
                break;
            case "knife":
                knife=true;
                break;
            case "altar":
                altar=true;
                break;
            case "altar2":
                altar2=true;
                break;
            case "blazium":
                blazium=true;
                break;
            case "hush":
                hush=true;
                break;
            case "daize":
                daize=true;
                break;
            case "eOre":
                eOre=true;
                break;
            case "eIngot":
                eIngot=true;
                break;
            case "tank":
                tank=true;
                break;
            case "flux":
                flux=true;
                break;
            case "scraper":
                scraper=true;
                break;
            case "scraper2":
                scraper2=true;
                break;
            default:
                main = true;
                break;
        }
    }


    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {

        if(main) {
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_gui.png"));
            minecraft.getTextureManager().bindTexture(new ResourceLocation(BedrockResources.MODID,References.SCRAPE_TANK_GUI_BACK_TEXTURE_RESOURCE));
            this.blit((Minecraft.getInstance().mainWindow.getScaledWidth()/8)-3, (Minecraft.getInstance().mainWindow.getScaledWidth()/9)-1, (int)xOffset/2, (int)yOffset/2, (Minecraft.getInstance().mainWindow.getScaledWidth() - Minecraft.getInstance().mainWindow.getScaledWidth()/4)+2 , Minecraft.getInstance().mainWindow.getScaledHeight() - (Minecraft.getInstance().mainWindow.getScaledWidth()/5)-8);
            for (Widget b : buttons) {
                if (b.x < 46 || b.y< 56 || b.x + b.getWidth() > Minecraft.getInstance().mainWindow.getScaledWidth() - 46 || b.y + b.getHeight()> Minecraft.getInstance().mainWindow.getScaledHeight() - 56){
                    continue;
                }
                b.renderButton(p_render_1_, p_render_2_, p_render_3_);
            }
        }else if (scrapes){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/items/bedrock_scrapes.png"));
            drawString(minecraft.fontRenderer,"Bedrock Scrapes:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Bedrock Scrapes are the introductory item to this mod. They are particles that were, as the name implies, scrapped from bedrock. By doing so you will inhale some bedrock particles which arent great for you until you learn to manage them. To obtain use a Scaping knife on bedrock by shift right clicking it.Right clicking this on a block will place it down like redstone. ";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (knife){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/scrape_knife.png"));
            drawString(minecraft.fontRenderer,"Scrape Knife:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "The Scrape knife is used to scrape bedrock particles of bedrock, be careful, particles are released into the air and into your system.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (altar){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/altar.png"));
            drawString(minecraft.fontRenderer,"Altar:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "The altar is a multiblock structure found in the world composed of 4 Bedrocium Towers and 1 Bedrocium Pedestal. This structure can be used for many things, most importantly crafting and infusing items/blocks. You can right click any of the slots of the towers or the pedestal to place an item in it. Once a correct recipe is placed the output will be displayed on top of the altar. Right click the pedestal with bedrock scrapes to start crafting.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (altar2){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(32, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/altar.png"));
            drawString(minecraft.fontRenderer,"Altar:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
        }else if (scraper){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/scraper.png"));
            drawString(minecraft.fontRenderer,"Bedrock Scrapper:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "The Bedrock Scrapper is a multiblock structure that as the name implies will scrape bedrock and every minute will produce a scrape and spawn it into the world. If there were to be an inventory in front of the controller, the scraper will automatically insert it in the chest. Once said inventory is filled it will start spilling in world. This block contains no inventory inside of it.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (scraper2){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(32, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/widget/scraper_in.png"));
            drawString(minecraft.fontRenderer,"Bedrock Scrapper:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
        }else if (blazium){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/blocks/blazium.png"));
            drawString(minecraft.fontRenderer,"Blazium:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Blazium is a flower that will spawn in very hostile environment. Because of this it has evolved to protect itself from anything trying to harvest it for its blaze powder. Any creature that breaks it will get burnt, and to prevent anyone from trying to get it by removing its roots it has learned to be independent from the block bellow it.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (hush){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/blocks/ender_hush_base.png"));
            drawString(minecraft.fontRenderer,"Ender Hush:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Ender Hush is a plant that was born from the specs left behind by endermen teleporting or moving blocks around.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (daize){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/blocks/sun_daize.png"));
            drawString(minecraft.fontRenderer,"Sun Daize:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Sun Daize is a flower believed to have been the predecessors of the sun flower.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (eOre){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/blocks/enderian_ore.png"));
            drawString(minecraft.fontRenderer,"Enderian Ore:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "A very rare ore found at the very bottom of the world, some say its more rare than diamonds. It is said that its an alloy of gold, ender pearls, and obsidian";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (eIngot){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/items/enderian_ingot.png"));
            drawString(minecraft.fontRenderer,"Enderian Ingot:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Given the rarity of the ore, legend tells of a shrine, or altar capable of crafting these by infusing a gold ingot with four ender pearls, one obsidian on top of each pearl all facing the pedestal...";
            String ss = "There seems to be some scribbles you cant fully tell what they mean, but they hint at having to touch with your hands for the pearls to go into the towers. I wonder what it means.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
            renderString(ss,28+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-53))-55,Minecraft.getInstance().mainWindow.getScaledWidth()-30,16766720);
        }else if (tank){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/tank.png"));
            drawString(minecraft.fontRenderer,"Scrapes Tank:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "A tank belived to be upgradable by altars or shrines left by the olders before us. For some reason it can only accept scrapes of bedrock.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (flux){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/flux.png"));
            drawString(minecraft.fontRenderer,"Bedrock Flux:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "It seems utilizing bedrock as a resource or infusing things with it will cause it to release some particles ill be calling bedrock flux. Im not sure what they cause, but it cant be good. I hope i don't go mad...";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }


        back.renderButton(p_render_1_, p_render_2_, p_render_3_);


    }

    @SuppressWarnings("Duplicates")
    private void renderString(String s,int x, int y,int screenWidth){
        StringBuilder temp = new StringBuilder();
        int counter =0;
        int chars = screenWidth/48;
        String[] list2 = s.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for (String aList2 : list2) {
            temp.append(aList2).append(" ");
            counter++;
            if (counter == chars) {
                list.add(temp.toString());
                temp = new StringBuilder();
                counter = 0;
            }

        }
        if (counter>0){
            list.add(temp.toString());
        }

        int height =1;
        for (String ss: list) {
            drawString(minecraft.fontRenderer,ss,x, ((10*height) +y), 12268197);
            height++;


        }
    }

    @SuppressWarnings("Duplicates")
    private void renderString(String s, int x, int y, int screenWidth, int color){
        StringBuilder temp = new StringBuilder();
        int counter =0;
        int chars = screenWidth/48;
        String[] list2 = s.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for (String aList2 : list2) {
            temp.append(aList2).append(" ");
            counter++;
            if (counter == chars) {
                list.add(temp.toString());
                temp = new StringBuilder();
                counter = 0;
            }

        }
        if (counter>0){
            list.add(temp.toString());
        }

        int height =1;
        for (String ss: list) {
            drawString(minecraft.fontRenderer,ss,x, ((10*height) +y), color);
            height++;


        }
    }



}
