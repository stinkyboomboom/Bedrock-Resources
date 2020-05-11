package com.alexvr.bedres.blocks;

import com.alexvr.bedres.blocks.tiles.BedrockiumPedestalTile;
import com.alexvr.bedres.blocks.tiles.ItemPlatformTile;
import com.alexvr.bedres.registry.ModItems;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPlatform extends DirectionalBlock {

    private static final VoxelShape ITEM_PLATFORM_EAST_AABB = Block.makeCuboidShape(15.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    private static final VoxelShape ITEM_PLATFORM_WEST_AABB = Block.makeCuboidShape(0.0D, 6.0D, 6.0D, 1.0D, 10.0D, 10.0D);
    private static final VoxelShape ITEM_PLATFORM_SOUTH_AABB = Block.makeCuboidShape(6.0D, 6.0D, 15.0D, 10.0D, 10.0D, 16.0D);
    private static final VoxelShape ITEM_PLATFORM_NORTH_AABB = Block.makeCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 1.0D);
    private static final VoxelShape ITEM_PLATFORM_UP_AABB = Block.makeCuboidShape(6.0D, 15.0D, 6.0D, 10, 16.0D, 10.0D);
    private static final VoxelShape ITEM_PLATFORM_DOWN_AABB = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10, 1.0D, 10.0D);

    public ItemPlatform() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL).lightValue(8).variableOpacity().hardnessAndResistance(5.0F, 36000.0F));
        setRegistryName(References.ITEM_PLATFORM_REGNAME);

    }




    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity tileentity = builder.get(LootParameters.BLOCK_ENTITY);
        if (tileentity instanceof ItemPlatformTile) {
            ItemPlatformTile itemPlatformTile = (ItemPlatformTile)tileentity;
            builder = builder.withDynamicDrop(ShulkerBoxBlock.CONTENTS, (p_220168_1_, p_220168_2_) -> {
                itemPlatformTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    if (h.getStackInSlot(0) != ItemStack.EMPTY) {
                        p_220168_2_.accept(h.getStackInSlot(0));
                    }
                });
            });
        }

        return super.getDrops(state, builder);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        CompoundNBT compoundnbt = stack.getChildTag("BlockEntityTag");
        EnderianRitualPedestal.generateShowItemStoredToolTip(tooltip, compoundnbt);

    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(FACING)) {
            case DOWN:
                return ITEM_PLATFORM_DOWN_AABB;
            case UP:
            default:
                return ITEM_PLATFORM_UP_AABB;
            case NORTH:
                return ITEM_PLATFORM_NORTH_AABB;
            case SOUTH:
                return ITEM_PLATFORM_SOUTH_AABB;
            case WEST:
                return ITEM_PLATFORM_WEST_AABB;
            case EAST:
                return ITEM_PLATFORM_EAST_AABB;
        }
    }



    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (!worldIn.isRemote && player.getHeldItemMainhand() == ItemStack.EMPTY){
            TileEntity te = worldIn.getTileEntity(pos);
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                if (h.getStackInSlot(0) != ItemStack.EMPTY) {
                    if (!player.isCreative()) {
                        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), h.extractItem(0, 1, false));
                    }else{
                        h.extractItem(0, 1, false);
                    }
                    ((ItemPlatformTile) te).item = "none";
                    te.markDirty();
                    ((ItemPlatformTile) te).sendUpdates();
                    state.updateNeighbors(worldIn,pos,32);
                }
            });
        }
        super.onBlockClicked(state, worldIn, pos, player);
    }



    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ItemPlatformTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote){
            TileEntity te = worldIn.getTileEntity(pos);
            if(player.getHeldItemMainhand() != ItemStack.EMPTY){
                te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    if (player.getHeldItemMainhand().getItem().getRegistryName().equals(ModItems.scrapesKnife.getRegistryName())){

                        if (h.getStackInSlot(0) != ItemStack.EMPTY) {
                            if (!player.isCreative()) {
                                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), h.extractItem(0, 1, false));
                            }else{
                                h.extractItem(0, 1, false);
                            }
                            player.getHeldItemMainhand().damageItem(2, player, (p_220044_0_) -> p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                            ((ItemPlatformTile) te).item = "none";
                            te.markDirty();
                            ((ItemPlatformTile) te).sendUpdates();
                        }
                    }else {
                        if (h.getStackInSlot(0) == ItemStack.EMPTY) {
                            h.insertItem(0, new ItemStack(player.getHeldItemMainhand().getItem(), 1), false);
                            ((ItemPlatformTile) te).item = player.getHeldItemMainhand().getItem().getRegistryName().toString();
                            if (!player.isCreative()) {
                                player.getHeldItemMainhand().shrink(1);
                            }
                            te.markDirty();
                            ((ItemPlatformTile) te).sendUpdates();
                        }
                    }
                });
                return ActionResultType.PASS;
            }else{
                for(int i =0 ; i< player.inventory.getSizeInventory(); i ++) {
                    ItemStack stack = player.inventory.getStackInSlot(i);
                    if (stack.getItem().getRegistryName().equals(Items.ENDER_PEARL.getRegistryName()) && te instanceof BedrockiumPedestalTile) {
                        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                            if (h.getStackInSlot(0) == ItemStack.EMPTY) {
                                h.insertItem(0, new ItemStack(Items.ENDER_PEARL, 1), false);
                                if (!player.isCreative()) {
                                    stack.shrink(1);
                                }
                                ((ItemPlatformTile) te).item = stack.getItem().getRegistryName().toString();
                                te.markDirty();
                                ((ItemPlatformTile) te).sendUpdates();

                            }
                        });
                    }
                }
                return ActionResultType.PASS;
            }
        }

        return ActionResultType.FAIL;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        switch(state.get(FACING)) {
            case DOWN:
                return worldIn.getBlockState(pos.down()).getBlock() != Blocks.AIR;
            case UP:
                return worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR;
            case NORTH:
                return worldIn.getBlockState(pos.north()).getBlock() != Blocks.AIR;
            case SOUTH:
                return worldIn.getBlockState(pos.south()).getBlock() != Blocks.AIR;
            case WEST:
                return worldIn.getBlockState(pos.west()).getBlock() != Blocks.AIR;
            case EAST:
                return worldIn.getBlockState(pos.east()).getBlock() != Blocks.AIR;
        }
        return true;
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {

        switch (facing){
            case UP:
                if (worldIn.getBlockState(currentPos.up()).getBlock() == Blocks.AIR){
                    return Blocks.AIR.getDefaultState();
                }
                break;
            case DOWN:
                if (worldIn.getBlockState(currentPos.down()).getBlock() == Blocks.AIR){
                    return Blocks.AIR.getDefaultState();
                }
                break;
            case NORTH:
                if (worldIn.getBlockState(currentPos.north()).getBlock() == Blocks.AIR){
                    return Blocks.AIR.getDefaultState();
                }
                break;
            case EAST:
                if (worldIn.getBlockState(currentPos.east()).getBlock() == Blocks.AIR){
                    return Blocks.AIR.getDefaultState();
                }
                break;
            case WEST:
                if (worldIn.getBlockState(currentPos.west()).getBlock() == Blocks.AIR){
                    return Blocks.AIR.getDefaultState();
                }
                break;
            case SOUTH:
                if (worldIn.getBlockState(currentPos.south()).getBlock() == Blocks.AIR){
                    return Blocks.AIR.getDefaultState();
                }
                break;
        }
        return stateIn;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection());
    }


    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState rotate(BlockState state, net.minecraft.world.IWorld world, BlockPos pos, Rotation direction) {
        return state.with(FACING, direction.rotate(state.get(FACING)));
    }


    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
