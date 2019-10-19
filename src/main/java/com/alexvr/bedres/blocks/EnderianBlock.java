package com.alexvr.bedres.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class EnderianBlock extends Block {

    public EnderianBlock() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(32.0f)
                .lightValue(2));
        setRegistryName("enderian_block");

    }

}
