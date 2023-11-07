package fr.gabinks.clearlag;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand {
    static String MsgExecuted = Config.cl_executed.get();

    public static void clearEntityCommand(MinecraftServer server)  {
        List<Entity> actualList = new ArrayList<>();
        server.getAllLevels().forEach(serverLevel -> {
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
        server.getPlayerList().broadcastSystemMessage(Component.literal(MsgExecuted.replace("{entities}", String.valueOf(entityCleared.toArray().length))), false);
    }
}
