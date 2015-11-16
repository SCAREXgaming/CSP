package fr.scarex.csp;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class CSPConfiguration implements IModGuiFactory
{
    public static Configuration CONFIG;
    public static int solarCellBasicProduction = 5;
    public static int solarCellHardenedProduction = 10;
    public static int solarCellReinforcedProduction = 30;
    public static int solarCellResonantProduction = 50;
    public static byte productionUpgradeStackSize = 8;
    public static int powerUpgradeStorageRatio = 1000;
    public static int powerUpgradeOutputRatio = 64;
    public static boolean useSolarPanelFrameCoolRender = true;
    public static int solarPanelFrameRenderDistance = 16;

    @Override
    public void initialize(Minecraft minecraftInstance) {
        FMLCommonHandler.instance().bus().register(this);
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return CSPConfigGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static void syncConfig() {
        solarCellBasicProduction = CONFIG.getInt("solarCellBasicProduction", Configuration.CATEGORY_GENERAL, 5, 0, 500, "Basic SolarCell max production");
        solarCellHardenedProduction = CONFIG.getInt("solarCellHardenedProduction", Configuration.CATEGORY_GENERAL, 10, 0, 500, "Hardened SolarCell max production");
        solarCellReinforcedProduction = CONFIG.getInt("solarCellReinforcedProduction", Configuration.CATEGORY_GENERAL, 30, 0, 500, "Reinforced SolarCell max production");
        solarCellResonantProduction = CONFIG.getInt("solarCellResonantProduction", Configuration.CATEGORY_GENERAL, 50, 0, 500, "Resonant SolarCell max production");
        productionUpgradeStackSize = (byte) CONFIG.getInt("productionUpgradeStackSize", Configuration.CATEGORY_GENERAL, 8, 0, 64, "Maximum of production upgrades to reach 200% production");
        powerUpgradeStorageRatio = CONFIG.getInt("powerUpgradeStorageRatio", Configuration.CATEGORY_GENERAL, 1000, 0, 4000, "Energy storage ratio for the power upgrade");
        powerUpgradeOutputRatio = CONFIG.getInt("powerUpgradeOutputRatio", Configuration.CATEGORY_GENERAL, 64, 0, 400, "Energy output ratio for the power upgrade");
        useSolarPanelFrameCoolRender = CONFIG.getBoolean("useSolarPanelFrameCoolRender", Configuration.CATEGORY_GENERAL, true, "Use cool rendering for the solar panels");
        solarPanelFrameRenderDistance = CONFIG.getInt("solarPanelFrameRenderDistance", Configuration.CATEGORY_GENERAL, 16, 0, 128, "Render distance for the items in the solar panel frame");
        if (CONFIG.hasChanged()) CONFIG.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(CSP.MODID)) syncConfig();
    }

    public static class CSPConfigGui extends GuiConfig
    {
        public CSPConfigGui(GuiScreen parentScreen) {
            super(parentScreen, new ConfigElement(CONFIG.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), CSP.MODID, false, false, CSP.NAME);
        }
    }
}
