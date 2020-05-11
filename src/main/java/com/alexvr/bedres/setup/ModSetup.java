package com.alexvr.bedres.setup;

import com.alexvr.bedres.entities.effectball.EffectBallRenderer;
import com.alexvr.bedres.entities.fluxedcreep.FluxedCreepRenderer;
import com.alexvr.bedres.entities.sporedeity.SporeDeityRenderer;
import com.alexvr.bedres.network.Networking;
import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModEntities;
import com.alexvr.bedres.utils.RenderFactory;
import net.minecraft.entity.EntityType;
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
        RenderingRegistry.registerEntityRenderingHandler(EntityType.VILLAGER, new RenderFactory("villager"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.COW, new RenderFactory("cow"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.CAT, new RenderFactory("cat"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.CREEPER, new RenderFactory("creeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.PIG, new RenderFactory("pig"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SHEEP, new RenderFactory("sheep"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SKELETON, new RenderFactory("skeleton"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SPIDER, new RenderFactory("spider"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.ZOMBIE, new RenderFactory("zombie"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.CHICKEN, new RenderFactory("chicken"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.IRON_GOLEM, new RenderFactory("iron_golem"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SQUID, new RenderFactory("squid"));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.WITCH, new RenderFactory("witch"));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FLUXED_CREEP, FluxedCreepRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.sporeDeityEntityEntityType, SporeDeityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.effectBallEntityEntityType, EffectBallRenderer::new);

    }

}
