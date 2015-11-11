package fr.scarex.csp.item;

import java.util.HashMap;
import java.util.Map.Entry;

public class CSPItems
{
    public static HashMap<Class, AbstractItem> itemMap = new HashMap<Class, AbstractItem>();

    public static void preInit() {
        addItem(new SolarCellSupport());
        addItem(new SolarCell());
        addItem(new SolarCellFrame());
        addItem(new SolarUpgrade());
        
        for (Entry<Class, AbstractItem> e : itemMap.entrySet()) {
            e.getValue().register();
        }
    }

    public static void init() {
        for (Entry<Class, AbstractItem> e : itemMap.entrySet()) {
            e.getValue().init();
        }
    }

    private static <E extends AbstractItem> E addItem(E item) {
        itemMap.put(item.getClass(), item);
        return item;
    }
}
