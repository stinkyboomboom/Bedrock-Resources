package com.alexvr.bedres.entities.sporedeity;

import com.alexvr.bedres.entities.effectball.EffectBallEntity;
import com.alexvr.bedres.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class SporeDeityEntity extends MonsterEntity implements IMob {

    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(SporeDeityEntity.class, DataSerializers.BOOLEAN);

    private final ServerBossInfo bossInfo = (ServerBossInfo)(new ServerBossInfo(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20)).setDarkenSky(true).setCreateFog(true).setPlayEndBossMusic(true);



    public SporeDeityEntity(EntityType<? extends SporeDeityEntity> type, World p_i48553_2_) {
        super(type, p_i48553_2_);
        this.experienceValue = 8;
    }
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_213812_1_) -> {
            return Math.abs(p_213812_1_.posY - this.posY) <= 4.0D;
        }));
    }


    @OnlyIn(Dist.CLIENT)
    public boolean isAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.dataManager.set(ATTACKING, attacking);
    }


    public void tick() {
        super.tick();
        if (this.getAttackTarget() != null) {
            LivingEntity livingentity = this.getAttackTarget();

            if (new Random().nextInt(100) + 1 <= 1) {
                System.out.println("effect");
                double d1 = 4.0D;
                Vec3d vec3d = this.getLook(1.0F);
                double d2 = livingentity.posX - (posX + vec3d.x * 2.0D);
                double d3 = livingentity.getBoundingBox().minY + (double)(livingentity.getHeight()) - (0.5D + posY + (double)(getHeight()));
                double d4 = livingentity.posZ - (posZ + vec3d.z * 2.0D);
                world.playEvent((PlayerEntity)null, 1016, new BlockPos(this), 0);
                EffectBallEntity fireballentity = new EffectBallEntity(world, this, d2, d3, d4);
                fireballentity.explosionPower = 0;
                fireballentity.posX = this.posX + vec3d.x * 2.0D;
                fireballentity.posY = this.posY + (double)(this.getHeight())  + 0.5D;
                fireballentity.posZ = this.posZ + vec3d.z * 2.0D;
                world.addEntity(fireballentity);
            }
//            else if (new Random().nextInt(100) + 1 <= 1) {
//                System.out.println("fireball");
//                double d1 = 4.0D;
//                Vec3d vec3d = this.getLook(1.0F);
//                double d2 = livingentity.posX - (posX + vec3d.x * 2.0D);
//                double d3 = livingentity.getBoundingBox().minY + (double)(livingentity.getHeight()) - (0.5D + posY + (double)(getHeight()));
//                double d4 = livingentity.posZ - (posZ + vec3d.z * 2.0D);
//                world.playEvent((PlayerEntity)null, 1016, new BlockPos(this), 0);
//                FireballEntity fireballentity = new FireballEntity(world, this, d2, d3, d4);
//                fireballentity.explosionPower = 1;
//                fireballentity.posX = this.posX + vec3d.x * 2.0D;
//                fireballentity.posY = this.posY + (double)(this.getHeight())  + 0.5D;
//                fireballentity.posZ = this.posZ + vec3d.z * 2.0D;
//                world.addEntity(fireballentity);
//            }

        }

    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, false);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }else {
            return super.attackEntityFrom(source, amount);
        }
    }





    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(600.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(6.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.MASTER;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMBIENT_CAVE;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.FLUXED_CREEP_ROAR.getSound();
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 1.0F;
    }

    public static boolean func_223368_b(EntityType<SporeDeityEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return  func_223315_a(entityType, world, spawnReason, pos, random);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.ENTITY_ENDER_DRAGON_GROWL,4,3));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public EntityClassification getClassification(boolean forSpawnCount) {
        return EntityClassification.MONSTER;
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 1.75F;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    public void addTrackingPlayer(ServerPlayerEntity player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    public void removeTrackingPlayer(ServerPlayerEntity player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    public boolean isNonBoss() {
        return false;}

}
