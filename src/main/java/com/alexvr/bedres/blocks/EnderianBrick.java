package com.alexvr.bedres.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class EnderianBrick extends Block{

    public EnderianBrick(Material m, SoundType s, float hardness, int lightVal, String regName) {
        super(Properties.create(m)
                .sound(s).harvestTool(ToolType.PICKAXE)
                .hardnessAndResistance(hardness)
                .lightValue(lightVal));
        setRegistryName(regName);

    }

}
