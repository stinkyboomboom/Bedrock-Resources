package com.alexvr.bedres.blocks;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BedrockStair extends StairsBlock {

    public BedrockStair(Material m, SoundType s, float hardness, int lightVal, String regName) {
        super(new EnderianBrick(m,s,hardness,lightVal, References.ENDERIAN_BRICK_REGNAME).getDefaultState(),Properties.create(m)
                .sound(s).harvestTool(ToolType.PICKAXE)
                .hardnessAndResistance(hardness)
                .lightValue(lightVal));
        setRegistryName(regName);

    }

}
