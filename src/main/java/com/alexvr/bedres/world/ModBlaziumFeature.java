package com.alexvr.bedres.world;

import com.alexvr.bedres.registry.ModBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;


public class ModBlaziumFeature extends FlowersFeature {


    public ModBlaziumFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserializer)
    {
        super(deserializer);
    }

    @Override
    public BlockState getRandomFlower(Random p_202355_1_, BlockPos p_202355_2_)
    {


        return ModBlocks.blazium.getDefaultState();
    }
}
