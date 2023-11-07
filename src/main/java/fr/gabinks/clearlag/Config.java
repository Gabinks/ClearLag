package fr.gabinks.clearlag;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(modid = ClearLag.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec.ConfigValue<Integer> timebetweenclearlag;
    public static ForgeConfigSpec.ConfigValue<String> mob_filter;
    public static ForgeConfigSpec.ConfigValue<String> cl_remaining;
    public static ForgeConfigSpec.ConfigValue<String> cl_executed;
    static{
        ForgeConfigSpec.Builder serverConfigBuilder = new ForgeConfigSpec.Builder();
        serverConfigBuilder.comment("ClearLag configuration").push("general");
        serverConfigBuilder.comment("THE CLEAR LAG DON'T REMOVE ENTITY WITH CUSTOM NAME");
        serverConfigBuilder.comment("CLEAR LAG REMOVE AUTOMATICALLY ALL ITEMS ON THE GROUND");
        timebetweenclearlag = serverConfigBuilder
                .comment("Time Between each clear lags (in seconds)")
                .define("time_between_clearlag", 40);
        mob_filter = serverConfigBuilder
                .comment("Configure the mob to remove at clear lag. Example zombie,skeleton,wither,spider (Default clear all vanilla mobs and items)")
                .define("mob_filter", "");
        cl_remaining = serverConfigBuilder
                .comment("Configure the message when the clear lag is approaching. {timeRemaining} is the seconds remaining before clear lag so keep it in your sentence.")
                .define("clearlag_remaining_time_msg", "Clearlag is coming in {timeRemaining} seconds");
        cl_executed = serverConfigBuilder
                .comment("Configure the message when the clear lag is executed. {entities} is the number of entities removed so keep it in your sentence.")
                .define("clearlag_executed_message", "Clearlag removed {entities} entities");
        serverConfigBuilder.pop();

        SERVER_CONFIG = serverConfigBuilder.build();
    }
}
