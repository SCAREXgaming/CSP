package fr.scarex.csp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fr.scarex.csp.block.CSPBlocks;

@Mod(modid = CSP.MODID, name = CSP.NAME, version = CSP.VERSION)
public class CSP
{
    public static final String MODID = "csp";
    public static final String NAME = "Custom Solar Panels";
    public static final String VERSION = "@VERSION@";
    
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CSPBlocks.preInit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        CSPBlocks.init();
    }
}
