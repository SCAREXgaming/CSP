package fr.scarex.csp.item;

import java.util.List;

import fr.scarex.csp.CSP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class SolarCell extends AbstractItem implements ISolarCell
{
    public static final String[] levels = new String[] {
            "Basic", "Hardened",
            "Reinforced", "Resonant" };
    public static final IIcon[] icons = new IIcon[levels.length];

    @Override
    public void init() {
        super.init();
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < icons.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String getName() {
        return "SolarCell";
    }

    @Override
    protected String getIconString() {
        return null;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + levels[stack.getItemDamage() & 3];
    }

    @Override
    public void registerIcons(IIconRegister r) {
        for (int i = 0; i < icons.length; i++) {
            icons[i] = r.registerIcon(CSP.MODID + ":" + this.getName() + levels[i]);
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return icons[meta & 3];
    }

    @Override
    public int amountToGenerate(World world, int x, int y, int z, ItemStack stack, float sunPosInRadians, int sunLight, float ratio) {
        switch (stack.getItemDamage()) {
        case 0:
            return Math.round((float) ratio / (15 / 5));
        case 1:
            return Math.round((float) ratio / (15 / 10));
        case 2:
            return Math.round((float) ratio / (15 / 30F));
        case 3:
            return Math.round((float) ratio / (15 / 50F));
        }
        return 0;
    }
}
