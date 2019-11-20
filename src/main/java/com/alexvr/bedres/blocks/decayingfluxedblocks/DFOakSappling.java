package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.References;
import com.alexvr.bedres.world.dftree.DFOakTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class DFOakSappling extends SaplingBlock {


    public DFOakSappling() {
        super(new DFOakTree(), Properties.create(Material.PLANTS)
                .sound(SoundType.PLANT)
        .hardnessAndResistance(2.0f).variableOpacity().tickRandomly());
        setRegistryName(References.DF_SAPPLING_REGNAME);

    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND || block == ModBlocks.dfDirt || block == ModBlocks.dfGrass;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        DFBase.Spread(worldIn, pos);
        super.animateTick(stateIn, worldIn, pos, rand);
    }




}
