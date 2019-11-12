package com.alexvr.bedres.registry;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.items.*;
import com.alexvr.bedres.utils.References;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

//    @ObjectHolder(BedrockResources.MODID+ ":"+References.BEDROCK_SCRAPE_REGNAME)
//    public static BedrockScrape bedrockScrapes;

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

}
