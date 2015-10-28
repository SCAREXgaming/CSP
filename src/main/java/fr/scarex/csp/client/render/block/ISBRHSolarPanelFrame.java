package fr.scarex.csp.client.render.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import fr.scarex.csp.block.SolarPanelFrame;
import fr.scarex.csp.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class ISBRHSolarPanelFrame implements ISimpleBlockRenderingHandler
{
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        Tessellator.instance.startDrawingQuads();
        GL11.glScalef(0.9F, 0.9F, 0.9F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        if (block.getClass() == SolarPanelFrame.class) renderSolarPanel(renderer, 0, 0, 0, (SolarPanelFrame) block);
        Tessellator.instance.draw();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if (block.getClass() == SolarPanelFrame.class) return renderSolarPanel(renderer, x, y, z, (SolarPanelFrame) block);
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ClientProxy.renderId;
    }

    public static boolean renderSolarPanel(RenderBlocks renderer, float x, float y, float z, SolarPanelFrame block) {
        Tessellator t = Tessellator.instance;
        t.addTranslation(x, y, z);

        t.setColorRGBA(0, 30, 180, 255);
        renderCube(t, 0, 0, 0, 1, 0.2, 1);
        double middleWidth = 0.2;
        renderCube(t, 0.5 - (middleWidth / 2), 0.2, 0.5 - (middleWidth / 2), 0.5 + (middleWidth / 2), 0.8, 0.5 + (middleWidth / 2));
        renderCube(t, 0, 0.8, 0, 1, 0.95, 1);

        t.setColorRGBA(0, 0, 0, 255);
        double frameWidth = 0.07;
        renderCube(t, 0, 0.95, 0, frameWidth, 1, 1);
        renderCube(t, 0, 0.95, 0, 1, 1, frameWidth);
        renderCube(t, 0, 0.95, 1 - frameWidth, 1, 1, 1);
        renderCube(t, 1 - frameWidth, 0.95, 0, 1, 1, 1);

        double middleFrameWidth = 0.2 - 2 * frameWidth;
        renderCube(t, 0.5 - (middleFrameWidth / 2), 0.95, 0, 0.5 + (middleFrameWidth / 2), 1, 1);
        renderCube(t, 0, 0.95, 0.5 - (middleFrameWidth / 2), 1, 1, 0.5 + (middleFrameWidth / 2));

        t.addTranslation(-x, -y, -z);
        return true;
    }

    public static void renderCube(Tessellator t, double x, double y, double z, double x1, double y1, double z1) {
        t.addVertex(x1, y, z);
        t.addVertex(x1, y, z1);
        t.addVertex(x, y, z1);
        t.addVertex(x, y, z);

        t.addVertex(x, y, z1);
        t.addVertex(x, y1, z1);
        t.addVertex(x, y1, z);
        t.addVertex(x, y, z);

        t.addVertex(x, y1, z1);
        t.addVertex(x, y, z1);
        t.addVertex(x1, y, z1);
        t.addVertex(x1, y1, z1);

        t.addVertex(x1, y1, z1);
        t.addVertex(x1, y, z1);
        t.addVertex(x1, y, z);
        t.addVertex(x1, y1, z);

        t.addVertex(x1, y1, z);
        t.addVertex(x1, y, z);
        t.addVertex(x, y, z);
        t.addVertex(x, y1, z);

        t.addVertex(x, y1, z);
        t.addVertex(x, y1, z1);
        t.addVertex(x1, y1, z1);
        t.addVertex(x1, y1, z);
    }
}
