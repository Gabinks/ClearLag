package fr.gabinks.clearlag;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Events {
    int ticks = 0;
    int seconds = 0;
    int execute = Config.timebetweenclearlag.get();
    String MsgRemainingTime = Config.cl_remaining.get();
    static String MsgExecuted = Config.cl_executed.get();
    static boolean started = true;

    @SubscribeEvent
    public void tickEvent(TickEvent.ServerTickEvent event) {
        if (started) {
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
        JSONObject jsonPrincipal = new JSONObject();
        JSONObject jsonDate = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
        String dateStr = sdf.format(new Date());
        List<Entity> items = new ArrayList<>();
        entityCleared.forEach(entity -> {
            if (entity.getType().toString().contains("item") && !entity.getType().toString().contains("item_frame") && !entity.getType().toString().contains("item_frame") && !entity.getType().toString().contains("armor_stand") && !entity.getType().toString().contains("painting")){
                items.add(entity);
                JSONObject jsonNomItem = new JSONObject();
                jsonNomItem.put("Coordinate", entity.xo + "," + entity.yo + "," + entity.zo);
                jsonDate.put(entity.getName().toString(), jsonNomItem);
            }
        });
        jsonPrincipal.put(dateStr, jsonDate);
        if(items.size() >= 0){
            try(FileWriter fichier = new FileWriter("logs/clearlag_logs/clearlag-logs-"+dateStr+".json")){
                fichier.write(jsonPrincipal.toString());
                System.out.println("Fichier JSON créé avec succès : " + "clearlag-logs-"+dateStr);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        event.getServer().getPlayerList().broadcastSystemMessage(Component.literal(MsgExecuted.replace("{entities}", String.valueOf(entityCleared.toArray().length))), false);

    }
}
