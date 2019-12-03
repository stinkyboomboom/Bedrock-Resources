package com.alexvr.bedres.capability.bedrock_flux;

import com.alexvr.bedres.gui.FluxOracleScreen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
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
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("playerflux",instance.getBedrockFlux());
        tag.putFloat("playermin",instance.getMin());
        tag.putDouble("playermax",instance.getMaxBedrockFlux());
        tag.putInt("playertimer",instance.getTimer());
        tag.putInt("playermax_timer",instance.getMaxTimer());
        tag.putBoolean("playercrafted",instance.getCrafterFlux());

        return tag;
    }

    @Override
    public void readNBT(Capability<IBedrockFlux> capability, IBedrockFlux instance, Direction side, INBT nbt)
    {
        instance.set(((CompoundNBT)nbt).getFloat("playerflux"));
        instance.setMin(((CompoundNBT)nbt).getFloat("playermin"));
        instance.setMaxBedrockFlux(((CompoundNBT)nbt).getDouble("playermax"));
        instance.setTimer(((CompoundNBT)nbt).getInt("playertimer"));
        instance.setMaxTimer(((CompoundNBT)nbt).getInt("playermax_timer"));
        instance.setCrafterFlux(((CompoundNBT)nbt).getBoolean("playercrafted"));
        if (instance.getCrafterFlux()){
            FluxOracleScreen ff = new FluxOracleScreen();
            ff.flux = instance;
            instance.setScreen(ff);
        }

    }
}