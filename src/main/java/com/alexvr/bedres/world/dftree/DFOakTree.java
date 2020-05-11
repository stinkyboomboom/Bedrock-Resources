package com.alexvr.bedres.world.dftree;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class DFOakTree extends Tree {
//    @Nullable
//    protected TreeFeature<TreeFeatureConfig> getTreeFeature(Random random) {
//        return new DFOakTreeFeature(TreeFeatureConfig::deserialize, true, false);
//    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
        return null;
    }
}