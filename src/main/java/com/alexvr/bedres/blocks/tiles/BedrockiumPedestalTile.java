package com.alexvr.bedres.blocks.tiles;

import com.alexvr.bedres.Config;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import java.util.ArrayList;

public class BedrockiumPedestalTile extends TileEntity implements ITickableTileEntity {

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    public BlockPos towerpos1,towerpos2,towerpos3,towerpos4;
    //north     south       east    west

    public static ArrayList RECEPI = new ArrayList<>();

    public boolean crafting=false;
    public int craftingTotalTimer=0,craftingCurrentTimer=0;
    public BlockPos extractFrom;


    int counterCheck = 0;
    private int ticksPerItem;

    public BedrockiumPedestalTile() {
        super(ModBlocks.bedrockiumPedestalType);
        towerpos1= new BlockPos(0,0,0);
        towerpos2= new BlockPos(0,0,0);
        towerpos3= new BlockPos(0,0,0);
        towerpos4= new BlockPos(0,0,0);
        extractFrom= towerpos1;
        createList();
        ticksPerItem = Config.PEDESTAL_TICKS_PER_ITEM.get();
    }

    private void createList() {
        RECEPI = new ArrayList<>();

        ArrayList GRAVITYBUBBLE = new ArrayList() {{

            add(new ItemStack(ModBlocks.fluxedGravityBubble));
            add(new ArrayList<String>() {{

                add(ModItems.enderianIngot.getRegistryName().toString());
                add(Items.MAGENTA_STAINED_GLASS_PANE.getRegistryName().toString());
                add(ModItems.enderianIngot.getRegistryName().toString());
                add(Items.MAGENTA_STAINED_GLASS_PANE.getRegistryName().toString());
                add(ModItems.enderianIngot.getRegistryName().toString());
                add(Items.MAGENTA_STAINED_GLASS_PANE.getRegistryName().toString());
                add(ModItems.enderianIngot.getRegistryName().toString());
                add(Items.MAGENTA_STAINED_GLASS_PANE.getRegistryName().toString());

            }});
            add(ModBlocks.bedrockCompressedWireBlock.getRegistryName().toString());


        }};
        ArrayList EINGOT = new ArrayList() {{

            add(new ItemStack(ModItems.enderianIngot,8));
            add(new ArrayList<String>() {{

                add(Items.ENDER_PEARL.getRegistryName().toString());
                add(Items.OBSIDIAN.getRegistryName().toString());
                add(Items.ENDER_PEARL.getRegistryName().toString());
                add(Items.OBSIDIAN.getRegistryName().toString());
                add(Items.ENDER_PEARL.getRegistryName().toString());
                add(Items.OBSIDIAN.getRegistryName().toString());
                add(Items.ENDER_PEARL.getRegistryName().toString());
                add(Items.OBSIDIAN.getRegistryName().toString());

            }});
            add(Items.GOLD_INGOT.getRegistryName().toString());


        }};
        ArrayList ITEMPLATFORM = new ArrayList() {{

            add(new ItemStack(ModBlocks.itemPlatform,8));
            add(new ArrayList<String>() {{

                add(Item.getItemFromBlock(Blocks.GOLD_BLOCK).getRegistryName().toString());
                add(Items.GOLD_INGOT.getRegistryName().toString());
                add(Item.getItemFromBlock(Blocks.GOLD_BLOCK).getRegistryName().toString());
                add(Items.GOLD_INGOT.getRegistryName().toString());
                add(Item.getItemFromBlock(Blocks.GOLD_BLOCK).getRegistryName().toString());
                add(Items.GOLD_INGOT.getRegistryName().toString());
                add(Item.getItemFromBlock(Blocks.GOLD_BLOCK).getRegistryName().toString());
                add(Items.GOLD_INGOT.getRegistryName().toString());

            }});
            add(Items.ENDER_PEARL.getRegistryName().toString());


        }};

        ArrayList MOTOR = new ArrayList() {{

            add(new ItemStack(ModBlocks.motor));
            add(new ArrayList<String>() {{

                add(Item.getItemFromBlock(ModBlocks.enderianBlock).getRegistryName().toString());
                add(ModItems.enderianIngot.getRegistryName().toString());
                add(Item.getItemFromBlock(ModBlocks.enderianBlock).getRegistryName().toString());
                add(ModItems.enderianIngot.getRegistryName().toString());
                add(Item.getItemFromBlock(ModBlocks.enderianBlock).getRegistryName().toString());
                add(ModItems.enderianIngot.getRegistryName().toString());
                add(Item.getItemFromBlock(ModBlocks.enderianBlock).getRegistryName().toString());
                add(ModItems.enderianIngot.getRegistryName().toString());

            }});
            add(ModItems.mesh.getRegistryName().toString());


        }};

        RECEPI.add(EINGOT);
        RECEPI.add(ITEMPLATFORM);
        RECEPI.add(MOTOR);
        RECEPI.add(GRAVITYBUBBLE);
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


        if(!world.isRemote && (world.getBlockState(towerpos1).getBlock().hasTileEntity( world.getBlockState(towerpos1)) && world.getTileEntity(towerpos1) instanceof BedrockiumTowerTile)&&
                (world.getBlockState(towerpos2).getBlock().hasTileEntity( world.getBlockState(towerpos2)) && world.getTileEntity(towerpos2) instanceof BedrockiumTowerTile)&&
                (world.getBlockState(towerpos3).getBlock().hasTileEntity( world.getBlockState(towerpos3)) && world.getTileEntity(towerpos3) instanceof BedrockiumTowerTile)&&
                (world.getBlockState(towerpos4).getBlock().hasTileEntity( world.getBlockState(towerpos4)) && world.getTileEntity(towerpos4) instanceof BedrockiumTowerTile)){
            if(crafting){
                craftingCurrentTimer++;
                if (craftingCurrentTimer>=craftingTotalTimer+30){
                    crafting=false;
                    craftingCurrentTimer=0;
                    craftingTotalTimer=0;
                    handler.ifPresent(g -> {
                        InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY() + 1, pos.getZ(), g.extractItem(1, g.getStackInSlot(1).getCount(), false));

                    });
                    createList();
                    handler = LazyOptional.of(this::createHandler);
                    markDirty();
                    sendUpdates();
                    return;
                }
                if (craftingCurrentTimer % 30 == 0) {
                    world.getTileEntity(towerpos1).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                        world.getTileEntity(towerpos2).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(j -> {
                            world.getTileEntity(towerpos3).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(k -> {
                                world.getTileEntity(towerpos4).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(l -> {
                                    handler.ifPresent(g ->  {
                                        for (int i =0;i<8;i++){
                                            if(h.getStackInSlot(i) != ItemStack.EMPTY && !h.getStackInSlot(i).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                                h.extractItem(i,1,false);
                                                (world.getTileEntity(towerpos1)).markDirty();
                                                ((BedrockiumTowerTile) world.getTileEntity(towerpos1)).sendUpdates();
                                                extractFrom = towerpos1;
                                                break;
                                            }
                                            if(j.getStackInSlot(i) != ItemStack.EMPTY && !j.getStackInSlot(i).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                                j.extractItem(i,1,false);
                                                (world.getTileEntity(towerpos2)).markDirty();
                                                ((BedrockiumTowerTile) world.getTileEntity(towerpos2)).sendUpdates();
                                                extractFrom = towerpos2;
                                                break;
                                            }
                                            if(k.getStackInSlot(i) != ItemStack.EMPTY && !k.getStackInSlot(i).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                                k.extractItem(i,1,false);
                                                (world.getTileEntity(towerpos3)).markDirty();
                                                ((BedrockiumTowerTile) world.getTileEntity(towerpos3)).sendUpdates();
                                                extractFrom = towerpos3;
                                                break;
                                            }
                                            if(l.getStackInSlot(i) != ItemStack.EMPTY && !l.getStackInSlot(i).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                                l.extractItem(i,1,false);
                                                (world.getTileEntity(towerpos4)).markDirty();
                                                ((BedrockiumTowerTile) world.getTileEntity(towerpos4)).sendUpdates();
                                                extractFrom = towerpos4;
                                                break;
                                            }
                                        }
                                        if(g.getStackInSlot(0) != ItemStack.EMPTY && !g.getStackInSlot(0).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                            g.extractItem(0,1,false);
                                            markDirty();
                                            sendUpdates();
                                            extractFrom = pos;
                                        }
                                    });
                                });
                            });
                        });
                    });
                    markDirty();
                    sendUpdates();

                }
            }else {
                counterCheck++;
                if (counterCheck >= 20) {
                    counterCheck = 0;
                    checkRecepi((BedrockiumTowerTile) world.getTileEntity(towerpos1), (BedrockiumTowerTile) world.getTileEntity(towerpos2), (BedrockiumTowerTile) world.getTileEntity(towerpos3), (BedrockiumTowerTile) world.getTileEntity(towerpos4));
                    markDirty();
                    sendUpdates();
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void checkRecepi(BedrockiumTowerTile tileEntity, BedrockiumTowerTile tileEntity1, BedrockiumTowerTile tileEntity2, BedrockiumTowerTile tileEntity3) {

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            tileEntity1.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(j -> {
                tileEntity2.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(k -> {
                    tileEntity3.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(l -> {
                        handler.ifPresent(g ->  {
                            ArrayList<String> itemsNames = new ArrayList<>();
                            for(int i = 0; i < 8 ; i++){
                                if(h.getStackInSlot(i) != ItemStack.EMPTY && !h.getStackInSlot(i).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                    itemsNames.add(h.getStackInSlot(i).getItem().getRegistryName().toString());
                                }
                                if(j.getStackInSlot(i) != ItemStack.EMPTY && !j.getStackInSlot(i).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                    itemsNames.add(j.getStackInSlot(i).getItem().getRegistryName().toString());
                                }
                                if(k.getStackInSlot(i) != ItemStack.EMPTY && !k.getStackInSlot(i).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                    itemsNames.add(k.getStackInSlot(i).getItem().getRegistryName().toString());
                                }
                                if(l.getStackInSlot(i) != ItemStack.EMPTY && !l.getStackInSlot(i).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                    itemsNames.add(l.getStackInSlot(i).getItem().getRegistryName().toString());
                                }
                            }

                            boolean shouldErase = true;
                            for (int kindex =0;kindex<RECEPI.size();kindex++){

                                if (((ArrayList)RECEPI.get(kindex)).size()>=3) {

                                    if (((ArrayList) RECEPI.get(kindex)).get(1).equals(itemsNames) && (g.getStackInSlot(0).getItem().getRegistryName().toString().equals(((ArrayList) RECEPI.get(kindex)).get(2)))) {

                                        if (g.getStackInSlot(1) != ItemStack.EMPTY ||!g.getStackInSlot(1).getItem().getRegistryName().toString().equals("minecraft:air")) {
                                            g.extractItem(1, g.getStackInSlot(1).getCount(), false);
                                        }
                                        g.insertItem(1, ((ItemStack) ((ArrayList) RECEPI.get(kindex)).get(0)), false);
                                        markDirty();
                                        sendUpdates();
                                        shouldErase = false;
                                    }
                                }else{
                                    if (((ArrayList) RECEPI.get(kindex)).get(1).equals(itemsNames) && g.getStackInSlot(0) == ItemStack.EMPTY) {
                                        if (g.getStackInSlot(1) != ItemStack.EMPTY) {
                                            g.extractItem(1, 1, false);
                                        }
                                        ItemStack s = ((ItemStack) ((ArrayList) RECEPI.get(kindex)).get(0));
                                        s.setCount(s.getCount());
                                        g.insertItem(1,s, false);
                                        markDirty();
                                        sendUpdates();
                                        shouldErase = false;
                                    }
                                }
                            }
                            if(shouldErase){
                                if(g.getStackInSlot(1) != ItemStack.EMPTY){
                                    g.extractItem(1,1,false);
                                    markDirty();
                                    sendUpdates();
                                }

                            }
                        });
                    });
                });
            });
        });


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
        if(compound.contains("extract")){
            long[] list = compound.getLongArray("extract");
            extractFrom = new BlockPos(list[0],list[1],list[2]);
        }
        if(compound.contains("crafting")){
            crafting = compound.getBoolean("crafting");
        }
        if(compound.contains("total")){
            craftingTotalTimer = compound.getInt("total");
        }
        if(compound.contains("current")){
            craftingCurrentTimer = compound.getInt("current");
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
            long[] extractF = {extractFrom.getX(),extractFrom.getY(),extractFrom.getZ()};
            compound.putLongArray("extract",extractF);
            compound.putBoolean("crafting",crafting);
            compound.putInt("total",craftingTotalTimer);
            compound.putInt("current",craftingCurrentTimer);
        });

        return super.write(tag);
    }

    private IItemHandler createHandler() {
        return new ItemStackHandler(2) {

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
                if(slot==0) {
                    return 1;
                }
                else{
                    return 64;
                }
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

    public void craft(ItemStack itemStack) {
        crafting = true;
        craftingCurrentTimer=0;
        for (int kindex =0;kindex<RECEPI.size();kindex++) {
            if ((((ArrayList) RECEPI.get(kindex)).get(0)) == itemStack) {
                craftingTotalTimer = ((ArrayList) ((ArrayList) RECEPI.get(kindex)).get(1)).size();
                if (((ArrayList) RECEPI.get(kindex)).size() >= 3) {
                    craftingTotalTimer++;
                }
                craftingTotalTimer *= ticksPerItem;
                break;
            }

        }
        markDirty();
        sendUpdates();
    }
}
