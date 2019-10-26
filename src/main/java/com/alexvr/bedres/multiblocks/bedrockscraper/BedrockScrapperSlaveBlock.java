package com.alexvr.bedres.multiblocks.bedrockscraper;

import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

import static com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScrapperControllerBlock.FACING_HORIZ;


public class BedrockScrapperSlaveBlock extends Block {

    public BedrockScrapperSlaveBlock() {
            super(Block.Properties.create(Material.IRON)
                    .sound(SoundType.ANVIL)
                    .hardnessAndResistance(24.0f)
                    .lightValue(0).variableOpacity());
            setRegistryName(References.BEDROCK_SCRAPER_SLAVE_REGNAME);

        setDefaultState(getStateContainer().getBaseState().with(FACING_HORIZ, Direction.NORTH));


    }


    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING_HORIZ, context.getPlayer().getHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING_HORIZ);
    }
    }

