package fr.scarex.csp.client.inventory;

import org.lwjgl.opengl.GL11;

import fr.scarex.csp.CSP;
import fr.scarex.csp.inventory.container.ContainerSolarPanelFrame;
import fr.scarex.csp.item.SolarCellFrame;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSolarPanelFrame extends GuiContainer
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(CSP.MODID, "textures/gui/container/GuiSolarFrame.png");
    protected final InventoryPlayer playerInv;
    protected final TileEntitySolarPanel inv;

    public GuiSolarPanelFrame(InventoryPlayer playerInv, TileEntitySolarPanel inv) {
        super(new ContainerSolarPanelFrame(inv, playerInv));
        this.playerInv = playerInv;
        this.inv = inv;
        this.ySize = 248;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float prt, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        for (byte i = 0; i < 4; i++) {
            if (this.inv.getStackInSlot(i) != null && this.inv.getStackInSlot(i).getItem() instanceof SolarCellFrame) {
                this.drawTexturedModalRect(k + 51, l + i * 26 + 11, 176, 11, 52, 26);
                this.drawTexturedModalRect(k + 103, l + i * 26 + 11, 176, 37, 52, 26);
            }
        }
    }
}
