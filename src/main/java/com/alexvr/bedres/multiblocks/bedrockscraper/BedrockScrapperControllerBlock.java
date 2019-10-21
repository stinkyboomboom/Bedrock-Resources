package com.alexvr.bedres.multiblocks.bedrockscraper;

import com.alexvr.bedres.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

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
        if(!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof BedrockScraperControllerTile) {
                BlockPos pos1 = new BlockPos(0, 0, 0);
                BlockPos pos2 = new BlockPos(0, 0, 0);
                BlockPos pos3 = new BlockPos(0, 0, 0);
                switch (placer.getHorizontalFacing().toString()) {
                    case "west"://expects in neg Z, neg X
                        pos1 = pos.offset(Direction.WEST);
                        pos2 = pos.offset(Direction.WEST).offset(Direction.NORTH);
                        pos3 = pos.offset(Direction.NORTH);
                        break;
                    case "south"://expects in pos Z, neg X
                        pos1 = pos.offset(Direction.SOUTH);
                        pos2 =  pos.offset(Direction.WEST).offset(Direction.SOUTH);
                        pos3 = pos.offset(Direction.WEST);
                        break;
                    case "east"://expects in pos Z, pos X
                        pos1 = pos.offset(Direction.EAST);
                        pos2 = pos.offset(Direction.EAST).offset(Direction.SOUTH);
                        pos3 = pos.offset(Direction.SOUTH);
                        break;
                    case "north"://expects in neg Z, pos X
                        pos1 = pos.offset(Direction.NORTH);
                        pos2 = pos.offset(Direction.EAST).offset(Direction.NORTH);
                        pos3 = pos.offset(Direction.EAST);
                        break;
                }
                ((BedrockScraperControllerTile) worldIn.getTileEntity(pos)).setExpecctedBlockPositions(pos1, pos2, pos3);

            }
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof BedrockScraperControllerTile) {
                BlockPos pos1 = ((BedrockScraperControllerTile) worldIn.getTileEntity(pos)).pos1;
                BlockPos pos2 = ((BedrockScraperControllerTile) worldIn.getTileEntity(pos)).pos2;
                BlockPos pos3 = ((BedrockScraperControllerTile) worldIn.getTileEntity(pos)).pos3;
                if (worldIn.getBlockState(pos1).getBlock().getRegistryName().equals(ModBlocks.bedrockScraperSlaveBlock.getRegistryName()) && worldIn.getBlockState(pos2).getBlock().getRegistryName().equals(ModBlocks.bedrockScraperSlaveBlock.getRegistryName()) && worldIn.getBlockState(pos3).getBlock().getRegistryName().equals(ModBlocks.bedrockScraperSlaveBlock.getRegistryName())) {
                    TileEntity tileEntity = worldIn.getTileEntity(pos);
                    if (tileEntity instanceof INamedContainerProvider) {
                        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
                    }
                } else {
                    if (!worldIn.getBlockState(pos1).getBlock().getRegistryName().equals(ModBlocks.bedrockScraperSlaveBlock.getRegistryName())) {
                        player.sendStatusMessage(new StringTextComponent("Missing Slave Block at: " + pos1.toString()), false);
                    }
                    if (!worldIn.getBlockState(pos2).getBlock().getRegistryName().equals(ModBlocks.bedrockScraperSlaveBlock.getRegistryName())) {
                        player.sendStatusMessage(new StringTextComponent("Missing Slave Block at: " + pos2.toString()), false);
                    }
                    if (!worldIn.getBlockState(pos3).getBlock().getRegistryName().equals(ModBlocks.bedrockScraperSlaveBlock.getRegistryName())) {
                        player.sendStatusMessage(new StringTextComponent("Missing Slave Block at: " + pos3.toString()), false);
                    }
                }
            }
        }
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
