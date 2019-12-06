package com.alexvr.bedres.entities.effectball;

import com.alexvr.bedres.registry.ModEntities;
import com.alexvr.bedres.utils.WorldEventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EffectBallEntity extends DamagingProjectileEntity {

    private static final DataParameter<ItemStack> field_213899_f = EntityDataManager.createKey(EffectBallEntity.class, DataSerializers.ITEMSTACK);
    public int explosionPower = 0;

    public EffectBallEntity(EntityType<? extends EffectBallEntity> p_i50166_1_, World p_i50166_2_) {
        super(p_i50166_1_, p_i50166_2_);
    }

    public EffectBallEntity(EntityType<? extends EffectBallEntity> p_i50167_1_, double p_i50167_2_, double p_i50167_4_, double p_i50167_6_, double p_i50167_8_, double p_i50167_10_, double p_i50167_12_, World p_i50167_14_) {
        super(p_i50167_1_, p_i50167_2_, p_i50167_4_, p_i50167_6_, p_i50167_8_, p_i50167_10_, p_i50167_12_, p_i50167_14_);
    }

    public EffectBallEntity(EntityType<? extends EffectBallEntity> p_i50168_1_, LivingEntity p_i50168_2_, double p_i50168_3_, double p_i50168_5_, double p_i50168_7_, World p_i50168_9_) {
        super(p_i50168_1_, p_i50168_2_, p_i50168_3_, p_i50168_5_, p_i50168_7_, p_i50168_9_);
    }

    @OnlyIn(Dist.CLIENT)
    public EffectBallEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        this(ModEntities.effectBallEntityEntityType, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    public EffectBallEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        this(ModEntities.effectBallEntityEntityType, shooter, accelX, accelY, accelZ, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

    }

    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                Entity entity = ((EntityRayTraceResult)result).getEntity();
                entity.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0F);
                this.applyEnchantments(this.shootingEntity, entity);
            }
            WorldEventHandler.transformArea(world,getPosition().offset(Direction.DOWN),1);
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.shootingEntity);
            this.world.createExplosion((Entity)null, this.posX, this.posY, this.posZ, (float)this.explosionPower, flag, flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
            this.remove();
        }

    }

    public void func_213898_b(ItemStack p_213898_1_) {
        if (p_213898_1_.getItem() != Items.FIRE_CHARGE || p_213898_1_.hasTag()) {
            this.getDataManager().set(field_213899_f, Util.make(p_213898_1_.copy(), (p_213897_0_) -> {
                p_213897_0_.setCount(1);
            }));
        }

    }

    protected ItemStack func_213896_l() {
        return this.getDataManager().get(field_213899_f);
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        ItemStack itemstack = this.func_213896_l();
        return itemstack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : itemstack;
    }

    protected void registerData() {
        this.getDataManager().register(field_213899_f, new ItemStack(Items.FIRE_CHARGE));
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        ItemStack itemstack = this.func_213896_l();
        if (!itemstack.isEmpty()) {
            compound.put("Item", itemstack.write(new CompoundNBT()));
        }
        compound.putInt("ExplosionPower", this.explosionPower);


    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        ItemStack itemstack = ItemStack.read(compound.getCompound("Item"));
        this.func_213898_b(itemstack);
        if (compound.contains("ExplosionPower", 99)) {
            this.explosionPower = compound.getInt("ExplosionPower");
        }
    }
}
