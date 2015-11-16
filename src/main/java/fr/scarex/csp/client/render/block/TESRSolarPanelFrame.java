package fr.scarex.csp.client.render.block;

import org.lwjgl.opengl.GL11;

import cofh.api.item.IToolHammer;
import fr.scarex.csp.CSP;
import fr.scarex.csp.CSPConfiguration;
import fr.scarex.csp.item.ISolarCell;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class TESRSolarPanelFrame extends AbstractTESR
{
    public static final ModelSolarPanelFrame MODEL = new ModelSolarPanelFrame();
    public static final ResourceLocation TEXTURE = new ResourceLocation(CSP.MODID, "textures/models/blocks/SolarPanelFrame.png");

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float prt) {
        this.renderTileEntitySolarPanelFrameAt((TileEntitySolarPanel) tile, x, y, z, prt);
    }

    private void renderTileEntitySolarPanelFrameAt(TileEntitySolarPanel tile, double x, double y, double z, float prt) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        this.bindTexture(TEXTURE);
        MODEL.renderAll();
        GL11.glPopMatrix();

        if (tile != null && CSPConfiguration.useSolarPanelFrameCoolRender) {
            for (byte i = 0; i < 4; i++) {
                for (byte j = 0; j < 4; j++) {
                    ItemStack stack = tile.getStackInSlot(4 + i * 4 + j);
                    if (stack != null) {
                        GL11.glPushMatrix();
                        float scale = 0.22F;
                        GL11.glTranslated(x + 0.06F + i * scale, y + 1D, z + 0.72F - (j * scale));
                        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                        GL11.glScalef(scale, scale, 0.1F);
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
            EntityPlayer p = Minecraft.getMinecraft().thePlayer;
            if (p.getCurrentEquippedItem() != null && p.getCurrentEquippedItem().getItem() instanceof IToolHammer && p.getDistance(tile.xCoord, tile.yCoord, tile.zCoord) <= CSPConfiguration.solarPanelFrameRenderDistance) {
                int size = 0;
                for (byte i = 0; i < 4; i++) {
                    if (tile.getStackInSlot(20 + i) != null) size++;
                }
                if (size > 0) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(x + 0.5D, y - 0.04F, z + 0.5D);
                    float scale = 0.18F;
                    GL11.glScalef(scale, scale, scale);
                    double dx = p.posX - (tile.xCoord + 0.5D);
                    double dz = p.posZ - (tile.zCoord + 0.5D);
                    double angle = Math.atan2(dz, dx);
                    GL11.glRotated(-Math.toDegrees(angle) - 90D, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
                    float begin = size / 2F;
                    int j = 0;
                    for (byte i = 0; i < 4; i++) {
                        if (tile.getStackInSlot(20 + i) != null) {
                            j++;
                            GL11.glPushMatrix();
                            GL11.glTranslatef(begin - j, 0F, -1F);
                            this.bindTexture(TextureMap.locationItemsTexture);
                            IIcon icon = tile.getStackInSlot(20 + i).getIconIndex();
                            float f9 = 0.0625F;
                            float minU = icon.getMinU();
                            float maxU = icon.getMaxU();
                            float minV = icon.getMinV();
                            float maxV = icon.getMaxV();
                            ItemRenderer.renderItemIn2D(Tessellator.instance, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), f9);
                            GL11.glPopMatrix();
                        }
                    }
                    GL11.glPopMatrix();
                }
            }
        }
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        this.bindTexture(TEXTURE);
        MODEL.renderAll();
        GL11.glPopMatrix();
    }
}
