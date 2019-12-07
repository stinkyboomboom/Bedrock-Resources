package com.alexvr.bedres.entities.effectball;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumSet;
import java.util.Random;

public class EffectBallEntity extends FlyingEntity implements IMob {

    private static final DataParameter<ItemStack> field_213899_f = EntityDataManager.createKey(EffectBallEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<ItemStack> dye = EntityDataManager.createKey(EffectBallEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(EffectBallEntity.class, DataSerializers.BOOLEAN);

    public int explosionPower = 0;

    public EffectBallEntity(EntityType<? extends EffectBallEntity> type, World p_i48553_2_) {
        super(type, p_i48553_2_);
        this.experienceValue = 5;
        this.moveController = new EffectBallEntity.MoveHelperController(this);

    }

    public EffectBallEntity(EntityType<? extends EffectBallEntity> type, World p_i48553_2_,LivingEntity entity) {
        super(type, p_i48553_2_);
        this.experienceValue = 5;
        this.moveController = new EffectBallEntity.MoveHelperController(this);
    }


    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove();
        }
        BlockPos pos = this.getPosition();
        Minecraft.getInstance().worldRenderer.addParticle(ParticleTypes.LARGE_SMOKE,true,pos.getX()+.5,pos.getY()+.8999,pos.getZ()+.5,0,0,0);


    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, new EffectBallEntity.RandomFlyGoal(this));
        this.goalSelector.addGoal(7, new EffectBallEntity.LookAroundGoal(this));
        this.goalSelector.addGoal(7, new EffectBallEntity.AttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_213812_1_) -> {
            return Math.abs(p_213812_1_.posY - this.posY) <= 4.0D;
        }));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(1000.0D);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE){
            this.remove();
            return true;
        }
        return false;

    }

    public void func_213898_b(ItemStack p_213898_1_) {
        this.getDataManager().set(field_213899_f, p_213898_1_);
    }

    public void setDye(ItemStack p_213898_1_) {
        this.getDataManager().set(dye, p_213898_1_);
    }

    protected ItemStack func_213896_l() {
        return this.getDataManager().get(field_213899_f);
    }

    protected ItemStack getDyeData() {
        return this.getDataManager().get(dye);
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        ItemStack itemstack = this.func_213896_l();
        return itemstack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : itemstack;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getDye() {
        ItemStack itemstack = this.getDyeData();
        return itemstack.isEmpty() ? new ItemStack(Items.MAGENTA_DYE) : itemstack;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.dataManager.set(ATTACKING, attacking);
    }

    protected void registerData() {
        super.registerData();
        this.getDataManager().register(field_213899_f, new ItemStack(Items.FIRE_CHARGE));
        this.getDataManager().register(dye, new ItemStack(Items.MAGENTA_DYE));
        this.getDataManager().register(ATTACKING, false);

    }

    public int getMaxSpawnedInChunk() {
        return 4;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.8F;
    }


    static class MoveHelperController extends MovementController {
        private final EffectBallEntity parentEntity;
        private int courseChangeCooldown;

        public MoveHelperController(EffectBallEntity ghast) {
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
        private final EffectBallEntity parentEntity;

        public RandomFlyGoal(EffectBallEntity ghast) {
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
            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 0.4D);
        }
    }

    static class LookAroundGoal extends Goal {
        private final EffectBallEntity parentEntity;

        public LookAroundGoal(EffectBallEntity ghast) {
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

    static class AttackGoal extends Goal {
        private final EffectBallEntity parentEntity;
        public int attackTimer;

        public AttackGoal(EffectBallEntity ghast) {
            this.parentEntity = ghast;
        }
        public boolean shouldExecute() {
            return this.parentEntity.getAttackTarget() != null;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.attackTimer = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.parentEntity.setAttacking(false);
        }
        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.parentEntity.getAttackTarget();
            if (livingentity.getDistanceSq(this.parentEntity) < 4096.0D) {
                if (livingentity.getDistanceSq(this.parentEntity) < 1) {
                    livingentity.attackEntityFrom(DamageSource.MAGIC, 4);
                    this.parentEntity.setAttacking(false);
                    if (((DyeItem)this.parentEntity.getDye().getItem()).getDyeColor().getId() == DyeColor.BLACK.getId()){
                        livingentity.addPotionEffect(new EffectInstance(Effects.WITHER,120,3));
                    }else if (((DyeItem)this.parentEntity.getDye().getItem()).getDyeColor().getId() == DyeColor.GREEN.getId()){
                        livingentity.addPotionEffect(new EffectInstance(Effects.POISON,120,3));
                    }else if (((DyeItem)this.parentEntity.getDye().getItem()).getDyeColor().getId() == DyeColor.WHITE.getId()){
                        livingentity.addPotionEffect(new EffectInstance(Effects.LEVITATION,60,3));
                    }else if (((DyeItem)this.parentEntity.getDye().getItem()).getDyeColor().getId() == DyeColor.BROWN.getId()){
                        livingentity.addPotionEffect(new EffectInstance(Effects.SLOWNESS,120,3));
                    }else if (((DyeItem)this.parentEntity.getDye().getItem()).getDyeColor().getId() == DyeColor.PURPLE.getId()){
                        livingentity.addPotionEffect(new EffectInstance(Effects.WEAKNESS,120,3));
                    }else if (((DyeItem)this.parentEntity.getDye().getItem()).getDyeColor().getId() == DyeColor.YELLOW.getId()){
                        livingentity.addPotionEffect(new EffectInstance(Effects.NAUSEA,180,3));
                    }else if (((DyeItem)this.parentEntity.getDye().getItem()).getDyeColor().getId() == DyeColor.RED.getId()){
                        livingentity.addPotionEffect(new EffectInstance(Effects.HUNGER,120,3));
                    }else if (((DyeItem)this.parentEntity.getDye().getItem()).getDyeColor().getId() == DyeColor.MAGENTA.getId()){
                        livingentity.addPotionEffect(new EffectInstance(Effects.BLINDNESS,120,3));
                    }

                    this.parentEntity.remove();
                }
                this.parentEntity.setAttacking(true);
                BlockPos pos = livingentity.getPosition().offset(livingentity.getHorizontalFacing().getOpposite());
                this.parentEntity.getMoveHelper().setMoveTo(pos.getX(), pos.getY(), pos.getZ(), 25D);

            }
        }
    }


}
