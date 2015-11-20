package fr.scarex.csp.client.render.block;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import fr.scarex.csp.CSPConfiguration;
import fr.scarex.csp.item.ISolarConvertable;
import fr.scarex.csp.tileentity.TileEntitySolarCellConverter;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class TESRSolarCellConverter extends AbstractTESR
{
    public static final ModelSolarCellConverter MODEL = new ModelSolarCellConverter();
    private EntityItem entityItem = null;
    private Random rand = new Random();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float prt) {
        this.renderTileEntitySolarCellConverter((TileEntitySolarCellConverter) tile, x, y, z, prt);
    }

    private void renderTileEntitySolarCellConverter(TileEntitySolarCellConverter tile, double x, double y, double z, float prt) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        this.bindTexture(TESRSolarPanelFrame.TEXTURE);
        MODEL.renderAll();
        GL11.glPopMatrix();

        if (tile != null && tile.getStackInSlot(0) != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y + 0.2, z + 0.5);
            if (CSPConfiguration.allowSolarConversion && tile.canConvert() && !((ISolarConvertable) tile.getStackInSlot(0).getItem()).isConversionFinished(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, tile.getStackInSlot(0))) GL11.glRotatef(tile.getWorldObj().getTotalWorldTime() % 360 * 3, 0F, 1F, 0F);
            GL11.glTranslatef(-0.15F, 0.0F, 0.0F);
            GL11.glScalef(0.3F, 0.3F, 0.3F);
            this.bindTexture(TextureMap.locationItemsTexture);
            IIcon icon = tile.getStackInSlot(0).getIconIndex();
            float f9 = 0.0625F;
            float minU = icon.getMinU();
            float maxU = icon.getMaxU();
            float minV = icon.getMinV();
            float maxV = icon.getMaxV();
            ItemRenderer.renderItemIn2D(Tessellator.instance, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), f9);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        this.bindTexture(TESRSolarPanelFrame.TEXTURE);
        MODEL.renderAll();
        GL11.glPopMatrix();
    }
}
