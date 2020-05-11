package com.alexvr.bedres.biomes.decayingfluxed;

import com.alexvr.bedres.registry.ModFeatures;
import com.alexvr.bedres.world.ModFlowerFeature;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import static net.minecraft.world.gen.feature.IFeatureConfig.NO_FEATURE_CONFIG;

public class DecayingFluxedBiome extends Biome {

    public DecayingFluxedBiome() {
        super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(),Blocks.DIRT.getDefaultState(),Blocks.GRAVEL.getDefaultState())).precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.1F).scale(0.2F).temperature(0.9F).downfall(0.3F).waterColor(8348619).waterFogColor(6512843).parent((String)null));
        this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.MINESHAFT.withConfiguration(new MineshaftConfig(0.004D,MineshaftStructure.Type.NORMAL)).withPlacement(Placement.NOPE.configure(new NoPlacementConfig())));
        this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.VILLAGE.withConfiguration(new VillageConfig("village/plains/town_centers",6)).withPlacement(Placement.NOPE.configure(new NoPlacementConfig())));

        DefaultBiomeFeatures.addDeadBushes(this);
        DefaultBiomeFeatures.addFossils(this);
        DefaultBiomeFeatures.addInfestedStone(this);
        DefaultBiomeFeatures.addStructures(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addMushrooms(this);
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 8, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.HORSE, 5, 2, 6));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.DONKEY, 1, 1, 3));
        this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 10, 1, 4));
        this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));

        //addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.DFOAK_TREE.withConfiguration( BaseTreeFeatureConfig::deserialize).withPlacement(Placement.NOPE.configure(new NoPlacementConfig())));
        addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, new ModFlowerFeature(NoFeatureConfig::deserialize).withConfiguration(NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(5))));
        addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.ALTAR.withConfiguration(NO_FEATURE_CONFIG).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(new NoPlacementConfig())));
        addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModFeatures.ALTAR.withConfiguration(NO_FEATURE_CONFIG).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(new NoPlacementConfig())));

    }

    @Override
    public int getGrassColor(double p_225528_1_, double p_225528_3_) {
        return 5272164;
    }

    @Override
    public int getSkyColor() {
        return 6111134;
    }

}
