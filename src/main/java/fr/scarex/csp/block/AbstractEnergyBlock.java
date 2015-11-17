package fr.scarex.csp.block;

import cofh.api.block.IDismantleable;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.scarex.csp.block.itemblock.ItemBlockEnergy;
import net.minecraft.block.material.Material;

public abstract class AbstractEnergyBlock extends AbstractBlockDismantleable implements IDismantleable
{
    protected AbstractEnergyBlock(Material m) {
        super(m);
        this.setHardness(15.0F);
        this.setResistance(25.0F);
    }

    @Override
    public void register() {
        GameRegistry.registerBlock(this, ItemBlockEnergy.class, this.getName());
        if (this.getTileEntityClass() != null) {
            GameRegistry.registerTileEntity(this.getTileEntityClass(), this.getName());
        }
    }
}
