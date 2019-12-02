package com.alexvr.bedres.entities.sporedeity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class SporeDeityEntity extends MonsterEntity implements IMob {

    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(SporeDeityEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> SUMMONER = EntityDataManager.createKey(SporeDeityEntity.class, DataSerializers.STRING);


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

    @OnlyIn(Dist.CLIENT)
    public String getSummoner() {
        return this.dataManager.get(SUMMONER);
    }

    public void setSummoner(String name) {
        this.dataManager.set(SUMMONER, name);
    }

    public void tick() {
        super.tick();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, false);
        this.dataManager.register(SUMMONER, "");
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
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(1000.0D);
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMBIENT_CAVE;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PLAYER_DEATH;
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
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public EntityClassification getClassification(boolean forSpawnCount) {
        return EntityClassification.MONSTER;
    }

    public int getMaxSpawnedInChunk() {
        return 4;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 1.75F;
    }

}
