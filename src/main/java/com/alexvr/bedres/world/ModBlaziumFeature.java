package com.alexvr.bedres.world;

import com.alexvr.bedres.registry.ModBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;


public class ModBlaziumFeature extends FlowersFeature {


    public ModBlaziumFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserializer)
    {
        super(deserializer);
    }


    @Override
    public boolean func_225559_a_(IWorld p_225559_1_, BlockPos p_225559_2_, IFeatureConfig p_225559_3_) {
        return false;
    }

    @Override
    public int func_225560_a_(IFeatureConfig p_225560_1_) {
        return 0;
    }

    @Override
    public BlockPos getNearbyPos(Random p_225561_1_, BlockPos p_225561_2_, IFeatureConfig p_225561_3_) {
        return null;
    }

    @Override
    public BlockState getFlowerToPlace(Random p_225562_1_, BlockPos p_225562_2_, IFeatureConfig p_225562_3_) {
        return ModBlocks.blazium.getDefaultState();
    }
}
