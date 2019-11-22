package com.alexvr.bedres.registry;


import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.*;
import com.alexvr.bedres.blocks.decayingfluxedblocks.*;
import com.alexvr.bedres.blocks.multiblocks.bedrockscraper.BedrockScraperContainer;
import com.alexvr.bedres.blocks.multiblocks.bedrockscraper.BedrockScraperControllerTile;
import com.alexvr.bedres.blocks.multiblocks.bedrockscraper.BedrockScrapperControllerBlock;
import com.alexvr.bedres.blocks.multiblocks.bedrockscraper.BedrockScrapperSlaveBlock;
import com.alexvr.bedres.blocks.tiles.*;
import com.alexvr.bedres.containers.ScrapeTankContainer;
import com.alexvr.bedres.renderer.*;
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

    @ObjectHolder(BedrockResources.MODID+ ":"+References.ENDERIAN_BRICK_REGNAME)
    public static EnderianBrick enderianBrick;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BEDROCK_STAIRS_REGNAME)
    public static BedrockStair bedrockStair;

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

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BEDROCK_WIRE_REGNAME)
    public static BedrockWireBlock bedrockWire;

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

    @ObjectHolder(BedrockResources.MODID+ ":"+References.ENDERIAN_RITUAL_PEDESTAL_REGNAME)
    public static EnderianRitualPedestal enderianRitualPedestal;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.ENDERIAN_RITUAL_PEDESTAL_REGNAME)
    public static TileEntityType<EnderianRitualPedestalTile> enderianRitualPedestalTileTileEntityType;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.ITEM_PLATFORM_REGNAME)
    public static ItemPlatform itemPlatform;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.ITEM_PLATFORM_REGNAME)
    public static TileEntityType<ItemPlatformTile> itemPlatformTileTileEntityType;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.SCRAPER_MOTOR_REGNAME)
    public static ScraperMotor motor;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.DF_DIRT_REGNAME)
    public static DFDirt dfDirt;
    @ObjectHolder(BedrockResources.MODID+ ":"+References.DF_COOBLE_REGNAME)
    public static DFCobble dfCobble;
    @ObjectHolder(BedrockResources.MODID+ ":"+References.DF_GRASS_REGNAME)
    public static DFGrass dfGrass;
    @ObjectHolder(BedrockResources.MODID+ ":"+References.DF_OAK_LEAVES_REGNAME)
    public static DFOakLeave dfOakLeave;
    @ObjectHolder(BedrockResources.MODID+ ":"+References.DF_OAK_LOG_REGNAME)
    public static DFOakLog dfOakLog;
    @ObjectHolder(BedrockResources.MODID+ ":"+References.DF_SAPPLING_REGNAME)
    public static DFOakSappling dfOakSappling;
    @ObjectHolder(BedrockResources.MODID+ ":"+References.DF_OAK_PLANKS_REGNAME)
    public static DFOakPlanks dfOakPlanks;
    @ObjectHolder(BedrockResources.MODID+ ":"+References.DF_OAK_SLAB_REGNAME)
    public static DFOakSlabs dfOakSlabs;
    @ObjectHolder(BedrockResources.MODID+ ":"+References.DF_STRIPPED_OAK_LOG_REGNAME)
    public static DFOakStrippedLog dfOakStrippedLog;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.FLUXED_SPORES_REGNAME)
    public static FluxedSpores fluxedSpores;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.FLUXED_GRAVITY_BUBBLE_REGNAME)
    public static FluxedGravityBubble fluxedGravityBubble;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.FLUXED_GRAVITY_BUBBLE_REGNAME)
    public static TileEntityType<FluxedGravityBubbleTile> fluxedGravityBubbleTileTileEntityType;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.RANGE_VIEW_REGNAME)
    public static RangeView rangeView;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.BEDROCK_COMPRESSED_WIRE_REGNAME)
    public static BedrockCompressedWireBlock bedrockCompressedWireBlock;

    @ObjectHolder(BedrockResources.MODID+ ":"+References.VOID_TEAR_REGNAME)
    public static VoidTears voidTears;

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(event -> {
           ClientRegistry.bindTileEntitySpecialRenderer(ScrapeTankTile.class, new ScrapeTankTER());
            ClientRegistry.bindTileEntitySpecialRenderer(BedrockScraperControllerTile.class, new ScraperControllerTER());
            ClientRegistry.bindTileEntitySpecialRenderer(BedrockiumTowerTile.class, new BedrockiumTowerTER());
            ClientRegistry.bindTileEntitySpecialRenderer(BedrockiumPedestalTile.class, new BedrockiumPedestalTER());
            ClientRegistry.bindTileEntitySpecialRenderer(EnderianRitualPedestalTile.class, new EnderianRitualPedestalTER());
            ClientRegistry.bindTileEntitySpecialRenderer(ItemPlatformTile.class, new ItemPlatformTER());
            ClientRegistry.bindTileEntitySpecialRenderer(FluxedGravityBubbleTile.class, new FluxedGravityBubbleTER());
        });
    }
}
