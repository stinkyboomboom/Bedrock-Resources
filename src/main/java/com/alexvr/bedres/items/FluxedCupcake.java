package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.bedrock_flux.BedrockFluxProvider;
import com.alexvr.bedres.capability.bedrock_flux.IBedrockFlux;
import com.alexvr.bedres.utils.References;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class FluxedCupcake extends Item {
    public static final Food CUPCAKE = (new Food.Builder()).hunger(4).saturation(1.3F).build();

    public FluxedCupcake() {
        super(new Properties()
                .group(BedrockResources.setup.itemgroup).food(CUPCAKE).maxStackSize(1));
        setRegistryName(References.FLUXED_CUPCAKE_REGNAME);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {


        return super.onItemUse(context);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        LazyOptional<IBedrockFlux> abilities = entityLiving.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
        abilities.ifPresent(flux -> {
            flux.consume(flux.getBedrockFlux()/8);
        });
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
