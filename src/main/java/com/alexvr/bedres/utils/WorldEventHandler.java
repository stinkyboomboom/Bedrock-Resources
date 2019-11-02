package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.capability.bedrock_flux.BedrockFluxProvider;
import com.alexvr.bedres.capability.bedrock_flux.IBedrockFlux;
import com.alexvr.bedres.gui.FluxOracleScreen;
import com.alexvr.bedres.gui.FluxOracleScreenGui;
import com.alexvr.bedres.items.FluxOracle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.text.DecimalFormat;

@EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT)
public class WorldEventHandler {

    public static FluxOracleScreenGui fxG = new FluxOracleScreenGui();
    static Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    static void renderWorldLastEvent(RenderWorldLastEvent evt) {
        if(mc.player.getHeldItemMainhand().getItem() instanceof FluxOracle && ((FluxOracle)mc.player.getHeldItemMainhand().getItem()).beingUsed) {
            mc.displayGuiScreen(fxG);

        }

    }

    @SubscribeEvent
    public static void onRegisterModelsEvent(final ModelRegistryEvent event) {

    }

    @SubscribeEvent
    public static void onInitGuiEvent(final InitGuiEvent event) {
        final Screen gui = event.getGui();
        if (gui instanceof FluxOracleScreenGui) {

        }
    }

    @SubscribeEvent
    public static void onPlayerLogsIn(PlayerEvent.PlayerLoggedInEvent event)
    {

        PlayerEntity player = event.getPlayer();
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {

            String message = String.format("Hello there, you have %s flux.",h.getBedrockFluxString());
            player.sendStatusMessage(new StringTextComponent(message),false);
            if (h.getCrafterFlux()){
                h.setScreen((FluxOracleScreen)BedrockResources.proxy.getMinecraft().ingameGUI);
                h.getScreen().flux = h;
                BedrockResources.proxy.getMinecraft().ingameGUI=h.getScreen();
            }

        });

        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            String message = String.format("Hello there, you have %s sword %s axe %s shovel %s hoe %s pick. And speed of %d and jump of %d",h.getSword(),h.getAxe(),h.getShovel(),h.getHoe(),h.getPick(),h.getSpeedBoost(),h.getJumpBoost());
            player.sendStatusMessage(new StringTextComponent(message),false);


        });


    }

    @SubscribeEvent
    public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event){
        if (event.getCrafting().getItem() instanceof FluxOracle && !event.getPlayer().world.isRemote){
            PlayerEntity player = event.getPlayer();
            LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
            FluxOracleScreen fx = new FluxOracleScreen();
            bedrockFlux.ifPresent(h -> {
                h.setCrafterFlux();
                fx.flux = h;
                h.setScreen(fx);
                BedrockResources.proxy.getMinecraft().ingameGUI=fx;
                String message = ("You out of nowhere understand flux and can sense the amount of flux on you");
                player.sendStatusMessage(new StringTextComponent(message),true);
            });
        }
    }

    @SubscribeEvent
    public static void PlayerHarvestEvent(PlayerEvent.HarvestCheck event)
    {
        System.out.println("Harvest");
        System.out.println(event.getTargetBlock().getMaterial().toString());
        System.out.println(event.getTargetBlock().toString());
        System.out.println();
        PlayerEntity player = event.getPlayer();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            System.out.println(event.getTargetBlock().getMaterial().toString());
        });
    }

    @SubscribeEvent
    public static void PlayerLeftClickEvent(PlayerInteractEvent.LeftClickBlock event)
    {
        System.out.println("Left");
        System.out.println(event.getUseBlock().toString());
        System.out.println(event.getUseItem().toString());
        System.out.println();
        PlayerEntity player = event.getPlayer();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {

        });
    }

    @SubscribeEvent
    public static void PlayerBreakSpeedEvent(PlayerEvent.BreakSpeed event)
    {
        PlayerEntity player = event.getPlayer();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            event.setNewSpeed(event.getOriginalSpeed()+h.getSpeedBoost());
            System.out.println(event.getNewSpeed());
            System.out.println();
        });
    }

    @SubscribeEvent
    public static void PlayerWakeUpEvent(PlayerSleepInBedEvent event)
    {
        PlayerEntity player = event.getEntityPlayer();

        if (player.world.isRemote) return;

        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {

            String message = ("You hear whispers as you wake up from bed.");
            h.fill(100);
            if(BedrockResources.proxy.getMinecraft().ingameGUI instanceof FluxOracleScreen){
                h.setScreen((FluxOracleScreen)BedrockResources.proxy.getMinecraft().ingameGUI);
                h.getScreen().flux = h;
            }
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

    /**
     * Copy data from dead player to the new player
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        PlayerEntity player = event.getEntityPlayer();
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
        LazyOptional<IBedrockFlux> oldbedrockFlux =  event.getOriginal().getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> oldbedrockFlux.ifPresent(o -> h.set(o.getBedrockFlux())));
    }
}
