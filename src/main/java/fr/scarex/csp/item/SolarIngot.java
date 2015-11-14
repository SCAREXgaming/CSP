package fr.scarex.csp.item;

import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.thermalfoundation.item.TFItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class SolarIngot extends AbstractItem
{
    @Override
    public String getName() {
        return "SolarIngot";
    }

    @Override
    public void register() {
        super.register();
        OreDictionary.registerOre("ingotSolar", this);
    }

    @Override
    public void registerCrafts() {
        GameRegistry.addRecipe(new ShapelessOreRecipe(this, "dustGlowstone", "ingotTin", "ingotTin", "ingotTin"));
        ThermalExpansionHelper.addSmelterRecipe(2400, GameRegistry.findItemStack("ThermalFoundation", "ingotTin", 2), new ItemStack(Items.glowstone_dust), new ItemStack(this));
    }
}
