package com.alexvr.bedres.multiblocks.bedrockscraper;

import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModItems;
import com.alexvr.bedres.utils.IRestorableTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class BedrockScraperControllerTile extends TileEntity implements IRestorableTileEntity , INamedContainerProvider, ITickableTileEntity {

    BlockPos pos1,pos2,pos3;
    Boolean n,s,e,w;
    public String dir="";
    public boolean multiBlock=false;
    double distanceDamage = 0.65;
    int progress;



    public BedrockScraperControllerTile() {
        super(ModBlocks.bedrockScraperControllerTile);
        pos1= new BlockPos(0,0,0);
        pos2= new BlockPos(0,0,0);
        pos3= new BlockPos(0,0,0);
        progress=0;

    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {

        return new BedrockScraperContainer(p_createMenu_1_,world,pos,p_createMenu_2_,p_createMenu_3_);
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
        if(compound.contains("dir")){
            String direction = compound.getString("dir");
            setBaseDirection(direction);
            setDirection(direction);
        }

        if(compound.contains("tick")){
            progress = compound.getInt("tick");
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
        compound.putString("dir",dir);
        compound.putInt("tick",progress);

    }

    private ItemStackHandler itemHandler = new ItemStackHandler(3*9) {

        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            BedrockScraperControllerTile.this.markDirty();
        }
    };

    public void setExpecctedBlockPositions(BlockPos pos1,BlockPos pos2,BlockPos pos3,String direction){
        this.pos1=pos1;
        this.pos2=pos2;
        this.pos3=pos3;
        setDirection(direction);
        setBaseDirection(direction);
        markDirty();
    }

    private void setBaseDirection(String direction){
        dir = direction;

    }


    private void setDirection(String direction){
        switch (direction){
            case "n":
                n=true;
                break;
            case "s":
                s=true;
                break;
            case "e":
                e=true;
                break;
            case "w":
                w=true;
                break;
        }

    }


    @Override
    public double getMaxRenderDistanceSquared() {
        return 4096.0D;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }


    public boolean checkSlaves(){
        if ((!world.getBlockState(pos1).getBlock().getRegistryName().equals(ModBlocks.bedrockScraperSlaveBlock.getRegistryName())|| !world.getBlockState(pos2).getBlock().getRegistryName().equals(ModBlocks.bedrockScraperSlaveBlock.getRegistryName())|| !world.getBlockState(pos3).getBlock().getRegistryName().equals(ModBlocks.bedrockScraperSlaveBlock.getRegistryName()))){
            multiBlock = false;
            return false;
        }
        n=false;s=false;e=false;w=false;
        setDirection(dir);
        setDirection(String.valueOf(world.getBlockState(pos1).get(BedrockScrapperControllerBlock.FACING_HORIZ).toString().charAt(0)));
        setDirection(String.valueOf(world.getBlockState(pos2).get(BedrockScrapperControllerBlock.FACING_HORIZ).toString().charAt(0)));
        setDirection(String.valueOf(world.getBlockState(pos3).get(BedrockScrapperControllerBlock.FACING_HORIZ).toString().charAt(0)));
        multiBlock = n&&s&&e&&w;
        return multiBlock;
    }

    @Override
    public void tick() {
        if(!world.isRemote){
            if(!multiBlock) {
                if(progress>0){
                    progress=0;
                    markDirty();

                }
                if (world.isPlayerWithin(pos1.getX(), pos1.getY(), pos1.getZ(), distanceDamage)) {
                    world.getClosestPlayer(pos1.getX(), pos1.getY(), pos1.getZ(), distanceDamage, false).attackEntityFrom(DamageSource.DROWN, 1.5f);
                }
                if (world.isPlayerWithin(pos2.getX(), pos2.getY(), pos2.getZ(), distanceDamage)) {
                    world.getClosestPlayer(pos2.getX(), pos2.getY(), pos2.getZ(), distanceDamage, false).attackEntityFrom(DamageSource.CACTUS, 1.5f);
                }
                if (world.isPlayerWithin(pos3.getX(), pos3.getY(), pos3.getZ(), distanceDamage)) {
                    world.getClosestPlayer(pos3.getX(), pos3.getY(), pos3.getZ(), distanceDamage, false).attackEntityFrom(DamageSource.CACTUS, 1.5f);
                }
            }
            else{
                progress++;
                System.out.println(progress%20);
                if (progress%20==0){
                    markDirty();
                }
                if(progress>= 20*60){
                    progress=0;
                    ItemStack stack = new ItemStack(ModItems.bedrockScrapes,1);
                    TileEntity block = (world.getTileEntity(getPos().offset(getBlockState().get(BedrockScrapperControllerBlock.FACING_HORIZ))));
                    if (block instanceof IInventory){
                        for (int i =0;i<((IInventory)block).getSizeInventory();i++){
                            if(((IInventory)block).getStackInSlot(i).getItem() == ModItems.bedrockScrapes && ((IInventory)block).getStackInSlot(i).getCount()<64){
                                ((IInventory)block).getStackInSlot(i).setCount(((IInventory)block).getStackInSlot(i).getCount()+1);
                                return;
                            }else if(((IInventory)block).getStackInSlot(i) == ItemStack.EMPTY){
                                ((IInventory)block).setInventorySlotContents(i,stack);
                                return;
                            }
                        }
                    }else{
                        InventoryHelper.spawnItemStack(world,  getPos().getX(), getPos().getY()+0.5, getPos().getZ(),stack);
                        return;
                    }
                }
            }
        }
    }

}
