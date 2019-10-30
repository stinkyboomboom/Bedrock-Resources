package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.References;
import net.minecraft.item.Item;

public class BedrockScrape extends Item {

    public BedrockScrape() {
        super(new Item.Properties()
                .group(BedrockResources.setup.itemgroup));
        setRegistryName(References.BEDROCK_SCRAPE_REGNAME);
    }


}
