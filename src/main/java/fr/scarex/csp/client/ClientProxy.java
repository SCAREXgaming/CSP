package fr.scarex.csp.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.scarex.csp.CommonProxy;
import fr.scarex.csp.client.render.block.ISBRHSolarPanelFrame;

public class ClientProxy extends CommonProxy
{
    public static int renderId;

    @Override
    public void registerRender() {
        this.renderId = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(new ISBRHSolarPanelFrame());
    }
}
