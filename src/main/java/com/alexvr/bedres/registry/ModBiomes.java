package com.alexvr.bedres.registry;

import com.alexvr.bedres.biomes.decayingfluxed.DecayingFluxedBiome;
import net.minecraft.world.biome.Biome;

public class ModBiomes {

    public static Biome dfBiome = new DecayingFluxedBiome().setRegistryName("df_biome");
}
