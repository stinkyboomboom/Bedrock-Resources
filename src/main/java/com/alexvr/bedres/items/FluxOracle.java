package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;


public class FluxOracle extends Item {
    public boolean beingUsed = false;
    public FluxOracle() {
        super(new Item.Properties()
                .group(BedrockResources.setup.itemgroup).maxStackSize(1));
        setRegistryName("flux_oracle");
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!worldIn.isRemote) {
            beingUsed = !beingUsed;
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
