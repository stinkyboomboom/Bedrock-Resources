package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class DFBase extends Block {


    public DFBase(Material mat,SoundType sound, String name, float hardRes,ToolType tool) {
        super(Properties.create(mat)
                .sound(sound).harvestTool(tool)
                .hardnessAndResistance(hardRes).tickRandomly());
        setRegistryName(name);

    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        Spread(worldIn, pos);
        super.animateTick(stateIn, worldIn, pos, rand);
    }


    static void Spread(World worldIn, BlockPos pos) {
        if (new Random().nextInt(1200) < 2){
            BlockPos postition = pos;
            switch (new Random().nextInt(6)){
                case 0:
                    postition = pos.down();
                    break;
                case 1:
                    postition = pos.up();
                    break;
                case 2:
                    postition = pos.south();
                    break;
                case 3:
                    postition = pos.north();
                    break;
                case 4:
                    postition = pos.west();
                    break;
                case 5:
                    postition = pos.east();
                    break;
            }

            transform(worldIn,postition);


        }
    }

    public static void transform(World worldIn, BlockPos postition) {
        if (worldIn.getBlockState(postition).getBlock() == Blocks.GRASS_BLOCK){
            System.out.println("grass");
            worldIn.setBlockState(postition, ModBlocks.dfGrass.getDefaultState());
            particle(postition);
        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.DIRT){
            System.out.println("dirt");
            worldIn.setBlockState(postition, ModBlocks.dfDirt.getDefaultState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.COBBLESTONE){
            System.out.println("cobble");
            worldIn.setBlockState(postition, ModBlocks.dfCobble.getDefaultState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.OAK_LOG){
            System.out.println("oak");
            worldIn.setBlockState(postition, ModBlocks.dfOakLog.getDefaultState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.OAK_PLANKS){
            System.out.println("plank");
            worldIn.setBlockState(postition, ModBlocks.dfOakPlanks.getDefaultState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.OAK_LEAVES){
            System.out.println("leaves");
            worldIn.setBlockState(postition, ModBlocks.dfOakLeave.getDefaultState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.OAK_SLAB){
            System.out.println("slab");
            worldIn.setBlockState(postition, ModBlocks.dfOakSlabs.getDefaultState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.STRIPPED_OAK_LOG){
            System.out.println("stripped");
            worldIn.setBlockState(postition, ModBlocks.dfOakStrippedLog.getDefaultState());
            particle(postition);
        }
    }


        static void particle(BlockPos pos){
        int multiplier = 0;
        for (int i =0; i< 16; i++){
            Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5f-(new Random().nextFloat()-0.5),pos.getY()+1,pos.getZ()+0.5f-(new Random().nextFloat()-0.5),new Random().nextFloat()*multiplier,new Random().nextFloat()*multiplier,new Random().nextFloat()*multiplier);
        }



    }
}
