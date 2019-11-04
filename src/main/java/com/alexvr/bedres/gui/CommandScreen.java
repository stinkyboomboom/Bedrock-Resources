package com.alexvr.bedres.gui;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.network.Networking;
import com.alexvr.bedres.network.packets.PacketSpawn;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class CommandScreen extends Screen {

    private static final int WIDTH = 179;
    private static final int HEIGHT = 151;

    private ResourceLocation GUI = new ResourceLocation(BedrockResources.MODID, "textures/gui/spawner_gui.png");


    public CommandScreen() {
        super(new StringTextComponent("Select Command"));
    }

    @Override
    protected void init() {
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;

        addButton(new Button(relX + 10, relY + 20, 160, 20, "Find and Teleport to Altar", button -> spawn("bedres:altar")));
        addButton(new Button(relX + 10, relY + 47, 160, 20, "Reset Flux", button -> spawn("bedres:rflux")));
        addButton(new Button(relX + 10, relY + 74, 160, 20, "Maximize Flux", button -> spawn("bedres:mflux")));
        addButton(new Button(relX + 10, relY + 101, 160, 20, "Half-way Flux", button -> spawn("bedres:hflux")));
        addButton(new Button(relX + 10, relY + 128, 160, 20, "Show Flux", button -> spawn("bedres:show")));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void spawn(String id) {
        Networking.INSTANCE.sendToServer(new PacketSpawn(id, minecraft.player.dimension, minecraft.player.getPosition()));
        minecraft.displayGuiScreen(null);
    }



    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(relX, relY, 0, 0, WIDTH, HEIGHT);
        drawCenteredString(font,"Choose a command:",relX+88,relY+8,16777215);
        super.render(mouseX, mouseY, partialTicks);
    }


    public static void open() {
        Minecraft.getInstance().displayGuiScreen(new CommandScreen());
    }}
