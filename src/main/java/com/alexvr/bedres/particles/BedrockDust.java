package com.alexvr.bedres.particles;

import com.alexvr.bedres.registry.ModBlocks;
import com.alexvr.bedres.registry.ModParticleSprites;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BedrockDust extends ModParticle {
    private final float rotSpeed;
    private float angle;
    private final float scale;
    private final int MAX_FRAME_ID = 5;
    protected int currentFrame = 0;
    private boolean directionRight = true;
    private int lastTick = 0;
    private double initialX,initialY,initialZ;
    private double[] nextposition;

    public BedrockDust(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        initialX=posX;
        initialY=posY;
        initialZ=posZ;
        this.motionX = this.motionX * 0.009999999776482582d + motionX;
        this.motionY = this.motionY * 0.009999999776482582d + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582d + motionZ;
        this.scale = this.particleScale = 0.25f;
        this.rotSpeed = ((float)Math.random() - 0.5F) * 0.1F;
        this.particleAngle = (float)Math.random() * ((float)Math.PI * 2F);
        this.angle = (float)Math.random() * ((float)Math.PI * 2F);
        this.particleGravity = 0.0F;
        this.particleRed = 1f;
        this.particleGreen = 1f;
        this.particleBlue = 1f;
        this.age=0;
        this.maxAge = 20*7;
        nextposition = new double[3];
        nextposition[0] = initialX + new Random().nextFloat()-0.5f;
        nextposition[1] = initialY + new Random().nextFloat()-0.5f;
        nextposition[2] = initialZ + new Random().nextFloat()-0.5f;
        this.canCollide = false;


    }

    @Override
    public void move(double x, double y, double z) {
        if(nextposition[0] > posX){
            x = 0.005;
        }else if(nextposition[0] < posX){
            x = -0.005;
        }
        if(nextposition[1] > posY){
            y = 0.005;
        }else if(nextposition[1] < posY){
            y = -0.005;
        }

        if(nextposition[2] > posZ){
            z = 0.005;
        }else if(nextposition[2] < posZ){
            z = -0.005;
        }
        if(posX < nextposition[0] +0.005 && posX > nextposition[0] -0.005 && posY < nextposition[1] +0.005 && posY > nextposition[1] -0.005 && posZ < nextposition[2] +0.005 && posZ > nextposition[2] -0.005){
            nextposition[0] = initialX + new Random().nextFloat()-0.5f;
            nextposition[1] = initialY + new Random().nextFloat()-0.5f;
            nextposition[2] = initialZ + new Random().nextFloat()-0.5f;

        }

        super.move(x,y,z);

    }

    @Override
    public void onPreRender(BufferBuilder buffer, ActiveRenderInfo activeInfo, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        super.onPreRender(buffer, activeInfo, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        Entity entity = activeInfo.getRenderViewEntity();
        if (entity.ticksExisted >= this.lastTick + 5) {
            if (this.currentFrame == MAX_FRAME_ID) {
                this.directionRight = false;
            } else if (currentFrame == 0) {
                this.directionRight = true;
            }
            this.currentFrame = this.currentFrame + (directionRight ? 1 : -1);
            this.lastTick = entity.ticksExisted;
        }
        float f = ((float) this.age + partialTicks) / (float) this.maxAge;
        this.particleScale = this.scale * (1f - f * f * 0.5f);
    }

    @Override
    public void tick() {
        if(!world.getBlockState(new BlockPos(initialX,initialY,initialZ)).getBlock().getRegistryName().equals(ModBlocks.scrapeTank.getRegistryName()) ){
            this.age++;
        }else{
            this.age=0;
        }
        super.tick();
        this.prevParticleAngle = this.particleAngle;
        this.particleAngle += (float)Math.PI * this.rotSpeed * 2.0F;
        if(this.posX> initialX || this.posX < initialX){
            this.motionX *=-1;
        }else{
            this.motionX += Math.cos(angle) * 0.0025;

        }

        if (this.posY> initialY || this.posY < initialY){
            this.motionY *=-1;
        }else{
            this.motionY += Math.sin(angle) * 0.0025;
        }

        if (this.posZ> initialZ || this.posZ < initialZ){
            this.motionZ *=-1;
        }else{
            this.motionZ += Math.sin(angle) * 0.0025;
        }

    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        float f = ((float) this.age + partialTick) / (float) this.maxAge;
        f = MathHelper.clamp(f, 0f, 1f);
        int i = super.getBrightnessForRender(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j = j + (int) (f * 15f * 16f);
        if (j > 240) {
            j = 240;
        }
        return j | k << 16;
    }

    @Override
    ResourceLocation getTexture() {
        return ModParticleSprites.BEDROCK_DUST;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticle {

        @Override
        public Particle makeParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... params) {
            return new BedrockDust(world, x, y, z, xSpeed, ySpeed, zSpeed);
        }

    }
}
