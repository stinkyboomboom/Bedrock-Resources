package com.alexvr.bedres.utils;

import com.alexvr.bedres.biomes.decayingfluxed.DecayingFluxedBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
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
        }else if (entity.contains("cat")) {
            return new ModCatRender(manager);
        }else if (entity.contains("creeper")) {
            return new ModCreeperRender(manager);
        }else if (entity.contains("pig")) {
            return new ModPigRender(manager);
        }else if (entity.contains("sheep")) {
            return new ModSheepRender(manager);
        }else if (entity.contains("skeleton")) {
            return new ModSkeletonRender(manager);
        }else if (entity.contains("spider")) {
            return new ModSpiderRender(manager);
        }else if (entity.contains("zombie")) {
            return new ModZombieRender(manager);
        }else if (entity.contains("chicken")) {
            return new ModChickenRender(manager);
        }else if (entity.contains("iron_golem")) {
            return new ModIronGolemRender(manager);
        }else if (entity.contains("squid")) {
            return new ModSquidRender(manager);
        }else if (entity.contains("witch")) {
            return new ModWitchRender(manager);
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

    private class ModCatRender  extends CatRenderer {
        private final ResourceLocation CAT_TEXTURES = new ResourceLocation("bedres:textures/entity/cat/cat.png");

        public ModCatRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(CatEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return CAT_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModCreeperRender  extends CreeperRenderer {
        private final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("bedres:textures/entity/creeper/creeper.png");

        public ModCreeperRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(CreeperEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return CREEPER_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModPigRender  extends PigRenderer {
        private final ResourceLocation PIG_TEXTURES = new ResourceLocation("bedres:textures/entity/pig/pig.png");

        public ModPigRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(PigEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return PIG_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModSheepRender  extends SheepRenderer {
        private final ResourceLocation COW_TEXTURES = new ResourceLocation("bedres:textures/entity/sheep/sheep.png");

        public ModSheepRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(SheepEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return COW_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModSkeletonRender  extends SkeletonRenderer {
        private final ResourceLocation COW_TEXTURES = new ResourceLocation("bedres:textures/entity/skeleton/skeleton.png");

        public ModSkeletonRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(AbstractSkeletonEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return COW_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModSpiderRender  extends SpiderRenderer {
        private final ResourceLocation COW_TEXTURES = new ResourceLocation("bedres:textures/entity/spider/spider.png");

        public ModSpiderRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(SpiderEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return COW_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModZombieRender  extends ZombieRenderer {
        private final ResourceLocation COW_TEXTURES = new ResourceLocation("bedres:textures/entity/zombie/zombie.png");

        public ModZombieRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(ZombieEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return COW_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModChickenRender  extends ChickenRenderer {
        private final ResourceLocation COW_TEXTURES = new ResourceLocation("bedres:textures/entity/chicken/chicken.png");

        public ModChickenRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(ChickenEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return COW_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModIronGolemRender  extends IronGolemRenderer {
        private final ResourceLocation COW_TEXTURES = new ResourceLocation("bedres:textures/entity/iron_golem.png");

        public ModIronGolemRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(IronGolemEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return COW_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModSquidRender  extends SquidRenderer {
        private final ResourceLocation COW_TEXTURES = new ResourceLocation("bedres:textures/entity/squid.png");

        public ModSquidRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(SquidEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return COW_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

    private class ModWitchRender  extends WitchRenderer {
        private final ResourceLocation COW_TEXTURES = new ResourceLocation("bedres:textures/entity/witch.png");

        public ModWitchRender(EntityRendererManager renderManager) {
            super(renderManager);

        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(WitchEntity entity) {
            if (entity.world.getBiome(new BlockPos(entity.posX,entity.posY,entity.posZ)) instanceof DecayingFluxedBiome) {
                return COW_TEXTURES;
            }else{
                return super.getEntityTexture(entity);
            }
        }
    }

}
