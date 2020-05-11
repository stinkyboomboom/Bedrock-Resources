package com.alexvr.bedres.world.dftree;

import com.alexvr.bedres.registry.ModBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class DFOakTreeFeature extends TreeFeature {
    private static final BlockState LOG = ModBlocks.dfOakLog.getDefaultState();
    private static final BlockState LEAF = ModBlocks.dfOakLeave.getDefaultState();
    private final boolean useExtraRandomHeight;
    IPlantable sapling ;

    public DFOakTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> configIn, boolean doBlockNotifyIn, boolean extraRandomHeightIn) {
        super(configIn);
        this.useExtraRandomHeight = extraRandomHeightIn;
        this.setSapling(ModBlocks.dfOakSappling);
    }

    private void setSapling(IPlantable dfOakSappling) {
        sapling = dfOakSappling;
    }



    private IPlantable getSapling() {
        return sapling;
    }


    @Override
    public boolean place(IWorldGenerationReader worldIn, Random rand, BlockPos position, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox boundingBoxIn, TreeFeatureConfig configIn) {
        int i = rand.nextInt(3) + 5;
        if (this.useExtraRandomHeight) {
            i += rand.nextInt(7);
        }


        boolean flag = true;
        if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight()) {
            for(int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
                int k = 1;
                if (j == position.getY()) {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2) {
                    k = 2;
                }


                for(int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
                    for(int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
                        if (j >= 0 && j < worldIn.getMaxHeight()) {
                            if (!canBeReplacedByLogs(worldIn, position)) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else if ((isSoil(worldIn, position.down(), getSapling())) && position.getY() < worldIn.getMaxHeight() - i - 1) {
                this.setDirtAt(worldIn, position.down(), position);

                for(int l1 = position.getY() - 3 + i; l1 <= position.getY() + i; ++l1) {
                    int j2 = l1 - (position.getY() + i);
                    int k2 = 1 - j2 / 2;

                    for(int l2 = position.getX() - k2; l2 <= position.getX() + k2; ++l2) {
                        int i3 = l2 - position.getX();

                        for(int j1 = position.getZ() - k2; j1 <= position.getZ() + k2; ++j1) {
                            int k1 = j1 - position.getZ();
                            if (Math.abs(i3) != k2 || Math.abs(k1) != k2 || rand.nextInt(2) != 0 && j2 != 0) {
                                BlockPos blockpos = new BlockPos(l2, l1, j1);
                                if (isAirOrLeaves(worldIn, blockpos)) {
                                    this.setBlockState( worldIn, blockpos, LEAF);
                                }
                            }
                        }
                    }
                }

                for(int i2 = 0; i2 < i; ++i2) {
                    if (isAirOrLeaves(worldIn, position.up(i2))) {
                        this.setBlockState(worldIn, position.up(i2), LOG);
                    }
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }    }
}