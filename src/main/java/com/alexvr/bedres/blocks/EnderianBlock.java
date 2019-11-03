package com.alexvr.bedres.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemTier;
import net.minecraftforge.common.ToolType;

public class EnderianBlock extends Block{

    public EnderianBlock(Material m, SoundType s, float hardness, int lightVal, String regName) {
        super(Block.Properties.create(m)
                .sound(s).harvestTool(ToolType.PICKAXE).harvestLevel(ItemTier.DIAMOND.getHarvestLevel())
                .hardnessAndResistance(hardness)
                .lightValue(lightVal));
        setRegistryName(regName);

    }

}
