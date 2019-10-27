package com.alexvr.bedres.tiles;

import com.alexvr.bedres.registry.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BedrockiumPedestalTile extends TileEntity implements ITickableTileEntity {

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    BlockPos towerpos1,towerpos2,towerpos3,towerpos4;
            //north     south       east    west

    public BedrockiumPedestalTile() {
        super(ModBlocks.bedrockiumPedestalType);
        towerpos1= new BlockPos(0,0,0);
        towerpos2= new BlockPos(0,0,0);
        towerpos3= new BlockPos(0,0,0);
        towerpos4= new BlockPos(0,0,0);
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


        if(!world.isRemote && (world.getBlockState(towerpos1).getBlock().hasTileEntity( world.getBlockState(towerpos1)) && world.getTileEntity(towerpos1) instanceof BedrockiumTowerTile)&&
                (world.getBlockState(towerpos2).getBlock().hasTileEntity( world.getBlockState(towerpos2)) && world.getTileEntity(towerpos2) instanceof BedrockiumTowerTile)&&
                (world.getBlockState(towerpos3).getBlock().hasTileEntity( world.getBlockState(towerpos3)) && world.getTileEntity(towerpos3) instanceof BedrockiumTowerTile)&&
                (world.getBlockState(towerpos4).getBlock().hasTileEntity( world.getBlockState(towerpos4)) && world.getTileEntity(towerpos4) instanceof BedrockiumTowerTile)){

            markDirty();
            sendUpdates();

        }
    }




    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT compound = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(compound));
        if(compound.contains("pos1")){
            long[] list = compound.getLongArray("pos1");
           towerpos1 = new BlockPos(list[0],list[1],list[2]);
        }
        if(compound.contains("pos2")){
            long[] list = compound.getLongArray("pos2");
            towerpos2 = new BlockPos(list[0],list[1],list[2]);
        }
        if(compound.contains("pos3")){
            long[] list = compound.getLongArray("pos3");
            towerpos3 = new BlockPos(list[0],list[1],list[2]);
        }
        if(compound.contains("pos4")){
            long[] list = compound.getLongArray("pos4");
            towerpos4 = new BlockPos(list[0],list[1],list[2]);
        }
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h ->{
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
            tag.put("inv",compound);

            compound.put("items", ((INBTSerializable<CompoundNBT>) h).serializeNBT());
            long[] position1 = {towerpos1.getX(),towerpos1.getY(),towerpos1.getZ()};
            compound.putLongArray("pos1",position1);
            long[] position2 = {towerpos2.getX(),towerpos2.getY(),towerpos2.getZ()};
            compound.putLongArray("pos2",position2);
            long[] position3 = {towerpos3.getX(),towerpos3.getY(),towerpos3.getZ()};
            compound.putLongArray("pos3",position3);
            long[] position4 = {towerpos4.getX(),towerpos4.getY(),towerpos4.getZ()};
            compound.putLongArray("pos4",position4);
        });

        return super.write(tag);
    }

    private IItemHandler createHandler() {
        return new ItemStackHandler(33) {

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
            protected void onContentsChanged(int slot) {
                BedrockiumPedestalTile.this.sendUpdates();
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
