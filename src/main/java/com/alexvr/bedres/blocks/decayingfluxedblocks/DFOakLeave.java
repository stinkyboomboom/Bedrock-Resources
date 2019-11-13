package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class DFOakLeave extends LeavesBlock {


    public DFOakLeave() {
        super(Properties.create(Material.LEAVES)
        .hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT));
        setRegistryName(References.DF_OAK_LEAVES_REGNAME);

    }


    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }




}
