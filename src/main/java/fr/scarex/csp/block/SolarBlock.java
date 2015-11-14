package fr.scarex.csp.block;

import net.minecraft.block.material.Material;

public class SolarBlock extends AbstractBlock
{
    protected SolarBlock() {
        super(Material.rock);
    }

    @Override
    public String getName() {
        return "SolarBlock";
    }
}
