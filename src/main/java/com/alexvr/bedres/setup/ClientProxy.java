package com.alexvr.bedres.setup;

import com.alexvr.bedres.blocks.multiblocks.bedrockscraper.BedrockScraperScreen;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityStorage;
import com.alexvr.bedres.capability.bedrock_flux.BedrockFlux;
import com.alexvr.bedres.capability.bedrock_flux.BedrockFluxStorage;
import com.alexvr.bedres.capability.bedrock_flux.IBedrockFlux;
import com.alexvr.bedres.gui.ScrapeTankScreen;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModFeatures;
import com.alexvr.bedres.world.ModBlaziumFeature;
import com.alexvr.bedres.world.ModFlowerFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ClientProxy implements IProxy{


    @Override
    public void init() {
        FlowersFeature MODFLOWER_FEATURE = new ModFlowerFeature(NoFeatureConfig::deserialize);
        FlowersFeature MODBLAZIUMFLOWER_FEATURE = new ModBlaziumFeature(NoFeatureConfig::deserialize);
        ScreenManager.registerFactory(ModBlocks.scrapeTankContainerType, ScrapeTankScreen::new);
        ScreenManager.registerFactory(ModBlocks.bedrockScraperControllerContainer, BedrockScraperScreen::new);
        CapabilityManager.INSTANCE.register(IBedrockFlux.class, new BedrockFluxStorage(), BedrockFlux::new);
        CapabilityManager.INSTANCE.register(IPlayerAbility.class, new PlayerAbilityStorage(), PlayerAbility::new);
        for (BiomeManager.BiomeType btype : BiomeManager.BiomeType.values()) {
            for (BiomeManager.BiomeEntry biomeEntry : BiomeManager.getBiomes(btype)) {
                biomeEntry.biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                        ModBlocks.enderianOre.getDefaultState(), 6)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2,0,0,16))));

                biomeEntry.biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MODFLOWER_FEATURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure( new FrequencyConfig(1))));
                biomeEntry.biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.ALTAR.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure( new NoPlacementConfig())));



            }
        }
        Biomes.NETHER.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MODBLAZIUMFLOWER_FEATURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure( new FrequencyConfig(5))));



    }


    public static void playSound(SoundEvent sound, float pitch,float volume) {
        Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(sound, pitch,volume));
    }

    public static void stopSound(SoundEvent sound, float pitch) {
        Minecraft.getInstance().getSoundHandler().stop(SimpleSound.master(sound, pitch));
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
