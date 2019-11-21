package com.alexvr.bedres.blocks;

import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class EnderHush extends FlowerBlock {

    public EnderHush() {
        super(Effects.NIGHT_VISION, 5, Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.PLANT).hardnessAndResistance(0));
        setRegistryName(References.ENDER_HUSH_REGNAME);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == ModBlocks.enderianBrick || block == Blocks.FIRE || block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        if (!worldIn.isRemote && block == Blocks.FIRE){
            if (worldIn.getBlockState(pos.east()).getBlock().getRegistryName().equals(ModBlocks.bedrockWire.getRegistryName())
                    && worldIn.getBlockState(pos.west()).getBlock().getRegistryName().equals(ModBlocks.bedrockWire.getRegistryName())
                    && worldIn.getBlockState(pos.north()).getBlock().getRegistryName().equals(ModBlocks.bedrockWire.getRegistryName())
                    && worldIn.getBlockState(pos.south()).getBlock().getRegistryName().equals(ModBlocks.bedrockWire.getRegistryName())
                    && worldIn.getBlockState(pos.east().north()).getBlock().getRegistryName().equals(ModBlocks.bedrockWire.getRegistryName())
                    && worldIn.getBlockState(pos.east().south()).getBlock().getRegistryName().equals(ModBlocks.bedrockWire.getRegistryName())
                    && worldIn.getBlockState(pos.west().north()).getBlock().getRegistryName().equals(ModBlocks.bedrockWire.getRegistryName())
                    && worldIn.getBlockState(pos.west().south()).getBlock().getRegistryName().equals(ModBlocks.bedrockWire.getRegistryName())){
                worldIn.setBlockState(pos,ModBlocks.bedrockCompressedWireBlock.getDefaultState());
                worldIn.setBlockState(pos.west(),Blocks.FIRE.getDefaultState());
                worldIn.setBlockState(pos.east(),Blocks.FIRE.getDefaultState());
                worldIn.setBlockState(pos.north(),Blocks.FIRE.getDefaultState());
                worldIn.setBlockState(pos.south(),Blocks.FIRE.getDefaultState());
                worldIn.setBlockState(pos.west().north(),Blocks.FIRE.getDefaultState());
                worldIn.setBlockState(pos.east().north(),Blocks.FIRE.getDefaultState());
                worldIn.setBlockState(pos.west().south(),Blocks.FIRE.getDefaultState());
                worldIn.setBlockState(pos.east().south(),Blocks.FIRE.getDefaultState());
                worldIn.addEntity(new LightningBoltEntity(worldIn,pos.getX(),pos.getY(),pos.getZ(),true));
                Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.LARGE_SMOKE,true,pos.getX(),pos.getY(),pos.getZ(),0,0,0);
                Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.LARGE_SMOKE,true,pos.getX(),pos.getY(),pos.getZ(),0,0,0);
                Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.LARGE_SMOKE,true,pos.getX(),pos.getY(),pos.getZ(),0,0,0);
            }
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double)pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D+ getOffset(stateIn,worldIn,pos).x;
        double d1 = (double)((float)pos.getY() + 0.0625F);
        double d2 = (double)pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D+ getOffset(stateIn,worldIn,pos).z;


        worldIn.addParticle(ParticleTypes.PORTAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
