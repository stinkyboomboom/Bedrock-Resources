package com.alexvr.bedres.blocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class VoidTears extends Block{

    private static final VoxelShape SHAPE = Block.makeCuboidShape(6D, 5D, 6D, 10D, 9D, 10D);


    public VoidTears() {
        super(Properties.create(Material.ICE)
            .sound(SoundType.SLIME)
            .hardnessAndResistance(2.0f)
            .lightValue(4).variableOpacity());
        setRegistryName(References.VOID_TEAR_REGNAME);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }


}
