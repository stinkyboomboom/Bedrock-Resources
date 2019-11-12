package com.alexvr.bedres.setup;

import com.alexvr.bedres.network.Networking;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.RenderFactory;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModSetup {

    public ItemGroup itemgroup = new ItemGroup("bedres") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.scrapeTank);
        }
    };

    public void init() {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
        Networking.registerMessages();
        RenderingRegistry.registerEntityRenderingHandler(VillagerEntity.class, new RenderFactory("villager"));

    }

}
