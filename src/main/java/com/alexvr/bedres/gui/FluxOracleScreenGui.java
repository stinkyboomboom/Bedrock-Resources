package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.items.FluxOracle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

import static com.alexvr.bedres.utils.RenderHelper.drawModalRectWithCustomSizedTexture;

public class FluxOracleScreenGui extends Screen {

    boolean main = true;
    boolean scrapes = false;


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
            main = true;
            scrapes = false;
        }

        return true;
    }


    @Override
    protected void init() {
        List<AbstractButton> buttons = new ArrayList<AbstractButton>() {{
            add(new ImageButton(15+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), 15+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),32,32,0,0,0,new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/bedrock_scrapes.png"),32,32, (button) -> {
                if(main) {
                    main = false;
                    scrapes = true;
                    System.out.println("Test 1");
                }

            }));

        }};

        buttons.forEach(this::addButton);

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
            drawModalRectWithCustomSizedTexture(5+((Minecraft.getInstance().mainWindow.getScaledWidth())/8.0f)-32, 5+ ((Minecraft.getInstance().mainWindow.getScaledWidth())/8), (5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6)) + 32, 5+((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6.0f), new ResourceLocation("bedres", "textures/gui/widget/bedrock_scrapes.png"));
            drawString(minecraft.fontRenderer,"Bedrock Scrapes:",12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),1111111);
            String s = "Bedrock Scrapes are the introductory item to this mod. They are particles that were, as the name implies, scrapped from bedrock. By doing so you will inhale some bedrock particles which arent great for you until you learn to manage them. To obtain use a Scaping knife on bedrock by shift right clicking it.";
            renderString(s,12+((Minecraft.getInstance().mainWindow.getScaledWidth()-15)/8), ((Minecraft.getInstance().mainWindow.getScaledHeight()-15)/6),Minecraft.getInstance().mainWindow.getScaledWidth()-30);
        }


    }

    private void renderString(String s,int x, int y,int screenWidth){
        String temp = "";
        int counter =0;
        int chars = screenWidth/8;
        ArrayList<String> list = new ArrayList<>();
        for (int i=0;i<s.length();i++) {
            temp+=s.charAt(i);
            counter++;
            if (counter == chars){
                list.add(temp);
                temp="";
                counter=0;
            }
        }
        int height =1;
        for (String ss: list) {
            drawString(minecraft.fontRenderer,ss,x, ((10*height) +y),2222222);
            height++;
        }
    }


}
