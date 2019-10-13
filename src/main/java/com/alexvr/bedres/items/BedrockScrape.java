package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.ModSetup;
import net.minecraft.item.Item;

public class BedrockScrape extends Item {

    public BedrockScrape() {
        super(new Item.Properties()
                .group(BedrockResources.setup.itemgroup));
        setRegistryName("bedrock_scrapes");
    }

}
