package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class DFOakStrippedLog extends LogBlock {


    public DFOakStrippedLog() {
        super(MaterialColor.ADOBE,Properties.create(Material.WOOD)
                .sound(SoundType.WOOD)
        .hardnessAndResistance(2.0f));
        setRegistryName(References.DF_STRIPPED_OAK_LOG_REGNAME);

    }






}
