package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DFGrass extends GrassBlock {


    public DFGrass() {
        super(Properties.create(Material.EARTH)
                .sound(SoundType.GROUND)
        .hardnessAndResistance(2.0f).tickRandomly());
        setRegistryName(References.DF_GRASS_REGNAME);

    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        DFBase.Spread(worldIn, pos);
        super.animateTick(stateIn, worldIn, pos, rand);
    }


}
