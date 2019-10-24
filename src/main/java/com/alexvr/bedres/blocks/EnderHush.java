package com.alexvr.bedres.blocks;

import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;

public class EnderHush extends FlowerBlock {

    public EnderHush() {
        super(Effects.NIGHT_VISION, 5, Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0));
        setRegistryName("ender_hush");
    }


}
