package com.alexvr.bedres.registry;


import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.*;
import com.alexvr.bedres.containers.ScrapeTankContainer;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScraperContainer;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScraperControllerTile;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScrapperControllerBlock;
import com.alexvr.bedres.multiblocks.bedrockscraper.BedrockScrapperSlaveBlock;
import com.alexvr.bedres.renderer.BedrockiumPedestalTER;
import com.alexvr.bedres.renderer.BedrockiumTowerTER;
import com.alexvr.bedres.renderer.ScrapeTankTER;
import com.alexvr.bedres.renderer.ScraperControllerTER;
import com.alexvr.bedres.tiles.BedrockiumPedestalTile;
import com.alexvr.bedres.tiles.BedrockiumTowerTile;
import com.alexvr.bedres.tiles.ScrapeTankTile;
import com.alexvr.bedres.utils.References;
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
    @ObjectHolder(BedrockResources.MODID+ ":"+References.SCRAPE_TANK_REGNAME)
    public static ScrapeTank scrapeTank;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.SCRAPE_TANK_REGNAME)
    public static TileEntityType<ScrapeTankTile> scrapeTankType;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.SCRAPE_TANK_REGNAME)
    public static ContainerType<ScrapeTankContainer> scrapeTankContainerType;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.ENDERIAN_BLOCK_REGNAME)
    public static EnderianBlock enderianBlock;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.ENDERIAN_ORE_REGNAME)
    public static EnderianOre enderianOre;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BEDROCK_SCRAPER_CONTROLLER_REGNAME)
    public static BedrockScrapperControllerBlock bedrockScraperControllerBlock;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BEDROCK_SCRAPER_CONTROLLER_REGNAME)
    public static TileEntityType<BedrockScraperControllerTile> bedrockScraperControllerTile;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BEDROCK_SCRAPER_CONTROLLER_REGNAME)
    public static ContainerType<BedrockScraperContainer> bedrockScraperControllerContainer;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BEDROCK_SCRAPER_SLAVE_REGNAME)
    public static BedrockScrapperSlaveBlock bedrockScraperSlaveBlock;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BLAZIUM_REGNAME)
    public static Blazium blazium;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.ENDER_HUSH_REGNAME)
    public static EnderHush enderHush;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.SUN_DAIZE_REGNAME)
    public static SunDaize sunDaize;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.SPIKE_REGNAME)
    public static BedrociumSpike bedrociumSpike;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BASE_SPIKE_REGNAME)
    public static BedrociumTower bedrociumTower;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BASE_SPIKE_REGNAME)
    public static TileEntityType<BedrockiumTowerTile> bedrockiumTowerType;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.PEDESTAL_REGNAME)
    public static BedrociumPedestal bedrociumPedestal;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.PEDESTAL_REGNAME)
    public static TileEntityType<BedrockiumPedestalTile> bedrockiumPedestalType;

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(event -> {
           ClientRegistry.bindTileEntitySpecialRenderer(ScrapeTankTile.class, new ScrapeTankTER());
            ClientRegistry.bindTileEntitySpecialRenderer(BedrockScraperControllerTile.class, new ScraperControllerTER());
            ClientRegistry.bindTileEntitySpecialRenderer(BedrockiumTowerTile.class, new BedrockiumTowerTER());
            ClientRegistry.bindTileEntitySpecialRenderer(BedrockiumPedestalTile.class, new BedrockiumPedestalTER());
        });
    }
}
