package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.BedrockFluxProvider;
import com.alexvr.bedres.capability.IBedrockFlux;
import com.alexvr.bedres.registry.ModItems;
import com.alexvr.bedres.registry.ModParticles;
import com.alexvr.bedres.utils.VectorHelper;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.*;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Set;

import static net.minecraft.state.properties.BlockStateProperties.AXIS;

public class ScrapeKnife extends SwordItem {

    public ScrapeKnife() {
        super(ItemTier.STONE,1, -2.4F, new Item.Properties()
                .group(BedrockResources.setup.itemgroup).maxDamage(128).setNoRepair());
        setRegistryName("scrape_knife");


    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!worldIn.isRemote){
            BlockRayTraceResult bs = selectBlock(playerIn.getHeldItemMainhand(),playerIn);
            if(playerIn.isSneaking() && bs != null){
                playerIn.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_AQUA + new TranslationTextComponent("message.bedres.validblock").getUnformattedComponentText()), true);
                ItemStack stack = new ItemStack(ModItems.bedrockScrapes);
                InventoryHelper.spawnItemStack(worldIn,  bs.getPos().getX(), bs.getPos().getY()+1, bs.getPos().getZ(),stack);
                playerIn.getHeldItemMainhand().damageItem(2, playerIn, (p_220044_0_) -> p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                LazyOptional<IBedrockFlux> bedrockFlux = playerIn.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

                bedrockFlux.ifPresent(h -> {
                    if(h.getBedrockFlux()==0.00f){
                        h.fill(250.32f);
                        String message = ("Some odd particles lift into the air, you back away as quickly as possible, yet you still feel you breathed in some. You inhaled over 200 particles");
                        playerIn.sendStatusMessage(new StringTextComponent(message),true);
                        playerIn.sendStatusMessage(new StringTextComponent("That's alright, how bad can it be,Ill be more careful from now on..."),false);
                        playerIn.move(MoverType.PLAYER,playerIn.getLookVec().subtractReverse(playerIn.getLookVec().mul(2,2,2)));
                    }else{
                        h.fill(20.32f);
                        playerIn.sendStatusMessage(new StringTextComponent("You were careful and only inhaled over 15 particles"),true);

                    }
                    playerIn.addPotionEffect(new EffectInstance(Effects.NAUSEA,20 *15,2,true,true));
                    playerIn.addPotionEffect(new EffectInstance(Effects.HUNGER,20 *25,2,true,true));
                    playerIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS,20 *15,1,true,true));

                });

            }
            else if(playerIn.isSneaking()){
                LazyOptional<IBedrockFlux> bedrockFlux = playerIn.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

                bedrockFlux.ifPresent(h -> h.consume(100));
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }


    public static BlockRayTraceResult selectBlock(ItemStack stack, PlayerEntity player) {
        // Used to find which block the player is looking at, and store it in NBT on the tool.
        World world = player.world;
        BlockRayTraceResult lookingAt = VectorHelper.getLookingAt(player, RayTraceContext.FluidMode.NONE);
        if (lookingAt == null || (world.getBlockState(VectorHelper.getLookingAt(player, stack).getPos()) == Blocks.AIR.getDefaultState())) return null;


        BlockState state = world.getBlockState(lookingAt.getPos());
        System.out.println(state.getBlock().getRegistryName());
        if (!(state.getBlock().getRegistryName().equals(Blocks.BEDROCK.getRegistryName()))) {
            player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + new TranslationTextComponent("message.bedres.invalidblock").getUnformattedComponentText()), true);
            return null;
        }
        BlockState placeState = getSpecificStates(state, world, player, lookingAt.getPos(), stack).getExtendedState(world, lookingAt.getPos());
        return lookingAt;
    }

    private static final Set<IProperty> SAFE_PROPERTIES =
            ImmutableSet.of(SlabBlock.TYPE, StairsBlock.HALF, LogBlock.AXIS, AXIS, DirectionalBlock.FACING, StairsBlock.FACING, TrapDoorBlock.HALF, TrapDoorBlock.OPEN, StairsBlock.SHAPE, LeverBlock.POWERED, RepeaterBlock.DELAY, PaneBlock.EAST, PaneBlock.WEST, PaneBlock.NORTH, PaneBlock.SOUTH);

    public static BlockState getSpecificStates(BlockState originalState, World world, PlayerEntity player, BlockPos pos, ItemStack tool) {
        BlockState placeState;

        try {
            placeState = originalState.getBlock().getStateForPlacement(
                    new BlockItemUseContext(new ItemUseContext(player, Hand.MAIN_HAND, VectorHelper.getLookingAt(player, tool))));
        } catch (Exception var8) {
            placeState = originalState.getBlock().getDefaultState();
        }

        if (placeState != null) {
            for (IProperty prop : placeState.getProperties()) {
                if (SAFE_PROPERTIES.contains(prop)) {
                    placeState = placeState.with(prop, originalState.get(prop));
                }

            }

            return placeState;
        }
        return null;
    }

}
