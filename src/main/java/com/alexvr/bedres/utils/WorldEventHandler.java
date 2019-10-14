package com.alexvr.bedres.utils;

import com.alexvr.bedres.capability.BedrockFluxProvider;
import com.alexvr.bedres.capability.IBedrockFlux;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;

@EventBusSubscriber
public class WorldEventHandler {

    @SubscribeEvent
    public static void onPlayerLogsIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        PlayerEntity player = event.getPlayer();
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String numberAsString = decimalFormat.format((h).getBedrockFlux());
            String message = String.format("Hello there, you have %s flux.",numberAsString);
            player.sendStatusMessage(new StringTextComponent(message),false);

        });


    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event)
    {
        PlayerEntity player = event.getEntityPlayer();

        if (player.world.isRemote) return;

        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {
            float y = h.fill(50);
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String numberAsString = decimalFormat.format((h).getBedrockFlux());
            String message;
            if (y==0){
               float x=50-y;
                String number2AsString = decimalFormat.format(x);
                message = String.format("You refreshed yourself in the bed. You received %s flux, you have %s flux left.",number2AsString, numberAsString);

            }else{
                message = String.format("You refreshed yourself in the bed. You received 50 flux, you have %s flux left.", numberAsString);

            }
            player.sendStatusMessage(new StringTextComponent(message),false);

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

                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String numberAsString = decimalFormat.format((h).getBedrockFlux());
                String number2AsString = decimalFormat.format(cost);
                String message = String.format("You absorbed fall damage. It costed %s mana, you have %s mana left.", number2AsString, numberAsString);
                player.sendStatusMessage(new StringTextComponent(message),false);


                event.setCanceled(true);
            }

        });

    }

    /**
     * Copy data from dead player to the new player
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        PlayerEntity player = event.getEntityPlayer();
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
        LazyOptional<IBedrockFlux> oldbedrockFlux =  event.getOriginal().getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {
            oldbedrockFlux.ifPresent(o -> {

                h.set(o.getBedrockFlux());

            });

        });
    }
}
