package fr.scarex.csp.block;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.scarex.csp.CSP;
import fr.scarex.csp.tileentity.TileEntitySolarPanel;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class SolarPanelFrame extends AbstractEnergyBlock
{
    protected SolarPanelFrame() {
        super(Material.rock);
    }

    @Override
    public String getName() {
        return "SolarPanelFrame";
    }

    @Override
    public Class getTileEntityClass() {
        return TileEntitySolarPanel.class;
    }

    @Override
    public void registerCrafts() {
        GameRegistry.addRecipe(new ShapedOreRecipe(this, "XXX", " Y ", "YYY", 'X', "ingotSolar", 'Y', "ingotIron"));
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntitySolarPanel();
    }

    @Override
    public boolean canDismantle(EntityPlayer arg0, World arg1, int arg2, int arg3, int arg4) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking() && !world.isRemote) {
            int stacksize;
            if (player.getCurrentEquippedItem() != null && world.getTileEntity(x, y, z) instanceof TileEntitySolarPanel && (stacksize = ((TileEntitySolarPanel) world.getTileEntity(x, y, z)).canInputItem(player, player.getCurrentEquippedItem().copy())) > 0) {
                player.getCurrentEquippedItem().stackSize -= stacksize;
                if (player.getCurrentEquippedItem().stackSize <= 0) player.setCurrentItemOrArmor(0, null);
            } else {
                player.openGui(CSP.INSTANCE, 0, world, x, y, z);
            }
        }
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean hasSpecialRender() {
        return true;
    }

    @Override
    protected String getTextureName() {
        return "glass";
    }
}
