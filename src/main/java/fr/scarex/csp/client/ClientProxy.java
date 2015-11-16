package fr.scarex.csp.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.scarex.csp.CommonProxy;
import fr.scarex.csp.block.AbstractBlock;
import fr.scarex.csp.client.render.block.AbstractTESR;
import fr.scarex.csp.client.render.block.TESRSolarCellConverter;
import fr.scarex.csp.client.render.block.TESRSolarPanelFrame;
import fr.scarex.csp.tileentity.TileEntitySolarCellConverter;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;

public class ClientProxy extends CommonProxy implements ISimpleBlockRenderingHandler
{
    public static int renderId;

    @Override
    public void registerRender() {
        this.renderId = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(this);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanel.class, new TESRSolarPanelFrame());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarCellConverter.class, new TESRSolarCellConverter());
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        AbstractTESR tesr = getAbastractTESRForBlock((AbstractBlock) block);
        if (tesr != null) tesr.renderInventoryBlock(block, metadata, modelId, renderer);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        AbstractTESR tesr = getAbastractTESRForBlock((AbstractBlock) block);
        return tesr != null ? tesr.renderWorldBlock(world, x, y, z, block, modelId, renderer) : false;
    }

    public static AbstractTESR getAbastractTESRForBlock(AbstractBlock block) {
        return (AbstractTESR) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(((AbstractBlock) block).getTileEntityClass());
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderId;
    }
}
