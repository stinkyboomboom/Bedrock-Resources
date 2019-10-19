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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ClientProxy implements IProxy{


    @Override
    public void init() {
        ScreenManager.registerFactory(ModBlocks.scrapeTankContainerType, ScrapeTankScreen::new);
        CapabilityManager.INSTANCE.register(IBedrockFlux.class, new BedrockFluxStorage(), BedrockFlux::new);

        for (BiomeManager.BiomeType btype : BiomeManager.BiomeType.values()) {
            for (BiomeManager.BiomeEntry biomeEntry : BiomeManager.getBiomes(btype)) {
                biomeEntry.biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                        ModBlocks.enderianOre.getDefaultState(), 6), Placement.COUNT_RANGE, new CountRangeConfig(2,0,0,16)));

            }
        }
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }


}
