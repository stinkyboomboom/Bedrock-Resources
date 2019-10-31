package com.alexvr.bedres.blocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class Blazium extends FlowerBlock {

    public Blazium() {
        super(Effects.NIGHT_VISION, 5, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0));
        setRegistryName(References.BLAZIUM_REGNAME);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.NETHERRACK || block == Blocks.SOUL_SAND ;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double)pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D + getOffset(stateIn,worldIn,pos).x;
        double d1 = (double)((float)pos.getY() + 0.4F);
        double d2 = (double)pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D + getOffset(stateIn,worldIn,pos).z;

        worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos pos, BlockPos facingPos) {
        if (worldIn.getBlockState(pos.offset(Direction.DOWN)).getBlock() == Blocks.AIR){
            BlockState blockstate1 = ((FireBlock)Blocks.FIRE).getStateForPlacement(worldIn, pos.offset(Direction.NORTH));
            worldIn.setBlockState(pos.offset(Direction.NORTH), blockstate1, 11);
            BlockState blockstate2 = ((FireBlock)Blocks.FIRE).getStateForPlacement(worldIn, pos.offset(Direction.SOUTH));
            worldIn.setBlockState(pos.offset(Direction.SOUTH), blockstate2, 11);
            BlockState blockstate3 = ((FireBlock)Blocks.FIRE).getStateForPlacement(worldIn, pos.offset(Direction.EAST));
            worldIn.setBlockState(pos.offset(Direction.EAST), blockstate3, 11);
            BlockState blockstate4 = ((FireBlock)Blocks.FIRE).getStateForPlacement(worldIn, pos.offset(Direction.WEST));
            worldIn.setBlockState(pos.offset(Direction.WEST), blockstate4, 11);
            return ((FireBlock)Blocks.FIRE).getStateForPlacement(worldIn, pos);
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, pos, facingPos);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        player.setFire(15);

        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
