package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.capability.bedrock_flux.BedrockFluxProvider;
import com.alexvr.bedres.capability.bedrock_flux.IBedrockFlux;
import com.alexvr.bedres.gui.FluxOracleScreen;
import com.alexvr.bedres.gui.FluxOracleScreenGui;
import com.alexvr.bedres.items.FluxOracle;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.tiles.EnderianRitualPedestalTile;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT)
public class WorldEventHandler {

    public static FluxOracleScreenGui fxG = new FluxOracleScreenGui();
    static Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    static void renderWorldLastEvent(RenderWorldLastEvent evt) {
        if(mc.player.getHeldItemMainhand().getItem() instanceof FluxOracle && ((FluxOracle)mc.player.getHeldItemMainhand().getItem()).beingUsed) {
            mc.displayGuiScreen(fxG);

        }

    }

    @SubscribeEvent
    public static void onInitGuiEvent(final InitGuiEvent event) {
        final Screen gui = event.getGui();
        if (gui instanceof FluxOracleScreenGui) {

        }
    }

    @SubscribeEvent
    public static void onPlayerLogsIn(PlayerEvent.PlayerLoggedInEvent event)
    {

        PlayerEntity player = event.getPlayer();
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {

            String message = String.format("Hello there, you have %s flux.",h.getBedrockFluxString());
            player.sendStatusMessage(new StringTextComponent(message),false);
            if (h.getCrafterFlux()){
                h.setScreen((FluxOracleScreen)BedrockResources.proxy.getMinecraft().ingameGUI);
                h.getScreen().flux = h;
                BedrockResources.proxy.getMinecraft().ingameGUI=h.getScreen();
            }

        });

        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            String message = String.format("Hello there, your list of skills is: %s sword %s axe %s shovel %s hoe %s pick. And speed of %d and jump of %f",h.getSword(),h.getAxe(),h.getShovel(),h.getHoe(),h.getPick(),h.getMiningSpeedBoost(),h.getJumpBoost());
            player.sendStatusMessage(new StringTextComponent(message),false);

        });


    }

    static ArrayList PICKAXE_UPGRADE = new ArrayList() {{

        add("bedres:pickUpgrade");
        add(new ArrayList<String>() {{

            add("wwwpwww");
            add("w  w  w");
            add("w rwr w");
            add("pww wwp");
            add("w rwr w");
            add("w  w  w");
            add("wwwpwww");

        }});
        add(new ArrayList<String>() {{

            add("w=bedres:bedrock_scrapes");
            add("p=minecraft:diamond_pickaxe");
            add("r=minecraft:redstone");

        }});


    }};

    public static ArrayList RECEPI = new ArrayList(){{

        add(PICKAXE_UPGRADE);

    }};

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event){

        BlockPos playerPos = new BlockPos(event.getEntityLiving().posX,event.getEntityLiving().posY,event.getEntityLiving().posZ);
        System.out.println(playerPos.toString());
        LazyOptional<IPlayerAbility> abilities = event.getEntityLiving().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(iPlayerAbility -> {
            if (!iPlayerAbility.getChecking() && !iPlayerAbility.getInRitual()){
                if (event.getEntityLiving() instanceof PlayerEntity && event.getSource() == DamageSource.IN_FIRE && event.getEntityLiving().world.getBlockState(playerPos.offset(Direction.DOWN)).getBlock() == ModBlocks.enderianBlock){
                    ArrayList<EnderianRitualPedestalTile> listOfTIles;
                    iPlayerAbility.flipChecking();
                    for (int i =0;i<RECEPI.size();i++) {
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
                                Character key = ((String)((ArrayList)((ArrayList)RECEPI.get(i)).get(1)).get(x+3)).charAt(y+3);
                                System.out.println("Character Analyzed: " + key);

                                if (key == ' '){
                                    System.out.println("Spaced Detected at: " + playerPos.east(x).south(y).toString());

                                    if (event.getEntityLiving().world.getBlockState(playerPos.east(x).south(y).down()).getBlock() != ModBlocks.enderianBrick ){
                                        System.out.println("Error: No Brick at: " + playerPos.east(x).south(y).down().toString());

                                        skip =true;
                                        break;
                                    }
                                    continue;
                                }
                                ItemStack stack = ItemStack.EMPTY;
                                for (int j =0;j<((ArrayList)((ArrayList)RECEPI.get(i)).get(2)).size();j++) {
                                    Character value = ((String)((ArrayList)((ArrayList)RECEPI.get(i)).get(2)).get(j)).charAt(0);
                                    System.out.println("Value Analyzed: " + value);

                                    if (key == value){
                                        String ss = ((String)((ArrayList)((ArrayList)RECEPI.get(i)).get(2)).get(j)).substring(2);
                                        ResourceLocation locatoin = new ResourceLocation(ss);
                                        System.out.println("Name to look for: " + ss);
                                        System.out.println("Resourse Location: " + locatoin.toString());
                                        stack = new ItemStack( ForgeRegistries.ITEMS.getValue(locatoin));
                                        System.out.println("Stack Found: " + stack.toString());

                                        break;
                                    }
                                }
                                if (stack == ItemStack.EMPTY || stack.getItem().getRegistryName().equals(ItemStack.EMPTY.getItem().getRegistryName())){
                                    System.out.println("Error: No stack to match: " + key);

                                    skip =true;
                                    break;
                                }
                                if (stack.getItem().getRegistryName().equals(new ItemStack((ModBlocks.bedrockWire)).getItem().getRegistryName()) &&event.getEntityLiving().world.getBlockState(playerPos.east(x).south(y)).getBlock() != ModBlocks.bedrockWire) {
                                    System.out.println("Error: No Wire at: " + playerPos.east(x).south(y).toString());

                                    skip = true;
                                    break;
                                }
                                if (!stack.getItem().getRegistryName().equals(new ItemStack((ModBlocks.bedrockWire)).getItem().getRegistryName()) &&event.getEntityLiving().world.getBlockState(playerPos.east(x).south(y)).getBlock() != ModBlocks.enderianRitualPedestal) {
                                    System.out.println("Error: No Pedestal at: " + playerPos.east(x).south(y).toString());
                                    skip = true;
                                    break;
                                }else if (!stack.getItem().getRegistryName().equals(new ItemStack((ModBlocks.bedrockWire)).getItem().getRegistryName()) &&event.getEntityLiving().world.getBlockState(playerPos.east(x).south(y)).getBlock() == ModBlocks.enderianRitualPedestal) {
                                    if (event.getEntityLiving().world.getTileEntity(playerPos.east(x).south(y)) instanceof EnderianRitualPedestalTile && !((EnderianRitualPedestalTile)event.getEntityLiving().world.getTileEntity(playerPos.east(x).south(y))).item.equals(stack.getItem().getRegistryName().toString())){
                                        System.out.println("Error: Wrong Item in pedestal at: " + playerPos.east(x).south(y).toString() + " | looking for: " + stack.getItem().getRegistryName().toString() + " and found: " + ((EnderianRitualPedestalTile)event.getEntityLiving().world.getTileEntity(playerPos.east(x).south(y))).item);

                                        skip = true;
                                        break;
                                    }else{
                                        System.out.println("Added Pedestal at: " + playerPos.east(x).south(y).toString() + " with correct item");

                                        listOfTIles.add(((EnderianRitualPedestalTile)event.getEntityLiving().world.getTileEntity(playerPos.east(x).south(y))));
                                    }
                                }
                            }
                        }
                        if(!skip){
                            System.out.println("Success: Crafted: " + (((ArrayList)RECEPI.get(i)).get(0)));
                            System.out.println();
                            System.out.println();
                            event.getEntityLiving().world.setBlockState(playerPos,Blocks.AIR.getDefaultState());
                            iPlayerAbility.setRitualCraftingResult((String)(((ArrayList)RECEPI.get(i)).get(0)));
                            iPlayerAbility.setRitualPedestals(listOfTIles);
                            iPlayerAbility.flipRitual();
                            iPlayerAbility.setRitualTotalTimer(listOfTIles.size()*120);
                            iPlayerAbility.setFOV(Minecraft.getInstance().gameSettings.fov);
                            event.getEntityLiving().lookAt(EntityAnchorArgument.Type.EYES,new Vec3d(.999,event.getEntityLiving().getLookVec().y+8,-0.999));
                            event.getEntityLiving().setFire(0);
                            event.getEntityLiving().posY+=1.5;
                            event.getEntityLiving().setNoGravity(true);
                            event.setCanceled(true);
                            System.out.println(event.getEntityLiving().getLookVec().toString());
                        }
                    }
                    iPlayerAbility.flipChecking();
                }
            }
        });
    }


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;

        if (player.world.isRemote) return;
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
                player.lookAt(EntityAnchorArgument.Type.EYES,new Vec3d(iPlayerAbility.getListOfPedestals().get(0).getPos().add(0,3,0)));
                BlockPos particlePos =  iPlayerAbility.getListOfPedestals().get(0).getPos();
                Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.PORTAL,true,(double)particlePos.getX()+0.5,(double)particlePos.getY()+.8,(double)particlePos.getZ()+.5,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5);
                Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.PORTAL,true,(double)player.posX,(double)player.posY+1,(double)player.posZ,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5,new Random().nextFloat()-0.5);
                if (iPlayerAbility.getRitualTimer()%120 == 0){
                    System.out.println(player.getLookVec().toString());
                    iPlayerAbility.getListOfPedestals().remove(0);
                }
                if (iPlayerAbility.getRitualTimer()>=iPlayerAbility.getRitualTotalTimer()){
                    iPlayerAbility.flipRitual();
                    player.posY-=1.5;
                    player.setNoGravity(false);
                    Minecraft.getInstance().gameSettings.mouseSensitivity = 0.5D;
                    event.player.world.addEntity(new LightningBoltEntity(event.player.world,player.posX,player.posY,player.posZ,true));
                    Minecraft.getInstance().gameSettings.thirdPersonView = 0;
                    Minecraft.getInstance().gameSettings.hideGUI = false;
                    Minecraft.getInstance().gameSettings.fov = iPlayerAbility.getFOV();
                    iPlayerAbility.setRitualTimer(1);


                }else{
                    iPlayerAbility.incrementRitualTimer();

                }

            }
        });

        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {
            if (h.getBedrockFlux()>250.32f) {
                h.count();
                if (h.getTimer() >= h.getMaxTimer()) {
                    h.changeMax();
                    //int rand = new Random().nextInt(20) + 1;
                    int rand = 30;
                    if (rand <= 4) {
                        if (h.getBedrockFlux()<400){
                            int choice =  new Random().nextInt(5);
                            System.out.println(choice);

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

                                    break;
                            }

                        }
                    }
                    String message = ("You dont feel too well");
                    player.sendStatusMessage(new StringTextComponent(message), true);
                }
            }
        });

    }

    /**
     * Edit the speed of an entity.
     *
     * @param entity
     * @param speedModifierUUID
     *            Unique UUID for modification
     * @param name
     *            Unique name for easier debugging
     * @param modifier
     *            The speed will be multiplied by this number
     */
    public static void changeSpeed(LivingEntity entity,
                                   UUID speedModifierUUID, String name, double modifier) {
        AttributeModifier speedModifier = (new AttributeModifier(
                speedModifierUUID, name, modifier - 1,AttributeModifier.Operation.byId(2)));
        IAttributeInstance attributeinstance = entity
                .getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

        if (attributeinstance.getModifier(speedModifierUUID) != null) {
            attributeinstance.removeModifier(speedModifier);
        }
        attributeinstance.applyModifier(speedModifier);
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
            bedrockFlux.ifPresent(h -> {
                h.setCrafterFlux();
                fx.flux = h;
                h.setScreen(fx);
                BedrockResources.proxy.getMinecraft().ingameGUI=fx;
                String message = ("You out of nowhere understand flux and can sense the amount of flux on you");
                player.sendStatusMessage(new StringTextComponent(message),true);
            });
        }
    }

    @SubscribeEvent
    public static void PlayerBreakSpeedEvent(PlayerEvent.BreakSpeed event)
    {
        PlayerEntity player = event.getPlayer();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            if (player.getHeldItemMainhand() == ItemStack.EMPTY) {
                event.setNewSpeed(event.getOriginalSpeed() + h.getMiningSpeedBoost());
            }

        });
    }

    private static int getharvestLevel(String material){
        switch (material) {
            case "wood":
                return ItemTier.WOOD.getHarvestLevel();
            case "stone":
                return  ItemTier.IRON.getHarvestLevel();
            case "iron":
                return ItemTier.IRON.getHarvestLevel();
            case "diamond":
                return  ItemTier.DIAMOND.getHarvestLevel();
        }
        return 0;
    }

    @SubscribeEvent
    public static void PlayerBreakBlockEvent(PlayerInteractEvent.HarvestCheck event)
    {
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
                if (event.getTargetBlock().getHarvestTool().toString().equals(net.minecraftforge.common.ToolType.PICKAXE.toString())) {

                    int i = getharvestLevel(h.getPick());

                    if (i >= event.getTargetBlock().getHarvestLevel()) {
                        flag = true;
                    } else {
                        Material material = event.getTargetBlock().getMaterial();
                        flag = material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
                    }
                }else if (event.getTargetBlock().getHarvestTool().toString().equals(ToolType.SHOVEL.toString())) {

                    int i = getharvestLevel(h.getShovel());

                    if (i >= event.getTargetBlock().getHarvestLevel()) {
                        flag = true;
                    }
                    if (!flag){
                        flag= block == Blocks.SNOW || block == Blocks.SNOW_BLOCK;
                    }
                }else if (event.getTargetBlock().getHarvestTool().toString().equals(ToolType.AXE.toString())) {

                    int i = getharvestLevel(h.getAxe());

                    if (i >= event.getTargetBlock().getHarvestLevel()) {
                        flag = true;
                    } else {
                        Material material = event.getTargetBlock().getMaterial();
                        flag = material != Material.WOOD && material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.BAMBOO ;
                    }
                }
                if (event.getTargetBlock().getMaterial().isToolNotRequired() || flag) {
                    event.setCanHarvest(true);
                }
            });
        }

    }

    protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(), Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(), Blocks.DIRT, Blocks.FARMLAND.getDefaultState(), Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));

    @SubscribeEvent
    public static void PlayerRightClickEvent( PlayerInteractEvent.RightClickBlock event)
    {
        if (!(event.getPlayer().getHeldItemMainhand().getItem() instanceof SwordItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof AxeItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof ShovelItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof PickaxeItem) &&
                !(event.getPlayer().getHeldItemMainhand().getItem() instanceof HoeItem)) {
            LazyOptional<IPlayerAbility> abilities = event.getPlayer().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                if(!h.getHoe().equals("no")){
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
    public static void PlayerWakeUpEvent(PlayerSleepInBedEvent event)
    {
        PlayerEntity player = event.getEntityPlayer();

        if (player.world.isRemote) return;

        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {

            String message = ("You hear whispers as you wake up from bed.");
            h.fill(100);
            if(BedrockResources.proxy.getMinecraft().ingameGUI instanceof FluxOracleScreen){
                h.setScreen((FluxOracleScreen)BedrockResources.proxy.getMinecraft().ingameGUI);
                h.getScreen().flux = h;
            }
            player.sendStatusMessage(new StringTextComponent(message),true);
            player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + new TranslationTextComponent("message.bedres.whispers").getUnformattedComponentText()), false);
            player.world.addEntity(new LightningBoltEntity(player.world,player.posX,player.posY,player.posZ,true));


        });

    }

    @SubscribeEvent
    public static void onPlayerFalls(LivingFallEvent event)
    {
        Entity entity = event.getEntity();

        if (entity.world.isRemote || !(entity instanceof PlayerEntity) || event.getDistance() < 3) return;

        PlayerEntity player = (PlayerEntity) entity;
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> {
            float points = h.getBedrockFlux();
            float cost = event.getDistance() * 2;

            if (points > cost)
            {


                h.consume(cost);

                String number2AsString = new DecimalFormat("#.00").format(cost);
                String message = String.format("You absorbed fall damage. It costed %s mana, you have %s mana left.", number2AsString, h.getBedrockFluxString());
                player.sendStatusMessage(new StringTextComponent(message),true);

                event.setCanceled(true);
            }

        });

    }

    /**
     * Copy data from dead player to the new player
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        PlayerEntity player = event.getEntityPlayer();
        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
        LazyOptional<IBedrockFlux> oldbedrockFlux =  event.getOriginal().getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);

        bedrockFlux.ifPresent(h -> oldbedrockFlux.ifPresent(o -> h.set(o.getBedrockFlux())));
    }
}
