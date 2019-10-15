package com.alexvr.bedres.utils;

import com.alexvr.bedres.capability.BedrockFluxProvider;
import com.alexvr.bedres.capability.IBedrockFlux;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.text.DecimalFormat;

@EventBusSubscriber
public class WorldEventHandler {

    @SubscribeEvent
    public static void onPlayerLogsIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        PlayerEntity player = event.getPlayer();
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {

            String message = String.format("Hello there, you have %s flux.",h.getBedrockFluxString());
            player.sendStatusMessage(new StringTextComponent(message),false);

        });


    }

    @SubscribeEvent
    public static void PlayerWakeUpEvent(PlayerSleepInBedEvent event)
    {
        PlayerEntity player = event.getEntityPlayer();

        if (player.world.isRemote) return;

        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {

            String message = ("You hear whispers as you wake up from bed.");;


            player.sendStatusMessage(new StringTextComponent(message),true);
            player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + new TranslationTextComponent("message.bedres.whispers").getUnformattedComponentText()), false);
            player.world.addEntity(new LightningBoltEntity(player.world,player.posX,player.posY,player.posZ,true));


        });

    }

    @SubscribeEvent
    public static void onPlayerFalls(LivingFallEvent event)
    {
        Entity entity = event.getEntity();

        if (entity.world.isRemote || !(entity instanceof PlayerEntity) || event.getDistance() < 3) return;

        PlayerEntity player = (PlayerEntity) entity;
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {
            float points = h.getBedrockFlux();
            float cost = event.getDistance() * 2;

            if (points > cost)
            {


                h.consume(cost);

                String number2AsString = new DecimalFormat("#.00").format(cost);
                String message = String.format("You absorbed fall damage. It costed %s mana, you have %s mana left.", number2AsString, h.getBedrockFluxString());
                player.sendStatusMessage(new StringTextComponent(message),true);

                event.setCanceled(true);
            }

        });

    }
}
