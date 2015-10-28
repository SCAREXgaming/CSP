package fr.scarex.csp.item;

import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ISolarUpgrade
{
    public void updateUpgrade(World world, int x, int y, int z, TileEntitySolarPanel tile, ItemStack upgrade);

    /**
     * @return if the upgrade allows generation
     * 
     */
    public boolean canGenerate(World world, int x, int y, int z, ItemStack upgrade);

    public boolean canBaseGenerate(World world, int x, int y, int z, ItemStack upgrade, boolean baseGenerate, long totalWorldTime, boolean hasNoSky, boolean canSeeTheSky);

    public int generateWithCell(World world, int x, int y, int z, ItemStack upgrade, ItemStack stack, float sunPosInRadians, int sunLight, float ratio, int generated);

    public int generate(World world, int x, int y, int z, ItemStack upgrade, int total);
}
