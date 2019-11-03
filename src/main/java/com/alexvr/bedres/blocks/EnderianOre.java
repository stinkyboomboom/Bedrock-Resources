package com.alexvr.bedres.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemTier;
import net.minecraftforge.common.ToolType;

public class EnderianOre extends Block{

    public EnderianOre(Material m, SoundType s, float hardness, int lightVal, String regName) {
        super(Properties.create(m).harvestTool(ToolType.PICKAXE).harvestLevel(ItemTier.DIAMOND.getHarvestLevel())
                .sound(s)
                .hardnessAndResistance(hardness)
                .lightValue(lightVal));
        setRegistryName(regName);


    }

}
