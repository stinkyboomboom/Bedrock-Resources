package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DFGrass extends GrassBlock {


    public DFGrass() {
        super(Properties.create(Material.EARTH)
                .sound(SoundType.GROUND)
        .hardnessAndResistance(2.0f));
        setRegistryName(References.DF_GRASS_REGNAME);

    }






}
