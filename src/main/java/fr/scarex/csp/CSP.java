package fr.scarex.csp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import fr.scarex.csp.block.CSPBlocks;
import fr.scarex.csp.block.SolarPanelFrame;
import fr.scarex.csp.item.CSPItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

@Mod(modid = CSP.MODID, name = CSP.NAME, version = CSP.VERSION)
public class CSP
{
    public static final String MODID = "csp";
    public static final String NAME = "Custom Solar Panels";
    public static final String VERSION = "@VERSION@";
    public static final boolean DEBUG = "@DEBUG@" == "@" + "DEBUG@";
    private static long timeToLoad = 0L;
    @SidedProxy(serverSide = "fr.scarex.csp.CommonProxy", clientSide = "fr.scarex.csp.client.ClientProxy")
    public static CommonProxy PROXY;
    @Mod.Instance
    public static CSP INSTANCE;

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(CSPBlocks.blockMap.get(SolarPanelFrame.class));
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        long t = System.currentTimeMillis();

        CSPItems.preInit();
        CSPBlocks.preInit();

        FMLInterModComms.sendMessage("Waila", "register", "fr.scarex.csp.waila.CSPWailaCompat.load");

        timeToLoad += (System.currentTimeMillis() - t);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        long t = System.currentTimeMillis();

        CSPItems.init();
        CSPBlocks.init();

        PROXY.registerRender();

        timeToLoad += (System.currentTimeMillis() - t);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        long t = System.currentTimeMillis();

        CSPItems.postInit();
        CSPBlocks.postInit();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, CSP.PROXY);

        timeToLoad += (System.currentTimeMillis() - t);
        CSP.LOGGER.info("Time to load : " + CSP.timeToLoad + "ms");
    }
}
