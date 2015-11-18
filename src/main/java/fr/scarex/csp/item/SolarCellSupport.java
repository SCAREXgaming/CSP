package fr.scarex.csp.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class SolarCellSupport extends AbstractItem
{
    @Override
    public String getName() {
        return "SolarCellSupport";
    }

    @Override
    public void init() {
        super.init();
        this.setMaxStackSize(1);
    }

    @Override
    public void registerCrafts() {
        GameRegistry.addRecipe(new ShapedOreRecipe(this, "XXX", " Y ", "XXX", 'X', Blocks.iron_bars, 'Y', "ingotSolarium"));
    }
}
