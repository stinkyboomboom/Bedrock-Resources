package com.alexvr.bedres.blocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class RangeView extends Block{


    public RangeView() {
        super(Properties.create(Material.MISCELLANEOUS)
        .hardnessAndResistance(0.0f).variableOpacity());
        setRegistryName(References.RANGE_VIEW_REGNAME);

    }


    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }



}
