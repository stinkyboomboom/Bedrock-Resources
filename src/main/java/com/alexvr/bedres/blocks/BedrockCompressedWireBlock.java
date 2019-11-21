package com.alexvr.bedres.blocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BedrockCompressedWireBlock extends Block{


    public BedrockCompressedWireBlock() {
        super(Properties.create(Material.MISCELLANEOUS)
        .hardnessAndResistance(5.0f));
        setRegistryName(References.BEDROCK_COMPRESSED_WIRE_REGNAME);

    }


}
