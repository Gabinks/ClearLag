package fr.gabinks.clearlag;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.File;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ClearLag.MODID)
@Mod.EventBusSubscriber(modid = ClearLag.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClearLag {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "clearlag";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public ClearLag() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
        File logsFolder = new File("logs/", "clearlag_logs");
        if(!logsFolder.exists()){
            boolean creationSuccess = logsFolder.mkdir();
            if(creationSuccess){
                System.out.println("[ClearLags] Logs folder created successfully : " + logsFolder.getAbsolutePath());
            }
        }
        else{
            System.out.println("[ClearLags] Logs folder already exists, skipping.");
        }
    }
    private void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(CommandsHandler.register());
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        MinecraftForge.EVENT_BUS.register(new Events());
        LOGGER.info("[Server Side] ClearLag started.");
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
        }
    }
}
