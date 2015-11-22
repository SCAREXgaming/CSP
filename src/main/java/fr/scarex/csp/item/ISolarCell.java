package fr.scarex.csp.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public interface ISolarCell
{
    public int amountToGenerate(World world, int x, int y, int z, ItemStack stack, float sunPosInRadians, int sunLight, float ratio);

    public boolean canGenerate(World world, int x, int y, int z, ItemStack stack, boolean baseGenerate, long totalWorldTime, boolean hasNoSky, boolean canSeeTheSky, int speedUpgradesAmount);

    public IIcon getIconForWorldRendering(World world, int x, int y, int z, ItemStack stack, int slot);
    
    public boolean hasSolarEffect(World world, int x, int y, int z, ItemStack stack, int slot);
    
    /**
     * default : 0.76F
     */
    public float getColorForSolarEffect(World world, int x, int y, int z, ItemStack stack, int slot);
}
