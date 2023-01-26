package ztcly.translation;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import ztcly.translation.command.TrslCommand;

@Mod(modid = TranslationMod.MODID, name = TranslationMod.NAME, version = TranslationMod.VERSION,guiFactory = "ztcly.translation.config.TSConfigGUIFactory",clientSideOnly = true)
public class TranslationMod
{
    public static final String MODID = "translation";
    public static final String NAME = "translation";
    public static final String VERSION = "0.1";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ClientCommandHandler.instance.registerCommand(new TrslCommand());
        // some example code
        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
