package fr.scarex.csp.block;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.scarex.csp.IRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class AbstractBlock extends Block implements IRegister
{
    protected AbstractBlock(Material m) {
        super(m);
    }

    @Override
    public void register() {
        GameRegistry.registerBlock(this, this.getName());
        if (this.getTileEntityClass() != null) {
            GameRegistry.registerTileEntity(this.getTileEntityClass(), this.getName());
        }
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + this.getName();
    }
    
    public Class getTileEntityClass() {
        return null;
    }
}
