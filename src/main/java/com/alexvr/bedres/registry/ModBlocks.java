package com.alexvr.bedres.registry;

import com.alexvr.bedres.blocks.ScrapeTank;
import com.alexvr.bedres.tiles.ScrapeTankTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {
    @ObjectHolder("bedres:scrape_tank")
    public static ScrapeTank scrapeTank;

    @ObjectHolder("bedres:scrape_tank")
    public static TileEntityType<ScrapeTankTile> scrapeTankType;

}
