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

import javax.annotation.Nullable;

public class FluxedGravityBubbleTile extends TileEntity implements ITickableTileEntity {


    public boolean playerInArea = false;
    int xDist = 4,yDist =4,zDist=4;
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
            if (counter % 10 == 0 ) {
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
                                        player.addVelocity(0, player.getMotion().y / 4, 0);
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
                        for (PlayerEntity player : world.getPlayers()) {
                            if (!player.isCreative() && player.getName().getFormattedText().equals(name)) {
                                player.abilities.allowFlying = false;
                                player.abilities.isFlying = false;
                                player.sendPlayerAbilities();
                                name = "NA$null";
                                player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 40, 4,true,false));

                            }
                        }
                    }
                    markDirty();
                    sendUpdates();
                }
            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        playerInArea = tag.getBoolean("playerIn");
        name = tag.getString("playerInName");
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putBoolean("playerIn",playerInArea);
        tag.putString("playerInName",name);
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
