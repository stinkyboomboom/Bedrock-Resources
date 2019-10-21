package com.alexvr.bedres.multiblocks.bedrockscraper;

import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.IRestorableTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class BedrockScraperControllerTile extends TileEntity implements IRestorableTileEntity , INamedContainerProvider {


    public BedrockScraperControllerTile() {
        super(ModBlocks.bedrockScraperControllerTile);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {

        return new BedrockScraperContainer(ModBlocks.bedrockScraperControllerContainer,p_createMenu_1_,world,pos,p_createMenu_2_,p_createMenu_3_);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        readRestorableFromNBT(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        writeRestorableToNBT(compound);
        return super.write(compound);
    }

    @Override
    public void readRestorableFromNBT(CompoundNBT compound) {
        if (compound.contains("items")) {
            itemHandler.deserializeNBT((CompoundNBT) compound.getCompound("items"));
        }
    }

    @Override
    public void writeRestorableToNBT(CompoundNBT compound) {
        compound.put("items", itemHandler.serializeNBT());
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return playerIn.getDistanceSq(new Vec3d(pos.add(0.5D, 0.5D, 0.5D))) <= 64D;
    }

    private ItemStackHandler itemHandler = new ItemStackHandler(3*9) {

        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            BedrockScraperControllerTile.this.markDirty();
        }
    };


}
