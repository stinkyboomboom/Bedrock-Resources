package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BedrockScrape extends Item {

    public BedrockScrape() {
        super(new Item.Properties()
                .group(BedrockResources.setup.itemgroup));
        setRegistryName("bedrock_scrapes");
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {

        return 0;
    }
}
