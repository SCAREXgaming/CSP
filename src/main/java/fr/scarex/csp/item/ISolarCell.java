package fr.scarex.csp.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public interface ISolarCell
{
    public int amountToGenerate(World world, int x, int y, int z, ItemStack stack, float sunPosInRadians, int sunLight, float ratio);
    
    public IIcon getIconForWorldRendering(World world, int x, int y, int z, ItemStack stack, int slot);
}
