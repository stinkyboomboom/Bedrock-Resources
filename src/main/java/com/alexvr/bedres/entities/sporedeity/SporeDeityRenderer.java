package com.alexvr.bedres.entities.sporedeity;

import com.alexvr.bedres.entities.sporedeity.layers.body.BodyLayer;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

public class SporeDeityRenderer extends MobRenderer<SporeDeityEntity, SporeDeityModel<SporeDeityEntity>> {

    public SporeDeityRenderer(EntityRendererManager renderManager) {
        super(renderManager,new SporeDeityModel<>(0.0F, false), 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5F), new BipedModel<>(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new ArrowLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new BodyLayer<>(this));
    }

    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(SporeDeityEntity entity) {
        ResourceLocation resourcelocation;
        Minecraft minecraft = Minecraft.getInstance();
        GameProfile profile = Minecraft.getInstance().getSession().getProfile();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);
        if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
            resourcelocation = minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
        } else {
            resourcelocation = DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(profile));
        }
        return resourcelocation;
    }

    @ParametersAreNonnullByDefault
    public void doRender(SporeDeityEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
            double d0 = y;
            if (entity.shouldRenderSneaking()) {
                d0 = y - 0.125D;
            }

            this.setModelVisibilities(entity);
            GlStateManager.setProfile(GlStateManager.Profile.PLAYER_SKIN);
            super.doRender(entity, x, d0, z, entityYaw, partialTicks);
            GlStateManager.unsetProfile(GlStateManager.Profile.PLAYER_SKIN);

    }

    private void setModelVisibilities(SporeDeityEntity clientPlayer) {
        SporeDeityModel<SporeDeityEntity> playermodel = this.getEntityModel();
        if (clientPlayer.isSpectator()) {
            playermodel.setVisible(false);
            playermodel.bipedHead.showModel = true;
            playermodel.bipedHeadwear.showModel = true;
        } else {
            ItemStack itemstack = clientPlayer.getHeldItemMainhand();
            ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
            playermodel.setVisible(true);
            playermodel.isSneak = clientPlayer.shouldRenderSneaking();
            BipedModel.ArmPose bipedmodel$armpose = this.func_217766_a(clientPlayer, itemstack, itemstack1, Hand.MAIN_HAND);
            BipedModel.ArmPose bipedmodel$armpose1 = this.func_217766_a(clientPlayer, itemstack, itemstack1, Hand.OFF_HAND);
            if (clientPlayer.getPrimaryHand() == HandSide.RIGHT) {
                playermodel.rightArmPose = bipedmodel$armpose;
                playermodel.leftArmPose = bipedmodel$armpose1;
            } else {
                playermodel.rightArmPose = bipedmodel$armpose1;
                playermodel.leftArmPose = bipedmodel$armpose;
            }
        }

    }

    private BipedModel.ArmPose func_217766_a(SporeDeityEntity p_217766_1_, ItemStack p_217766_2_, ItemStack p_217766_3_, Hand p_217766_4_) {
        BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
        ItemStack itemstack = p_217766_4_ == Hand.MAIN_HAND ? p_217766_2_ : p_217766_3_;
        if (!itemstack.isEmpty()) {
            bipedmodel$armpose = BipedModel.ArmPose.ITEM;
            if (p_217766_1_.getItemInUseCount() > 0) {
                UseAction useaction = itemstack.getUseAction();
                if (useaction == UseAction.BLOCK) {
                    bipedmodel$armpose = BipedModel.ArmPose.BLOCK;
                } else if (useaction == UseAction.BOW) {
                    bipedmodel$armpose = BipedModel.ArmPose.BOW_AND_ARROW;
                } else if (useaction == UseAction.SPEAR) {
                    bipedmodel$armpose = BipedModel.ArmPose.THROW_SPEAR;
                } else if (useaction == UseAction.CROSSBOW && p_217766_4_ == p_217766_1_.getActiveHand()) {
                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_CHARGE;
                }
            } else {
                boolean flag3 = p_217766_2_.getItem() == Items.CROSSBOW;
                boolean flag = CrossbowItem.isCharged(p_217766_2_);
                boolean flag1 = p_217766_3_.getItem() == Items.CROSSBOW;
                boolean flag2 = CrossbowItem.isCharged(p_217766_3_);
                if (flag3 && flag) {
                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
                }

                if (flag1 && flag2 && p_217766_2_.getItem().getUseAction(p_217766_2_) == UseAction.NONE) {
                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
                }
            }
        }

        return bipedmodel$armpose;
    }

    protected void preRenderCallback(SporeDeityEntity entitylivingbaseIn, float partialTickTime) {
        float f = 0.9375F;
        GlStateManager.scalef(0.9375F, 0.9375F, 0.9375F);
    }


    protected void applyRotations(SporeDeityEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        float f = entityLiving.getSwimAnimation(partialTicks);
        if (entityLiving.isElytraFlying()) {
            super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
            float f1 = (float)entityLiving.getTicksElytraFlying() + partialTicks;
            float f2 = MathHelper.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
            if (!entityLiving.isSpinAttacking()) {
                GlStateManager.rotatef(f2 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
            }

            Vec3d vec3d = entityLiving.getLook(partialTicks);
            Vec3d vec3d1 = entityLiving.getMotion();
            double d0 = Entity.horizontalMag(vec3d1);
            double d1 = Entity.horizontalMag(vec3d);
            if (d0 > 0.0D && d1 > 0.0D) {
                double d2 = (vec3d1.x * vec3d.x + vec3d1.z * vec3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
                double d3 = vec3d1.x * vec3d.z - vec3d1.z * vec3d.x;
                GlStateManager.rotatef((float)(Math.signum(d3) * Math.acos(d2)) * 180.0F / (float)Math.PI, 0.0F, 1.0F, 0.0F);
            }
        } else if (f > 0.0F) {
            super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
            float f3 = entityLiving.isInWater() ? -90.0F - entityLiving.rotationPitch : -90.0F;
            float f4 = MathHelper.lerp(f, 0.0F, f3);
            GlStateManager.rotatef(f4, 1.0F, 0.0F, 0.0F);
            if (entityLiving.isActualySwimming()) {
                GlStateManager.translatef(0.0F, -1.0F, 0.3F);
            }
        } else {
            super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        }

    }


}
