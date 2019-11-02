package com.alexvr.bedres.capability.bedrock_flux;

import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

/**
 * This class is responsible for saving and reading mana data from or to server
 */
public class BedrockFluxStorage implements Capability.IStorage<IBedrockFlux>
{
    @Override
    public INBT writeNBT(Capability<IBedrockFlux> capability, IBedrockFlux instance, Direction side)
    {
        return new FloatNBT(instance.getBedrockFlux());
    }

    @Override
    public void readNBT(Capability<IBedrockFlux> capability, IBedrockFlux instance, Direction side, INBT nbt)
    {
        instance.set(((NumberNBT) nbt).getFloat());
    }
}