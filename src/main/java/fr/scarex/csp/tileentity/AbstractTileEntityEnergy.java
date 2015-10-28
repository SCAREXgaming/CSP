package fr.scarex.csp.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import fr.scarex.csp.util.EnergyUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class AbstractTileEntityEnergy extends TileEntity implements IEnergyHandler
{
    protected final EnergyStorage storage;

    public AbstractTileEntityEnergy(int capacity, int input, int output) {
        this.storage = new EnergyStorage(capacity, input, output);
    }

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
        return storage.receiveEnergy(arg1, arg2);
    }

    public void transmit() {
        EnergyUtils.transmit(this);
    }

    @Override
    public void readFromNBT(NBTTagCompound comp) {
        super.readFromNBT(comp);
        this.storage.readFromNBT(comp);
    }

    @Override
    public void writeToNBT(NBTTagCompound comp) {
        super.writeToNBT(comp);
        this.storage.writeToNBT(comp);
    }

    public NBTTagCompound writeExtraCompound() {
        NBTTagCompound comp = new NBTTagCompound();
        this.storage.writeToNBT(comp);
        return comp;
    }

    public void readExtraCompound(NBTTagCompound comp) {
        this.storage.readFromNBT(comp);
    }
}
