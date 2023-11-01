package fr.gabinks.clearlag;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    /*private static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();
    public static class General{
        public final ForgeConfigSpec.ConfigValue<Integer> TIME_BETWEEN_CLEARLAG;
        public final ForgeConfigSpec.ConfigValue<String> MOB_FILTER;
        public final ForgeConfigSpec.ConfigValue<String> CL_REMAINING;
        public final ForgeConfigSpec.ConfigValue<String> CL_EXECUTED;
        public General(ForgeConfigSpec.Builder builder){
            builder.push("General");
            TIME_BETWEEN_CLEARLAG = builder
                    .comment("Change the time between every clearlag (in seconds)")
                    .define("time_between_clearlag", 40);
            MOB_FILTER = builder
                    .comment("Add the mobs you want to clear Example : zombie,skeleton,creeper,wither")
                    .define("mob_filter", "");
            CL_REMAINING = builder
                    .comment("Customize the message for the ClearLag. {timeRemaining} is the seconds before ClearLag execution.")
                    .define("clearlag_remaining_time_message", "ClearLag is comming in {timeRemaining} seconds");
            CL_EXECUTED = builder
                    .comment("Customize the message for the ClearLag. {entities} is the number of entities removed.")
                    .define("clearlag_executed_message", "ClearLag removed {entities} entities");
            builder.pop();
        }
    }*/
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;
    static{
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
    public static class CommonConfig{
        public final ForgeConfigSpec.ConfigValue<Integer> TIME_BETWEEN_CLEARLAG;
        public final ForgeConfigSpec.ConfigValue<String> MOB_FILTER;
        public final ForgeConfigSpec.ConfigValue<String> CL_REMAINING;
        public final ForgeConfigSpec.ConfigValue<String> CL_EXECUTED;
        public CommonConfig(ForgeConfigSpec.Builder builder){
            builder.comment("Common configuration settings").push("common");
            TIME_BETWEEN_CLEARLAG = builder
                    .comment("Time between clearlag in seconds")
                    .define("timeBetweenClearlag", 40);
            MOB_FILTER = builder
                    .comment("Example zombie,skeleton,wither,spider")
                    .define("mob_filter", "");
            CL_REMAINING = builder
                    .comment("Modify the broadcasted message for the time remaining before clearlag execution")
                    .define("clearlag_remaining_time_message", "Clearlag comming in {remainingTime} seconds");
            CL_EXECUTED = builder
                    .comment("Modify the broadcasted message when the clearlag was executed")
                    .define("clearlag_executed_message", "Clearlag remove {entities} entities");

            builder.pop();
        }
    }
}
