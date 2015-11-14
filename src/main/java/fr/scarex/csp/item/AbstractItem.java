package fr.scarex.csp.item;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.scarex.csp.CSP;
import fr.scarex.csp.IRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class AbstractItem extends Item implements IRegister
{
    @Override
    public void register() {
        GameRegistry.registerItem(this, this.getName());
    }

    @Override
    public void init() {
        this.setCreativeTab(CSP.CREATIVE_TAB);
    }

    @Override
    public String getUnlocalizedName() {
        return "item." + CSP.MODID + "_" + this.getName();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }

    @Override
    protected String getIconString() {
        return CSP.MODID + ":" + this.getName();
    }

    @Override
    public void registerCrafts() {}
}
