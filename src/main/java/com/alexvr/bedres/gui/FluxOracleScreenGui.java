package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.items.FluxOracle;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModItems;
import com.alexvr.bedres.utils.NBTHelper;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.alexvr.bedres.utils.RendererHelper.drawModalRectWithCustomSizedTexture;

public class FluxOracleScreenGui extends Screen {

    private boolean main = true;
    private boolean scrapes = false;
    private boolean knife = false;
    private boolean altar = false;
    private boolean altar2 = false;
    private boolean altar3 = false;
    private boolean altar4 = false;

    private boolean blazium = false;
    private boolean hush = false;
    private boolean daize = false;
    private boolean eOre = false;
    private boolean eIngot = false;
    private boolean tank = false;
    private boolean scraper = false;
    private boolean scraper2 = false;
    private boolean flux = false;
    private boolean itemplat = false;
    private boolean ritualped = false;
    private boolean ritualped2 = false;
    private boolean ritualped3= false;
    private boolean ritualped4 = false;
    private boolean ritualped5 = false;
    private boolean ritualped6 = false;
    private boolean ritualped7 = false;
    private boolean ritualped8 = false;
    private boolean ritualped9 = false;
    private boolean shrum = false;
    private boolean cupcake = false;
    private boolean compressed = false;
    private boolean gravityBubble = false;
    private boolean staff = false;
    private boolean nebula = false;

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
        genButts();
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

        genButts();
        return super.mouseDragged(p_mouseDragged_1_,p_mouseDragged_3_,p_mouseDragged_5_,p_mouseDragged_6_,p_mouseDragged_8_);
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {

        if (scaleX+ p_mouseScrolled_5_>-23 && scaleY+ p_mouseScrolled_5_<9) {
            scaleX += p_mouseScrolled_5_;
            scaleY += p_mouseScrolled_5_;
        }
        genButts();
        return super.mouseScrolled(p_mouseScrolled_1_,p_mouseScrolled_3_,p_mouseScrolled_5_);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        if(BedrockResources.proxy.getMinecraft().player.getHeldItemMainhand().getItem() instanceof FluxOracle && NBTHelper.getBoolean( (BedrockResources.proxy.getMinecraft().player.getHeldItemMainhand()),"active")) {
            NBTHelper.setBoolean( (BedrockResources.proxy.getMinecraft().player.getHeldItemMainhand()),"active",false);
            changePage("main");
        }
        return true;
    }

    @Override
    protected void init() {

        back = new ImageButton(((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/18), ((Minecraft.getInstance().mainWindow.getScaledHeight()-48)),32,32,0,0,0,
                new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/back.png"),32,32, (button) -> {
            xOffset=0;
            yOffset=5;
            scaleY=0;
            scaleX=0;
            if(altar2){
                changePage("altar");
            }else if(altar3){
                changePage("altar2");
            }else if(altar4){
                changePage("altar3");
            }else if(scraper2){
                changePage("scraper");
            }else if(ritualped2){
                changePage("ritualped");
            }else if(ritualped3){
                changePage("ritualped2");
            }else if(ritualped4){
                changePage("ritualped3");
            }else if(ritualped5){
                changePage("ritualped4");
            }else if(ritualped6){
                changePage("ritualped5");
            }else if(ritualped7){
                changePage("ritualped6");
            }else if(ritualped8){
                changePage("ritualped7");
            }else if(ritualped9){
                changePage("ritualped8");
            }else {
                changePage("main");
            }
        });

        next = new ImageButton(((Minecraft.getInstance().mainWindow.getScaledWidth()-64)), ((Minecraft.getInstance().mainWindow.getScaledHeight()-48)),32,32,0,0,0,
                new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/next.png"),32,32, (button) -> {
            if (altar){
                changePage("altar2");
            }else if (altar2){
                changePage("altar3");
            }else if (altar3){
                changePage("altar4");
            }else if(scraper){
                changePage("scraper2");
            }else if(ritualped){
                changePage("ritualped2");
            }else if(ritualped2){
                changePage("ritualped3");
            }else if(ritualped3){
                changePage("ritualped4");
            }else if(ritualped4){
                changePage("ritualped5");
            }else if(ritualped5){
                changePage("ritualped6");
            }else if(ritualped6){
                changePage("ritualped7");
            }else if(ritualped7){
                changePage("ritualped8");
            }else if(ritualped8){
                changePage("ritualped9");
            }
        });

        genButts();

    }

    public void genButts(){
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

            add(new ImageButton((int)xOffset+332+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+97+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/item_platform.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("itemplat");
                }
            }));

            add(new ImageButton((int)xOffset+355+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+15+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/ritual_platform.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("ritualped");
                }
            }));

            add(new ImageButton((int)xOffset+64+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+126+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/fluxed_spores.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("shrum");
                }
            }));

            add(new ImageButton((int)xOffset+235+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+143+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/items/fluxed_cupcake.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("cupcake");
                }
            }));

            add(new ImageButton((int)xOffset+135+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+165+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/blocks/bedrock_compressed_wire.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("compressed");
                }
            }));

            add(new ImageButton((int)xOffset+385+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+143+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/fluxed_gravity_bubble.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("gravityBubble");
                }
            }));

            add(new ImageButton((int)xOffset+305+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+173+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/fire_staff.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("staff");
                }
            }));

            add(new ImageButton((int)xOffset+255+ (int)scaleX+((Minecraft.getInstance().mainWindow.getScaledWidth()-64)/8), (int)yOffset+203+ (int)scaleY+((Minecraft.getInstance().mainWindow.getScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/nebula.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(main) {
                    changePage("nebula");
                }
            }));


            add(next);
            add(back);


        }};
        this.children.clear();
        this.buttons.clear();
        buttons.forEach(this::addButton);

    }

    public void changePage(String name){
        materialPick = new Random(System.nanoTime()).nextInt(5);
        materialAxe = new Random(System.nanoTime()).nextInt(5);
        materialShovel = new Random(System.nanoTime()).nextInt(5);
        materialSword = new Random(System.nanoTime()).nextInt(5);
        main = false;
        scrapes = false;
        knife = false;
        altar = false;
        altar2 = false;
        altar3 = false;
        altar4 = false;
        blazium = false;
        hush = false;
        daize = false;
        eOre = false;
        eIngot = false;
        tank = false;
        flux = false;
        scraper = false;
        scraper2 = false;
        itemplat = false;
        ritualped = false;
        ritualped2 = false;
        ritualped3 = false;
        ritualped4 = false;
        ritualped5 = false;
        ritualped6 = false;
        ritualped7 = false;
        ritualped8 = false;
        ritualped9 = false;
        shrum = false;
        cupcake = false;
        compressed = false;
        gravityBubble = false;
        staff = false;
        nebula = false;

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
            case "altar3":
                altar3=true;
                break;
            case "altar4":
                altar4=true;
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
            case "itemplat":
                itemplat=true;
                break;
            case "ritualped":
                ritualped=true;
                break;
            case "ritualped2":
                ritualped2=true;
                break;
            case "ritualped3":
                ritualped3=true;
                break;
            case "ritualped4":
                ritualped4=true;
                break;
            case "ritualped5":
                ritualped5=true;
                break;
            case "ritualped6":
                ritualped6=true;
                break;
            case "ritualped7":
                ritualped7=true;
                break;
            case "ritualped8":
                ritualped8=true;
                break;
            case "ritualped9":
                ritualped9=true;
                break;
            case "shrum":
                shrum=true;
                break;
            case "cupcake":
                cupcake=true;
                break;
            case "compressed":
                compressed=true;
                break;
            case "gravityBubble":
                gravityBubble=true;
                break;
            case "staff":
                staff=true;
                break;
            case "nebula":
                nebula=true;
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
            next.renderButton(p_render_1_, p_render_2_, p_render_3_);
        }else if (altar3){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            String s = "Current recipes all follow same convention, stand in the platform and place item A on the top slot of each tower' face you have direct line of sight to, and item B on the lower slot. Then item C on the pedestal, Once you see the expected output, right click with bedrock scrapes and it will start the crafting.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/8),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (altar4){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawString(minecraft.fontRenderer,"Recipes:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/8),1111111);
            drawString(minecraft.fontRenderer,"Item A - Item B - Item C -> Output",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/4), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/8),1111111);

            drawString(minecraft.fontRenderer,"Magenta Panes - Compressed Wire - Void Tears -> 1 Gravity Bubble",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4)-8  ,1111111);
            renderInfusingItems(new ItemStack(Items.MAGENTA_STAINED_GLASS_PANE),new ItemStack(ModBlocks.bedrockCompressedWireBlock),new ItemStack(ModBlocks.voidTears),new ItemStack(ModBlocks.fluxedGravityBubble),
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4)-8);


            drawString(minecraft.fontRenderer,"Gold Ingots - Gold Blocks - Ender Pearl -> 8 Item Platforms",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    (((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4)+ (Minecraft.getInstance().mainWindow.getScaledHeight()-15)/10)  ,1111111);
            renderInfusingItems(new ItemStack(Items.GOLD_INGOT),new ItemStack(Blocks.GOLD_BLOCK),new ItemStack(Items.ENDER_PEARL),new ItemStack(ModBlocks.itemPlatform),
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    (((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4)+ (Minecraft.getInstance().mainWindow.getScaledHeight()-15)/10));

            drawString(minecraft.fontRenderer,"Blazium - Void Tears - Gold Block -> 1 Gravity Staff",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/2)  ,1111111);
            renderInfusingItems(new ItemStack(ModBlocks.blazium),new ItemStack(ModBlocks.voidTears),new ItemStack(Blocks.GOLD_BLOCK),new ItemStack(ModItems.staff),
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/2));

            drawString(minecraft.fontRenderer,"Obsidian - Ender Pearl - Gold Ingot -> 8 Enderian Ingots",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    (((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/2)+ (Minecraft.getInstance().mainWindow.getScaledHeight()-15)/5)-12  ,1111111);
            renderInfusingItems(new ItemStack(Blocks.OBSIDIAN),new ItemStack(Items.ENDER_PEARL),new ItemStack(Items.GOLD_INGOT),new ItemStack(ModItems.enderianIngot),
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    (((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/2)+ (Minecraft.getInstance().mainWindow.getScaledHeight()-15)/5)-12);

            drawString(minecraft.fontRenderer,"Enderian Ingot - Enderian Block - Scraper Mesh -> 1 Scraper Motor",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    (((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/2)+ (Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4 )+8  ,1111111);
            renderInfusingItems(new ItemStack(ModItems.enderianIngot),new ItemStack(ModBlocks.enderianBlock),new ItemStack(ModItems.mesh),new ItemStack(ModBlocks.motor),
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/9),
                    (((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/2)+ (Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4 )+8);

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
        }else if (itemplat){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/item_platform.png"));
            drawString(minecraft.fontRenderer,"Item Platform:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Arcane infused gold blocks were infused into this Item Platform allowing blocks to be held in space. Due to their nature they will place in the direction you are facing, regardless of block face clicked. They seem to shine with arcane essence.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (ritualped){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/ritual_platform.png"));
            drawString(minecraft.fontRenderer,"Enderian Rirual Platform:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Constructing a pillar of enderian bricks and blocks, with gold bars and an item platform results in a Enderian Ritual Platform, they inherit the ability of the item platform and allows it to infuse a person when channeled correctly.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
            String sw = "Note: Any empty blocks in the ritual area (7x7) must have an Enderian Brick floor to channel correctly, the rest is up to you";
            renderString(sw,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/2),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (ritualped2){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(Minecraft.getInstance().mainWindow.getScaledWidth()/2.0, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/ritual_upgrades.png"));
            drawString(minecraft.fontRenderer,"General Upgrade Ritual:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            renderTitleItem(new ArrayList<Item>(){{add(Items.WOODEN_AXE);add(Items.WOODEN_PICKAXE);add(Items.WOODEN_SWORD);add(Items.WOODEN_SHOVEL);add(Items.DIAMOND_HOE);add(ModItems.enderianIngot);}},12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6));
            renderString("This pattern seems to be the most used, a pattern where one can put 4 of any tool (any material but all 4 the same) will infuse you with the attributes of the chosen material.",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4) - 55);
            renderString("The hoe seems to be the exception, it'll only accept diamond hoes and infuse you with its ability; besides, whats the use of a wooden hoe.",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/2),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4));
            renderString("The diamond Shovel seems to also infuse you with its path making ability",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/2) + ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4));

            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (ritualped3){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(Minecraft.getInstance().mainWindow.getScaledWidth()/2.0, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/ritual_jump.png"));
            drawString(minecraft.fontRenderer,"Jump Upgrade Ritual:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);

            renderTitleItem(new ArrayList<Item>(){{add(Items.PISTON);add(Items.RABBIT_FOOT);add(ModItems.enderianIngot);}},12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6));
            renderString("This pattern came to be by studying the upgrade pattern. It seems placing rabbit foot on the north and south pedestal and pistons on the east and west will grant you the ability to jump an extra half a block",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4) - 55);

            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (ritualped4){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(Minecraft.getInstance().mainWindow.getScaledWidth()/2.0, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/ritual_speed.png"));
            drawString(minecraft.fontRenderer,"Mining Speed Upgrade Ritual:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            renderTitleItem(new ArrayList<Item>(){{add(Items.SUGAR);add(Items.REDSTONE);add(Items.COOKIE);add(ModItems.fluxedCupcake);add(ModItems.enderianIngot);}},12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6));
            renderString("This pattern also came to be by studying the upgrade pattern. It seems placing sugar on the north and a fluxed cupcake on the south pedestal and cookies and redstone on west and east respectively, will make you mine slightly faster.",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4) - 85);
        }else if (ritualped5){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(Minecraft.getInstance().mainWindow.getScaledWidth()/2.0, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/ritual_day.png"));
            drawString(minecraft.fontRenderer,"Day Time Ritual:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            renderTitleItem(new ArrayList<Item>(){{add(Items.GLOWSTONE);add(Item.getItemFromBlock(ModBlocks.sunDaize));}},12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6));
            renderString("This pattern also came to be by studying the upgrade pattern. It seems placing Sun Daize, and Glowstone Blocks in the pedestals, will make it day time.",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4) - 85);
            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (ritualped6){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(Minecraft.getInstance().mainWindow.getScaledWidth()/2.0, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/ritual_night.png"));
            drawString(minecraft.fontRenderer,"Night Time Ritual:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            renderTitleItem(new ArrayList<Item>(){{add(Items.INK_SAC);add(Item.getItemFromBlock(ModBlocks.dfOakSappling));}},12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6));
            renderString("This pattern also came to be by studying the upgrade pattern. It seems placing Decaying Fluxed Sapplings, and ink sacks in the pedestals, will make it night time.",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4) - 85);

            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (ritualped7){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(Minecraft.getInstance().mainWindow.getScaledWidth()/2.0, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/ritual_rain.png"));
            drawString(minecraft.fontRenderer,"Rain Ritual:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);

            renderTitleItem(new ArrayList<Item>(){{add(Items.SUGAR_CANE);add(Items.WATER_BUCKET);}},12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6));
            renderString("This pattern also came to be by studying the upgrade pattern. It seems placing sugar cane, and water buckets in the pedestals, will make it rain.",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4) - 55);

            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (ritualped8){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(Minecraft.getInstance().mainWindow.getScaledWidth()/2.0, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/ritual_clear_rain.png"));
            drawString(minecraft.fontRenderer,"Clear Rain Ritual:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            renderTitleItem(new ArrayList<Item>(){{add(Items.DRIED_KELP);add(Items.BUCKET);}},12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6));
            renderString("This pattern also came to be by studying the upgrade pattern. It seems placing dried kelp, and empty buckets in the pedestals, will absorb the rain into the items and stop it.",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4) - 85);
            next.renderButton(p_render_1_, p_render_2_, p_render_3_);

        }else if (ritualped9){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(Minecraft.getInstance().mainWindow.getScaledWidth()/2.0, Minecraft.getInstance().mainWindow.getScaledWidth()-32, (Minecraft.getInstance().mainWindow.getScaledHeight()-32) , 32,
                    new ResourceLocation("bedres", "textures/gui/ritual_flight.png"));
            drawString(minecraft.fontRenderer,"Flight Ritual:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            renderTitleItem(new ArrayList<Item>(){{add(Items.NETHER_STAR);add(Items.ELYTRA);add(ModItems.nebulaHeart);add(ModItems.enderianIngot);add(Item.getItemFromBlock(ModBlocks.fluxedGravityBubble));}},12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6));
            renderString("This pattern also came to be by studying the upgrade pattern. It seems placing nether star on the north and an elytra on the south pedestal and a nebula heart and a fluxed gravity bubble on west and east respectively, will make you infuse the abilities of these items to you and grant you flight.",
                    12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/20), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/4),Minecraft.getInstance().mainWindow.getScaledWidth() -(Minecraft.getInstance().mainWindow.getScaledWidth()/4) - 85);
        }else if (shrum){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/blocks/fluxed_spores.png"));
            drawString(minecraft.fontRenderer,"Fluxed Spores:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s ="It seems that once you have enough flux on your system little spores start detaching from your body and falling off as to try and spread the infection, i should probably pick them up. Maybe i can make something with them to fight the flux in my system.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (cupcake){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/items/fluxed_cupcake.png"));
            drawString(minecraft.fontRenderer,"Fluxed Cupcake:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "I can combine spores with some sugar and the same components of a cake and mix them in a wooden bowl into a cupcake that might reduce around an eight of my current flux.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (compressed){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/blocks/bedrock_compressed_wire.png"));
            drawModalRectWithCustomSizedTexture(((Minecraft.getInstance().mainWindow.getScaledWidth()/2)), ((Minecraft.getInstance().mainWindow.getScaledWidth())-32), (((Minecraft.getInstance().mainWindow.getScaledHeight()-64))) + 32, (((Minecraft.getInstance().mainWindow.getScaledHeight()-64))) + 16 - (Minecraft.getInstance().mainWindow.getScaledHeight()/3),
                    new ResourceLocation("bedres", "textures/gui/compressed_ritual.png"));
            drawString(minecraft.fontRenderer,"Bedrock Compressed Wire:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Bedrock scrapes seem to react around the ender hush, maybe making a circle with the scrapes and setting the block in the middle (one y level bellow the wire) on fire and planting the flower on said fire might excite the flower to infuse it with the scrapes resulting in a block of scrapes binded whith the ender ability of the flower. I wonder what this can be used for... ";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (gravityBubble){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/fluxed_gravity_bubble.png"));
            drawString(minecraft.fontRenderer,"Fluxed Gravity Bubble:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "By combining the bedrock compressed wire with some Void Tears from the creatures of the fluxed biome and magenta glass panes I seem to be able to create a bubble that can let me control gravity in a defined area, also it seems to prevent fall damage to a degree in its radius, around 150 block drop by default. Left clicking with a knife will allow me to see the area of effect, right clicking with anything else will enlarge the area in the direction of the face you clicked (north/south is the Z axis and east/west the X axis) and shift right click will decrease in the same manner. Maybe if i research i can find a way to make the field upgradable (upgrades WIP).";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (staff){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/fire_staff.png"));
            drawString(minecraft.fontRenderer,"Gravity Staff:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "By combining Void Tears from the creatures of the fluxed biome with some blazium and a gold block I seem to be able to create a staff that can let me control gravity to a certain degree. When activated it seems to ocacionally infuse some flux into me but it seems to be very small, after using it for a while i get hungry, and week and i cant activate it again until it passes. Regardless when its activated my vertical velocity is suspended and the height i had fallen is reduced by half (damage wise), significantly diminishing the damaged i would have taken. ";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }else if (nebula){
            drawModalRectWithCustomSizedTexture(15, Minecraft.getInstance().mainWindow.getScaledWidth() - 15, Minecraft.getInstance().mainWindow.getScaledHeight() - 15, 15, new ResourceLocation("bedres", "textures/gui/flux_oracle_book_info_gui.png"));
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f),
                    new ResourceLocation("bedres", "textures/gui/widget/nebula.png"));
            drawString(minecraft.fontRenderer,"Nebula Heart:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Legend tells of a creature, a creature like no other, stronger than any dragon and any wither. Capable of launching fireballs, of shooting you with projectiles with many effects, and that can teleport around, a god, a deity if you will. This creature is fortold to only appears to those consumed by flux, consumed to the very core. Others belive this creature is actually a spore creature that when enough of it is inside your body can split from you and take a mind of its own, after which it tries to get rid of its late host. ";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }


        back.renderButton(p_render_1_, p_render_2_, p_render_3_);


    }

    public static int materialPick = new Random(System.nanoTime()).nextInt(5);
    public static int materialSword = new Random(System.nanoTime()).nextInt(5);
    public static int materialShovel = new Random(System.nanoTime()).nextInt(5);
    public static int materialAxe = new Random(System.nanoTime()).nextInt(5);
    private void renderTitleItem(ArrayList<Item> itemStack, int x, int y) {
        RenderHelper.disableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        int counter=0;
        for (Item i: itemStack) {
            if (i instanceof SwordItem){
                switch (materialSword){
                    case 0:
                        break;
                    case 1:
                        i = Items.STONE_SWORD;
                        break;
                    case 2:
                        i = Items.IRON_SWORD;
                        break;
                    case 3:
                        i = Items.GOLDEN_SWORD;
                        break;
                    case 4:
                        i = Items.DIAMOND_SWORD;
                        break;
                }
            }
            if (i instanceof ShovelItem){
                switch (materialShovel){
                    case 0:
                        break;
                    case 1:
                        i = Items.STONE_SHOVEL;
                        break;
                    case 2:
                        i = Items.IRON_SHOVEL;
                        break;
                    case 3:
                        i = Items.GOLDEN_SHOVEL;
                        break;
                    case 4:
                        i = Items.DIAMOND_SHOVEL;
                        break;
                }
            }
            if (i instanceof AxeItem){
                switch (materialAxe){
                    case 0:
                        break;
                    case 1:
                        i = Items.STONE_AXE;
                        break;
                    case 2:
                        i = Items.IRON_AXE;
                        break;
                    case 3:
                        i = Items.GOLDEN_AXE;
                        break;
                    case 4:
                        i = Items.DIAMOND_AXE;
                        break;
                }
            }
            if (i instanceof PickaxeItem){
                switch (materialPick){
                    case 0:
                        break;
                    case 1:
                        i = Items.STONE_PICKAXE;
                        break;
                    case 2:
                        i = Items.IRON_PICKAXE;
                        break;
                    case 3:
                        i = Items.GOLDEN_PICKAXE;
                        break;
                    case 4:
                        i = Items.DIAMOND_PICKAXE;
                        break;
                }
            }
            Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(i), x+(18 * counter++), y - 16);
        }
    }

    private void renderInfusingItems(ItemStack itemStack, ItemStack itemStack1, ItemStack itemStack2, ItemStack itemStack3, int x, int y) {
        RenderHelper.disableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(itemStack,x+92,y+10);
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(itemStack1,x+122,y+10);
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(itemStack2,x+152,y+10);
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(itemStack3,x+186,y+10);

        drawString(Minecraft.getInstance().fontRenderer,"+",x+112,y+14,11111);
        drawString(Minecraft.getInstance().fontRenderer,"+",x+142,y+14,11111);
        drawString(Minecraft.getInstance().fontRenderer,"->",x+172,y+14,11111);

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
