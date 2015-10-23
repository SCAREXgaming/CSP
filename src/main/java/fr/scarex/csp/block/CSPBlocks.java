package fr.scarex.csp.block;

import java.util.HashMap;
import java.util.Map.Entry;

public class CSPBlocks
{
    public static HashMap<String, AbstractBlock> blockMap = new HashMap<String, AbstractBlock>();
    
    public static void preInit() {
        addBlock(new SolarPanel());
    }
    
    public static void init() {
        for (Entry<String, AbstractBlock> e : blockMap.entrySet()) {
            e.getValue().register();
        }
    }
    
    private static void addBlock(AbstractBlock b) {
        CSPBlocks.blockMap.put(b.getName(), b);
    }
}
