package com.alexvr.bedres.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EnderianOre extends Block {
    public EnderianOre() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.STONE)
                .hardnessAndResistance(54.0f)
                .lightValue(3));
        setRegistryName("enderian_ore");

    }
}
