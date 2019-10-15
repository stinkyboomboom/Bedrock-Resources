package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.item.Item;

public class FluxOracle extends Item {
    public FluxOracle(Properties properties) {
        super(new Item.Properties()
                .group(BedrockResources.setup.itemgroup));
        setRegistryName("flux_oracle");
    }


}
