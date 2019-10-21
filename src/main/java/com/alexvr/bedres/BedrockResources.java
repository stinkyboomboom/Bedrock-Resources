package com.alexvr.bedres;

import com.alexvr.bedres.blocks.EnderianBlock;
import com.alexvr.bedres.blocks.EnderianOre;
import com.alexvr.bedres.blocks.ScrapeTank;
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
import com.alexvr.bedres.setup.ClientProxy;
import com.alexvr.bedres.setup.IProxy;
import com.alexvr.bedres.setup.ModSetup;
import com.alexvr.bedres.setup.ServerProxy;
import com.alexvr.bedres.tiles.ScrapeTankTile;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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


    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new ScrapeTank());
            event.getRegistry().register(new EnderianBlock(Material.IRON, SoundType.METAL,32,2,"enderian_block"));
            event.getRegistry().register(new EnderianOre(Material.IRON, SoundType.STONE,54,3,"enderian_ore"));
            event.getRegistry().register(new BedrockScrapperControllerBlock());
            event.getRegistry().register(new BedrockScrapperSlaveBlock());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties()
                    .group(setup.itemgroup);
            event.getRegistry().register(new BlockItem(ModBlocks.scrapeTank, properties).setRegistryName("scrape_tank"));
            event.getRegistry().register(new BlockItem(ModBlocks.enderianBlock, properties).setRegistryName("enderian_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.enderianOre, properties).setRegistryName("enderian_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrockScraperControllerBlock, properties).setRegistryName("bedrock_scraper_controller"));
            event.getRegistry().register(new BlockItem(ModBlocks.bedrockScraperSlaveBlock, properties).setRegistryName("bedrock_scraper_slave"));
            event.getRegistry().register(new BedrockScrape());
            event.getRegistry().register(new ScrapeKnife());
            event.getRegistry().register(new FluxOracle());
            event.getRegistry().register(new EnderianIngot());



        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(ScrapeTankTile::new, ModBlocks.scrapeTank).build(null).setRegistryName("scrape_tank"));
            event.getRegistry().register(TileEntityType.Builder.create(BedrockScraperControllerTile::new).build(null).setRegistryName("bedrock_scraper_controller"));

        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                return new ScrapeTankContainer(windowId,BedrockResources.proxy.getClientWorld(),data.readBlockPos(),inv,BedrockResources.proxy.getClientPlayer());
            }).setRegistryName("scrape_tank"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                return new BedrockScraperContainer(windowId,BedrockResources.proxy.getClientWorld(),data.readBlockPos(),inv,BedrockResources.proxy.getClientPlayer());
            }).setRegistryName("bedrock_scraper_controller"));


        }

    }


}
