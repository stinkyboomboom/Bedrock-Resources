package com.alexvr.bedres.blocks;

import com.alexvr.bedres.blocks.tiles.ScrapeTankTile;
import com.alexvr.bedres.registry.ModParticles;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ScrapeTank extends Block{


    public ScrapeTank() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL).harvestTool(ToolType.PICKAXE)
        .hardnessAndResistance(2.0f)
        .lightValue(13).variableOpacity());
        setRegistryName(References.SCRAPE_TANK_REGNAME);

    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        for (int i = 0; i < 64 ; i++) {
            ModParticles.BEDROCK_DUST.spawn(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof INamedContainerProvider){
                NetworkHooks.openGui((ServerPlayerEntity) player,(INamedContainerProvider)tileEntity,tileEntity.getPos());
                return true;
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity tileentity = builder.get(LootParameters.BLOCK_ENTITY);
        if (tileentity instanceof ScrapeTankTile) {
            ScrapeTankTile scrapeTankTile = (ScrapeTankTile)tileentity;
            builder = builder.withDynamicDrop(ShulkerBoxBlock.CONTENTS, (p_220168_1_, p_220168_2_) -> {
                scrapeTankTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
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
        if (compoundnbt != null) {
            if (compoundnbt.contains("LootTable", 8)) {
                tooltip.add(new StringTextComponent("???????"));
            }
            if (compoundnbt.contains("Items", 9)) {
                NonNullList<ItemStack> nonnulllist = NonNullList.withSize(1, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundnbt, nonnulllist);
                if (!nonnulllist.get(0).isEmpty()) {
                    ITextComponent itextcomponent = nonnulllist.get(0).getDisplayName().deepCopy();
                    itextcomponent.appendText(" x").appendText(String.valueOf(nonnulllist.get(0).getCount()));
                    tooltip.add(itextcomponent);
                }
            }
        }

    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ScrapeTankTile();
    }




}
