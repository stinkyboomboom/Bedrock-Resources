package com.alexvr.bedres.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;

public class ScrapeTank extends Block {

    public ScrapeTank() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
        .hardnessAndResistance(2.0f)
        .lightValue(13).variableOpacity());
        setRegistryName("scrape_tank");
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
