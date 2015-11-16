package fr.scarex.csp.util.energy;

import cofh.api.energy.IEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;

public class CSPEnergyStorage implements IEnergyStorage
{
    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public CSPEnergyStorage(int capacity) {
        this(capacity, capacity, capacity);
    }

    public CSPEnergyStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer);
    }

    public CSPEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public CSPEnergyStorage readFromNBT(NBTTagCompound nbt) {
        this.energy = nbt.getInteger("Energy");
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("Energy", energy);
        return nbt;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMaxTransfer(int maxTransfer) {
        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
    }

    public void setMaxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
    }

    public void setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public void setEnergyStored(int energy) {
        this.energy = energy;
    }

    public void modifyEnergyStored(int energy) {
        this.energy += energy;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

        if (!simulate) energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

        if (!simulate) energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    public void update() {
        if (energy > capacity) energy = capacity;
        if (energy < 0) energy = 0;
    }
}
