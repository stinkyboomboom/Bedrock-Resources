package com.alexvr.bedres.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemTier;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.common.ToolType;

public class ScraperMotor extends Block{

    public ScraperMotor(Material m, SoundType s, float hardness, int lightVal, String regName) {
        super(Properties.create(m).harvestTool(ToolType.PICKAXE).harvestLevel(ItemTier.IRON.getHarvestLevel())
                .sound(s)
                .hardnessAndResistance(hardness)
                .lightValue(lightVal).variableOpacity());
        setRegistryName(regName);


    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }


}
