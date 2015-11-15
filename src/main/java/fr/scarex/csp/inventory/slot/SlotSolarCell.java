package fr.scarex.csp.inventory.slot;

import fr.scarex.csp.item.ISolarCell;
import fr.scarex.csp.item.SolarCellSupport;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class SlotSolarCell extends Slot
{
    public SlotSolarCell(IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ISolarCell;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean func_111238_b() {
        int i = MathHelper.floor_float((float) (this.slotNumber / 4)) - 1;
        if (this.inventory.getStackInSlot(i) != null && this.inventory.getStackInSlot(i).getItem() instanceof SolarCellSupport) return true;
        return false;
    }
}
