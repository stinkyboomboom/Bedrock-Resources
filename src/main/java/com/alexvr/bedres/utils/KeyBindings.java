package com.alexvr.bedres.utils;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.List;

import static com.alexvr.bedres.BedrockResources.MODID;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;

public class KeyBindings {
    private static final List<KeyBinding> allBindings;
    public static final KeyBinding toggleOverlay;

    static {
        allBindings = ImmutableList.of(
        toggleOverlay = new KeyBinding(MODID + ".key.toggleOverlay", KeyConflictContext.IN_GAME, getKey(GLFW_KEY_K), "key.categories." + MODID)
        );
    }

    static InputMappings.Input getKey(int key) {
        return InputMappings.Type.KEYSYM.getOrMakeInput(key);
    }

    public static void init() {
        for (KeyBinding binding : allBindings) {
            ClientRegistry.registerKeyBinding(binding);
        }
    }
}
