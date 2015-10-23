package fr.scarex.csp.block;

import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SolarPanel extends AbstractBlock
{
    protected SolarPanel() {
        super(Material.rock);
    }

    @Override
    public String getName() {
        return "SolarPanel";
    }

    @Override
    public void register() {
        super.register();
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public Class getTileEntityClass() {
        return TileEntitySolarPanel.class;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntitySolarPanel();
    }
}
