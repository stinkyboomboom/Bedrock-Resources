package com.alexvr.bedres.registry;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.world.altar.AltarGenerator;
import com.alexvr.bedres.world.altar.AltarPieces;
import com.alexvr.bedres.world.dftree.DFOakTreeFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Locale;


public class ModFeatures
{


    public static final Structure<NoFeatureConfig> ALTAR = new AltarGenerator(NoFeatureConfig::deserialize);

    public static final IStructurePieceType ALTAR_STRUCTURE = IStructurePieceType.register(AltarPieces.Piece::new, "altar_piece");

    public static final Feature<NoFeatureConfig> DFOAK_TREE = (new DFOakTreeFeature(NoFeatureConfig::deserialize, false, false));


    public static void registerFeatures(IForgeRegistry<Feature<?>> event)
    {

        generic(event).add(new ResourceLocation(BedrockResources.MODID, "altar"), ALTAR);
        generic(event).add(new ResourceLocation(BedrockResources.MODID, "df_tree"), DFOAK_TREE);

        Registry.register(Registry.STRUCTURE_FEATURE,"altar".toLowerCase(Locale.ROOT), ALTAR);
        Registry.register(Registry.STRUCTURE_PIECE,"altar_piece".toLowerCase(Locale.ROOT), ALTAR_STRUCTURE);
        Feature.STRUCTURES.put("altar".toLowerCase(Locale.ROOT), ALTAR);

        Registry.register(Registry.FEATURE,"df_tree".toLowerCase(Locale.ROOT), DFOAK_TREE);

    }


    public static <T extends IForgeRegistryEntry<T>> Generic<T> generic(IForgeRegistry<T> registry)
    {
        return new Generic<>(registry);
    }

    public static class Generic<T extends IForgeRegistryEntry<T>>
    {
        private final IForgeRegistry<T> registry;

        private Generic(IForgeRegistry<T> registry)
        {
            this.registry = registry;
        }

        public Generic<T> add(ResourceLocation name, T entry)
        {
            entry.setRegistryName(name);
            this.registry.register(entry);
            return this;
        }
    }
}
