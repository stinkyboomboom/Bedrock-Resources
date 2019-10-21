package com.alexvr.bedres.multiblocks.bedrockscraper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BedrockScrapperControllerBlock extends Block {

    public static final DirectionProperty FACING_HORIZ = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    BlockPos topRight,topLeft,bottomRight;

    public BedrockScrapperControllerBlock() {
        super(Block.Properties.create(Material.IRON)
                .sound(SoundType.ANVIL)
                .hardnessAndResistance(24.0f)
                .lightValue(0).variableOpacity());
        setRegistryName("bedrock_scraper_controller");

        setDefaultState(getStateContainer().getBaseState().with(FACING_HORIZ, Direction.NORTH));

    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        System.out.println(placer.getHorizontalFacing().toString());
        switch (placer.getHorizontalFacing().toString()){
            case "west"://expects in neg Z, neg X

                break;
            case "south"://expects in pos Z, neg X

                break;
            case "east"://expects in pos Z, pos X

                break;
            case "north"://expects in neg Z, pos X

                break;
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BedrockScraperControllerTile();
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING_HORIZ, context.getPlayer().getHorizontalFacing().getOpposite());
    }


    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }



    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING_HORIZ);
    }

}
