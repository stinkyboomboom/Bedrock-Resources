package com.alexvr.bedres.capability;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * Capability handler
 *
 * This class is responsible for attaching our capabilities
 */

@EventBusSubscriber
public class CapabilityHandler
{
    public static final ResourceLocation FLUX_CAP = new ResourceLocation(BedrockResources.MODID, "bedrock_flux");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof PlayerEntity)) return;

        event.addCapability(FLUX_CAP, new BedrockFluxProvider());
    }
}