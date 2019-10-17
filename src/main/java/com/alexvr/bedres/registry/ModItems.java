package com.alexvr.bedres.registry;

import com.alexvr.bedres.items.BedrockScrape;
import com.alexvr.bedres.items.FluxOracle;
import com.alexvr.bedres.items.ScrapeKnife;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder("bedres:bedrock_scrapes")
    public static BedrockScrape bedrockScrapes;

    @ObjectHolder("bedres:scrape_knife")
    public static ScrapeKnife scrapesKnife;

    @ObjectHolder("bedres:flux_oracle")
    public static FluxOracle oracleFlux;

}
