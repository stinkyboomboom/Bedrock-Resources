package com.alexvr.bedres.blocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;

public class SunDaize extends FlowerBlock {

    public SunDaize() {
        super(Effects.NIGHT_VISION, 5, Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0));
        setRegistryName(References.SUN_DAIZE_REGNAME);
    }


}
