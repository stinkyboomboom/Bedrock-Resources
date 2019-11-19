package com.alexvr.bedres.entities.fluxedcreep;

import com.alexvr.bedres.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumSet;
import java.util.Random;

public class FluxedCreepEntity extends FlyingEntity implements IMob {

    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(FluxedCreepEntity.class, DataSerializers.BOOLEAN);


    public FluxedCreepEntity(EntityType<? extends FluxedCreepEntity> p_i50206_1_, World p_i50206_2_) {
        super(p_i50206_1_, p_i50206_2_);
        this.experienceValue = 5;
        this.moveController = new FluxedCreepEntity.MoveHelperController(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, new FluxedCreepEntity.RandomFlyGoal(this));
        this.goalSelector.addGoal(7, new FluxedCreepEntity.LookAroundGoal(this));
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
        if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove();
        }
        BlockPos pos = new BlockPos(this.getPosition());
        Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.LARGE_SMOKE,true,pos.getX(),pos.getY()+.6,pos.getZ(),0,0,0);

    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, false);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source.getImmediateSource() instanceof FireballEntity && source.getTrueSource() instanceof PlayerEntity) {
            super.attackEntityFrom(source, 1000.0F);
            return true;
        } else {
            return super.attackEntityFrom(source, amount);
        }
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        if (this.getDisplayName().getUnformattedComponentText().contains("Favi")){
            return ModSounds.FLUXED_CREEP_IDLEOG.getSound();
        }
        return ModSounds.FLUXED_CREEP_IDLE.getSound();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        if (this.getDisplayName().getUnformattedComponentText().contains("Favi")){
            return ModSounds.FLUXED_CREEP_ROAROG.getSound();
        }
        return ModSounds.FLUXED_CREEP_ROAR.getSound();
    }

    protected SoundEvent getDeathSound() {
        if (this.getDisplayName().getUnformattedComponentText().contains("Favi")){
            return ModSounds.FLUXED_CREEP_ROAROG.getSound();
        }
        return ModSounds.FLUXED_CREEP_ROAR.getSound();
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 8.0F;
    }

    public static boolean func_223368_b(EntityType<FluxedCreepEntity> p_223368_0_, IWorld p_223368_1_, SpawnReason p_223368_2_, BlockPos p_223368_3_, Random p_223368_4_) {
        return p_223368_1_.getDifficulty() != Difficulty.PEACEFUL && p_223368_4_.nextInt(20) == 0 && func_223315_a(p_223368_0_, p_223368_1_, p_223368_2_, p_223368_3_, p_223368_4_);
    }

    public int getMaxSpawnedInChunk() {
        return 4;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.5F;
    }

    static class MoveHelperController extends MovementController {
        private final FluxedCreepEntity parentEntity;
        private int courseChangeCooldown;

        public MoveHelperController(FluxedCreepEntity ghast) {
            super(ghast);
            this.parentEntity = ghast;
        }

        public void tick() {
            if (this.action == MovementController.Action.MOVE_TO) {
                if (this.courseChangeCooldown-- <= 0) {
                    this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
                    Vec3d vec3d = new Vec3d(this.posX - this.parentEntity.posX, this.posY - this.parentEntity.posY, this.posZ - this.parentEntity.posZ);
                    double d0 = vec3d.length();
                    vec3d = vec3d.normalize();
                    if (this.func_220673_a(vec3d, MathHelper.ceil(d0))) {
                        this.parentEntity.setMotion(this.parentEntity.getMotion().add(vec3d.scale(0.1D)));
                    } else {
                        this.action = MovementController.Action.WAIT;
                    }
                }

            }
        }

        private boolean func_220673_a(Vec3d p_220673_1_, int p_220673_2_) {
            AxisAlignedBB axisalignedbb = this.parentEntity.getBoundingBox();

            for(int i = 1; i < p_220673_2_; ++i) {
                axisalignedbb = axisalignedbb.offset(p_220673_1_);
                if (!this.parentEntity.world.isCollisionBoxesEmpty(this.parentEntity, axisalignedbb)) {
                    return false;
                }
            }

            return true;
        }
    }

    static class RandomFlyGoal extends Goal {
        private final FluxedCreepEntity parentEntity;

        public RandomFlyGoal(FluxedCreepEntity ghast) {
            this.parentEntity = ghast;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            MovementController movementcontroller = this.parentEntity.getMoveHelper();
            if (!movementcontroller.isUpdating()) {
                return true;
            } else {
                double d0 = movementcontroller.getX() - this.parentEntity.posX;
                double d1 = movementcontroller.getY() - this.parentEntity.posY;
                double d2 = movementcontroller.getZ() - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            Random random = this.parentEntity.getRNG();
            double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
            double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 0.5D);
        }
    }

    static class LookAroundGoal extends Goal {
        private final FluxedCreepEntity parentEntity;

        public LookAroundGoal(FluxedCreepEntity ghast) {
            this.parentEntity = ghast;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.parentEntity.getAttackTarget() == null) {
                Vec3d vec3d = this.parentEntity.getMotion();
                this.parentEntity.rotationYaw = -((float)MathHelper.atan2(vec3d.x, vec3d.z)) * (180F / (float)Math.PI);
                this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
            } else {
                LivingEntity livingentity = this.parentEntity.getAttackTarget();
                double d0 = 64.0D;
                if (livingentity.getDistanceSq(this.parentEntity) < 4096.0D) {
                    double d1 = livingentity.posX - this.parentEntity.posX;
                    double d2 = livingentity.posZ - this.parentEntity.posZ;
                    this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
                    this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
                }
            }

        }
    }

}
