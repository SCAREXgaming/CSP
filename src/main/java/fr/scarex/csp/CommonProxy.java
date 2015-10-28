package fr.scarex.csp;

import cpw.mods.fml.common.network.IGuiHandler;
import fr.scarex.csp.client.inventory.GuiSolarPanelFrame;
import fr.scarex.csp.inventory.container.ContainerSolarPanelFrame;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
        case 0:
            return new ContainerSolarPanelFrame((TileEntitySolarPanel) world.getTileEntity(x, y, z), player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
        case 0:
            return new GuiSolarPanelFrame(player.inventory, (TileEntitySolarPanel) world.getTileEntity(x, y, z));
        }
        return null;
    }

    public void registerRender() {}
}
