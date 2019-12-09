package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.References;
import net.minecraft.item.Item;

public class NebulaHeart extends Item {

    public NebulaHeart() {
        super(new Properties()
                .group(BedrockResources.setup.itemgroup));
        setRegistryName(References.NEBULA_HEART_REGNAME);
    }



}
