package fr.scarex.csp.block;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SolariumBlock extends AbstractBlock
{
    protected SolariumBlock() {
        super(Material.rock);
    }

    @Override
    public String getName() {
        return "SolariumBlock";
    }

    @Override
    public void register() {
        super.register();
        OreDictionary.registerOre("blockSolarium", this);
    }

    @Override
    public void registerCrafts() {
        super.registerCrafts();
        ItemHelper.addStorageRecipe(new ItemStack(this), "ingotSolarium");
    }
}
