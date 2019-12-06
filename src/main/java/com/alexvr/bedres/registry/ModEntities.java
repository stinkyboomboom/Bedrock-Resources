package com.alexvr.bedres.registry;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.entities.effectball.EffectBallEntity;
import com.alexvr.bedres.entities.fluxedcreep.FluxedCreepEntity;
import com.alexvr.bedres.entities.sporedeity.SporeDeityEntity;
import com.alexvr.bedres.utils.References;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModEntities {

    @ObjectHolder(BedrockResources.MODID+ ":"+ References.FLUXED_CREEP_REGNAME)
    public static EntityType<FluxedCreepEntity> FLUXED_CREEP;

    @ObjectHolder(BedrockResources.MODID+ ":"+ References.SPORE_DEITY_REGNAME)
    public static EntityType<SporeDeityEntity> sporeDeityEntityEntityType;

    @ObjectHolder(BedrockResources.MODID+ ":"+ References.EFFECT_BALL_REGNAME)
    public static EntityType<EffectBallEntity> effectBallEntityEntityType;
}
