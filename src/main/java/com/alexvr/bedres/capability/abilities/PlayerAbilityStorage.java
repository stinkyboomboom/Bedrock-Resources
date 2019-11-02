package com.alexvr.bedres.capability.abilities;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerAbilityStorage implements Capability.IStorage<IPlayerAbility>
{
    @Override
    public INBT writeNBT(Capability<IPlayerAbility> capability, IPlayerAbility instance, Direction side)
    {
        return new StringNBT(instance.getNAme());
    }

    @Override
    public void readNBT(Capability<IPlayerAbility> capability, IPlayerAbility instance, Direction side, INBT nbt)
    {
        instance.setname(nbt.getString());
    }
}
