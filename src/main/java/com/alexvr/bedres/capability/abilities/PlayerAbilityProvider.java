package com.alexvr.bedres.capability.abilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerAbilityProvider implements ICapabilitySerializable<INBT>
{
    @CapabilityInject(IPlayerAbility.class)
    public static final Capability<IPlayerAbility> PLAYER_ABILITY_CAPABILITY = null;


    private IPlayerAbility instance = PLAYER_ABILITY_CAPABILITY.getDefaultInstance();

    public boolean hasCapability(Capability<?> capability, Direction facing)
    {
        return capability == PLAYER_ABILITY_CAPABILITY;
    }


    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_ABILITY_CAPABILITY) {
            return LazyOptional.of(() -> (T) instance);
        }
        return LazyOptional.empty();
    }





    @Override
    public INBT serializeNBT()
    {
        return PLAYER_ABILITY_CAPABILITY.getStorage().writeNBT(PLAYER_ABILITY_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt)
    {
        PLAYER_ABILITY_CAPABILITY.getStorage().readNBT(PLAYER_ABILITY_CAPABILITY, this.instance, null, nbt);
    }
}
