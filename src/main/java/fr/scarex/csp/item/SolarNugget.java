package fr.scarex.csp.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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
