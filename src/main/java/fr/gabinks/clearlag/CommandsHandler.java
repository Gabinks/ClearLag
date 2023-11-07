package fr.gabinks.clearlag;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandsHandler {
    public static LiteralArgumentBuilder<CommandSourceStack> register(){
        return Commands.literal("clear")
                .executes(Events::clearEntityCommand);
    }
}
