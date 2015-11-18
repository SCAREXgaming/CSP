package fr.scarex.csp.item;

import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class SolariumIngot extends AbstractItem
{
    @Override
    public String getName() {
        return "SolariumIngot";
    }

    @Override
    public void register() {
        super.register();
        OreDictionary.registerOre("ingotSolarium", this);
    }

    @Override
    public void registerCrafts() {
        GameRegistry.addRecipe(new ShapelessOreRecipe(this, "dustGlowstone", "ingotTin", "ingotTin", "ingotTin"));
        ThermalExpansionHelper.addSmelterRecipe(2400, GameRegistry.findItemStack("ThermalFoundation", "ingotTin", 2), new ItemStack(Items.glowstone_dust), new ItemStack(this));
        ItemHelper.addTwoWayStorageRecipe(new ItemStack(this), "ingotSolarium", new ItemStack(CSPItems.itemMap.get(SolariumNugget.class)), "nuggetSolarium");
        ItemHelper.addReverseStorageRecipe(new ItemStack(this), "blockSolarium");
    }
}
