package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.References;
import net.minecraft.item.Item;

public class ScraperMesh extends Item {

    public ScraperMesh() {
        super(new Properties()
                .group(BedrockResources.setup.itemgroup));
        setRegistryName(References.SCRAPER_MESH_REGNAME);
    }



}
