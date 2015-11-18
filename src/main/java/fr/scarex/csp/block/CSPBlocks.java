package fr.scarex.csp.block;

import java.util.HashMap;
import java.util.Map.Entry;

public class CSPBlocks
{
    public static HashMap<Class, AbstractBlock> blockMap = new HashMap<Class, AbstractBlock>();

    public static void preInit() {
        addBlock(new SolarPanelFrame());
        addBlock(new SolariumBlock());
        addBlock(new SolarCellConverter());

        for (Entry<Class, AbstractBlock> e : blockMap.entrySet()) {
            e.getValue().init();
        }
    }

    public static void init() {
        for (Entry<Class, AbstractBlock> e : blockMap.entrySet()) {
            e.getValue().register();
        }
    }

    public static void postInit() {
        for (Entry<Class, AbstractBlock> e : blockMap.entrySet()) {
            e.getValue().registerCrafts();
        }
    }

    private static void addBlock(AbstractBlock b) {
        CSPBlocks.blockMap.put(b.getClass(), b);
    }
}
