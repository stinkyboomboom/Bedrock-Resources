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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnderianRitualPedestalTile extends TileEntity implements ITickableTileEntity {

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    public String item;

    public EnderianRitualPedestalTile() {
        super(ModBlocks.enderianRitualPedestalTileTileEntityType);
        item = "";
        createList();
    }

    private void createList() {

    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT compound = tag.getCompound("inv");
        handler.ifPresent(h -> {
            ((INBTSerializable<CompoundNBT>) h).deserializeNBT(compound);
        });
        item = compound.getString("name");
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h ->{
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
            tag.put("inv",compound);

            compound.put("items", ((INBTSerializable<CompoundNBT>) h).serializeNBT());
            compound.putString("name",item);

        });

        return super.write(tag);
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 4096.0D;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }


    @Override
    public void tick() {
    }




    private IItemHandler createHandler() {
        return new ItemStackHandler(1) {

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
                EnderianRitualPedestalTile.this.item = getStackInSlot(0).getItem().getRegistryName().toString();
                EnderianRitualPedestalTile.this.sendUpdates();
                super.onContentsChanged(slot);
            }

            @Override
            public int getSlotLimit(int slot) {

                return 1;

            }

            @Override
            protected void onLoad() {
                EnderianRitualPedestalTile.this.item = getStackInSlot(0).getItem().getRegistryName().toString();
                super.onLoad();
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
