package fr.scarex.csp.item;

import fr.scarex.csp.CSP;

public class SolarCellSupport extends AbstractItem
{
    @Override
    public String getName() {
        return "SolarCellSupport";
    }

    @Override
    public void init() {
        super.init();
        this.setCreativeTab(CSP.CREATIVE_TAB);
    }
}
