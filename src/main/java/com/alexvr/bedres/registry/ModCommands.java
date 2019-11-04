package com.alexvr.bedres.registry;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.command.CommandHelp;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher){
        LiteralCommandNode<CommandSource> cmdBedRes = dispatcher.register(
                Commands.literal(BedrockResources.MODID)
                .then(CommandHelp.register(dispatcher))
        );
        dispatcher.register(Commands.literal("bedres").redirect(cmdBedRes));
    }

}
