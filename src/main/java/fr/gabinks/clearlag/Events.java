package fr.gabinks.clearlag;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class Events {
    int ticks = 0;
    int seconds = 0;
    int execute = Config.timebetweenclearlag.get();
    String MsgRemainingTime = Config.cl_remaining.get();
    static String MsgExecuted = Config.cl_executed.get();

    @SubscribeEvent
    public void tickEvent(TickEvent.ServerTickEvent event) {
        if (event.side.isServer()) {
            ticks++;
            if (ticks == 40) {
                ticks = 0;
                seconds++;
                if (seconds == (execute - 20)) {
                    event.getServer().getPlayerList().broadcastSystemMessage(Component.literal(MsgRemainingTime.replace("{timeRemaining}", Integer.toString((execute - seconds)))), false);
                } else if (seconds == (execute - 10)) {
                    event.getServer().getPlayerList().broadcastSystemMessage(Component.literal(MsgRemainingTime.replace("{timeRemaining}", Integer.toString((execute - seconds)))), false);
                } else if (seconds == (execute - 5)) {
                    event.getServer().getPlayerList().broadcastSystemMessage(Component.literal(MsgRemainingTime.replace("{timeRemaining}", Integer.toString((execute - seconds)))), false);
                } else if (seconds == execute) {
                    clearEntity(event);
                    seconds = 0;
                }
            }
        }
    }

    public static void clearEntity(TickEvent.ServerTickEvent event) {
        List<Entity> actualList = new ArrayList<>();
        event.getServer().getAllLevels().forEach(serverLevel -> {
            serverLevel.getAllEntities().iterator().forEachRemaining(actualList::add);
        });
        List<Entity> entityCleared = new ArrayList<>();
        actualList.forEach(entity -> {
            if (!Config.mob_filter.get().isEmpty()) {
                for (String mob : Config.mob_filter.get().split(",")) {
                    if (!entity.hasCustomName() && entity.getType().toString().contains(mob) && !entity.getType().toString().contains("player") && !entity.getType().toString().contains("item_frame") && !entity.getType().toString().contains("armor_stand") && !entity.getType().toString().contains("painting")) {
                        entityCleared.add(entity);
                        entity.discard();
                    }
                    if (entity.getType().toString().contains("item") && !entity.getType().toString().contains("item_frame")) {
                        entityCleared.add(entity);
                        entity.discard();
                    }
                }
            } else {
                if (!entity.hasCustomName() && entity.getType().toString().contains("minecraft") && !entity.getType().toString().contains("player") && !entity.getType().toString().contains("item_frame") && !entity.getType().toString().contains("armor_stand") && !entity.getType().toString().contains("painting")) {
                    entityCleared.add(entity);
                    entity.discard();
                }
                if (entity.getType().toString().contains("item") && !entity.getType().toString().contains("item_frame")) {
                    entityCleared.add(entity);
                    entity.discard();
                }
            }
        });
        event.getServer().getPlayerList().broadcastSystemMessage(Component.literal(MsgExecuted.replace("{entities}", String.valueOf(entityCleared.toArray().length))), false);
    }

    public static int clearEntityCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSource source = (CommandSource) context.getSource();
        clearEntity(new TickEvent.ServerTickEvent(TickEvent.Phase.START, new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return false;
            }
        }, context.getSource().getServer()));
        return 1;
    }
}
