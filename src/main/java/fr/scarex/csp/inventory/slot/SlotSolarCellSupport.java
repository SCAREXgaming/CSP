package fr.scarex.csp.inventory.slot;

import fr.scarex.csp.item.SolarCellSupport;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSolarCellSupport extends Slot
{
    public SlotSolarCellSupport(IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof SolarCellSupport;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
