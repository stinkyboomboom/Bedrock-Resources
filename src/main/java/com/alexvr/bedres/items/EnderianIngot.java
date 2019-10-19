package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.item.Item;

public class EnderianIngot extends Item {

    public EnderianIngot() {
        super(new Properties()
                .group(BedrockResources.setup.itemgroup));
        setRegistryName("enderian_ingot");
    }



}
