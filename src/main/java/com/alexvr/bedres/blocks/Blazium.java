package com.alexvr.bedres.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Blazium extends FlowerBlock {

    public Blazium() {
        super(Effects.NIGHT_VISION, 5, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0));
        setRegistryName("blazium");
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.NETHERRACK || block == Blocks.SOUL_SAND ;
    }
}
