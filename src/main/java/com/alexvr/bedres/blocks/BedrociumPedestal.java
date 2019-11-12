package com.alexvr.bedres.blocks;

import com.alexvr.bedres.blocks.tiles.BedrockiumPedestalTile;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModItems;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BedrociumPedestal extends Block {

    protected static final VoxelShape Base = Block.makeCuboidShape(0, 0.0D, 0, 16, .3, 16);


    protected static final VoxelShape Shape = VoxelShapes.or(Base);
    public BedrociumPedestal() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .lightValue(13).variableOpacity().hardnessAndResistance(-1.0F, 3600000.0F).noDrops());
        setRegistryName(References.PEDESTAL_REGNAME);

    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shape;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity tileentity = builder.get(LootParameters.BLOCK_ENTITY);
        if (tileentity instanceof BedrockiumPedestalTile) {
            BedrockiumPedestalTile bedrockiumPedestalTile = (BedrockiumPedestalTile)tileentity;
            builder = builder.withDynamicDrop(ShulkerBoxBlock.field_220169_b, (p_220168_1_, p_220168_2_) -> {
                bedrockiumPedestalTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    if (h.getStackInSlot(0) != ItemStack.EMPTY) {
                        p_220168_2_.accept(h.getStackInSlot(0));
                    }
                });
            });
        }

        return super.getDrops(state, builder);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {

        super.onBlockClicked(state, worldIn, pos, player);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if(!worldIn.isRemote){
            TileEntity te = worldIn.getTileEntity(pos);
            if(player.getHeldItemMainhand() != ItemStack.EMPTY){
                te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    if (player.getHeldItemMainhand().getItem().getRegistryName().equals(Item.getItemFromBlock(ModBlocks.bedrockWire).getRegistryName())){
                        if (h.getStackInSlot(1) != ItemStack.EMPTY && !((BedrockiumPedestalTile)worldIn.getTileEntity(pos)).crafting) {
                            System.out.println("dsf");
                            ((BedrockiumPedestalTile)worldIn.getTileEntity(pos)).craft(h.getStackInSlot(1));
                        }
                    }
                    else if (player.getHeldItemMainhand().getItem().getRegistryName().equals(ModItems.scrapesKnife.getRegistryName())){

                        if (h.getStackInSlot(0) != ItemStack.EMPTY) {
                            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), h.extractItem(0, 1, false));
                            player.getHeldItemMainhand().damageItem(2, player, (p_220044_0_) -> p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                            te.markDirty();
                            ((BedrockiumPedestalTile) te).sendUpdates();
                        }

                    }else {
                        if (h.getStackInSlot(0) == ItemStack.EMPTY) {
                            h.insertItem(0, new ItemStack(player.getHeldItemMainhand().getItem(), 1), false);
                            player.getHeldItemMainhand().shrink(1);
                            te.markDirty();
                            ((BedrockiumPedestalTile) te).sendUpdates();
                        }
                    }
                });
                return true;
            }else{
                for(int i =0 ; i< player.inventory.getSizeInventory(); i ++) {
                    ItemStack stack = player.inventory.getStackInSlot(i);
                    if (stack.getItem().getRegistryName().equals(Items.ENDER_PEARL.getRegistryName()) && te instanceof BedrockiumPedestalTile) {
                        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                            if (h.getStackInSlot(0) == ItemStack.EMPTY) {
                                h.insertItem(0, new ItemStack(Items.ENDER_PEARL, 1), false);
                                stack.shrink(1);
                                te.markDirty();
                                ((BedrockiumPedestalTile) te).sendUpdates();
                            }
                        });
                    }
                }

                return true;
            }
        }

        return false;
    }
    public void addParticleRandom( World world, BlockPos pos){
        switch (new Random().nextInt(3)){
            case 0:
                world.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5,pos.getY()+2,pos.getZ()+.5,new Random().nextFloat()-0.5,0,0);
                world.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5,pos.getY()+2,pos.getZ()+.5,new Random().nextFloat()-0.5,0,0);
                world.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5,pos.getY()+2,pos.getZ()+.5,new Random().nextFloat()-0.5,0,0);
                break;
            case 1:
                world.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5,pos.getY()+2,pos.getZ()+.5,0,new Random().nextFloat()-0.5,0);
                world.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5,pos.getY()+2,pos.getZ()+.5,0,new Random().nextFloat()-0.5,0);
                world.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5,pos.getY()+2,pos.getZ()+.5,0,new Random().nextFloat()-0.5,0);

                break;
            case 2:
                world.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5,pos.getY()+2,pos.getZ()+.5,0,0,new Random().nextFloat()-0.5);
                world.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5,pos.getY()+2,pos.getZ()+.5,0,0,new Random().nextFloat()-0.5);
                world.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5,pos.getY()+2,pos.getZ()+.5,0,0,new Random().nextFloat()-0.5);
                break;

        }
    }

    @Override
    public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand) {
        world.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            if (h.getStackInSlot(1) != ItemStack.EMPTY){
                if(((BedrockiumPedestalTile)world.getTileEntity(pos)).crafting){
                    if (((BedrockiumPedestalTile)world.getTileEntity(pos)).craftingCurrentTimer>=((BedrockiumPedestalTile)world.getTileEntity(pos)).craftingTotalTimer){
                        addParticleRandom(world,pos);
                    }

                    if(((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX()-(pos.getX()) == 0){
                        if (((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()-(pos.getZ()) > 0){
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,0,-.15,-0.2);
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,0,-.15,-0.2);
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,0,-.15,-0.2);
                        }else{
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,0,-.15,0.2);
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,0,-.15,0.2);
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,0,-.15,0.2);
                        }
                    }else if(((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()-(pos.getZ()) == 0){
                        if (((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX()-(pos.getX()) > 0){
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,-.2,-.15,0);
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,-.2,-.15,0);
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,-.2,-.15,0);

                        }else{
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,0.2,-.15,0);
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,0.2,-.15,0);
                            world.addParticle(ParticleTypes.SMOKE,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getX() + 0.5,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getY() + 2,((BedrockiumPedestalTile)world.getTileEntity(pos)).extractFrom.getZ()+ 0.5,0.2,-.15,0);
                        }
                    }
                }else{
                    addParticleRandom(world,pos);
                }

            }
        });
        super.animateTick(stateIn, world, pos, rand);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BedrockiumPedestalTile();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {


        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
}
