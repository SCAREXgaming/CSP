package fr.scarex.csp.block;

import java.util.HashMap;
import java.util.Map.Entry;

public class CSPBlocks
{
    public static HashMap<Class, AbstractBlock> blockMap = new HashMap<Class, AbstractBlock>();

    public static void preInit() {
        addBlock(new SolarPanelFrame());
    }

    public static void init() {
        for (Entry<Class, AbstractBlock> e : blockMap.entrySet()) {
            e.getValue().register();
            e.getValue().init();
        }
    }

    private static void addBlock(AbstractBlock b) {
        CSPBlocks.blockMap.put(b.getClass(), b);
    }
}
