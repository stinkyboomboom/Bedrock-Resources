package com.alexvr.bedres.blocks;

import com.alexvr.bedres.tiles.ScrapeTankTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

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

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ScrapeTankTile();
    }
}
