package fr.scarex.csp.inventory.container;

import fr.scarex.csp.inventory.slot.SlotSolarCell;
import fr.scarex.csp.inventory.slot.SlotSolarCellFrame;
import fr.scarex.csp.inventory.slot.SlotSolarUpgrade;
import fr.scarex.csp.item.ISolarCell;
import fr.scarex.csp.item.ISolarUpgrade;
import fr.scarex.csp.item.SolarCellFrame;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSolarPanelFrame extends Container
{
    public final TileEntitySolarPanel tileEntity;
    public final InventoryPlayer playerInv;
    public final int[] stackProduced = new int[16];

    public ContainerSolarPanelFrame(TileEntitySolarPanel tileEntity, InventoryPlayer playerInv) {
        this.tileEntity = tileEntity;
        this.playerInv = playerInv;

        this.tileEntity.openInventory();

        this.bindCellSlots();
        this.bindAugmentsSlots();
        this.bindPlayerSlots();
    }

    private void bindCellSlots() {
        for (byte i = 0; i < 4; i++) {
            this.addSlotToContainer(new SlotSolarCellFrame(this.tileEntity, i, 18, 16 + i * 26));
        }
        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 4; j++) {
                this.addSlotToContainer(new SlotSolarCell(this.tileEntity, i * 4 + j + 4, 56 + j * 26, 16 + i * 26));
            }
        }
    }

    private void bindAugmentsSlots() {
        for (byte i = 0; i < 4; i++) {
            this.addSlotToContainer(new SlotSolarUpgrade(this.tileEntity, 20 + i, 42 + i * 26, 130));
        }
    }

    private void bindPlayerSlots() {
        byte i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(this.playerInv, j + i * 9 + 9, 8 + j * 18, 164 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(this.playerInv, i, 8 + i * 18, 222));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index >= this.tileEntity.getSizeInventory()) {
                if (itemstack1.getItem() instanceof ISolarCell) {
                    boolean flag = true;
                    for (byte i = 0; i < 4 && flag; i++) {
                        if (this.tileEntity.getStackInSlot(i) != null && this.tileEntity.getStackInSlot(i).getItem() instanceof SolarCellFrame && this.mergeItemStack(itemstack1, i * 4 + 4, i * 4 + 8, false)) flag = false;
                    }
                    if (flag) return null;
                } else if (itemstack1.getItem() instanceof SolarCellFrame) {
                    if (!this.mergeItemStack(itemstack1, 0, 4, false)) return null;
                } else if (itemstack1.getItem() instanceof ISolarUpgrade) {
                    if (!this.mergeItemStack(itemstack1, 20, 24, false)) return null;
                } else if (index > (this.inventorySlots.size() - 9)) {
                    if (!this.mergeItemStack(itemstack1, this.tileEntity.getSizeInventory(), this.inventorySlots.size() - 9, false)) return null;
                } else {
                    if (!this.mergeItemStack(itemstack1, this.inventorySlots.size() - 9, this.inventorySlots.size(), false)) return null;
                }
            } else if (!this.mergeItemStack(itemstack1, this.tileEntity.getSizeInventory(), this.inventorySlots.size(), false)) { return null; }

            if (itemstack1.stackSize == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();

            if (itemstack1.stackSize == itemstack.stackSize) return null;

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.isUseableByPlayer(player);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        this.tileEntity.closeInventory();
    }

    @Override
    public ItemStack slotClick(int index, int buttonPressed, int flag, EntityPlayer player) {
        if (flag != 3 && index >= 0 && index < 4) {
            for (byte i = 0; i < 4; i++) {
                player.dropPlayerItemWithRandomChoice(this.tileEntity.getStackInSlotOnClosing(index * 4 + 4 + i), false);
            }
            this.detectAndSendChanges();
        }
        return super.slotClick(index, buttonPressed, flag, player);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        
        for (int i = 0; i < this.crafters.size(); i++) {
            for (byte j = 4; j < 20; j++) {
                ((ICrafting) this.crafters.get(i)).sendProgressBarUpdate(this, j - 4, this.tileEntity.getAmountProducedBy(j - 4));
            }
        }
    }

    @Override
    public void updateProgressBar(int slot, int amount) {
        this.stackProduced[slot] = amount;
    }
}
