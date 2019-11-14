package com.alexvr.bedres.capability.abilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerAbilityStorage implements Capability.IStorage<IPlayerAbility>
{
    @Override
    public INBT writeNBT(Capability<IPlayerAbility> capability, IPlayerAbility instance, Direction side)
    {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("axe",instance.getAxe());
        tag.putString("pick",instance.getPick());
        tag.putString("shovel",instance.getShovel());
        tag.putString("sword",instance.getSword());
        tag.putString("hoe",instance.getHoe());
        tag.putString("result",instance.getRitualCraftingResult());
        tag.putFloat("speed",instance.getMiningSpeedBoost());
        tag.putInt("ritualTimer",instance.getRitualTimer());
        tag.putInt("totalRitual",instance.getRitualTotalTimer());
        tag.putFloat("gravilty",instance.getGravityMultiplier());
        tag.putFloat("jump",instance.getJumpBoost());
        tag.putBoolean("ritual",instance.getInRitual());
        tag.putBoolean("checking",instance.getChecking());
        tag.putDouble("FOV",instance.getFOV());
        if (instance.getlookPos() != null) {
            tag.putDouble("lookingAtX", instance.getlookPos().x);
            tag.putDouble("lookingAtY", instance.getlookPos().y);
            tag.putDouble("lookingAtZ", instance.getlookPos().z);
        }


        return tag;
    }

    @Override
    public void readNBT(Capability<IPlayerAbility> capability, IPlayerAbility instance, Direction side, INBT nbt)
    {

        instance.setAxe(((CompoundNBT)nbt).getString("axe"));
        instance.setPick(((CompoundNBT)nbt).getString("pick"));
        instance.setShovel(((CompoundNBT)nbt).getString("shovel"));
        instance.setSword(((CompoundNBT)nbt).getString("sword"));
        instance.setHoe(((CompoundNBT)nbt).getString("hoe"));
        instance.setMiningSpeedBoost(((CompoundNBT)nbt).getFloat("speed"));
        instance.setJumpBoost(((CompoundNBT)nbt).getFloat("jump"));
        instance.setGRavityMultiplier(((CompoundNBT)nbt).getFloat("gravilty"));
        instance.setRitualTimer(((CompoundNBT)nbt).getInt("ritualTimer"));
        instance.setRitualTotalTimer(((CompoundNBT)nbt).getInt("totalRitual"));
        instance.setRitualCraftingResult(((CompoundNBT)nbt).getString("result"));
        instance.setFOV(((CompoundNBT)nbt).getDouble("FOV"));
        if (((CompoundNBT)nbt).contains("lookingAtX")) {
            instance.setLookPos(new Vec3d(((CompoundNBT) nbt).getDouble("lookingAtX"), ((CompoundNBT) nbt).getDouble("lookingAtY"), ((CompoundNBT) nbt).getDouble("lookingAtZ")));
        }
    }
}
