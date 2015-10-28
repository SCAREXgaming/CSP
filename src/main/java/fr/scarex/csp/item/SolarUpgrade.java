package fr.scarex.csp.item;

import java.util.List;

import fr.scarex.csp.CSP;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class SolarUpgrade extends AbstractItem implements ISolarUpgrade
{
    public static final String[] TYPES = {
            "Speed", "Power",
            "Production" };
    public static final IIcon[] icons = new IIcon[TYPES.length];

    @Override
    public String getName() {
        return "SolarUpgrade";
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < TYPES.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void init() {
        super.init();
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + TYPES[stack.getItemDamage() & 3];
    }

    @Override
    public void registerIcons(IIconRegister r) {
        for (byte i = 0; i < TYPES.length; i++) {
            icons[i] = r.registerIcon(CSP.MODID + ":" + TYPES[i] + this.getName());
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return icons[meta & 3];
    }

    @Override
    public void updateUpgrade(World world, int x, int y, int z, TileEntitySolarPanel tile, ItemStack upgrade) {
        if (upgrade.getItemDamage() == 1) {
            tile.getEnergyStorage().setCapacity(upgrade.stackSize * 1000);
            tile.getEnergyStorage().setMaxExtract(upgrade.stackSize * 100);
        }
    }

    @Override
    public boolean canGenerate(World world, int x, int y, int z, ItemStack upgrade) {
        return true;
    }

    @Override
    public boolean canBaseGenerate(World world, int x, int y, int z, ItemStack upgrade, boolean baseGenerate, long totalWorldTime, boolean hasNoSky, boolean canSeeTheSky) {
        return upgrade.getItemDamage() == 0 ? true : baseGenerate;
    }

    @Override
    public int generateWithCell(World world, int x, int y, int z, ItemStack upgrade, ItemStack stack, float sunPosInRadians, int sunLight, float ratio, int generated) {
        return generated;
    }

    @Override
    public int generate(World world, int x, int y, int z, ItemStack upgrade, int total) {
        return upgrade.getItemDamage() == 2 ? total * 2 : total;
    }
}
