package com.alexvr.bedres.blocks.multiblocks.bedrockscraper;

import com.alexvr.bedres.blocks.tiles.ScrapeTankTile;
import com.alexvr.bedres.registry.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BedrockScraperContainer extends Container {

    public TileEntity tileEntity;
    private PlayerEntity player;

    private InvWrapper playerInventory;

    private int sizeInventory;

    public BedrockScraperContainer(int id, World world, BlockPos pos, PlayerInventory pi, PlayerEntity player) {
        super(ModBlocks.bedrockScraperControllerContainer,id);
        TileEntity e = world.getTileEntity(pos);
        this.player=player;
        this.tileEntity=e;
        this.playerInventory=new InvWrapper(player.inventory);

        e.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h,0,80,33));
            sizeInventory = ((ScrapeTankTile)tileEntity).getTotalItems();
        });

        layoutPlayerInventorySlots(8,84);

    }

    public BedrockScraperContainer(BedrockScraperControllerTile te,int id, World world, BlockPos pos, PlayerInventory pi, PlayerEntity player) {
        super(ModBlocks.bedrockScraperControllerContainer,id);
        this.player=player;
        this.tileEntity= te;
        this.playerInventory=new InvWrapper(player.inventory);

        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h,0,80,33));
            sizeInventory = ((ScrapeTankTile)tileEntity).getTotalItems();
        });

        layoutPlayerInventorySlots(8,84);

    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int slotIndex) {
        ItemStack itemStack1 = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()){
            ItemStack itemStack2 = slot.getStack();
            itemStack1 = itemStack2.copy();
            if (slotIndex >= sizeInventory && slotIndex < sizeInventory + 26){ // player inventory slots
                if (!mergeItemStack(itemStack2, sizeInventory + 26, sizeInventory + 35, false)){
                    return ItemStack.EMPTY;
                }
            }
            if (slotIndex >= sizeInventory + 26 && slotIndex < sizeInventory + 35
                    && !mergeItemStack(itemStack2, sizeInventory, sizeInventory + 27, false)){ // hotbar slots
                return ItemStack.EMPTY;
            }
            if (!mergeItemStack(itemStack2, sizeInventory, sizeInventory + 35, false)){
                return ItemStack.EMPTY;
            }
            if (itemStack2.getCount() == 0){
                slot.putStack(ItemStack.EMPTY);
            }
            else{
                slot.onSlotChanged();
            }
            if (itemStack2.getCount() == itemStack1.getCount()){
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemStack2);
        }
        return itemStack1;
    }


    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx){
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler,index,x,y));
            x+=dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy){
        for (int i = 0; i < verAmount; i++) {
            index = addSlotRange(handler,index,x,y,horAmount,dx);
            y+=dy;
        }
        return index;
    }

    protected void layoutPlayerInventorySlots(int leftCol, int topRow){
        addSlotBox(playerInventory,9,leftCol,topRow,9,18,3,18);

        topRow+=58;
        addSlotRange(playerInventory,0,leftCol,topRow,9,18);

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(),tileEntity.getPos()),player,ModBlocks.bedrockScraperControllerBlock);
    }
}
