package com.alexvr.bedres.capability.bedrock_flux;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Mana provider
 *
 * This class is responsible for providing a capability. Other modders may
 * attach their own provider with implementation that returns another
 * implementation of IMana to the target's (Entity, TE, ItemStack, etc.) disposal.
 */
public class BedrockFluxProvider implements ICapabilitySerializable<INBT>
{
    @CapabilityInject(IBedrockFlux.class)
    public static final Capability<IBedrockFlux> BEDROCK_FLUX_CAPABILITY = null;


    private IBedrockFlux instance = BEDROCK_FLUX_CAPABILITY.getDefaultInstance();

    public boolean hasCapability(Capability<?> capability, Direction facing)
    {
        return capability == BEDROCK_FLUX_CAPABILITY;
    }


    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == BEDROCK_FLUX_CAPABILITY) {
            return LazyOptional.of(() -> (T) instance);
        }
        return LazyOptional.empty();
    }





    @Override
    public INBT serializeNBT()
    {
        return BEDROCK_FLUX_CAPABILITY.getStorage().writeNBT(BEDROCK_FLUX_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt)
    {
        BEDROCK_FLUX_CAPABILITY.getStorage().readNBT(BEDROCK_FLUX_CAPABILITY, this.instance, null, nbt);
    }
}