package fr.scarex.csp.waila;

import java.util.List;

import fr.scarex.csp.CSP;
import fr.scarex.csp.block.AbstractBlock;
import fr.scarex.csp.tileentity.AbstractCSPTileEntity;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CSPWailaCompat implements IWailaDataProvider
{
    public static final CSPWailaCompat INSTANCE = new CSPWailaCompat();

    public static void load(IWailaRegistrar registrar) {
        registrar.registerHeadProvider(INSTANCE, AbstractBlock.class);
        registrar.registerBodyProvider(INSTANCE, AbstractBlock.class);
        registrar.registerTailProvider(INSTANCE, AbstractBlock.class);
        registrar.registerNBTProvider(INSTANCE, AbstractBlock.class);

        CSP.LOGGER.info("Waila compatibility loaded");
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getTileEntity() instanceof AbstractCSPTileEntity) currenttip = ((AbstractCSPTileEntity) accessor.getTileEntity()).getWailaHead(itemStack, currenttip, accessor, config);
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getTileEntity() instanceof AbstractCSPTileEntity) currenttip = ((AbstractCSPTileEntity) accessor.getTileEntity()).getWailaBody(itemStack, currenttip, accessor, config);
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getTileEntity() instanceof AbstractCSPTileEntity) currenttip = ((AbstractCSPTileEntity) accessor.getTileEntity()).getWailaTail(itemStack, currenttip, accessor, config);
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        if (te instanceof AbstractCSPTileEntity) tag = ((AbstractCSPTileEntity) te).getWailaNBTData(player, te, tag, world, x, y, z);
        return tag;
    }
}
