package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DFOakLeave extends LeavesBlock {


    public DFOakLeave() {
        super(Properties.create(Material.LEAVES)
        .hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT).tickRandomly());
        setRegistryName(References.DF_OAK_LEAVES_REGNAME);

    }


    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        DFBase.Spread(worldIn, pos);
        super.animateTick(stateIn, worldIn, pos, rand);
    }




}
