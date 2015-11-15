package fr.scarex.csp.block.itemblock;

import java.util.List;

import fr.scarex.csp.CSP;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockEnergy extends ItemBlock
{
    public ItemBlockEnergy(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p, List list, boolean debug) {
        super.addInformation(stack, p, list, debug);
        if (CSP.DEBUG && stack.getTagCompound() != null) list.add(stack.getTagCompound().toString());
    }
}
