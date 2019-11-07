package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.References;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;

public class Staff extends SwordItem {

    public Staff() {
        super(ItemTier.IRON,2, -0.4F, new Item.Properties()
                .group(BedrockResources.setup.itemgroup).maxDamage(12800).setNoRepair());
        setRegistryName(References.STAFF_REGNAME);


    }
}
