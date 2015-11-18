package fr.scarex.csp.item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.scarex.csp.CSP;
import fr.scarex.csp.CSPConfiguration;
import fr.scarex.csp.tileentity.AbstractCSPTileEntity;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

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
    public void registerCrafts() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), "YXY", "XZX", "YXY", 'X', Blocks.iron_bars, 'Z', "ingotSolarium", 'Y', "dustPyrotheum"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), "YXY", "XZX", "YXY", 'X', Blocks.iron_bars, 'Z', "ingotSolarium", 'Y', "ingotGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), "AXA", "XZX", "YXY", 'A', "ingotEnderium", 'X', Blocks.iron_bars, 'Z', "ingotSolarium", 'Y', "gemEmerald"));
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
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
        super.addInformation(stack, player, list, debug);
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            switch (stack.getItemDamage()) {
            case 0:
                list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal(this.getUnlocalizedName(stack) + ".info"));
                list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal(this.getUnlocalizedName(stack) + ".info1"));
                break;
            case 1:
                if (CSPConfiguration.powerUpgradeStorageRatio > 0 || CSPConfiguration.powerUpgradeOutputRatio > 0) list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal(this.getUnlocalizedName(stack) + ".info"));
                if (CSPConfiguration.powerUpgradeStorageRatio == 0) list.add(EnumChatFormatting.RED + StatCollector.translateToLocal(this.getUnlocalizedName(stack) + ".storage.desactivated"));
                if (CSPConfiguration.powerUpgradeOutputRatio == 0) list.add(EnumChatFormatting.RED + StatCollector.translateToLocal(this.getUnlocalizedName(stack) + ".output.desactivated"));
                break;
            case 2:
                if (CSPConfiguration.productionUpgradeStackSize > 0)
                    list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocalFormatted(this.getUnlocalizedName(stack) + ".info", CSPConfiguration.productionUpgradeStackSize, this.getItemStackDisplayName(stack)));
                else
                    list.add(EnumChatFormatting.RED + StatCollector.translateToLocal(this.getUnlocalizedName(stack) + ".desactivated"));
                break;
            }
        } else {
            list.add(EnumChatFormatting.WHITE + "" + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("misc." + CSP.MODID + "_hold_shift"));
        }
    }

    @Override
    public void onReadFromNBT(World world, int x, int y, int z, AbstractCSPTileEntity tile, ItemStack upgrade, NBTTagCompound comp) {}

    @Override
    public void onWriteToNBT(World world, int x, int y, int z, AbstractCSPTileEntity tile, ItemStack upgrade, NBTTagCompound comp) {}

    @Override
    public void load(World world, int x, int y, int z, AbstractCSPTileEntity tile, ItemStack upgrade) {
        if (tile instanceof TileEntitySolarPanel && upgrade.getItemDamage() == 1) {
            ((TileEntitySolarPanel) tile).getEnergyStorage().setCapacity(upgrade.stackSize * CSPConfiguration.powerUpgradeStorageRatio);
            ((TileEntitySolarPanel) tile).getEnergyStorage().setMaxExtract(upgrade.stackSize * CSPConfiguration.powerUpgradeOutputRatio);
        }
    }

    // @Override
    // public boolean canGenerate(World world, int x, int y, int z, ItemStack
    // upgrade) {
    // return true;
    // }
    //
    // @Override
    // public boolean canBaseGenerate(World world, int x, int y, int z,
    // ItemStack upgrade, boolean baseGenerate, long totalWorldTime, boolean
    // hasNoSky, boolean canSeeTheSky) {
    // switch (upgrade.getItemDamage()) {
    // case 0:
    // return canSeeTheSky && !hasNoSky && (upgrade.stackSize >= 2 ? true :
    // world.getTotalWorldTime() % 2 == 0);
    // }
    // return baseGenerate;
    // }

    @Override
    public int generateWithCell(World world, int x, int y, int z, ItemStack upgrade, ItemStack stack, float sunPosInRadians, int sunLight, float ratio, int generated) {
        return generated;
    }

    @Override
    public int generate(World world, int x, int y, int z, ItemStack upgrade, int total) {
        switch (upgrade.getItemDamage()) {
        case 2:
            if (CSPConfiguration.productionUpgradeStackSize > 0) {
                int stacksize = upgrade.stackSize > CSPConfiguration.productionUpgradeStackSize ? CSPConfiguration.productionUpgradeStackSize : upgrade.stackSize;
                return total + total * stacksize / CSPConfiguration.productionUpgradeStackSize;
            }
        }
        return total;
    }
}
