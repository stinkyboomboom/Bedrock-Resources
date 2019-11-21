package com.alexvr.bedres.blocks.tiles;

import com.alexvr.bedres.registry.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FluxedGravityBubbleTile extends TileEntity implements ITickableTileEntity {


    public boolean playerInArea = false,render = false;
    public int xDist = 4,yDist =4,zDist=4;
    public int maxxDist = 4,maxyDist =4,maxzDist=4;
    String name = "NA$null";
    int counter = 0;


    public FluxedGravityBubbleTile() {
        super(ModBlocks.fluxedGravityBubbleTileTileEntityType);
    }

    @Override
    public void tick() {
        if(!world.isRemote) {
            counter++;
            boolean flag = false;
            if (counter % 2 == 0 ) {
                counter=0;
                for (int x = -xDist; x < xDist + 1; x++) {
                    for (int y = -yDist; y < yDist + 1; y++) {
                        for (int z = -zDist; z < zDist + 1; z++) {
                            if (x==0&&y==0&&z==0){
                                continue;
                            }
                            for (PlayerEntity player : world.getPlayers()) {
                                if (player.getPosition().equals(getPos().north(x).east(z).up(y))) {
                                    if (!player.abilities.isFlying && !player.isSneaking() && !player.onGround) {
                                        player.addVelocity(0, 0, 0);
                                        player.velocityChanged = true;

                                    }
                                    if (!player.abilities.allowFlying && player.getName().getFormattedText().equals(name) || (!player.isCreative() && name.contains("NA$null"))) {
                                        player.abilities.allowFlying = true;
                                        player.fallDistance = 0;
                                        player.abilities.isFlying = true;
                                        name = player.getName().getFormattedText();
                                        player.sendPlayerAbilities();

                                    }
                                    playerInArea = true;
                                    flag = true;
                                    markDirty();
                                    sendUpdates();
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!flag && playerInArea) {
                    playerInArea = false;
                    if (!name.contains("NA$null")) {
                        flightChange(this,world,false);
                    }

                }
            }
        }
    }

    public static void flightChange(FluxedGravityBubbleTile tile, World world, boolean canFly){
        for (PlayerEntity player : world.getPlayers()) {
            if (!player.isCreative() && player.getName().getFormattedText().equals(tile.name)) {
                player.abilities.allowFlying = canFly;
                player.abilities.isFlying = canFly;
                player.sendPlayerAbilities();
                tile.name = "NA$null";
                player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 200, 4,true,false));
                tile.markDirty();
                tile.sendUpdates();
                return;
            }
        }
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 4096.0D;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public void read(CompoundNBT tag) {
        playerInArea = tag.getBoolean("playerIn");
        render = tag.getBoolean("render");
        name = tag.getString("playerInName");
        xDist = tag.getInt("xDist");
        yDist = tag.getInt("yDist");
        zDist = tag.getInt("zDist");
        maxxDist = tag.getInt("maxxDist");
        maxyDist = tag.getInt("maxyDist");
        maxzDist = tag.getInt("maxzDist");
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putBoolean("playerIn",playerInArea);
        tag.putBoolean("render",render);
        tag.putString("playerInName",name);
        tag.putInt("xDist",xDist);
        tag.putInt("yDist",yDist);
        tag.putInt("zDist",zDist);
        tag.putInt("maxxDist",maxxDist);
        tag.putInt("maxyDist",maxyDist);
        tag.putInt("maxzDist",maxzDist);
        return super.write(tag);
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    public void sendUpdates() {
        world.notifyBlockUpdate(pos, this.getBlockState(), getBlockState(), 3);
        markDirty();
    }

}
