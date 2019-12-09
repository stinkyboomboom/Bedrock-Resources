package com.alexvr.bedres.registry;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.items.*;
import com.alexvr.bedres.utils.References;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder(BedrockResources.MODID+ ":"+References.SCRAPE_KNIFE_REGNAME)
    public static ScrapeKnife scrapesKnife;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.FLUX_ORACLE_REGNAME)
    public static FluxOracle oracleFlux;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.ENDERIAN_INGOT_REGNAME)
    public static EnderianIngot enderianIngot;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.STAFF_REGNAME)
    public static Staff staff;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.SCRAPER_MESH_REGNAME)
    public static ScraperMesh mesh;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.FLUXED_CUPCAKE_REGNAME)
    public static FluxedCupcake fluxedCupcake;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.FLUXED_CREEP_EGG_REGNAME)
    public static FluxedCreepEggItem fluxedCreepEggItem;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.SPORE_DEITY_EGG_REGNAME)
    public static SporeDeityEggItem sporeDeityEggItem;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.NEBULA_HEART_REGNAME)
    public static NebulaHeart nebulaHeart;
}
