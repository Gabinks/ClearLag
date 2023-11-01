package fr.gabinks.clearlag;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class Events {
    int ticks = 0;
    int seconds = 0;
    int execute = Config.COMMON.TIME_BETWEEN_CLEARLAG.get();
    String MsgRemainingTime = Config.COMMON.CL_REMAINING.get();
    String MsgExecuted = Config.COMMON.CL_EXECUTED.get();
    @SubscribeEvent
    public void tickEvent(TickEvent.ServerTickEvent event){
        if(event.side.isServer()){
            ticks++;
            if(ticks == 40){
                ticks = 0;
                seconds++;
                if(seconds == (execute-20)){
                    event.getServer().getPlayerList().broadcastSystemMessage(Component.literal(MsgRemainingTime.replace("{timeRemaining}", Integer.toString((execute-seconds)))), false);
                }
                else if(seconds == (execute-10)){
                    event.getServer().getPlayerList().broadcastSystemMessage(Component.literal(MsgRemainingTime.replace("{timeRemaining}", Integer.toString((execute-seconds)))), false);
                }
                else if(seconds == (execute-5)){
                    event.getServer().getPlayerList().broadcastSystemMessage(Component.literal(MsgRemainingTime.replace("{timeRemaining}", Integer.toString((execute-seconds)))), false);
                }
                else if(seconds == execute){
                    List<Entity> actualList = new ArrayList<>();
                    event.getServer().overworld().getAllEntities().iterator().forEachRemaining(actualList::add);
                    List<Entity> entityCleared = new ArrayList<>();
                    actualList.forEach(entity -> {
                        if(!Config.COMMON.MOB_FILTER.get().isEmpty()){
                            for(String mob : Config.COMMON.MOB_FILTER.get().split(",")){
                                if(!entity.hasCustomName() && entity.getType().toString().contains(mob) && !entity.getType().toString().contains("player")){
                                    entityCleared.add(entity);
                                    entity.discard();
                                }
                                if(entity.getType().toString().contains("item")){
                                    entityCleared.add(entity);
                                    entity.discard();
                                }
                            }
                        }
                        else{
                            if(!entity.hasCustomName() && entity.getType().toString().contains("minecraft") && !entity.getType().toString().contains("player")){
                                entityCleared.add(entity);
                                entity.discard();
                            }
                            if(entity.getType().toString().contains("item")){
                                entityCleared.add(entity);
                                entity.discard();
                            }
                        }
                    });
                    event.getServer().getPlayerList().broadcastSystemMessage(Component.literal(MsgExecuted.replace("{entities}", String.valueOf(entityCleared.toArray().length))), false);
                    seconds = 0;
                }
            }
        }
    }
}
