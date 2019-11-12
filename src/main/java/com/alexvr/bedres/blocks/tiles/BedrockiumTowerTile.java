package com.alexvr.bedres.blocks.tiles;

import com.alexvr.bedres.registry.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BedrockiumTowerTile extends TileEntity implements ITickableTileEntity {

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    public BedrockiumTowerTile() {
        super(ModBlocks.bedrockiumTowerType);

    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 4096.0D;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }


    @Override
    public void tick() {
        if(!world.isRemote) {
            handler.ifPresent(h -> {

                if (world.getBlockState(pos.offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.DOWN)).getBlock().hasTileEntity(world.getBlockState(pos.offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.DOWN))) &&
                        world.getTileEntity(pos.offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.DOWN)) instanceof BedrockiumPedestalTile) {
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.DOWN))).towerpos1 = pos;
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.DOWN))).markDirty();
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.NORTH).offset(Direction.DOWN))).sendUpdates();

                }
                else if (world.getBlockState(pos.offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.DOWN)).getBlock().hasTileEntity(world.getBlockState(pos.offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.DOWN))) &&
                        world.getTileEntity(pos.offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.DOWN)) instanceof BedrockiumPedestalTile) {
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.DOWN))).towerpos2 = pos;
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.DOWN))).markDirty();
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.SOUTH).offset(Direction.DOWN))).sendUpdates();

                }
                else if (world.getBlockState(pos.offset(Direction.EAST).offset(Direction.EAST).offset(Direction.EAST).offset(Direction.DOWN)).getBlock().hasTileEntity(world.getBlockState(pos.offset(Direction.EAST).offset(Direction.EAST).offset(Direction.EAST).offset(Direction.DOWN))) &&
                        world.getTileEntity(pos.offset(Direction.EAST).offset(Direction.EAST).offset(Direction.EAST).offset(Direction.DOWN)) instanceof BedrockiumPedestalTile) {
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.EAST).offset(Direction.EAST).offset(Direction.EAST).offset(Direction.DOWN))).towerpos3 = pos;
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.EAST).offset(Direction.EAST).offset(Direction.EAST).offset(Direction.DOWN))).markDirty();
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.EAST).offset(Direction.EAST).offset(Direction.EAST).offset(Direction.DOWN))).sendUpdates();

                }
                else if (world.getBlockState(pos.offset(Direction.WEST).offset(Direction.WEST).offset(Direction.WEST).offset(Direction.DOWN)).getBlock().hasTileEntity(world.getBlockState(pos.offset(Direction.WEST).offset(Direction.WEST).offset(Direction.WEST).offset(Direction.DOWN))) &&
                        world.getTileEntity(pos.offset(Direction.WEST).offset(Direction.WEST).offset(Direction.WEST).offset(Direction.DOWN)) instanceof BedrockiumPedestalTile) {
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.WEST).offset(Direction.WEST).offset(Direction.WEST).offset(Direction.DOWN))).towerpos4 = pos;
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.WEST).offset(Direction.WEST).offset(Direction.WEST).offset(Direction.DOWN))).markDirty();
                    ((BedrockiumPedestalTile) world.getTileEntity(pos.offset(Direction.WEST).offset(Direction.WEST).offset(Direction.WEST).offset(Direction.DOWN))).sendUpdates();

                }


            });
        }


    }




    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(invTag));

        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h ->{
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
            tag.put("inv",compound);

        });

        return super.write(tag);
    }

    private IItemHandler createHandler() {
        return new ItemStackHandler(8) {

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            protected void onContentsChanged(int slot) {BedrockiumTowerTile.this.sendUpdates();
                super.onContentsChanged(slot);
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };

    }



    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }
        sendUpdates();
        return super.getCapability(cap, side);
    }


    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    public void sendUpdates() {
        world.notifyBlockUpdate(pos, this.getBlockState(), getBlockState(), 3);
        markDirty();
    }
}
