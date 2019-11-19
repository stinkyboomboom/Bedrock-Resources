package com.alexvr.bedres.registry;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.ClientProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum ModSounds {
    FLUXED_CREEP_ROAROG("fluxed_creep_roarog"),
    FLUXED_CREEP_IDLEOG("fluxed_creep_idleog"),
    FLUXED_CREEP_ROAR("fluxed_creep_roar"),
    FLUXED_CREEP_IDLE("fluxed_creep_idle");
    private SoundEvent sound;

    ModSounds(String name) {
        ResourceLocation loc = new ResourceLocation(BedrockResources.MODID, name);
        sound = new SoundEvent(loc).setRegistryName(name);
    }

    public SoundEvent getSound() {
        return sound;
    }

    public void playSound() {
        playSound(1.0F);
    }

    public void playSound(float pitch) {
        ClientProxy.playSound(sound, pitch);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        for (ModSounds sound : values()) {
            event.getRegistry().register(sound.getSound());
        }
    }

}