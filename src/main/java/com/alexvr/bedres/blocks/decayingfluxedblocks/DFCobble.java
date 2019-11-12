package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class DFCobble extends Block{


    public DFCobble() {
        super(Properties.create(Material.ROCK)
                .sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)
        .hardnessAndResistance(2.0f));
        setRegistryName(References.DF_COOBLE_REGNAME);

    }






}
