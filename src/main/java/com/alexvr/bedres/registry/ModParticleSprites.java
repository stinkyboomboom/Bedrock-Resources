package com.alexvr.bedres.registry;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticleSprites {

    public static ResourceLocation BEDROCK_DUST = new ResourceLocation("bedres:textures/particles/bedrock_dust.png");

}