package com.alexvr.bedres.setup;

import com.alexvr.bedres.network.Networking;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.utils.RenderFactory;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
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
        RenderingRegistry.registerEntityRenderingHandler(CowEntity.class, new RenderFactory("cow"));
        RenderingRegistry.registerEntityRenderingHandler(CatEntity.class, new RenderFactory("cat"));
        RenderingRegistry.registerEntityRenderingHandler(CreeperEntity.class, new RenderFactory("creeper"));
        RenderingRegistry.registerEntityRenderingHandler(PigEntity.class, new RenderFactory("pig"));
        RenderingRegistry.registerEntityRenderingHandler(SheepEntity.class, new RenderFactory("sheep"));
        RenderingRegistry.registerEntityRenderingHandler(SkeletonEntity.class, new RenderFactory("skeleton"));
        RenderingRegistry.registerEntityRenderingHandler(SpiderEntity.class, new RenderFactory("spider"));
        RenderingRegistry.registerEntityRenderingHandler(ZombieEntity.class, new RenderFactory("zombie"));
        RenderingRegistry.registerEntityRenderingHandler(ChickenEntity.class, new RenderFactory("chicken"));
        RenderingRegistry.registerEntityRenderingHandler(IronGolemEntity.class, new RenderFactory("iron_golem"));
        RenderingRegistry.registerEntityRenderingHandler(SquidEntity.class, new RenderFactory("squid"));
        RenderingRegistry.registerEntityRenderingHandler(WitchEntity.class, new RenderFactory("witch"));

    }

}
