package fr.scarex.csp.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import fr.scarex.csp.util.BlockCoord;
import fr.scarex.csp.util.energy.PowerDistributor;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class AbstractTileEntityEnergy extends TileEntity implements IEnergyHandler
{
    protected PowerDistributor powerDistributor;
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
        if (this.powerDistributor == null) this.powerDistributor = new PowerDistributor(new BlockCoord(this));
        int canTransmit = Math.min(this.storage.getEnergyStored(), this.storage.getMaxExtract());
        if (canTransmit <= 0) return;
        int transmitted = this.powerDistributor.transmitEnergy(this.worldObj, canTransmit);
        this.storage.setEnergyStored(this.storage.getEnergyStored() - transmitted);
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

    public void onNeighborBlockChange(Block block) {
        if (this.powerDistributor != null) this.powerDistributor.neighboursChanged();
    }
}
