package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.NBTHelper;
import com.alexvr.bedres.utils.References;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class Staff extends SwordItem {

    private boolean acivated = false;

    public Staff() {
        super(ItemTier.IRON,2, -0.4F, new Item.Properties()
                .group(BedrockResources.setup.itemgroup).maxDamage(12800).setNoRepair());
        setRegistryName(References.STAFF_REGNAME);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        NBTHelper.flipBoolean(playerIn.getHeldItemMainhand(),"status");
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Gravity cancel: " + NBTHelper.getBoolean(stack,"status",false)));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
