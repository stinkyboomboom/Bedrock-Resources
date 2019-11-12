package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class DFOakLeave extends Block{


    public DFOakLeave() {
        super(Properties.create(Material.PLANTS)
                .sound(SoundType.PLANT)
        .hardnessAndResistance(2.0f).variableOpacity());
        setRegistryName(References.DF_OAK_LEAVES_REGNAME);

    }


    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }




}
