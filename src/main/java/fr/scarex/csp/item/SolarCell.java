package fr.scarex.csp.item;

import java.util.List;

import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.thermalfoundation.item.TFItems;
import fr.scarex.csp.CSP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class SolarCell extends AbstractItem implements ISolarCell
{
    public static final String[] LEVELS = new String[] {
            "Basic", "Hardened",
            "Reinforced", "Resonant" };
    public static final int[] LEVELS_RATES = new int[] {
            5, 10, 30, 50 };
    public static final IIcon[] icons = new IIcon[LEVELS.length];
    public static final IIcon[] worldIcons = new IIcon[LEVELS.length];

    @Override
    public void init() {
        super.init();
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);

        ItemStack leadStack = TFItems.ingotLead.copy();
        leadStack.stackSize = 4;
        ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(CSPItems.itemMap.get(SolarCellSupport.class)), leadStack, new ItemStack(this, 1, 0));
        ItemStack electrumStack = TFItems.ingotElectrum.copy();
        electrumStack.stackSize = 4;
        ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(CSPItems.itemMap.get(SolarCellSupport.class)), electrumStack, new ItemStack(this, 1, 1));
        ItemStack signalumStack = TFItems.ingotSignalum.copy();
        signalumStack.stackSize = 4;
        ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(CSPItems.itemMap.get(SolarCellSupport.class)), signalumStack, new ItemStack(this, 1, 2));
        ItemStack enderiumStack = TFItems.ingotEnderium.copy();
        enderiumStack.stackSize = 4;
        ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(CSPItems.itemMap.get(SolarCellSupport.class)), enderiumStack, new ItemStack(this, 1, 3));
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
        return this.getUnlocalizedName() + LEVELS[stack.getItemDamage() & 3];
    }

    @Override
    public void registerIcons(IIconRegister r) {
        for (int i = 0; i < icons.length; i++) {
            icons[i] = r.registerIcon(CSP.MODID + ":" + this.getName() + LEVELS[i]);
            worldIcons[i] = r.registerIcon(CSP.MODID + ":" + this.getName() + LEVELS[i] + "_world");
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return icons[meta & 3];
    }

    @Override
    public int amountToGenerate(World world, int x, int y, int z, ItemStack stack, float sunPosInRadians, int sunLight, float ratio) {
        return stack.getItemDamage() >= 0 && stack.getItemDamage() < LEVELS_RATES.length ? Math.round((float) ratio / (15.0F / LEVELS_RATES[stack.getItemDamage()])) : 0;
    }

    @Override
    public IIcon getIconForWorldRendering(World world, int x, int y, int z, ItemStack stack, int slot) {
        return worldIcons[stack.getItemDamage()];
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
        super.addInformation(stack, player, list, debug);
        if (stack.getItemDamage() >= 0 && stack.getItemDamage() < LEVELS_RATES.length) list.add(StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".maxProduction", LEVELS_RATES[stack.getItemDamage()]));
    }
}
