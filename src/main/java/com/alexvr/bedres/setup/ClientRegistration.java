package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.entities.effectball.EffectBallEntity;
import com.alexvr.bedres.entities.effectball.EffectBallRenderer;
import com.alexvr.bedres.registry.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, i) -> 0x9e61d8, ModItems.fluxedCreepEggItem);
        event.getItemColors().register((stack, i) -> 0xf11209, ModItems.sporeDeityEggItem);
    }

    @SubscribeEvent
    public static void registerRenderers(final ModelRegistryEvent event) {
        System.out.println("client render registry");
        RenderingRegistry.registerEntityRenderingHandler(EffectBallEntity.class, EffectBallRenderer::new);
    }

//    @SubscribeEvent
//    public static void handleSpawnObject(SSpawnObjectPacket packetIn) {
//        double d0 = packetIn.getX();
//        double d1 = packetIn.getY();
//        double d2 = packetIn.getZ();
//        EntityType<?> entitytype = packetIn.getType();
//        Entity entity = null;
//        if (entitytype == ModEntities.effectBallEntityEntityType) {
//            entity = new FireballEntity(Minecraft.getInstance().world, d0, d1, d2, packetIn.func_218693_g(), packetIn.func_218695_h(), packetIn.func_218692_i());
//        }
//        if (entity != null) {
//            int i = packetIn.getEntityID();
//            entity.func_213312_b(d0, d1, d2);
//            entity.rotationPitch = (float)(packetIn.getPitch() * 360) / 256.0F;
//            entity.rotationYaw = (float)(packetIn.getYaw() * 360) / 256.0F;
//            entity.setEntityId(i);
//            entity.setUniqueId(packetIn.getUniqueId());
//            Minecraft.getInstance().world.addEntity(i, entity);
//
//        }
//    }




    }
