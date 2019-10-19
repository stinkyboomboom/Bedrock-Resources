package com.alexvr.bedres.registry;

import com.alexvr.bedres.blocks.EnderianBlock;
import com.alexvr.bedres.blocks.EnderianOre;
import com.alexvr.bedres.blocks.ScrapeTank;
import com.alexvr.bedres.containers.ScrapeTankContainer;
import com.alexvr.bedres.tiles.ScrapeTankTile;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

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


}
