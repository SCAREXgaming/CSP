package fr.scarex.csp.item;

import java.util.HashMap;
import java.util.Map.Entry;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.item.ItemStack;

public class CSPItems
{
    public static HashMap<Class, AbstractItem> itemMap = new HashMap<Class, AbstractItem>();

    public static void preInit() {
        addItem(new SolarIngot());
        addItem(new SolarNugget());
        addItem(new SolarCellSupport());
        addItem(new SolarCell());
        addItem(new SolarCellFrame());
        addItem(new SolarUpgrade());

        for (Entry<Class, AbstractItem> e : itemMap.entrySet()) {
            e.getValue().init();
        }
    }

    public static void init() {
        for (Entry<Class, AbstractItem> e : itemMap.entrySet()) {
            e.getValue().register();
        }
    }

    public static void postInit() {
        for (Entry<Class, AbstractItem> e : itemMap.entrySet()) {
            e.getValue().registerCrafts();
        }

        ItemHelper.addTwoWayStorageRecipe(new ItemStack(itemMap.get(SolarIngot.class)), "ingotSolar", new ItemStack(itemMap.get(SolarNugget.class)), "nuggetSolar");
        ItemHelper.addReverseStorageRecipe(new ItemStack(itemMap.get(SolarIngot.class)), "blockSolar");
    }

    private static <E extends AbstractItem> E addItem(E item) {
        itemMap.put(item.getClass(), item);
        return item;
    }
}
