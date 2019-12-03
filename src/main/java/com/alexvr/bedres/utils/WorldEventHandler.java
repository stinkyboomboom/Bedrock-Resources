package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.Config;
import com.alexvr.bedres.blocks.decayingfluxedblocks.DFBase;
import com.alexvr.bedres.blocks.tiles.EnderianRitualPedestalTile;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.capability.bedrock_flux.BedrockFluxProvider;
import com.alexvr.bedres.capability.bedrock_flux.IBedrockFlux;
import com.alexvr.bedres.gui.FluxOracleScreen;
import com.alexvr.bedres.gui.FluxOracleScreenGui;
import com.alexvr.bedres.items.FluxOracle;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModSounds;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static com.alexvr.bedres.utils.KeyBindings.toggleOverlay;

@EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT)
public class WorldEventHandler {

    public static final int ticksPerItemRitual = Config.RITUAL_TICKS_PER_ITEM.get();
    public static FluxOracleScreenGui fxG = new FluxOracleScreenGui();
    static Minecraft mc = Minecraft.getInstance();
    public static boolean displayOverLay = true;



    @SubscribeEvent
    public static void onPlayerLogsIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
        bedrockFlux.ifPresent(flux -> {
            if (flux.getCrafterFlux()){
                BedrockResources.proxy.getMinecraft().ingameGUI=flux.getScreen();
            }
        });
    }

    static ArrayList RECEPI = RitualCrafting.generateRecipes();

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event){
        BlockPos playerPos = new BlockPos(event.getEntityLiving().posX,event.getEntityLiving().posY,event.getEntityLiving().posZ);
        LazyOptional<IPlayerAbility> abilities = event.getEntityLiving().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(iPlayerAbility -> {
            if (!iPlayerAbility.getChecking() && !iPlayerAbility.getInRitual()){
                if (event.getEntityLiving() instanceof PlayerEntity && event.getSource() == DamageSource.IN_FIRE && event.getEntityLiving().world.getBlockState(playerPos.offset(Direction.DOWN)).getBlock() == ModBlocks.enderianBlock){
                    ArrayList<EnderianRitualPedestalTile> listOfTIles;
                    iPlayerAbility.flipChecking();
                    RitualCrafting.generateRecipes();
                    for (int i =0;i<RECEPI.size();i++) {
                        //System.out.println("Crafting: " + RECEPI.get(i).toString());
                        //System.out.println((((ArrayList)RECEPI.get(i)).get(1)).toString());

                        listOfTIles = new ArrayList<>();
                        boolean skip = false;
                        for (int x = -3; x < 4; x++) {
                            if (skip){
                                break;
                            }
                            for (int y = -3; y < 4; y++) {
                                if (x==0&&y==0){
                                    continue;
                                }
                                //System.out.println((((ArrayList)RECEPI.get(i)).get(1)).toString());
                                Character key = ((String)((ArrayList)((ArrayList)RECEPI.get(i)).get(1)).get(x+3)).charAt(y+3);
                                //System.out.println("Character Analyzed: " + key);

                                if (key == ' '){
                                    //System.out.println("Spaced Detected at: " + playerPos.east(x).south(y).toString());

                                    if (event.getEntityLiving().world.getBlockState(playerPos.east(x).south(y).down()).getBlock() != ModBlocks.enderianBrick ){
                                        //System.out.println("Error: No Brick at: " + playerPos.east(x).south(y).down().toString());

                                        skip =true;
                                        break;
                                    }
                                    continue;
                                }
                                ItemStack stack = ItemStack.EMPTY;
                                for (int j =0;j<((ArrayList)((ArrayList)RECEPI.get(i)).get(2)).size();j++) {
                                    Character value = ((String)((ArrayList)((ArrayList)RECEPI.get(i)).get(2)).get(j)).charAt(0);
                                    //System.out.println("Value Analyzed: " + value);

                                    if (key == value){
                                        String ss = ((String)((ArrayList)((ArrayList)RECEPI.get(i)).get(2)).get(j)).substring(2);
                                        ResourceLocation locatoin = new ResourceLocation(ss);
                                        //System.out.println("Name to look for: " + ss);
                                        //System.out.println("Resourse Location: " + locatoin.toString());
                                        stack = new ItemStack( ForgeRegistries.ITEMS.getValue(locatoin));
                                        //System.out.println("Stack Found: " + stack.toString());

                                        break;
                                    }
                                }
                                if (stack == ItemStack.EMPTY || stack.getItem().getRegistryName().equals(ItemStack.EMPTY.getItem().getRegistryName())){
                                    //System.out.println("Error: No stack to match: " + key);

                                    skip =true;
                                    break;
                                }
                                if (stack.getItem().getRegistryName().equals(new ItemStack((ModBlocks.bedrockWire)).getItem().getRegistryName()) &&event.getEntityLiving().world.getBlockState(playerPos.east(x).south(y)).getBlock() != ModBlocks.bedrockWire) {
                                    //System.out.println("Error: No Wire at: " + playerPos.east(x).south(y).toString());

                                    skip = true;
                                    break;
                                }
                                if (!stack.getItem().getRegistryName().equals(new ItemStack((ModBlocks.bedrockWire)).getItem().getRegistryName()) &&event.getEntityLiving().world.getBlockState(playerPos.east(x).south(y)).getBlock() != ModBlocks.enderianRitualPedestal) {
                                    //System.out.println("Error: No Pedestal at: " + playerPos.east(x).south(y).toString());
                                    skip = true;
                                    break;
                                }else if (!stack.getItem().getRegistryName().equals(new ItemStack((ModBlocks.bedrockWire)).getItem().getRegistryName()) &&event.getEntityLiving().world.getBlockState(playerPos.east(x).south(y)).getBlock() == ModBlocks.enderianRitualPedestal) {

                                    if (event.getEntityLiving().world.getTileEntity(playerPos.east(x).south(y)) instanceof EnderianRitualPedestalTile && !((EnderianRitualPedestalTile)event.getEntityLiving().world.getTileEntity(playerPos.east(x).south(y))).item.equals(stack.getItem().getRegistryName().toString())){
                                        //System.out.println("Error: Wrong Item in pedestal at: " + playerPos.east(x).south(y).toString() + " | looking for: " + stack.getItem().getRegistryName().toString() + " and found: " + ((EnderianRitualPedestalTile)event.getEntityLiving().world.getTileEntity(playerPos.east(x).south(y))).item);

                                        skip = true;
                                        break;
                                    }else{
                                        //System.out.println("Added Pedestal at: " + playerPos.east(x).south(y).toString() + " with correct item");

                                        listOfTIles.add(((EnderianRitualPedestalTile)event.getEntityLiving().world.getTileEntity(playerPos.east(x).south(y))));
                                    }
                                }
                            }
                        }
                        //System.out.println();
                        //System.out.println();

                        if(!skip){
                            //System.out.println("Success: Crafted: " + (((ArrayList)RECEPI.get(i)).get(0)));
                            //System.out.println();
                            //System.out.println();
                            event.getEntityLiving().world.setBlockState(playerPos,Blocks.AIR.getDefaultState());
                            iPlayerAbility.setRitualCraftingResult((String)(((ArrayList)RECEPI.get(i)).get(0)));
                            iPlayerAbility.setRitualPedestals(listOfTIles);
                            iPlayerAbility.flipRitual();
                            iPlayerAbility.setRitualTotalTimer(listOfTIles.size()* ticksPerItemRitual);
                            iPlayerAbility.setFOV(Minecraft.getInstance().gameSettings.fov);
                            event.getEntityLiving().lookAt(EntityAnchorArgument.Type.EYES,new Vec3d(.999,event.getEntityLiving().getLookVec().y+6,-0.999));
                            event.getEntityLiving().setFire(0);
                            ModSounds.RITUAL_AMBIENT.playSound(1,3);
                            iPlayerAbility.setLookPos(new Vec3d(listOfTIles.get(0).getPos().getX(),listOfTIles.get(0).getPos().getY()+2,listOfTIles.get(0).getPos().getZ()));
                            event.setCanceled(true);
                        }
                    }
                    iPlayerAbility.flipChecking();
                }
            }
        });
    }

    public static float checkForMin(String tool,IPlayerAbility ability,IBedrockFlux flux){
        float min =0;
        String tier = "no";
        switch (tool){
            case "pick":
                tier = ability.getPick();
                break;
            case "axe":
                tier = ability.getAxe();
                break;
            case "shovel":
                tier = ability.getShovel();
                break;
            case "sword":
                tier = ability.getSword();
                break;
        }
        switch (tier){
            case "wood":
                min = 45;
                break;
            case "stone":
                min = 85;
                break;
            case "iron":
                min = 145;
                break;
            case "golden":
                min = 245;
                break;
            case "diamond":
                min = 400;
                break;
            default:
                min=0;
                break;
        }
        return min;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if (player.world.isRemote) return;
        if (toggleOverlay.isPressed()) {
            WorldEventHandler.displayOverLay =! WorldEventHandler.displayOverLay;
        }
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(iPlayerAbility -> {
            if (iPlayerAbility.getInRitual()){
                KeyBinding.unPressAllKeys();
                player.setVelocity(0,0,0);
                player.setJumping(false);
                player.addPotionEffect(new EffectInstance(Effects.SLOWNESS,1,999));
                Minecraft.getInstance().gameSettings.thirdPersonView = 2;
                Minecraft.getInstance().gameSettings.hideGUI = true;
                Minecraft.getInstance().gameSettings.fov = 195;
                Minecraft.getInstance().gameSettings.mouseSensitivity = -1F/3F;
                BlockPos thisblock;
                if (iPlayerAbility.getListOfPedestals().size()>0) {
                    thisblock = iPlayerAbility.getListOfPedestals().get(0).getPos();
                }else{
                    thisblock = player.getPosition().offset(Direction.NORTH).north().east(2);
                }

                if (iPlayerAbility.getListOfPedestals().size()>1){
                    BlockPos nextblock = iPlayerAbility.getListOfPedestals().get(1).getPos();
                    double xDif =((thisblock.getX()-nextblock.getX())/(ticksPerItemRitual+0.0))  ;
                    double zDif = ((thisblock.getZ()-nextblock.getZ())/(ticksPerItemRitual+0.0))  ;
                    double speed = ((0.1 * iPlayerAbility.getRitualTimer()%(ticksPerItemRitual/2.0)) /10);
                    if (xDif >= 0) {
                        iPlayerAbility.setLookPos(iPlayerAbility.getlookPos().add(speed, 0, 0));
                    } else {
                        iPlayerAbility.setLookPos(iPlayerAbility.getlookPos().add(-speed, 0, 0));
                    }

                    if (zDif >= 0) {
                        iPlayerAbility.setLookPos(iPlayerAbility.getlookPos().add(0, 0, speed));
                    } else {
                        iPlayerAbility.setLookPos(iPlayerAbility.getlookPos().add(0, 0, -speed));
                    }
                    player.lookAt(EntityAnchorArgument.Type.EYES,iPlayerAbility.getlookPos());
                }else{
                    BlockPos nextblock = player.getPosition().west(2).north(3);
                    double xDif =((thisblock.getX()-nextblock.getX())/(ticksPerItemRitual+0.0))  ;
                    double zDif = ((thisblock.getZ()-nextblock.getZ())/(ticksPerItemRitual+0.0))  ;
                    double speed = ((0.1 * iPlayerAbility.getRitualTimer()%(ticksPerItemRitual/2.0)) /10);
                    if (xDif >= 0) {
                        iPlayerAbility.setLookPos(iPlayerAbility.getlookPos().add(speed, 0, 0));
                    } else {
                        iPlayerAbility.setLookPos(iPlayerAbility.getlookPos().add(-speed, 0, 0));
                    }

                    if (zDif >= 0) {
                        iPlayerAbility.setLookPos(iPlayerAbility.getlookPos().add(0, 0, speed));
                    } else {
                        iPlayerAbility.setLookPos(iPlayerAbility.getlookPos().add(0, 0, -speed));
                    }
                    player.lookAt(EntityAnchorArgument.Type.EYES,iPlayerAbility.getlookPos());
                }

                BlockPos particlePos;
                if (iPlayerAbility.getListOfPedestals().size()>0) {
                    particlePos = iPlayerAbility.getListOfPedestals().get(0).getPos();
                }else{
                    particlePos = player.getPosition();
                }
                Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.PORTAL,false,(double)particlePos.getX()+0.5,(double)particlePos.getY()+.8,(double)particlePos.getZ()+.5,(new Random().nextFloat()-0.7),new Random().nextFloat()-0.7,new Random().nextFloat()-0.7);
                if (iPlayerAbility.getRitualTimer()%(ticksPerItemRitual+0.0) == 0){
                    iPlayerAbility.getListOfPedestals().get(0).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                        if (h.getStackInSlot(0) != ItemStack.EMPTY) {
                            if (h.getStackInSlot(0).getItem() == Items.WATER_BUCKET){
                                h.extractItem(0,1, false);
                                h.insertItem(0,new ItemStack(Items.BUCKET),false);
                            }else if (h.getStackInSlot(0).getItem() == Items.BUCKET && iPlayerAbility.getRitualCraftingResult().contains("clearrainUpgrade")){
                                h.extractItem(0,1, false);
                                h.insertItem(0,new ItemStack(Items.WATER_BUCKET),false);
                            }else if (h.getStackInSlot(0).getItem() == Items.DRIED_KELP && iPlayerAbility.getRitualCraftingResult().contains("clearrainUpgrade")){
                                h.extractItem(0,1, false);
                                h.insertItem(0,new ItemStack(Items.KELP),false);
                            }else if (h.getStackInSlot(0).getItem() == Items.KELP && iPlayerAbility.getRitualCraftingResult().contains("rainUpgrade")){
                                h.extractItem(0,1, false);
                                h.insertItem(0,new ItemStack(Items.DRIED_KELP),false);
                            }else {
                                h.extractItem(0, 1, false);
                            }
                            ( iPlayerAbility.getListOfPedestals().get(0)).markDirty();
                            ( iPlayerAbility.getListOfPedestals().get(0)).sendUpdates();
                        }
                    });
                    iPlayerAbility.getListOfPedestals().remove(0);
                }
                if (iPlayerAbility.getRitualTimer()>=iPlayerAbility.getRitualTotalTimer()){
                    BlockPos playerPos = new BlockPos(event.player.posX,event.player.posY,event.player.posZ);

                    for (int x = -3; x < 4; x++) {

                        for (int y = -3; y < 4; y++) {
                            if (event.player.world.getBlockState(playerPos.east(x).south(y)).getBlock() == ModBlocks.bedrockWire){
                                player.world.setBlockState(playerPos.east(x).south(y),Blocks.FIRE.getDefaultState());
                            }
                        }
                    }
                    Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.PORTAL,false,(double)player.posX,(double)player.posY+3,(double)player.posZ,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5);
                    Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.PORTAL,false,(double)player.posX,(double)player.posY+3,(double)player.posZ,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5);
                    Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.PORTAL,false,(double)player.posX,(double)player.posY+3,(double)player.posZ,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5);
                    iPlayerAbility.flipRitual();
                    Minecraft.getInstance().gameSettings.mouseSensitivity = 0.5D;
                    event.player.world.addEntity(new LightningBoltEntity(event.player.world,player.posX,player.posY,player.posZ,true));
                    Minecraft.getInstance().gameSettings.thirdPersonView = 0;
                    Minecraft.getInstance().gameSettings.hideGUI = false;
                    Minecraft.getInstance().gameSettings.fov = iPlayerAbility.getFOV();
                    ModSounds.RITUAL_AMBIENT.stopSound();
                    LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
                    bedrockFlux.ifPresent(flux -> {
                        if (iPlayerAbility.getRitualCraftingResult().contains("Upgrade")) {
                            if (iPlayerAbility.getRitualCraftingResult().contains("stickUpgrade")) {
                                iPlayerAbility.clear();
                            }else if (iPlayerAbility.getRitualCraftingResult().contains("dayUpgrade")) {
                                for(ServerWorld serverworld :  event.player.getServer().getWorlds()) {
                                    serverworld.setDayTime(1000);
                                }
                                flux.fill(10);
                            }else if (iPlayerAbility.getRitualCraftingResult().contains("nightUpgrade")) {
                                for(ServerWorld serverworld :  event.player.getServer().getWorlds()) {
                                    serverworld.setDayTime(13000);
                                }
                                flux.fill(10);
                            }else if (iPlayerAbility.getRitualCraftingResult().contains("clearrainUpgrade")) {
                                event.player.world.getWorldInfo().setRaining(false);
                            }else if (iPlayerAbility.getRitualCraftingResult().contains("rainUpgrade")) {
                                event.player.world.getWorldInfo().setRaining(true);
                                flux.fill(10);
                            }else if (iPlayerAbility.getRitualCraftingResult().contains("pickUpgrade")) {
                                if (checkForMin("pick",iPlayerAbility,flux) >= 0){
                                    flux.consumeMin(checkForMin("pick",iPlayerAbility,flux));
                                }
                                if (iPlayerAbility.getRitualCraftingResult().contains("wood")) {
                                    if (iPlayerAbility.getPick().equals("wood")){
                                        iPlayerAbility.setPick("no");
                                    }else {
                                        iPlayerAbility.setPick("wood");
                                        flux.fillMin(45);
                                        flux.fill(45);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("stone")) {
                                    if (iPlayerAbility.getPick().equals("stone")){
                                        iPlayerAbility.setPick("no");
                                    }else {
                                        flux.fillMin(85);
                                        iPlayerAbility.setPick("stone");
                                        flux.fill(85);
                                    }
                                }  else if (iPlayerAbility.getRitualCraftingResult().contains("iron")) {
                                    if (iPlayerAbility.getPick().equals("iron")){
                                        iPlayerAbility.setPick("no");
                                    }else {
                                        flux.fillMin(145);
                                        iPlayerAbility.setPick("iron");
                                        flux.fill(145);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("gold")) {
                                    if (iPlayerAbility.getPick().equals("golden")){
                                        iPlayerAbility.setPick("no");
                                    }else {
                                        flux.fillMin(245);
                                        iPlayerAbility.setPick("golden");
                                        flux.fill(245);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("diamond")) {
                                    if (iPlayerAbility.getPick().equals("diamond")){
                                        iPlayerAbility.setPick("no");
                                    }else {
                                        flux.fillMin(400);
                                        iPlayerAbility.setPick("diamond");
                                        flux.fill(400);
                                    }
                                }

                            } else if (iPlayerAbility.getRitualCraftingResult().contains("axeUpgrade")) {
                                if (checkForMin("axe",iPlayerAbility,flux) >= 0){
                                    flux.consumeMin(checkForMin("axe",iPlayerAbility,flux));
                                }
                                if (iPlayerAbility.getRitualCraftingResult().contains("wood")) {
                                    if (iPlayerAbility.getAxe().equals("wood")){
                                        iPlayerAbility.setAxe("no");
                                    }else {
                                        flux.fillMin(45);
                                        iPlayerAbility.setAxe("wood");
                                        flux.fill(45);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("stone")) {
                                   if (iPlayerAbility.getAxe().equals("stone")){
                                        iPlayerAbility.setAxe("no");
                                    }else {
                                       flux.fillMin(85);
                                       iPlayerAbility.setAxe("stone");
                                       flux.fill(85);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("iron")) {
                                   if (iPlayerAbility.getAxe().equals("iron")){
                                        iPlayerAbility.setAxe("no");
                                    }else {
                                       flux.fillMin(145);
                                       iPlayerAbility.setAxe("iron");
                                       flux.fill(145);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("gold")) {
                                   if (iPlayerAbility.getAxe().equals("golden")){
                                        iPlayerAbility.setAxe("no");
                                    }else {
                                       flux.fillMin(245);
                                       iPlayerAbility.setAxe("golden");
                                       flux.fill(245);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("diamond")) {
                                   if (iPlayerAbility.getAxe().equals("diamond")){
                                        iPlayerAbility.setAxe("no");
                                    }else {
                                       flux.fillMin(400);
                                       iPlayerAbility.setAxe("diamond");
                                       flux.fill(400);
                                    }
                                }
                            } else if (iPlayerAbility.getRitualCraftingResult().contains("shovelUpgrade")) {
                                if (checkForMin("shovel",iPlayerAbility,flux) >= 0){
                                    flux.consumeMin(checkForMin("shovel",iPlayerAbility,flux));
                                }
                                if (iPlayerAbility.getRitualCraftingResult().contains("wood")) {
                                   if (iPlayerAbility.getShovel().equals("wood")){
                                        iPlayerAbility.setShovel("no");
                                    }else {


                                    }
                                    flux.fillMin(45);
                                    iPlayerAbility.setShovel("wood");
                                    flux.fill(45);
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("stone")) {
                                    if (iPlayerAbility.getShovel().equals("stone")){
                                        iPlayerAbility.setShovel("no");
                                    }else {
                                        flux.fillMin(85);
                                        iPlayerAbility.setShovel("stone");
                                        flux.fill(85);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("iron")) {
                                   if (iPlayerAbility.getShovel().equals("iron")){
                                        iPlayerAbility.setShovel("no");
                                    }else {
                                       flux.fillMin(145);
                                       iPlayerAbility.setShovel("iron");
                                       flux.fill(145);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("gold")) {
                                   if (iPlayerAbility.getShovel().equals("golden")){
                                        iPlayerAbility.setShovel("no");
                                    }else {
                                       flux.fillMin(245);
                                       iPlayerAbility.setShovel("golden");
                                       flux.fill(245);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("diamond")) {
                                   if (iPlayerAbility.getShovel().equals("diamond")){
                                        iPlayerAbility.setShovel("no");
                                    }else {
                                       flux.fillMin(400);
                                       iPlayerAbility.setShovel("diamond");
                                       flux.fill(400);
                                    }
                                }
                            } else if (iPlayerAbility.getRitualCraftingResult().contains("swordUpgrade")) {
                                if (checkForMin("sword",iPlayerAbility,flux) >= 0){
                                    flux.consumeMin(checkForMin("sword",iPlayerAbility,flux));
                                }
                                if (iPlayerAbility.getRitualCraftingResult().contains("wood")) {
                                    if (iPlayerAbility.getSword().equals("wood")){
                                        iPlayerAbility.setSword("no");
                                    }else {
                                        flux.fillMin(45);
                                        iPlayerAbility.setSword("wood");
                                        flux.fill(45);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("stone")) {
                                    if (iPlayerAbility.getSword().equals("stone")){
                                        iPlayerAbility.setSword("no");
                                    }else {
                                        flux.fillMin(85);
                                        iPlayerAbility.setSword("stone");
                                        flux.fill(85);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("iron")) {
                                    if (iPlayerAbility.getSword().equals("iron")){
                                        iPlayerAbility.setSword("no");
                                    }else {
                                        flux.fillMin(145);
                                        iPlayerAbility.setSword("iron");
                                        flux.fill(145);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("gold")) {
                                    if (iPlayerAbility.getSword().equals("golden")){
                                        iPlayerAbility.setSword("no");
                                    }else {
                                        flux.fillMin(245);
                                        iPlayerAbility.setSword("golden");
                                        flux.fill(245);
                                    }
                                } else if (iPlayerAbility.getRitualCraftingResult().contains("diamond")) {
                                    if (iPlayerAbility.getSword().equals("diamond")){
                                        iPlayerAbility.setSword("no");
                                    }else {
                                        flux.fillMin(400);
                                        iPlayerAbility.setSword("diamond");
                                        flux.fill(400);
                                    }
                                }
                            } else if (iPlayerAbility.getRitualCraftingResult().contains("hoeUpgrade")) {
                                if (iPlayerAbility.getHoe().equals("active")){
                                    flux.consumeMin(400);
                                    iPlayerAbility.setHoe("no");

                                }else {
                                    flux.fillMin(400);
                                    iPlayerAbility.setHoe("active");
                                    flux.fill(400);
                                }
                            } else if (iPlayerAbility.getRitualCraftingResult().contains("speed")) {
                                if (iPlayerAbility.getMiningSpeedBoost() < 1.35) {
                                    iPlayerAbility.setMiningSpeedBoost(iPlayerAbility.getMiningSpeedBoost() + .3f);
                                    flux.fillMin((float) (45 + iPlayerAbility.getMiningSpeedBoost()));
                                    flux.fill((float) (45 + iPlayerAbility.getMiningSpeedBoost()));
                                }else{
                                    player.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "Speed already maxed out."), false);
                                }
                            } else if (iPlayerAbility.getRitualCraftingResult().contains("jump")) {
                                if (iPlayerAbility.getJumpBoost()<=0.2) {
                                    if (iPlayerAbility.getJumpBoost()==0){
                                        iPlayerAbility.setJumpBoost(0.015f);
                                    }else if (iPlayerAbility.getJumpBoost()<=0.016){
                                        iPlayerAbility.setJumpBoost(0.08f);
                                    }else if (iPlayerAbility.getJumpBoost()<=0.09){
                                        iPlayerAbility.setJumpBoost(0.15f);
                                    }else if (iPlayerAbility.getJumpBoost()<=0.2){
                                        iPlayerAbility.setJumpBoost(0.25f);
                                    }
                                    flux.fillMin((float) (55 + iPlayerAbility.getJumpBoost()));
                                    flux.fill((float) (55 + iPlayerAbility.getJumpBoost()));
                                }else{
                                    player.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "Jump already maxed out."), false);
                                }
                            }
                            player.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + new TranslationTextComponent("message.bedres.stat_change").getUnformattedComponentText()), true);
                            player.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "Skills is:"), false);
                            player.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Sword", iPlayerAbility.getSword())), false);
                            player.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Axe", iPlayerAbility.getAxe())), false);
                            player.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Shovel", iPlayerAbility.getShovel())), false);
                            player.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Hoe", iPlayerAbility.getHoe())), false);
                            player.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Pickaxe", iPlayerAbility.getPick())), false);
                            player.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "Passive: "), false);
                            player.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Speed", iPlayerAbility.getMiningSpeedBoost())), false);
                            player.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Jump", iPlayerAbility.getJumpBoost())), false);
                            player.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Flux", flux.getBedrockFluxString())), false);
                        } else {
                            InventoryHelper.spawnItemStack(event.player.world, player.posX, player.posY, player.posZ, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(iPlayerAbility.getRitualCraftingResult()))));
                        }
                    });

                    iPlayerAbility.setRitualTimer(1);


                }else{
                    iPlayerAbility.incrementRitualTimer();

                }

            }
        });

        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {
            if (Config.NEG_EFFECTS_ACTIVE.get() && h.getBedrockFlux()>250.32f) {
                h.count();
                if (h.getTimer() >= h.getMaxTimer()) {
                    h.changeMax();
                    int rand = new Random().nextInt(20) + 1;
                    //int rand = 30;
                    if (rand <= 4) {
                        if (h.getBedrockFlux()<400){
                            int choice =  new Random().nextInt(5);
                            switch (choice){
                                case 0:
                                    player.setFire(40);
                                    break;
                                case 1:
                                    player.addPotionEffect(new EffectInstance(Effects.LEVITATION,40,2));
                                    break;
                                case 2:
                                    if (player.getRidingEntity() != null){
                                        player.stopRiding();
                                    }
                                    player.dropItem(player.getHeldItemMainhand(),false);
                                    player.getHeldItemMainhand().shrink(player.getHeldItemMainhand().getCount());
                                    player.jump();
                                    break;
                                case 3:
                                    player.addPotionEffect(new EffectInstance(Effects.BLINDNESS,100,2));
                                    break;
                                case 4:
                                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS,100,2));
                                    break;
                            }

                        }else if (h.getBedrockFlux()>=400 && h.getBedrockFlux()<950){
                            int choice =  new Random().nextInt(5);

                            switch (choice){
                                case 0:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.fluxedSpores.getDefaultState());
                                    break;
                                case 1:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.fluxedSpores.getDefaultState());
                                    break;
                                case 2:
                                    event.player.world.setBlockState(player.getPosition(),Blocks.FIRE.getDefaultState());
                                    transformArea(event.player.world,event.player.getPosition(),5);

                                    break;
                                case 3:
                                    player.addPotionEffect(new EffectInstance(Effects.POISON,160,3));
                                    break;
                                case 4:
                                    player.addPotionEffect(new EffectInstance(Effects.INSTANT_DAMAGE,2,1));
                                    player.addPotionEffect(new EffectInstance(Effects.HUNGER,60,2));
                                    break;
                            }

                        }else if (h.getBedrockFlux()>=950 && h.getBedrockFlux()<1350){
                            int choice =  new Random().nextInt(7);
                            switch (choice){
                                case 0:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.fluxedSpores.getDefaultState());
                                    break;
                                case 1:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.fluxedSpores.getDefaultState());
                                    break;
                                case 2:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.dfDirt.getDefaultState());
                                    break;
                                case 3:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.dfGrass.getDefaultState());
                                    break;
                                case 4:
                                    transformArea(event.player.world,event.player.getPosition(),10);
                                    break;
                                case 5:
                                    player.addPotionEffect(new EffectInstance(Effects.BAD_OMEN,999,2));
                                    break;
                                case 6:
                                    player.addPotionEffect(new EffectInstance(Effects.UNLUCK,120,4));
                                    break;
                                case 7:
                                    player.addPotionEffect(new EffectInstance(Effects.INSTANT_DAMAGE,2,1));
                                    player.addPotionEffect(new EffectInstance(Effects.HUNGER,85,3));
                                    break;
                            }
                        }else if (h.getBedrockFlux()>=1350 && h.getBedrockFlux()<1800){
                            int choice =  new Random().nextInt(9);

                            switch (choice){
                                case 0:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.fluxedSpores.getDefaultState());
                                    break;
                                case 1:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.fluxedSpores.getDefaultState());
                                    break;
                                case 2:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.dfDirt.getDefaultState());
                                    break;
                                case 3:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.dfGrass.getDefaultState());
                                    break;
                                case 4:
                                    transformArea(event.player.world,event.player.getPosition(),15);
                                    break;
                                case 5:
                                    player.addPotionEffect(new EffectInstance(Effects.BAD_OMEN,999,3));
                                    break;
                                case 6:
                                    player.addPotionEffect(new EffectInstance(Effects.POISON,80,4));
                                    break;
                                case 7:
                                    player.addPotionEffect(new EffectInstance(Effects.LEVITATION,80,4));
                                    break;
                                case 8:
                                    player.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE,80,4));
                                    break;
                            }
                        }else if (h.getBedrockFlux()>=1800 && h.getBedrockFlux()<h.getMaxBedrockFlux()){
                            int choice =  new Random().nextInt(10);

                            switch (choice){
                                case 0:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.fluxedSpores.getDefaultState());
                                    break;
                                case 1:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.fluxedSpores.getDefaultState());
                                    break;
                                case 2:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.dfDirt.getDefaultState());
                                    break;
                                case 3:
                                    event.player.world.setBlockState(player.getPosition(),ModBlocks.dfGrass.getDefaultState());
                                    break;
                                case 4:
                                    transformArea(event.player.world,event.player.getPosition(),20);
                                    break;
                                case 5:
                                    player.addPotionEffect(new EffectInstance(Effects.BAD_OMEN,999,5));
                                    break;
                                case 6:
                                    player.addPotionEffect(new EffectInstance(Effects.WITHER,120,3));
                                    break;
                                case 7:
                                    player.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE,120,3));
                                    break;
                                case 8:
                                    player.addPotionEffect(new EffectInstance(Effects.HUNGER,120,4));
                                    break;
                                case 9:
                                    player.addPotionEffect(new EffectInstance(Effects.NAUSEA,120,4));
                                    break;
                            }
                        }
                        String message = ("You dont feel well");
                        player.sendStatusMessage( new StringTextComponent(TextFormatting.RED + message), true);
                    }
                }
            }
        });

    }

    private static void transformArea(World world,BlockPos pos, int radius){
        for (int x =-radius;x<=radius;x++){
            for (int y =-radius;y<=radius;y++){
                for (int z =-radius;z<=radius;z++){
                    BlockPos position =(pos.north(x).up(y).west(z));
                    DFBase.transform(world,position);
                }
            }
        }
    }


    /**
     * Cancel the FOV decrease caused by the decreasing speed due to player penalties.
     * Original FOV value given by the event is never used, we start from scratch 1.0F value.
     * Edited from AbstractClientPlayer.getFovModifier()
     * @param event
     */
    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent event) {
        PlayerEntity player = event.getEntity();
        float modifier = 0;

        float f = 1.0F;


        IAttributeInstance iattributeinstance = player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        double oldAttributeValue = iattributeinstance.getValue() / modifier;
        f = (float)((double)f * ((oldAttributeValue / (double)player.getAIMoveSpeed() + 1.0D) / 2.0D));

        if (player.getAIMoveSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f))
        {
            f = 1.0F;
        }

        if (player.isHandActive() && player.getActiveItemStack() != null && player.getActiveItemStack().getItem() == Items.BOW)
        {
            int i = player.getItemInUseMaxCount();
            float f1 = (float)i / 20.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }
            else
            {
                f1 = f1 * f1;
            }

            f *= 1.0F - f1 * 0.15F;
        }

        event.setNewfov(f);
    }

    @SubscribeEvent
    public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event){
        if (event.getCrafting().getItem() instanceof FluxOracle && !event.getPlayer().world.isRemote){
            PlayerEntity player = event.getPlayer();
            LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
            FluxOracleScreen fx = new FluxOracleScreen();
            getFluxScreen(player, bedrockFlux, fx);
        }
    }

    public static void getFluxScreen(PlayerEntity player, LazyOptional<IBedrockFlux> bedrockFlux, FluxOracleScreen fx) {
        bedrockFlux.ifPresent(h -> {
            h.setCrafterFlux();
            fx.flux = h;
            h.setScreen(fx);
            BedrockResources.proxy.getMinecraft().ingameGUI=fx;
            String message = ("You out of nowhere understand flux and can sense the amount of flux on you");
            player.sendStatusMessage(new StringTextComponent(message),true);
        });
    }

    @SubscribeEvent
    public static void PlayerBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        PlayerEntity player = event.getPlayer();
        if (!player.isSneaking()) {
            LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                if (!(event.getPlayer().getHeldItemMainhand().getItem() instanceof SwordItem) &&
                        !(event.getPlayer().getHeldItemMainhand().getItem() instanceof AxeItem) &&
                        !(event.getPlayer().getHeldItemMainhand().getItem() instanceof ShovelItem) &&
                        !(event.getPlayer().getHeldItemMainhand().getItem() instanceof PickaxeItem) &&
                        !(event.getPlayer().getHeldItemMainhand().getItem() instanceof HoeItem)) {
                    float speeed = 0;
                    if (event.getState().getHarvestTool() == null) {
                        if (event.getState().getMaterial().toString().equals(Material.WOOD.toString())) {
                            speeed = getSpeed(h.getAxe());
                        } else if ((event.getState().getMaterial() == Material.ROCK || event.getState().getMaterial() == Material.IRON || event.getState().getMaterial() == Material.ANVIL)) {
                            speeed = getSpeed(h.getPick());
                        } else if ((event.getState().getMaterial() == Material.EARTH || event.getState().getMaterial() == Material.SAND || event.getState().getMaterial() == Material.SNOW || event.getState().getMaterial() == Material.CLAY || event.getState().getMaterial() == Material.ORGANIC)) {
                            speeed = getSpeed(h.getShovel());
                        } else if ((event.getState().getMaterial() == Material.WEB || event.getState().getMaterial() == Material.LEAVES || event.getState().getMaterial() == Material.WOOL)) {
                            speeed = getSpeed(h.getSword());
                        }
                    } else {
                        if (event.getState().getHarvestTool().toString().equals(net.minecraftforge.common.ToolType.PICKAXE.toString())) {
                            speeed = getSpeed(h.getPick());
                        } else if (event.getState().getHarvestTool().toString().equals(ToolType.SHOVEL.toString())) {
                            speeed = getSpeed(h.getShovel());

                        } else if (event.getState().getHarvestTool().toString().equals(ToolType.AXE.toString())) {
                            speeed = getSpeed(h.getAxe());
                        }
                    }
                    speeed += 1;
                    speeed *= 1.55;
                    if (event.getState().getBlock() == Blocks.STONE) {
                        speeed += 5.5;
                    }
                    if ((event.getState().getMaterial().isToolNotRequired() || event.getState().getBlock() == Blocks.STONE) && speeed > 0) {
                        event.setNewSpeed((float) (event.getOriginalSpeed() + h.getMiningSpeedBoost() + speeed));

                    } else if (speeed > 0) {
                        event.setNewSpeed((float) ((event.getOriginalSpeed() + h.getMiningSpeedBoost() + speeed) * (100.0 / 30.0)));

                    }

                } else if (h.getMiningSpeedBoost() > 0) {
                    event.setNewSpeed((float) (event.getOriginalSpeed() + h.getMiningSpeedBoost()));
                }
            });
        }
    }

    private static float getSpeed(String material) {
        switch (material) {
            case "wood":
                return ItemTier.WOOD.getEfficiency();
            case "stone":
                return  ItemTier.STONE.getEfficiency();
            case "iron":
                return ItemTier.IRON.getEfficiency();
            case "golden":
                return ItemTier.GOLD.getEfficiency();
            case "diamond":
                return  ItemTier.DIAMOND.getEfficiency();
        }
        return 0;
    }

    private static int getharvestLevel(String material){
        switch (material) {
            case "wood":
                return ItemTier.WOOD.getHarvestLevel();
            case "stone":
                return  ItemTier.STONE.getHarvestLevel();
            case "iron":
                return ItemTier.IRON.getHarvestLevel();
            case "golden":
                return ItemTier.GOLD.getHarvestLevel();
            case "diamond":
                return  ItemTier.DIAMOND.getHarvestLevel();
        }
        return -1;
    }

    @SubscribeEvent
    public static void PlayerBreakBlockEvent(PlayerInteractEvent.HarvestCheck event) {
        if (!(event.getPlayer().getHeldItemMainhand().getItem() instanceof SwordItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof AxeItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof ShovelItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof PickaxeItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof HoeItem) &&
                event.getTargetBlock().getHarvestTool() != null) {
            LazyOptional<IPlayerAbility> abilities = event.getPlayer().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                boolean flag = false;
                Block block = event.getTargetBlock().getBlock();
                if (event.getTargetBlock().getHarvestTool().toString().equals(net.minecraftforge.common.ToolType.PICKAXE.toString()) &&  (!h.getPick().equals("no"))) {
                    int i = getharvestLevel(h.getPick());
                    if (i >= event.getTargetBlock().getHarvestLevel()) {
                        flag = true;
                    } else {

                        Material material = event.getTargetBlock().getMaterial();
                        flag = material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
                    }
                }else if (event.getTargetBlock().getHarvestTool().toString().equals(ToolType.SHOVEL.toString())&&!h.getShovel().equals("no")) {
                    int i = getharvestLevel(h.getShovel());
                    if (i >= event.getTargetBlock().getHarvestLevel()) {
                        flag = true;
                    }
                    if (!flag){
                        flag= block == Blocks.SNOW || block == Blocks.SNOW_BLOCK;
                    }
                }else if (event.getTargetBlock().getHarvestTool().toString().equals(ToolType.AXE.toString()) && (!h.getAxe().equals("no"))) {
                    int i = getharvestLevel(h.getAxe());
                    if (i >= event.getTargetBlock().getHarvestLevel()) {
                        flag = true;
                    } else{
                        Material material = event.getTargetBlock().getMaterial();
                        flag = material != Material.WOOD && material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.BAMBOO ;
                    }
                }
                if (event.getTargetBlock().getMaterial().isToolNotRequired() || flag) {
                    event.setCanHarvest(true);
                }else{
                    event.setCanHarvest(false);
                }
            });
        }
    }

    private static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(),
            Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(), Blocks.DIRT, Blocks.FARMLAND.getDefaultState(), Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));

    protected static final Map<Block, BlockState> SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));

    @SubscribeEvent
    public static void PlayerRightClickEvent( PlayerInteractEvent.RightClickBlock event) {

        if (!(event.getPlayer().getHeldItemMainhand().getItem() instanceof SwordItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof AxeItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof ShovelItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof PickaxeItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof HoeItem)) {
            LazyOptional<IPlayerAbility> abilities = event.getPlayer().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                if(!h.getHoe().equals("no") && !event.getPlayer().isSneaking()){
                    World world = event.getWorld();
                    BlockPos blockpos = event.getPos();
                    int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(new ItemUseContext(event.getPlayer(),event.getHand(),selectBlock(event.getPlayer())));
                    if (hook != 0){
                        if (hook<0){
                            return;
                        }
                    }
                    if (event.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
                        BlockState blockstate = HOE_LOOKUP.get(world.getBlockState(blockpos).getBlock());
                        if (blockstate != null) {
                            PlayerEntity playerentity = event.getPlayer();
                            world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            if (!world.isRemote) {
                                world.setBlockState(blockpos, blockstate, 11);

                            }
                        }
                    }
                }
                if(h.getShovel().equals("diamond")){
                    World world = event.getWorld();
                    BlockPos blockpos = event.getPos();
                    if (event.getFace() != Direction.DOWN && world.getBlockState(blockpos.up()).isAir(world, blockpos.up()) && event.getPlayer().isSneaking()) {
                        BlockState blockstate = SHOVEL_LOOKUP.get(world.getBlockState(blockpos).getBlock());
                        if (blockstate != null) {
                            PlayerEntity playerentity = event.getPlayer();
                            world.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            if (!world.isRemote) {
                                world.setBlockState(blockpos, blockstate, 11);
                            }
                        }
                    }
                }
            });
        }
    }


    private static BlockRayTraceResult selectBlock(PlayerEntity player) {
        // Used to find which block the player is looking at, and store it in NBT on the tool.
        World world = player.world;
        BlockRayTraceResult lookingAt = VectorHelper.getLookingAt(player, RayTraceContext.FluidMode.NONE);
        if (lookingAt == null || (world.getBlockState(VectorHelper.getLookingAt(player).getPos()) == Blocks.AIR.getDefaultState())) return null;

        BlockState state = world.getBlockState(lookingAt.getPos());

        return lookingAt;
    }

    @SubscribeEvent
    public static void PlayerJumpEvent( PlayerEvent.LivingJumpEvent event) {
        LazyOptional<IPlayerAbility> abilities = event.getEntityLiving().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            if (h.getJumpBoost()>0) {
                event.getEntityLiving().addVelocity(0, (h.getJumpBoost()), 0);
                event.getEntityLiving().velocityChanged = true;
            }
        });


    }

    public static float getAttackBoost(String material){
        switch (material) {
            case "wood":
                return ((SwordItem)Items.WOODEN_SWORD).getAttackDamage();
            case "stone":
                return  ((SwordItem)Items.STONE_SWORD).getAttackDamage();
            case "iron":
                return ((SwordItem)Items.IRON_SWORD).getAttackDamage();
            case "golden":
                return ((SwordItem)Items.GOLDEN_SWORD).getAttackDamage();
            case "diamond":
                return  ((SwordItem)Items.DIAMOND_SWORD).getAttackDamage();
        }
        return 0;
    }

    @SubscribeEvent
    public static void PlayerAttackEvent( LivingHurtEvent event) {

        if (!(event.getEntityLiving() instanceof  PlayerEntity) && event.getSource().getTrueSource() instanceof PlayerEntity) {
            LazyOptional<IPlayerAbility> abilities = event.getSource().getTrueSource().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                if (!h.getSword().equals("no")) {
                    event.setAmount(event.getAmount() + getAttackBoost(h.getSword()));
                }
            });
        }
    }

    /**
     * Copy data from dead player to the new player
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        PlayerEntity player = event.getEntityPlayer();

        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
        LazyOptional<IBedrockFlux> oldbedrockFlux =  event.getOriginal().getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
        bedrockFlux.ifPresent(h -> oldbedrockFlux.ifPresent(o -> h.set(o.getBedrockFlux())));

        LazyOptional<IPlayerAbility> playerAbility = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        LazyOptional<IPlayerAbility> oldplayerAbility =  event.getOriginal().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        playerAbility.ifPresent(h -> oldplayerAbility.ifPresent(o -> {
            h.setJumpBoost(o.getJumpBoost());
            h.setGRavityMultiplier(o.getGravityMultiplier());
            h.setMiningSpeedBoost(o.getMiningSpeedBoost());
            h.setHoe(o.getHoe());
            h.setSword(o.getSword());
            h.setShovel(o.getShovel());
            h.setAxe(o.getAxe());
            h.setPick(o.getPick());
            h.setRitualTimer(0);
            h.setRitualTotalTimer(0);
            h.setRitualPedestals(new ArrayList<>());
            h.setFOV(o.getFOV());
            h.setLookPos(o.getlookPos());
            h.setname(o.getNAme());
        }));

    }
}
