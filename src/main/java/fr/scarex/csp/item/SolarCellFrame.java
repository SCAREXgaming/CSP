package fr.scarex.csp.item;

import fr.scarex.csp.CSP;

public class SolarCellFrame extends AbstractItem
{
    @Override
    public String getName() {
        return "SolarCellFrame";
    }

    @Override
    public void init() {
        super.init();
        this.setMaxStackSize(1);
        this.setCreativeTab(CSP.CREATIVE_TAB);
    }
}
