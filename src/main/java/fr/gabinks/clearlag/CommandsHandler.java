package fr.gabinks.clearlag;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

public class CommandsHandler {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("clearlag")
                .executes((context) -> {
                    context.getSource().sendSystemMessage(Component.literal("Etat actuelle du ClearLag : " + Events.started +
                            "\nCommandes pour le ClearLag\n" +
                                    "clear | Permets d'effectuer le clearlag manuellement.\n"+
                            "start | Permets de lancer le ClearLag automatique (si celui-ci avait été désactiver.)\n" +
                            "stop | Permet de stopper le ClearLag automatique (si celui-ci étais activer)"));
                    return 1;
                }).then(Commands.literal("clear").executes(context -> {
                    ClearCommand.clearEntityCommand(context.getSource().getServer());
                    return 1;
                })).then(Commands.literal("start").executes(context -> {
                    if (Events.started == false) {
                        Events.started = true;
                        context.getSource().sendSystemMessage(Component.literal("Vous venez d'activez le clearlag."));
                    } else {
                        context.getSource().sendSystemMessage(Component.literal("Le clearlag est déjà démarré."));
                    }
                    return 1;
                })).then(Commands.literal("stop").executes(context -> {
                    if (Events.started == true) {
                        Events.started = false;
                        context.getSource().sendSystemMessage(Component.literal("Vous venez de stoppé le clearlag."));
                    } else {
                        context.getSource().sendSystemMessage(Component.literal("Le clearlag est déjà stoppé."));
                    }
                    return 1;
                }));
    }
}
