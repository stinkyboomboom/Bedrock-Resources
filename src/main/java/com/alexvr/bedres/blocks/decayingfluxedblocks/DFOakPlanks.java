package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class DFOakPlanks extends Block{


    public DFOakPlanks() {
        super(Properties.create(Material.WOOD)
                .sound(SoundType.WOOD)
        .hardnessAndResistance(2.0f));
        setRegistryName(References.DF_OAK_PLANKS_REGNAME);

    }






}
