package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.capability.bedrock_flux.BedrockFluxProvider;
import com.alexvr.bedres.capability.bedrock_flux.IBedrockFlux;
import com.alexvr.bedres.utils.NBTHelper;
import com.alexvr.bedres.utils.References;
import com.alexvr.bedres.utils.WorldEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class FluxOracle extends Item {
    public FluxOracle() {
        super(new Item.Properties()
                .group(BedrockResources.setup.itemgroup).maxStackSize(1));
        setRegistryName(References.FLUX_ORACLE_REGNAME);
    }


    @SuppressWarnings("NullableProblems")
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!worldIn.isRemote) {
            if (playerIn.isSneaking()){
                LazyOptional<IPlayerAbility> abilities = playerIn.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
                abilities.ifPresent(iPlayerAbility -> {
                    LazyOptional<IBedrockFlux> bedrockFlux = playerIn.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
                    bedrockFlux.ifPresent(flux -> {
                        playerIn.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "Skills is:"), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Sword", iPlayerAbility.getSword())), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Axe", iPlayerAbility.getAxe())), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Shovel", iPlayerAbility.getShovel())), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Hoe", iPlayerAbility.getHoe())), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Pickaxe", iPlayerAbility.getPick())), false);
                        playerIn.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "Passive: "), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Speed", iPlayerAbility.getMiningSpeedBoost())), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Jump", iPlayerAbility.getJumpBoost())), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Flux", flux.getBedrockFluxString())), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Minimum Flux", flux.getMin())), false);
                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Max Flux", flux.getMaxBedrockFlux())), false);
                    });
                });
            }else{
                NBTHelper.flipBoolean(playerIn.getHeldItemMainhand(),"active");
                Minecraft.getInstance().displayGuiScreen(WorldEventHandler.fxG);

            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
