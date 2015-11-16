package fr.scarex.csp.item;

import net.minecraftforge.oredict.OreDictionary;

public class SolarNugget extends AbstractItem
{
    @Override
    public String getName() {
        return "SolarNugget";
    }

    @Override
    public void registerCrafts() {
        OreDictionary.registerOre("nuggetSolar", this);
    }
}
