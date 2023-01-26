package ztcly.translation.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import ztcly.translation.TranslationMod;

import java.util.Collections;
import java.util.Set;

public class TSConfigGUIFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft mc) {
        
    }

    @Override
    public boolean hasConfigGui() {
        return true; 
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parent) {
        return new GuiConfig(parent, ConfigElement.from(TSConfig.class).getChildElements(), TranslationMod.MODID, false, false, "TSModConfig", "= =");
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        
        return Collections.emptySet();
    }
}
