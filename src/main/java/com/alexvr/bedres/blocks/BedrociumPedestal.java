package com.alexvr.bedres.blocks;

import com.alexvr.bedres.tiles.BedrockiumPedestalTile;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BedrociumPedestal extends Block {

    protected static final VoxelShape Base = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, .2, 11.0D);
    protected static final VoxelShape SPIKE1 = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, .8, 11.0D);
    protected static final VoxelShape SPIKE2 = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, .8, 11.0D);
    protected static final VoxelShape SPIKE3 = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, .8, 11.0D);
    protected static final VoxelShape SPIKE4 = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, .8, 11.0D);

    protected static final VoxelShape Shape = VoxelShapes.or(Base,SPIKE1,SPIKE2,SPIKE3,SPIKE4);
    public BedrociumPedestal() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .lightValue(13).variableOpacity().hardnessAndResistance(-1.0F, 3600000.0F).noDrops());
        setRegistryName(References.PEDESTAL_REGNAME);

    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shape;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        super.onBlockClicked(state, worldIn, pos, player);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {


        return false;
    }

    @Override
    public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand) {

        super.animateTick(stateIn, world, pos, rand);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BedrockiumPedestalTile();
    }


    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {


        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
}
