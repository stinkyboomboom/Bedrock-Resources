package com.alexvr.bedres;

import com.alexvr.bedres.blocks.*;
import com.alexvr.bedres.blocks.decayingfluxedblocks.*;
import com.alexvr.bedres.blocks.multiblocks.bedrockscraper.BedrockScraperContainer;
import com.alexvr.bedres.blocks.multiblocks.bedrockscraper.BedrockScraperControllerTile;
import com.alexvr.bedres.blocks.multiblocks.bedrockscraper.BedrockScrapperControllerBlock;
import com.alexvr.bedres.blocks.multiblocks.bedrockscraper.BedrockScrapperSlaveBlock;
import com.alexvr.bedres.blocks.tiles.*;
import com.alexvr.bedres.containers.ScrapeTankContainer;
import com.alexvr.bedres.entities.fluxedcreep.FluxedCreepEntity;
import com.alexvr.bedres.items.*;
import com.alexvr.bedres.registry.ModBiomes;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModEntities;
import com.alexvr.bedres.registry.ModFeatures;
import com.alexvr.bedres.setup.ClientProxy;
import com.alexvr.bedres.setup.IProxy;
import com.alexvr.bedres.setup.ModSetup;
import com.alexvr.bedres.setup.ServerProxy;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(BedrockResources.MODID)
public class BedrockResources {


    public static final String MODID = "bedres";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(),() -> () -> new ServerProxy());

    private static final Logger LOGGER = LogManager.getLogger();

    public static ModSetup setup = new ModSetup();

    public BedrockResources() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);


    }

    private void setup(final FMLCommonSetupEvent event) {
        setup.init();
        proxy.init();
        DistExecutor.runWhenOn(Dist.CLIENT, () -> ModBlocks::registerRenderers);
        EntitySpawnPlacementRegistry.register(ModEntities.FLUXED_CREEP,
                EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, spawnReason, pos, random) -> {
                    if (entityType != ModEntities.FLUXED_CREEP)
                        throw new IllegalArgumentException( ModEntities.FLUXED_CREEP.getRegistryName() + " only!");

                    return FluxedCreepEntity.func_223368_b(entityType, world, spawnReason, pos, random);
                }
        );
        Biome biome = RegistryManager.ACTIVE.getRegistry(Biome.class).getValue(new ResourceLocation(MODID, "df_biome"));
        biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(ModEntities.FLUXED_CREEP,144,1,3));

    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new ScrapeTank());
            event.getRegistry().register(new EnderianBlock(Material.IRON, SoundType.METAL,16,2, References.ENDERIAN_BLOCK_REGNAME));
            event.getRegistry().register(new EnderianOre(Material.ROCK, SoundType.STONE,16,3,References.ENDERIAN_ORE_REGNAME));
            event.getRegistry().register(new ScraperMotor(Material.IRON, SoundType.METAL,16,0,References.SCRAPER_MOTOR_REGNAME));
            event.getRegistry().register(new EnderianBrick(Material.ROCK, SoundType.STONE,8,5,References.ENDERIAN_BRICK_REGNAME));
            event.getRegistry().register(new BedrockStair(Material.ROCK, SoundType.STONE,8,5,References.BEDROCK_STAIRS_REGNAME));
            event.getRegistry().register(new BedrockScrapperControllerBlock());
            event.getRegistry().register(new BedrockScrapperSlaveBlock());
            event.getRegistry().register(new Blazium());
            event.getRegistry().register(new BedrockWireBlock());
            event.getRegistry().register(new EnderianRitualPedestal());
            event.getRegistry().register(new ItemPlatform());
            event.getRegistry().register(new EnderHush());
            event.getRegistry().register(new SunDaize());
            event.getRegistry().register(new BedrociumSpike());
            event.getRegistry().register(new BedrociumTower());
            event.getRegistry().register(new BedrociumPedestal());
            event.getRegistry().register(new DFCobble());
            event.getRegistry().register(new DFDirt());
            event.getRegistry().register(new DFGrass());
            event.getRegistry().register(new DFOakLeave());
            event.getRegistry().register(new DFOakLog());
            event.getRegistry().register(new DFOakSappling());
            event.getRegistry().register(new DFOakPlanks());
            event.getRegistry().register(new DFOakSlabs());
            event.getRegistry().register(new DFOakStrippedLog());
            event.getRegistry().register(new FluxedSpores());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties()
                    .group(setup.itemgroup);
            event.getRegistry().register(new BlockItem(ModBlocks.scrapeTank, properties).setRegistryName(References.SCRAPE_TANK_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.enderianBlock, properties).setRegistryName(References.ENDERIAN_BLOCK_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.enderianOre, properties).setRegistryName(References.ENDERIAN_ORE_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.enderianBrick, properties).setRegistryName(References.ENDERIAN_BRICK_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrockStair, properties).setRegistryName(References.BEDROCK_STAIRS_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrockScraperControllerBlock, properties).setRegistryName(References.BEDROCK_SCRAPER_CONTROLLER_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.enderianRitualPedestal, properties).setRegistryName(References.ENDERIAN_RITUAL_PEDESTAL_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrockScraperSlaveBlock, properties).setRegistryName(References.BEDROCK_SCRAPER_SLAVE_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.blazium, properties).setRegistryName(References.BLAZIUM_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.sunDaize, properties).setRegistryName(References.SUN_DAIZE_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.enderHush, properties).setRegistryName(References.ENDER_HUSH_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrociumSpike, properties).setRegistryName(References.SPIKE_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrociumTower, properties).setRegistryName(References.BASE_SPIKE_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrociumPedestal, properties).setRegistryName(References.PEDESTAL_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.motor, properties).setRegistryName(References.SCRAPER_MOTOR_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.itemPlatform, properties).setRegistryName(References.ITEM_PLATFORM_REGNAME));
            event.getRegistry().register( new BlockNamedItem(ModBlocks.bedrockWire,properties).setRegistryName(References.BEDROCK_SCRAPE_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.dfCobble,properties).setRegistryName(References.DF_COOBLE_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.dfDirt,properties).setRegistryName(References.DF_DIRT_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.dfGrass,properties).setRegistryName(References.DF_GRASS_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.dfOakLeave,properties).setRegistryName(References.DF_OAK_LEAVES_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.dfOakLog,properties).setRegistryName(References.DF_OAK_LOG_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.dfOakSappling,properties).setRegistryName(References.DF_SAPPLING_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.dfOakPlanks,properties).setRegistryName(References.DF_OAK_PLANKS_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.dfOakSlabs,properties).setRegistryName(References.DF_OAK_SLAB_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.dfOakStrippedLog,properties).setRegistryName(References.DF_STRIPPED_OAK_LOG_REGNAME));
            event.getRegistry().register( new BlockItem(ModBlocks.fluxedSpores,properties).setRegistryName(References.FLUXED_SPORES_REGNAME));
            event.getRegistry().register(new ScrapeKnife());
            event.getRegistry().register(new ScraperMesh());
            event.getRegistry().register(new FluxedCreepEggItem());
            event.getRegistry().register(new Staff());
            event.getRegistry().register(new FluxOracle());
            event.getRegistry().register(new EnderianIngot());
            event.getRegistry().register(new FluxedCupcake());



        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(ScrapeTankTile::new, ModBlocks.scrapeTank).build(null).setRegistryName(References.SCRAPE_TANK_REGNAME));
            event.getRegistry().register(TileEntityType.Builder.create(BedrockScraperControllerTile::new, ModBlocks.bedrockScraperControllerBlock).build(null).setRegistryName(References.BEDROCK_SCRAPER_CONTROLLER_REGNAME));
            event.getRegistry().register(TileEntityType.Builder.create(BedrockiumTowerTile::new, ModBlocks.bedrociumTower).build(null).setRegistryName(References.BASE_SPIKE_REGNAME));
            event.getRegistry().register(TileEntityType.Builder.create(BedrockiumPedestalTile::new, ModBlocks.bedrociumPedestal).build(null).setRegistryName(References.PEDESTAL_REGNAME));
            event.getRegistry().register(TileEntityType.Builder.create(EnderianRitualPedestalTile::new, ModBlocks.enderianRitualPedestal).build(null).setRegistryName(References.ENDERIAN_RITUAL_PEDESTAL_REGNAME));
            event.getRegistry().register(TileEntityType.Builder.create(ItemPlatformTile::new, ModBlocks.itemPlatform).build(null).setRegistryName(References.ITEM_PLATFORM_REGNAME));

        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new ScrapeTankContainer(windowId,BedrockResources.proxy.getClientWorld(),data.readBlockPos(),inv,BedrockResources.proxy.getClientPlayer())).setRegistryName(References.SCRAPE_TANK_REGNAME));
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new BedrockScraperContainer(windowId,BedrockResources.proxy.getClientWorld(),data.readBlockPos(),inv,BedrockResources.proxy.getClientPlayer())).setRegistryName(References.BEDROCK_SCRAPER_CONTROLLER_REGNAME));


        }

        @SubscribeEvent
        public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
            ModEntities.FLUXED_CREEP = EntityType.Builder.create(FluxedCreepEntity::new, EntityClassification.MONSTER)
                    .size(1, 1).immuneToFire()
                    .setShouldReceiveVelocityUpdates(true)
                    .build(References.FLUXED_CREEP_REGNAME);
            ModEntities.FLUXED_CREEP.setRegistryName(References.FLUXED_CREEP_REGNAME);
            ForgeRegistries.ENTITIES.register(ModEntities.FLUXED_CREEP);

        }


        @SubscribeEvent
        public static void onRegisterFeatures(RegistryEvent.Register<Feature<?>> event) {
            IForgeRegistry<Feature<?>> registry = event.getRegistry();

            ModFeatures.registerFeatures(registry);

        }

        @SubscribeEvent
        public static void onRegisterBIOME(RegistryEvent.Register<Biome> event) {
            IForgeRegistry<Biome> registry = event.getRegistry();
            registry.register(ModBiomes.dfBiome);
            ForgeRegistries.BIOMES.register(ModBiomes.dfBiome);
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.dfBiome,10));
            BiomeManager.addSpawnBiome(ModBiomes.dfBiome);
        }

    }


}
