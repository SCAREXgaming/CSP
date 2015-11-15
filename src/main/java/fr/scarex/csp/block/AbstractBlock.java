package fr.scarex.csp.block;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.scarex.csp.CSP;
import fr.scarex.csp.IRegister;
import fr.scarex.csp.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public abstract class AbstractBlock extends Block implements IRegister
{
    protected AbstractBlock(Material m) {
        super(m);
    }

    @Override
    public void register() {
        GameRegistry.registerBlock(this, this.getName());
        if (this.getTileEntityClass() != null) {
            GameRegistry.registerTileEntity(this.getTileEntityClass(), this.getName());
        } else {
            for (ItemStack stack : this.getAllItemStacks()) {
                FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", stack);
            }
        }
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + CSP.MODID + "_" + this.getName();
    }

    public Class getTileEntityClass() {
        return null;
    }

    @Override
    public void init() {
        this.setBlockName(this.getName());
        this.setCreativeTab(CSP.CREATIVE_TAB);
    }

    @Override
    public int getRenderType() {
        return this.hasSpecialRender() ? ClientProxy.renderId : super.getRenderType();
    }

    public boolean hasSpecialRender() {
        return false;
    }

    @Override
    protected String getTextureName() {
        return this.hasSpecialRender() ? "" : CSP.MODID + ":" + this.getName();
    }

    @Override
    public void registerCrafts() {}

    public List<ItemStack> getAllItemStacks() {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.add(new ItemStack(this));
        return stacks;
    }
}
