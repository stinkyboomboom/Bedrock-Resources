package com.alexvr.bedres.world;

import com.alexvr.bedres.registry.ModBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;


public class ModFlowerFeature extends FlowersFeature {


    public ModFlowerFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserializer)
    {
        super(deserializer);
    }

    @Override
    public BlockState getRandomFlower(Random p_202355_1_, BlockPos p_202355_2_)
    {

        switch (new Random().nextInt(6)) {
            case 0:
                return ModBlocks.blazium.getDefaultState();
            case 1:
                return ModBlocks.enderHush.getDefaultState();
            case 2:
                return ModBlocks.sunDaize.getDefaultState();
            case 3:
                return ModBlocks.sunDaize.getDefaultState();

        }

        return ModBlocks.sunDaize.getDefaultState();
    }
}
