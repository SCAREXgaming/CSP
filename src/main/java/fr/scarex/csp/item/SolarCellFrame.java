package fr.scarex.csp.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class SolarCellFrame extends AbstractItem
{
    @Override
    public String getName() {
        return "SolarCellFrame";
    }

    @Override
    public void registerCrafts() {
        GameRegistry.addRecipe(new ShapedOreRecipe(this, " X ", "XYX", " X ", 'X', Blocks.iron_bars, 'Y', "nuggetSolar"));
    }
}
