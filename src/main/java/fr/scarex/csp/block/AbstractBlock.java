package fr.scarex.csp.block;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.scarex.csp.CSP;
import fr.scarex.csp.IRegister;
import fr.scarex.csp.client.ClientProxy;
import fr.scarex.csp.tileentity.AbstractCSPTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
    public boolean hasTileEntity(int metadata) {
        return this.getTileEntityClass() != null;
    }

    @Override
    public void init() {
        this.setBlockName(this.getName());
        this.setCreativeTab(CSP.CREATIVE_TAB);
    }

    @SideOnly(Side.CLIENT)
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

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        if (stack.getTagCompound() != null && world.getTileEntity(x, y, z) instanceof AbstractCSPTileEntity) ((AbstractCSPTileEntity) world.getTileEntity(x, y, z)).readExtraCompound(stack.getTagCompound());
    }

    public List<ItemStack> getAllItemStacks() {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.add(new ItemStack(this));
        return stacks;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity ent = world.getTileEntity(x, y, z);
        if (ent instanceof AbstractCSPTileEntity) {
            AbstractCSPTileEntity te = (AbstractCSPTileEntity) ent;
            te.onNeighborBlockChange(block);
        }
    }
}
