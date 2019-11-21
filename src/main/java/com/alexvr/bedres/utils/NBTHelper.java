package com.alexvr.bedres.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;

import javax.annotation.Nullable;

public class NBTHelper {

    public static void setToolBlock(ItemStack stack, @Nullable BlockState state) {
        //Store the selected block in the tool's NBT
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound == null) {
            tagCompound = new CompoundNBT();
        }
        if (state == null) {
            state = Blocks.AIR.getDefaultState();
        }
        CompoundNBT stateTag = new CompoundNBT();
        NBTUtil.writeBlockState(state);
        tagCompound.put("blockstate", stateTag);
        stack.setTag(tagCompound);
    }

    public static void setToolActualBlock(ItemStack stack, @Nullable BlockState state) {
        //Store the selected block actual state in the tool's NBT
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound == null) {
            tagCompound = new CompoundNBT();
        }
        if (state == null) {
            state = Blocks.AIR.getDefaultState();
        }
        CompoundNBT stateTag = new CompoundNBT();
        NBTUtil.writeBlockState(state);
        tagCompound.put("actualblockstate", stateTag);
        stack.setTag(tagCompound);
    }

    public static BlockState getToolBlock(ItemStack stack) {
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound == null) {
            setToolBlock(stack, Blocks.AIR.getDefaultState());
            return Blocks.AIR.getDefaultState();
        }
        return NBTUtil.readBlockState((CompoundNBT) tagCompound.get("blockstate"));
    }

    public static BlockState getToolActualBlock(ItemStack stack) {
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound == null) {
            setToolBlock(stack, Blocks.AIR.getDefaultState());
            tagCompound = stack.getTag();
            return Blocks.AIR.getDefaultState();
        }
        return NBTUtil.readBlockState((CompoundNBT) tagCompound.get("actualblockstate"));
    }

    public static void setInteger(ItemStack stack,String key, int amount) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }

        nbt.putInt(key,amount);

    }

    public static void decreaseInteger(ItemStack stack,String key, int amount) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        if(!nbt.contains(key)) {
            nbt.putInt(key,0);
        }else {
            nbt.putInt(key, nbt.getInt(key)-amount);
        }
        stack.setTag(nbt);

    }

    public static void increaseInteger(ItemStack stack,String key, int amount) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        if(!nbt.contains(key)) {
            nbt.putInt(key,0);
        }else {
            nbt.putInt(key, nbt.getInt(key)+amount);
        }
        stack.setTag(nbt);

    }

    public static void setString(ItemStack stack,String key, String string) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }

        nbt.putString(key,string);
        stack.setTag(nbt);

    }

    public static void setBoolean(ItemStack stack,String key, boolean amount) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        nbt.putBoolean(key,amount);
        stack.setTag(nbt);

    }

    public static int getInteger(ItemStack stack,String key) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        if(!nbt.contains(key)) {
            nbt.putInt(key,0);
        }
        stack.setTag(nbt);

        return nbt.getInt(key);
    }

    public static int getInteger(ItemStack stack,String key, int base) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        if(!nbt.contains(key)) {
            nbt.putInt(key,base);
        }
        stack.setTag(nbt);

        return nbt.getInt(key);
    }

    public static String getString(ItemStack stack,String key) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        if(!nbt.contains(key)) {
            nbt.putString(key,"");
        }
        stack.setTag(nbt);

        return nbt.getString(key);
    }

    public static String getString(ItemStack stack,String key, String base) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        if(!nbt.contains(key)) {
            nbt.putString(key,base);
        }
        stack.setTag(nbt);

        return nbt.getString(key);
    }

    public static boolean getBoolean(ItemStack stack,String key) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        if(!nbt.contains(key)) {
            nbt.putBoolean(key,false);
        }
        stack.setTag(nbt);
        return nbt.getBoolean(key);
    }

    public static boolean getBoolean(ItemStack stack,String key, boolean base) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        if(!nbt.contains(key)) {
            nbt.putBoolean(key,base);
        }
        stack.setTag(nbt);
        return nbt.getBoolean(key);
    }

    public static void flipBoolean(ItemStack stack,String key) {
        CompoundNBT nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundNBT();
        }
        if(!nbt.contains(key)) {
            nbt.putBoolean(key,true);
        }
        stack.setTag(nbt);
        nbt.putBoolean(key, !getBoolean(stack, key));
    }

}

