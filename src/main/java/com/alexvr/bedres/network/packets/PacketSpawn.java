package com.alexvr.bedres.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


    public class PacketSpawn {

        private final String id;
        private final DimensionType type;
        private final BlockPos pos;

        public PacketSpawn(PacketBuffer buf) {
            id = buf.readString();
            type = DimensionType.getById(buf.readInt());
            pos = buf.readBlockPos();
        }

        public PacketSpawn(String id, DimensionType type, BlockPos pos) {
            this.id = id;
            this.type = type;
            this.pos = pos;
        }

        public void toBytes(PacketBuffer buf) {
            buf.writeString(id);
            buf.writeInt(type.getId());
            buf.writeBlockPos(pos);
        }

        public void handle(Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerWorld spawnWorld = ctx.get().getSender().world.getServer().getWorld(type);
                BlockPos pos2 = spawnWorld.findNearestStructure("altar",pos,1024,true);

                if (id.equals("bedres:altar")){
                    ctx.get().getSender().addPotionEffect(new EffectInstance(Effects.ABSORPTION,180,40));
                    ctx.get().getSender().teleport(spawnWorld,pos2.getX(),pos2.getY() + 200,pos2.getZ(),ctx.get().getSender().getYaw(0),ctx.get().getSender().getPitch(0));
                }
                //entityType.spawn(spawnWorld, null, null, pos, SpawnReason.SPAWN_EGG, true, true);
            });
            ctx.get().setPacketHandled(true);
        }

}
