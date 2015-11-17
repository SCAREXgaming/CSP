package fr.scarex.csp.block;

import fr.scarex.csp.tileentity.TileEntitySolarCellConverter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SolarCellConverter extends AbstractBlockDismantleable
{
    protected SolarCellConverter() {
        super(Material.rock);
        float space = 0.125F;
        this.setBlockBounds(space, 0F, space, 1F - space, 0.855F, 1F - space);
    }

    @Override
    public String getName() {
        return "SolarCellConverter";
    }

    @Override
    public Class getTileEntityClass() {
        return TileEntitySolarCellConverter.class;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntitySolarCellConverter();
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

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking() && !world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntitySolarCellConverter) {
            int stacksize = 0;
            if (player.getCurrentEquippedItem() != null) {
                if ((stacksize = ((TileEntitySolarCellConverter) world.getTileEntity(x, y, z)).canInputItem(player, side, player.getCurrentEquippedItem().copy())) > 0) {
                    player.getCurrentEquippedItem().stackSize -= stacksize;
                    if (player.getCurrentEquippedItem().stackSize <= 0) player.setCurrentItemOrArmor(0, null);
                }
            } else if (((TileEntitySolarCellConverter) world.getTileEntity(x, y, z)).canExtractItem(side, null, 0, true)) {
                player.setCurrentItemOrArmor(0, ((TileEntitySolarCellConverter) world.getTileEntity(x, y, z)).getStackInSlotOnClosing(0));
            }
            return stacksize > 0;
        }
        return true;
    }
}
