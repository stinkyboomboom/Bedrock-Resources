package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DFDirt extends Block{


    public DFDirt() {
        super(Properties.create(Material.EARTH)
                .sound(SoundType.GROUND)
        .hardnessAndResistance(2.0f));
        setRegistryName(References.DF_DIRT_REGNAME);

    }






}
