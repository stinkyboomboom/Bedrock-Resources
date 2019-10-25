package com.alexvr.bedres.registry;


import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.*;
import com.alexvr.bedres.containers.ScrapeTankContainer;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScraperContainer;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScraperControllerTile;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScrapperControllerBlock;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScrapperSlaveBlock;
import com.alexvr.bedres.renderer.ScrapeTankTER;
import com.alexvr.bedres.renderer.ScraperControllerTER;
import com.alexvr.bedres.tiles.ScrapeTankTile;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    @ObjectHolder("bedres:scrape_tank")
    public static ScrapeTank scrapeTank;

    @ObjectHolder("bedres:scrape_tank")
    public static TileEntityType<ScrapeTankTile> scrapeTankType;

    @ObjectHolder("bedres:scrape_tank")
    public static ContainerType<ScrapeTankContainer> scrapeTankContainerType;

    @ObjectHolder("bedres:enderian_block")
    public static EnderianBlock enderianBlock;

    @ObjectHolder("bedres:enderian_ore")
    public static EnderianOre enderianOre;

    @ObjectHolder("bedres:bedrock_scraper_controller")
    public static BedrockScrapperControllerBlock bedrockScraperControllerBlock;

    @ObjectHolder("bedres:bedrock_scraper_controller")
    public static TileEntityType<BedrockScraperControllerTile> bedrockScraperControllerTile;

    @ObjectHolder("bedres:bedrock_scraper_controller")
    public static ContainerType<BedrockScraperContainer> bedrockScraperControllerContainer;

    @ObjectHolder("bedres:bedrock_scraper_slave")
    public static BedrockScrapperSlaveBlock bedrockScraperSlaveBlock;

    @ObjectHolder("bedres:blazium")
    public static Blazium blazium;

    @ObjectHolder("bedres:ender_hush")
    public static EnderHush enderHush;

    @ObjectHolder("bedres:sun_daize")
    public static SunDaize sunDaize;

    @ObjectHolder("bedres:spike")
    public static BedrociumSpike bedrociumSpike;

    @ObjectHolder("bedres:base_spike")
    public static BedrociumTower bedrociumTower;

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(event -> {
           ClientRegistry.bindTileEntitySpecialRenderer(ScrapeTankTile.class, new ScrapeTankTER());
            ClientRegistry.bindTileEntitySpecialRenderer(BedrockScraperControllerTile.class, new ScraperControllerTER());
        });
    }
}
