package fr.scarex.csp.item;

import java.util.List;

import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.thermalfoundation.item.TFItems;
import fr.scarex.csp.CSP;
import fr.scarex.csp.CSPConfiguration;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class SolarCell extends AbstractItem implements ISolarCell, ISolarConvertable
{
    public static final String[] LEVELS = new String[] {
            "Basic", "Hardened",
            "Reinforced", "Resonant" };
    public static final int[] LEVELS_CONVERSION = new int[] {
            2000, 3000, 6000, 12000 };
    public static final IIcon[] ICONS = new IIcon[LEVELS.length];
    public static final IIcon[] WORLD_ICONS = new IIcon[LEVELS.length];

    @Override
    public void init() {
        super.init();
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public void registerCrafts() {
        ItemStack leadStack = TFItems.ingotLead.copy();
        leadStack.stackSize = 4;
        ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(CSPItems.itemMap.get(SolarCellFrame.class)), leadStack, new ItemStack(this, 1, 0));
        ItemStack electrumStack = TFItems.ingotElectrum.copy();
        electrumStack.stackSize = 4;
        ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(CSPItems.itemMap.get(SolarCellFrame.class)), electrumStack, new ItemStack(this, 1, 1));
        ItemStack signalumStack = TFItems.ingotSignalum.copy();
        signalumStack.stackSize = 4;
        ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(CSPItems.itemMap.get(SolarCellFrame.class)), signalumStack, new ItemStack(this, 1, 2));
        ItemStack enderiumStack = TFItems.ingotEnderium.copy();
        enderiumStack.stackSize = 4;
        ThermalExpansionHelper.addSmelterRecipe(2000, new ItemStack(CSPItems.itemMap.get(SolarCellFrame.class)), enderiumStack, new ItemStack(this, 1, 3));
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < ICONS.length; i++) {
            list.add(new ItemStack(item, 1, i));
            NBTTagCompound comp = new NBTTagCompound();
            comp.setInteger("SolarConversionState", LEVELS_CONVERSION[i]);
            ItemStack stack = new ItemStack(item, 1, i);
            stack.setTagCompound(comp);
            list.add(stack);
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
        for (int i = 0; i < ICONS.length; i++) {
            ICONS[i] = r.registerIcon(CSP.MODID + ":" + this.getName() + LEVELS[i]);
            WORLD_ICONS[i] = r.registerIcon(CSP.MODID + ":" + this.getName() + LEVELS[i] + "_world");
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return ICONS[meta & 3];
    }

    @Override
    public int amountToGenerate(World world, int x, int y, int z, ItemStack stack, float sunPosInRadians, int sunLight, float ratio) {
        if (ratio == 0 && stack.hasTagCompound() && stack.getTagCompound().getInteger("SolarConversionState") > 0) {
            ratio = Math.abs(Math.round((float) 15 * MathHelper.cos(sunPosInRadians))) * 0.8F;
            if (world.getTotalWorldTime() % 2 == 0) stack.getTagCompound().setInteger("SolarConversionState", stack.getTagCompound().getInteger("SolarConversionState") - 1);
        }
        switch (stack.getItemDamage()) {
        case 0:
            return CSPConfiguration.solarCellBasicProduction > 0 ? Math.round((float) ratio / (15.0F / CSPConfiguration.solarCellBasicProduction)) : 0;
        case 1:
            return CSPConfiguration.solarCellHardenedProduction > 0 ? Math.round((float) ratio / (15.0F / CSPConfiguration.solarCellHardenedProduction)) : 0;
        case 2:
            return CSPConfiguration.solarCellReinforcedProduction > 0 ? Math.round((float) ratio / (15.0F / CSPConfiguration.solarCellReinforcedProduction)) : 0;
        case 3:
            return CSPConfiguration.solarCellResonantProduction > 0 ? Math.round((float) ratio / (15.0F / CSPConfiguration.solarCellResonantProduction)) : 0;
        }
        return 0;
    }

    @Override
    public boolean canGenerate(World world, int x, int y, int z, ItemStack stack, boolean baseGenerate, long totalWorldTime, boolean hasNoSky, boolean canSeeTheSky, int speedUpgradesAmount) {
        return baseGenerate;
    }

    @Override
    public IIcon getIconForWorldRendering(World world, int x, int y, int z, ItemStack stack, int slot) {
        return WORLD_ICONS[stack.getItemDamage()];
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
        super.addInformation(stack, player, list, debug);
        switch (stack.getItemDamage()) {
        case 0:
            list.add(StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".maxProduction", CSPConfiguration.solarCellBasicProduction));
            break;
        case 1:
            list.add(StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".maxProduction", CSPConfiguration.solarCellHardenedProduction));
            break;
        case 2:
            list.add(StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".maxProduction", CSPConfiguration.solarCellReinforcedProduction));
            break;
        case 3:
            list.add(StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".maxProduction", CSPConfiguration.solarCellResonantProduction));
            break;
        }
        if (stack.hasTagCompound() && stack.getTagCompound().getInteger("SolarConversionState") > 0) list.add(StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".solarConversionState", (stack.getTagCompound().getInteger("SolarConversionState") * 100 / LEVELS_CONVERSION[stack.getItemDamage()])));
    }

    @Override
    public int getMaxStackSize(World world, int x, int y, int z, ItemStack stack) {
        return 1;
    }

    @Override
    public boolean canAddItem(World world, int x, int y, int z, ItemStack stack, ItemStack newStack) {
        return false;
    }

    @Override
    public boolean canExtractItem(World world, int x, int y, int z, ItemStack stack, boolean isPlayer) {
        return isPlayer || (stack.hasTagCompound() && stack.getTagCompound().getInteger("SolarConversionState") >= LEVELS_CONVERSION[stack.getItemDamage()]);
    }

    @Override
    public boolean isConversionFinished(World world, int x, int y, int z, ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().getInteger("SolarConversionState") >= LEVELS_CONVERSION[stack.getItemDamage()];
    }

    @Override
    public void update(World world, int x, int y, int z, ItemStack stack) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        int i = stack.getTagCompound().hasKey("SolarConversionState", Constants.NBT.TAG_INT) ? stack.getTagCompound().getInteger("SolarConversionState") : 0;
        if (i < LEVELS_CONVERSION[stack.getItemDamage()]) stack.getTagCompound().setInteger("SolarConversionState", ++i);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("SolarConversionState", Constants.NBT.TAG_INT) && stack.getTagCompound().getInteger("SolarConversionState") > 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1D - ((double) stack.getTagCompound().getInteger("SolarConversionState") / LEVELS_CONVERSION[stack.getItemDamage()]);
    }
}
