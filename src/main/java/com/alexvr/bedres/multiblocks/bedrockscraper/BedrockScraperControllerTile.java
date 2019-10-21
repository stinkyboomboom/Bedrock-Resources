package com.alexvr.bedres.multiblocks.bedrockscraper;

import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.IRestorableTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class BedrockScraperControllerTile extends TileEntity implements IRestorableTileEntity , INamedContainerProvider {

    BlockPos pos1,pos2,pos3;



    public BedrockScraperControllerTile() {
        super(ModBlocks.bedrockScraperControllerTile);
        pos1= new BlockPos(0,0,0);
        pos2= new BlockPos(0,0,0);
        pos3= new BlockPos(0,0,0);

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
        if(compound.contains("pos1")){
            long[] list = compound.getLongArray("pos1");
            pos1 = new BlockPos(list[0],list[1],list[2]);
        }
        if(compound.contains("pos2")){
            long[] list = compound.getLongArray("pos2");
            pos2 = new BlockPos(list[0],list[1],list[2]);
        }
        if(compound.contains("pos3")){
            long[] list = compound.getLongArray("pos3");
            pos3 = new BlockPos(list[0],list[1],list[2]);
        }
    }

    @Override
    public void writeRestorableToNBT(CompoundNBT compound) {
        compound.put("items", itemHandler.serializeNBT());
        long[] position1 = {pos1.getX(),pos1.getY(),pos1.getZ()};
        compound.putLongArray("pos1",position1);
        long[] position2 = {pos2.getX(),pos2.getY(),pos2.getZ()};
        compound.putLongArray("pos2",position2);
        long[] position3 = {pos3.getX(),pos3.getY(),pos3.getZ()};
        compound.putLongArray("pos3",position3);
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

    public void setExpecctedBlockPositions(BlockPos pos1,BlockPos pos2,BlockPos pos3){
        this.pos1=pos1;
        this.pos2=pos2;
        this.pos3=pos3;
        markDirty();
    }


}
