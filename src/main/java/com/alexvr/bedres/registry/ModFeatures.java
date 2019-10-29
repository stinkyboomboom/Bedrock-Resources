package com.alexvr.bedres.registry;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.world.altar.AltarGenerator;
import com.alexvr.bedres.world.altar.AltarPieces;
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


    public static void registerFeatures(IForgeRegistry<Feature<?>> event)
    {

        generic(event).add(new ResourceLocation(BedrockResources.MODID, "altar"), ALTAR);

        Registry.register(Registry.STRUCTURE_FEATURE,"altar".toLowerCase(Locale.ROOT), ALTAR);
        Registry.register(Registry.STRUCTURE_PIECE,"altar_piece".toLowerCase(Locale.ROOT), ALTAR_STRUCTURE);
        Feature.STRUCTURES.put("altar".toLowerCase(Locale.ROOT), ALTAR);
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
