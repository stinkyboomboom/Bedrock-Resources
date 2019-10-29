package com.alexvr.bedres;

import com.alexvr.bedres.blocks.*;
import com.alexvr.bedres.containers.ScrapeTankContainer;
import com.alexvr.bedres.items.BedrockScrape;
import com.alexvr.bedres.items.EnderianIngot;
import com.alexvr.bedres.items.FluxOracle;
import com.alexvr.bedres.items.ScrapeKnife;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScraperContainer;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScraperControllerTile;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScrapperControllerBlock;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScrapperSlaveBlock;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModFeatures;
import com.alexvr.bedres.setup.ClientProxy;
import com.alexvr.bedres.setup.IProxy;
import com.alexvr.bedres.setup.ModSetup;
import com.alexvr.bedres.setup.ServerProxy;
import com.alexvr.bedres.tiles.BedrockiumPedestalTile;
import com.alexvr.bedres.tiles.BedrockiumTowerTile;
import com.alexvr.bedres.tiles.ScrapeTankTile;
import com.alexvr.bedres.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
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

    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new ScrapeTank());
            event.getRegistry().register(new EnderianBlock(Material.IRON, SoundType.METAL,32,2, References.ENDERIAN_BLOCK_REGNAME));
            event.getRegistry().register(new EnderianOre(Material.IRON, SoundType.STONE,54,3,References.ENDERIAN_ORE_REGNAME));
            event.getRegistry().register(new EnderianBrick(Material.IRON, SoundType.STONE,54,5,References.ENDERIAN_BRICK_REGNAME));
            event.getRegistry().register(new BedrockStair(Material.IRON, SoundType.STONE,54,5,References.BEDROCK_STAIRS_REGNAME));
            event.getRegistry().register(new BedrockScrapperControllerBlock());
            event.getRegistry().register(new BedrockScrapperSlaveBlock());
            event.getRegistry().register(new Blazium());
            event.getRegistry().register(new EnderHush());
            event.getRegistry().register(new SunDaize());
            event.getRegistry().register(new BedrociumSpike());
            event.getRegistry().register(new BedrociumTower());
            event.getRegistry().register(new BedrociumPedestal());
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
            event.getRegistry().register(new BlockItem(ModBlocks.bedrockScraperSlaveBlock, properties).setRegistryName(References.BEDROCK_SCRAPER_SLAVE_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.blazium, properties).setRegistryName(References.BLAZIUM_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.sunDaize, properties).setRegistryName(References.SUN_DAIZE_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.enderHush, properties).setRegistryName(References.ENDER_HUSH_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrociumSpike, properties).setRegistryName(References.SPIKE_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrociumTower, properties).setRegistryName(References.BASE_SPIKE_REGNAME));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrociumPedestal, properties).setRegistryName(References.PEDESTAL_REGNAME));
            event.getRegistry().register(new BedrockScrape());
            event.getRegistry().register(new ScrapeKnife());
            event.getRegistry().register(new FluxOracle());
            event.getRegistry().register(new EnderianIngot());



        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(ScrapeTankTile::new, ModBlocks.scrapeTank).build(null).setRegistryName(References.SCRAPE_TANK_REGNAME));
            event.getRegistry().register(TileEntityType.Builder.create(BedrockScraperControllerTile::new, ModBlocks.bedrockScraperControllerBlock).build(null).setRegistryName(References.BEDROCK_SCRAPER_CONTROLLER_REGNAME));
            event.getRegistry().register(TileEntityType.Builder.create(BedrockiumTowerTile::new, ModBlocks.bedrociumTower).build(null).setRegistryName(References.BASE_SPIKE_REGNAME));
            event.getRegistry().register(TileEntityType.Builder.create(BedrockiumPedestalTile::new, ModBlocks.bedrociumPedestal).build(null).setRegistryName(References.PEDESTAL_REGNAME));

        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new ScrapeTankContainer(windowId,BedrockResources.proxy.getClientWorld(),data.readBlockPos(),inv,BedrockResources.proxy.getClientPlayer())).setRegistryName(References.SCRAPE_TANK_REGNAME));
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new BedrockScraperContainer(windowId,BedrockResources.proxy.getClientWorld(),data.readBlockPos(),inv,BedrockResources.proxy.getClientPlayer())).setRegistryName(References.BEDROCK_SCRAPER_CONTROLLER_REGNAME));


        }

        @SubscribeEvent
        public static void onRegisterFeatures(RegistryEvent.Register<Feature<?>> event) {
            IForgeRegistry<Feature<?>> registry = event.getRegistry();

            ModFeatures.registerFeatures(registry);

        }


    }


}
