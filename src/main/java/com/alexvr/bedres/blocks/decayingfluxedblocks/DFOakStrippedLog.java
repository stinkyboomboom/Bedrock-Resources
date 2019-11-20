package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DFOakStrippedLog extends LogBlock {


    public DFOakStrippedLog() {
        super(MaterialColor.ADOBE,Properties.create(Material.WOOD)
                .sound(SoundType.WOOD)
        .hardnessAndResistance(2.0f).tickRandomly());
        setRegistryName(References.DF_STRIPPED_OAK_LOG_REGNAME);

    }




    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        DFBase.Spread(worldIn, pos);
        super.animateTick(stateIn, worldIn, pos, rand);
    }



}
