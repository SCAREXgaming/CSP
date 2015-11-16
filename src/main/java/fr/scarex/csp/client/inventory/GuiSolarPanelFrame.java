package fr.scarex.csp.client.inventory;

import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.nei.guihook.IContainerTooltipHandler;
import cofh.core.render.IconRegistry;
import cofh.lib.render.RenderHelper;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.Optional;
import fr.scarex.csp.CSP;
import fr.scarex.csp.block.CSPBlocks;
import fr.scarex.csp.block.SolarPanelFrame;
import fr.scarex.csp.inventory.container.ContainerSolarPanelFrame;
import fr.scarex.csp.item.SolarCellSupport;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@Optional.InterfaceList({
        @Optional.Interface(iface = "codechicken.nei.guihook.IContainerTooltipHandler", modid = "NotEnoughItems") })
public class GuiSolarPanelFrame extends GuiContainer implements IContainerTooltipHandler
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
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float prt, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        for (byte i = 0; i < 4; i++) {
            if (this.inv.getStackInSlot(i) != null && this.inv.getStackInSlot(i).getItem() instanceof SolarCellSupport) {
                this.drawTexturedModalRect(k + 51, l + i * 26 + 11, 176, 11, 52, 26);
                this.drawTexturedModalRect(k + 103, l + i * 26 + 11, 176, 37, 52, 26);
            }
        }
    }

    @Override
    protected void renderToolTip(ItemStack stack, int x, int y) {
        List list = stack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

        for (int k = 0; k < list.size(); ++k) {
            if (k == 0)
                list.set(k, stack.getRarity().rarityColor + (String) list.get(k));
            else
                list.set(k, EnumChatFormatting.GRAY + (String) list.get(k));
        }
        this.addAmountProduced(list);

        FontRenderer font = stack.getItem().getFontRenderer(stack);
        drawHoveringText(list, x, y, (font == null ? fontRendererObj : font));
    }

    public List addAmountProduced(List tooltip) {
        Slot slot = this.getSelectedSlot();
        if (slot != null && slot.getHasStack() && slot.slotNumber >= 0) {
            int[] stackP = ((ContainerSolarPanelFrame) this.inventorySlots).stackProduced;
            if (slot.slotNumber < 4)
                tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocalFormatted(CSPBlocks.blockMap.get(SolarPanelFrame.class).getUnlocalizedName() + ".currentlyProducing", (stackP[slot.slotNumber * 4] + stackP[slot.slotNumber * 4 + 1] + stackP[slot.slotNumber * 4 + 2] + stackP[slot.slotNumber * 4 + 3]) / ((ContainerSolarPanelFrame) this.inventorySlots).timeLastProd));
            else if (slot.slotNumber < 20) tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocalFormatted(CSPBlocks.blockMap.get(SolarPanelFrame.class).getUnlocalizedName() + ".currentlyProducing", stackP[slot.slotNumber - 4] / ((ContainerSolarPanelFrame) this.inventorySlots).timeLastProd));
        }
        return tooltip;
    }

    public Slot getSelectedSlot() {
        return ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, this, "theSlot", "field_147006_u");
    }

    public void drawIcon(IIcon paramIIcon, int paramInt1, int paramInt2, int paramInt3) {
        if (paramInt3 == 0) {
            RenderHelper.setBlockTextureSheet();
        } else {
            RenderHelper.setItemTextureSheet();
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModelRectFromIcon(paramInt1, paramInt2, paramIIcon, 16, 16);
    }

    public void drawIcon(String paramString, int paramInt1, int paramInt2, int paramInt3) {
        drawIcon(getIcon(paramString), paramInt1, paramInt2, paramInt3);
    }

    public IIcon getIcon(String paramString) {
        return IconRegistry.getIcon(paramString);
    }

    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }

    @Override
    @Optional.Method(modid = "NotEnoughItems")
    public List<String> handleItemDisplayName(GuiContainer gui, ItemStack stack, List<String> tooltip) {
        return tooltip;
    }

    @Override
    @Optional.Method(modid = "NotEnoughItems")
    public List<String> handleItemTooltip(GuiContainer gui, ItemStack stack, int x, int y, List<String> tooltip) {
        this.addAmountProduced(tooltip);
        return tooltip;
    }

    @Override
    @Optional.Method(modid = "NotEnoughItems")
    public List<String> handleTooltip(GuiContainer gui, int x, int y, List<String> tooltip) {
        return tooltip;
    }
}
