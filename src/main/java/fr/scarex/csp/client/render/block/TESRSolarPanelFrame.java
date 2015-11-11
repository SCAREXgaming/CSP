package fr.scarex.csp.client.render.block;

import org.lwjgl.opengl.GL11;

import fr.scarex.csp.item.ISolarCell;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class TESRSolarPanelFrame extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float prt) {
        this.renderTileEntitySolarPanelFrameAt((TileEntitySolarPanel) tile, x, y, z, prt);
    }

    private void renderTileEntitySolarPanelFrameAt(TileEntitySolarPanel tile, double x, double y, double z, float prt) {
        if (tile != null) {
            for (byte i = 0; i < 4; i++) {
                for (byte j = 0; j < 4; j++) {
                    ItemStack stack = tile.getStackInSlot(4 + i * 4 + j);
                    if (stack != null) {
                        GL11.glPushMatrix();
                        float scale = 0.2F;
                        GL11.glTranslated(x + 0.1F + i * scale, y + 0.95, z + 0.7F - (j * scale));
                        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                        GL11.glScalef(scale, scale, scale);
                        this.bindTexture(TextureMap.locationItemsTexture);
                        IIcon icon = ((ISolarCell) stack.getItem()).getIconForWorldRendering(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, stack, 4 + i * 4 + j);
                        float f9 = 0.0625F;
                        float minU = icon.getMinU();
                        float maxU = icon.getMaxU();
                        float minV = icon.getMinV();
                        float maxV = icon.getMaxV();
                        ItemRenderer.renderItemIn2D(Tessellator.instance, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), f9);
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }
}
