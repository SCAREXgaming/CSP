package fr.scarex.csp.inventory.slot;

import fr.scarex.csp.item.ISolarUpgrade;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSolarUpgrade extends Slot
{
    public SlotSolarUpgrade(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ISolarUpgrade;
    }

    @Override
    public void onSlotChanged() {
        super.onSlotChanged();
        ((TileEntitySolarPanel) this.inventory).updateUpgrades();
    }
}
