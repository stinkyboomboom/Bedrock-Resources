package com.alexvr.bedres.blocks;

import com.alexvr.bedres.blocks.tiles.FluxedGravityBubbleTile;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class FluxedGravityBubble extends Block{

    private static final VoxelShape SHAPE = Block.makeCuboidShape(4.5D, 4.0D, 4.5D, 11.5D, 11.5D, 11.5D);

    public FluxedGravityBubble() {
        super(Block.Properties.create(Material.IRON)
                .sound(SoundType.SCAFFOLDING).lightValue(16).variableOpacity().hardnessAndResistance(15.0F, 36000.0F));
        setRegistryName(References.FLUXED_GRAVITY_BUBBLE_REGNAME);

    }



    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if(((FluxedGravityBubbleTile)worldIn.getTileEntity(pos)).playerInArea) {
            spawnParticle(worldIn, pos, ParticleTypes.PORTAL, .5f, 0.4f, .5f, -0.75f, 0f, 0f);
            spawnParticle(worldIn, pos, ParticleTypes.PORTAL, .5f, 0.4f, .5f, 0.75f, 0f, 0f);
            spawnParticle(worldIn, pos, ParticleTypes.PORTAL, .5f, 0.4f, .5f, 0f, 0f, -0.75f);
            spawnParticle(worldIn, pos, ParticleTypes.PORTAL, .5f, 0.4f, .5f, 0f, 0f, 0.75f);
            spawnParticle(worldIn, pos, ParticleTypes.PORTAL, .5f, 0.5f, .5f, 0f, 0f, 0.f);
        }
        super.animateTick(stateIn, worldIn, pos, rand);
    }
    private static void spawnParticle(World worldIn, BlockPos pos, BasicParticleType types, float xOffset, float yOffset, float zOffset, float xSpeed, float ySpeed, float zSpeed){
        worldIn.addParticle(types,true,pos.getX()+xOffset,pos.getY()+yOffset,pos.getZ()+zOffset,xSpeed,ySpeed,zSpeed);
        worldIn.addParticle(types,true,pos.getX()+xOffset,pos.getY()+yOffset,pos.getZ()+zOffset,xSpeed,ySpeed,zSpeed);
        worldIn.addParticle(types,true,pos.getX()+xOffset,pos.getY()+yOffset,pos.getZ()+zOffset,xSpeed,ySpeed,zSpeed);

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }


    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FluxedGravityBubbleTile();
    }

}
