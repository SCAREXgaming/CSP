package fr.scarex.csp.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.scarex.csp.CommonProxy;
import fr.scarex.csp.client.render.block.ISBRHSolarPanelFrame;
import fr.scarex.csp.client.render.block.TESRSolarPanelFrame;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;

public class ClientProxy extends CommonProxy
{
    public static int renderId;

    @Override
    public void registerRender() {
        this.renderId = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(new ISBRHSolarPanelFrame());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanel.class, new TESRSolarPanelFrame());
    }
}
