package com.alexvr.bedres.blocks;

import com.alexvr.bedres.blocks.tiles.FluxedGravityBubbleTile;
import com.alexvr.bedres.items.ScrapeKnife;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class FluxedGravityBubble extends Block{

    private static final VoxelShape SHAPE = Block.makeCuboidShape(4.5D, 4.0D, 4.5D, 11.5D, 11.5D, 11.5D);

    public FluxedGravityBubble() {
        super(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE)
                .sound(SoundType.SCAFFOLDING).lightValue(16).variableOpacity().hardnessAndResistance(15.0F, 36000.0F));
        setRegistryName(References.FLUXED_GRAVITY_BUBBLE_REGNAME);

    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote && ((FluxedGravityBubbleTile)worldIn.getTileEntity(pos)).playerInArea){
            FluxedGravityBubbleTile.flightChange(((FluxedGravityBubbleTile)worldIn.getTileEntity(pos)),worldIn,false);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (!worldIn.isRemote() && (worldIn.getTileEntity(pos)) instanceof FluxedGravityBubbleTile) {
            FluxedGravityBubbleTile tile = (FluxedGravityBubbleTile) worldIn.getTileEntity(pos);
            if (player.getHeldItemMainhand().getItem() instanceof ScrapeKnife) {
                System.out.println("knife");
                System.out.println(tile.render);
                tile.render = !tile.render;
                System.out.println(tile.render);
                System.out.println();
                tile.markDirty();
                tile.sendUpdates();
            }
        } else {
            super.onBlockClicked(state, worldIn, pos, player);
        }
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote() && (worldIn.getTileEntity(pos)) instanceof  FluxedGravityBubbleTile) {
            FluxedGravityBubbleTile tile = (FluxedGravityBubbleTile) worldIn.getTileEntity(pos);
            System.out.println(hit.getFace());
                switch (hit.getFace()) {
                    case DOWN://y
                        if (player.isSneaking() && tile.yDist <= tile.maxyDist && tile.yDist > 1) {
                            tile.yDist--;
                        } else if(!player.isSneaking() && tile.yDist >= 1&& tile.yDist < tile.maxyDist){
                            tile.yDist++;
                        }
                        break;
                    case UP://y
                        if (player.isSneaking() && tile.yDist <= tile.maxyDist && tile.yDist > 1) {
                            tile.yDist--;
                        } else if(!player.isSneaking() && tile.yDist >= 1&& tile.yDist < tile.maxyDist){
                            tile.yDist++;
                        }
                        break;
                    case NORTH://z
                        if (player.isSneaking() && tile.zDist <= tile.maxzDist && tile.zDist > 1) {
                            tile.zDist--;
                        } else if(!player.isSneaking() && tile.zDist >= 1&& tile.zDist < tile.maxzDist){
                            tile.zDist++;
                        }
                        break;
                    case SOUTH://z
                        if (player.isSneaking() && tile.zDist <= tile.maxzDist && tile.zDist > 1) {
                            tile.zDist--;
                        } else if(!player.isSneaking() && tile.zDist >= 1&& tile.zDist < tile.maxzDist){
                            tile.zDist++;
                        }
                        break;
                    case EAST://x
                            if (player.isSneaking() && tile.xDist <= tile.maxxDist && tile.xDist > 1) {
                                tile.xDist--;
                            } else if(!player.isSneaking() && tile.xDist >= 1&& tile.xDist < tile.maxxDist){
                                tile.xDist++;
                            }

                        break;
                    case WEST://y
                        if (player.isSneaking() && tile.xDist <= tile.maxxDist && tile.xDist > 1) {
                            tile.xDist--;
                        } else if(!player.isSneaking() && tile.xDist >= 1&& tile.xDist < tile.maxxDist){
                            tile.xDist++;
                        }
                        break;
                }
                tile.markDirty();
                tile.sendUpdates();
                return true;
            }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
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
