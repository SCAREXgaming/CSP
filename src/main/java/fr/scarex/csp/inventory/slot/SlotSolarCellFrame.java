package fr.scarex.csp.inventory.slot;

import fr.scarex.csp.item.SolarCellFrame;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSolarCellFrame extends Slot
{
    public SlotSolarCellFrame(IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof SolarCellFrame;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
