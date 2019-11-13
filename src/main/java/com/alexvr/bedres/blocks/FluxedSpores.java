package com.alexvr.bedres.blocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class FluxedSpores extends BushBlock {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);

    public FluxedSpores() {
        super(Properties.create(Material.PLANTS)
                .sound(SoundType.PLANT).doesNotBlockMovement().variableOpacity().hardnessAndResistance(0.0f));
        setRegistryName(References.FLUXED_SPORES_REGNAME);

    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();

        return block != Blocks.AIR ;
    }
}
