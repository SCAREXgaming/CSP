package fr.scarex.csp.item;

import net.minecraftforge.oredict.OreDictionary;

public class SolariumNugget extends AbstractItem
{
    @Override
    public String getName() {
        return "SolariumNugget";
    }

    @Override
    public void registerCrafts() {
        OreDictionary.registerOre("nuggetSolarium", this);
    }
}
