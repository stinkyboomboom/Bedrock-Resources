package com.alexvr.bedres.blocks;

import com.alexvr.bedres.registry.ModParticles;
import com.alexvr.bedres.tiles.ScrapeTankTile;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ScrapeTank extends Block{


    public ScrapeTank() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
        .hardnessAndResistance(2.0f)
        .lightValue(13).variableOpacity());
        setRegistryName(References.SCRAPE_TANK_REGNAME);

    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        for (int i = 0; i < 64 ; i++) {
            ModParticles.BEDROCK_DUST.spawn(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof INamedContainerProvider){
                NetworkHooks.openGui((ServerPlayerEntity) player,(INamedContainerProvider)tileEntity,tileEntity.getPos());
                return true;
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {

        super.onBlockHarvested(worldIn, pos, state, player);
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
        return new ScrapeTankTile();
    }

    public void spawnParticles(int particlesToSpawn, BlockPos pos, World world) {

        for (int i =0; i<particlesToSpawn; i++){
            ModParticles.BEDROCK_DUST.spawn(world,pos.getX() + 0.5,pos.getY() + 0.5,pos.getZ()+ 0.5,0,0,0);
            System.out.println("Spawning particle");
        }

    }

    public void spawnParticle(BlockPos pos, World world) {

        ModParticles.BEDROCK_DUST.spawn(world,pos.getX() + 0.5,pos.getY() + 0.5,pos.getZ()+ 0.5,0,0,0);

    }



}
