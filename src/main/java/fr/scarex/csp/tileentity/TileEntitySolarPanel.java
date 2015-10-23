package fr.scarex.csp.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.TileEnergyHandler;
import fr.scarex.csp.CSP;
import fr.scarex.csp.util.EnergyUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySolarPanel extends TileEntity implements IEnergyHandler
{
    protected EnergyStorage storage = new EnergyStorage(32000, 0, 1000);

    @Override
    public boolean canConnectEnergy(ForgeDirection d) {
        return d != ForgeDirection.UP;
    }

    @Override
    public int extractEnergy(ForgeDirection d, int amount, boolean simulate) {
        return storage.extractEnergy(amount, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection arg0) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection arg0) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public int receiveEnergy(ForgeDirection arg0, int arg1, boolean arg2) {
        return 0;
    }

    @Override
    public void updateEntity() {
        if (this.worldObj != null && !this.getWorldObj().isRemote && !this.getWorldObj().provider.hasNoSky) {
            int i1 = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord + 1, this.zCoord) - this.worldObj.skylightSubtracted;
            float f = this.worldObj.getCelestialAngleRadians(1.0F);

            i1 = Math.max(0, Math.round((float) i1 * MathHelper.cos(f)));
            this.storage.modifyEnergyStored(i1);
            EnergyUtils.transmit(this);
        }
    }
}
