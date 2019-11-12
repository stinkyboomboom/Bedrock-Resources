package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.bedrock_flux.BedrockFluxProvider;
import com.alexvr.bedres.capability.bedrock_flux.IBedrockFlux;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.References;
import com.alexvr.bedres.utils.VectorHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class ScrapeKnife extends SwordItem {

    public ScrapeKnife() {
        super(ItemTier.STONE,1, -2.4F, new Item.Properties()
                .group(BedrockResources.setup.itemgroup).maxDamage(128).setNoRepair());
        setRegistryName(References.SCRAPE_KNIFE_REGNAME);


    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!worldIn.isRemote){
            BlockRayTraceResult bs = selectBlock(playerIn.getHeldItemMainhand(),playerIn);
            if(playerIn.isSneaking() && bs != null){
                LazyOptional<IBedrockFlux> bedrockFlux = playerIn.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
                bedrockFlux.ifPresent(h -> {
                    if (h.getBedrockFlux()<h.getMaxBedrockFlux()/2) {
                        ItemStack stack = new ItemStack(Item.getItemFromBlock(ModBlocks.bedrockWire));
                        InventoryHelper.spawnItemStack(worldIn,  bs.getPos().getX(), bs.getPos().getY()+1, bs.getPos().getZ(),stack);
                        playerIn.getHeldItemMainhand().damageItem(2, playerIn, (p_220044_0_) -> p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                        if (h.getBedrockFlux() == 0.00f) {
                            h.fill(250.32f);
                            String message = ("Some odd particles lift into the air, you back away as quickly as possible, yet you still feel you breathed in some. You inhaled over 200 particles");
                            playerIn.sendStatusMessage(new StringTextComponent(TextFormatting.RED +message), false);
                            playerIn.sendStatusMessage(new StringTextComponent("That's alright, how bad can it be,Ill be more careful from now on..."), false);
                            playerIn.move(MoverType.PLAYER, playerIn.getLookVec().subtractReverse(playerIn.getLookVec().mul(2, 2, 2)));
                        } else {
                            h.fill(20.32f);
                            playerIn.sendStatusMessage(new StringTextComponent("You were careful and only inhaled over 15 particles"), true);

                        }
                        if (playerIn.isPotionActive((Effects.NAUSEA))){
                            playerIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, (20 * 15) + playerIn.getActivePotionEffect(Effects.NAUSEA).getDuration(), 2, true, true));
                        }
                        else{
                            playerIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 20 * 15, 2, true, true));
                        }
                        if (playerIn.isPotionActive((Effects.HUNGER))){
                            playerIn.addPotionEffect(new EffectInstance(Effects.HUNGER, (20 * 15) + playerIn.getActivePotionEffect(Effects.HUNGER).getDuration(), 2, true, true));
                        }
                        else{
                            playerIn.addPotionEffect(new EffectInstance(Effects.HUNGER, 20 * 15, 2, true, true));
                        }
                        if (playerIn.isPotionActive((Effects.WEAKNESS))){
                            playerIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, (20 * 15) + playerIn.getActivePotionEffect(Effects.WEAKNESS).getDuration(), 1, true, true));
                        }
                        else{
                            playerIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 20 * 15, 1, true, true));
                        }
                    }else{
                        playerIn.sendStatusMessage(new StringTextComponent("I dont feel too good, I better not."), false);

                    }
                });

            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }


    private static BlockRayTraceResult selectBlock(ItemStack stack, PlayerEntity player) {
        // Used to find which block the player is looking at, and store it in NBT on the tool.
        World world = player.world;
        BlockRayTraceResult lookingAt = VectorHelper.getLookingAt(player, RayTraceContext.FluidMode.NONE);
        if (lookingAt == null || (world.getBlockState(VectorHelper.getLookingAt(player).getPos()) == Blocks.AIR.getDefaultState())) return null;


        BlockState state = world.getBlockState(lookingAt.getPos());
        if (!(state.getBlock().getRegistryName().equals(Blocks.BEDROCK.getRegistryName())) && !(state.getBlock().getRegistryName().equals(ModBlocks.itemPlatform.getRegistryName())) && !(state.getBlock().getRegistryName().equals(ModBlocks.enderianRitualPedestal.getRegistryName())) && !(state.getBlock().getRegistryName().equals(ModBlocks.bedrociumPedestal.getRegistryName()))) {
            player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + new TranslationTextComponent("message.bedres.invalidblock").getUnformattedComponentText()), true);
            return null;
        }
        return lookingAt;
    }

}
