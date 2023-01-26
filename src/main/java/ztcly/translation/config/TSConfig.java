package ztcly.translation.config;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ztcly.translation.TranslationMod;

@Config(modid = TranslationMod.MODID)
@Config.LangKey("config.translation.general")
public final class TSConfig {
    @Config.LangKey("config.translation.general.appkey")
    public static String APP_KEY ="app_key";

    @Config.LangKey("config.translation.general.appsecret")
    public static String APP_SECRET ="app_secert";

    @Mod.EventBusSubscriber(modid = TranslationMod.MODID)
    public static class ConfigSyncHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(TranslationMod.MODID)) {
                ConfigManager.sync(TranslationMod.MODID, Config.Type.INSTANCE);
            }
        }
    }
}


