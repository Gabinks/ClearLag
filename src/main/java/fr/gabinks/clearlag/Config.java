package fr.gabinks.clearlag;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
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
