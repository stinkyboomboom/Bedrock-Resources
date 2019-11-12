package com.alexvr.bedres.utils;

import com.alexvr.bedres.biomes.decayingfluxed.DecayingFluxedBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactory implements IRenderFactory {

    private String entity;

    public RenderFactory(String entity){
        this.entity = entity;

    }

    @Override
    public EntityRenderer createRenderFor(EntityRendererManager manager) {
        if (entity.contains("villager")) {
            return new ModVillagerRender(manager);
        }else if (entity.contains("cow")) {
            return new ModCowRender(manager);
        }
        return new ModVillagerRender(manager);
    }

    private class ModVillagerRender  extends VillagerRenderer {
        private final ResourceLocation VILLAGER_TEXTURES = new ResourceLocation("bedres:textures/entity/villager/decaying_fluxed_villager.png");

        public ModVillagerRender(EntityRendererManager renderManager) {
            super(renderManager, (IReloadableResourceManager) Minecraft.getInstance().getResourceManager());

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(VillagerEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return VILLAGER_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModCowRender  extends CowRenderer {
        private final ResourceLocation COW_TEXTURES = new ResourceLocation("bedres:textures/entity/cow/cow.png");

        public ModCowRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(CowEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return COW_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }


}
