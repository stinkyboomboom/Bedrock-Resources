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
    private boolean flux = false;


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
            if(altar2){
                changePage("altar");
            }else {
                changePage("main");
            }
        });

        next = new ImageButton(76+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-48)),32,32,0,0,0,
                new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/next.png"),32,32, (button) -> {
            if (altar){
                changePage("altar2");
            }
        });

        List<AbstractButton> buttons = new ArrayList<AbstractButton>() {{

            add(new ImageButton(15+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), 15+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/bedrock_scrapes.png"),32,32, (button) -> {
                if(main) {
                    changePage("scrapes");
                }
            }));

            add(new ImageButton(64+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 64+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/scrape_knife.png"),32,32, (button) -> {
                if(main) {
                    changePage("knife");
                }
            }));

            add(new ImageButton(164+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 46+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/altar.png"),32,32, (button) -> {
                if(main) {
                    changePage("altar");
                }
            }));

            add(new ImageButton(164+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 46+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/blazium.png"),32,32, (button) -> {
                if(main) {
                    changePage("blazium");
                }
            }));

            add(new ImageButton(164+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 46+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/ender_hush_backup.png"),32,32, (button) -> {
                if(main) {
                    changePage("hush");
                }
            }));

            add(new ImageButton(164+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 46+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/sun_daize.png"),32,32, (button) -> {
                if(main) {
                    changePage("daize");
                }
            }));

            add(new ImageButton(164+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 46+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/enderian_ore.png"),32,32, (button) -> {
                if(main) {
                    changePage("eOre");
                }
            }));

            add(new ImageButton(164+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 46+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/items/enderian_ingot.png"),32,32, (button) -> {
                if(main) {
                    changePage("eIngot");
                }
            }));

            add(new ImageButton(164+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 46+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/tank.png"),32,32, (button) -> {
                if(main) {
                    changePage("tank");
                }
            }));

            add(new ImageButton(164+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 46+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/scraper.png"),32,32, (button) -> {
                if(main) {
                    changePage("scraper");
                }
            }));

            add(new ImageButton(164+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), 46+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32,32,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/flux.png"),32,32, (button) -> {
                if(main) {
                    changePage("flux");
                }
            }));


            add(back);

            add(next);

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
        }
    }


    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {

        if(main) {
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_gui.png"));
            for (Widget b : buttons) {
                b.renderButton(p_render_1_, p_render_2_, p_render_3_);
            }
        }else if (scrapes){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f), new ResourceLocation("bedres", "textures/gui/widget/bedrock_scrapes.png"));
            drawString(minecraft.fontRenderer,"Bedrock Scrapes:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Bedrock Scrapes are the introductory item to this mod. They are particles that were, as the name implies, scrapped from bedrock. By doing so you will inhale some bedrock particles which arent great for you until you learn to manage them. To obtain use a Scaping knife on bedrock by shift right clicking it.Right clicking this on a block will place it down like redstone. ";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (knife){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f), new ResourceLocation("bedres", "textures/gui/widget/scrape_knife.png"));
            drawString(minecraft.fontRenderer,"Scrape Knife:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "The Scrape knife is used to scrape bedrock particles of bedrock, be careful, particles are released into the air and into your system.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (altar){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f), new ResourceLocation("bedres", "textures/gui/widget/altar.png"));
            drawString(minecraft.fontRenderer,"Altar:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "The altar is a multiblock structure composed of 4 Bedrocium Towers and 1 Bedrocium Pedestal. This structure can be used for many things, most importantly crafting and infusing items/blocks. You can right click any of the slots of the towers or the pedestal to place an item in it. Once a correct recipe is placed the output will be displayed on top of the altar. Right click the pedestal with bedrock scrapes to start crafting.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (altar2){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(32, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32, new ResourceLocation("bedres", "textures/gui/altar.png"));
            drawString(minecraft.fontRenderer,"Altar:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
        }

        back.renderButton(p_render_1_, p_render_2_, p_render_3_);


    }

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


}
