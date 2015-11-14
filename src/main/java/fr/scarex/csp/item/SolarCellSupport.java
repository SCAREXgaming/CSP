package fr.scarex.csp.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

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
        GameRegistry.addShapedRecipe(new ItemStack(this), "XXX", " Y ", "XXX", 'X', Blocks.iron_bars, 'Y', CSPItems.itemMap.get(SolarIngot.class));
    }
}
