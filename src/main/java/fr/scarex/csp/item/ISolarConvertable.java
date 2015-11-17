package fr.scarex.csp.item;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ISolarConvertable
{
    public int getMaxStackSize(World world, int x, int y, int z, ItemStack stack);

    public boolean canAddItem(World world, int x, int y, int z, ItemStack stack, ItemStack newStack);

    public boolean canExtractItem(World world, int x, int y, int z, ItemStack stack, boolean isPlayer);

    public boolean isConversionFinished(World world, int x, int y, int z, ItemStack stack);

    public void update(World world, int x, int y, int z, ItemStack stack);
}
