package com.alexvr.bedres.setup;

import com.alexvr.bedres.capability.BedrockFlux;
import com.alexvr.bedres.capability.BedrockFluxStorage;
import com.alexvr.bedres.capability.IBedrockFlux;
import com.alexvr.bedres.gui.ScrapeTankScreen;
import com.alexvr.bedres.registry.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ClientProxy implements IProxy{


    @Override
    public void init() {
        ScreenManager.registerFactory(ModBlocks.scrapeTankContainerType, ScrapeTankScreen::new);
        CapabilityManager.INSTANCE.register(IBedrockFlux.class, new BedrockFluxStorage(), BedrockFlux::new);

    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }


}
