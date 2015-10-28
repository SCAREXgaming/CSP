package fr.scarex.csp.util;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyUtils
{
    public static void transmit(IEnergyHandler emitter) {
        if (!((TileEntity) emitter).getWorldObj().isRemote) {
            List<ForgeDirection> possibleDirections = new ArrayList<ForgeDirection>();
            for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
                if (emitter.canConnectEnergy(d)) {
                    TileEntity t = ((TileEntity) emitter).getWorldObj().getTileEntity(((TileEntity) emitter).xCoord + d.offsetX, ((TileEntity) emitter).yCoord + d.offsetY, ((TileEntity) emitter).zCoord + d.offsetZ);
                    if (t instanceof IEnergyHandler && ((IEnergyHandler) t).canConnectEnergy(d.getOpposite())) {
                        possibleDirections.add(d);
                    }
                }
            }
            int size = possibleDirections.size();
            for (ForgeDirection d : possibleDirections) {
                int toSend = Math.round((float) emitter.getEnergyStored(d) / size) - (emitter.getEnergyStored(d) % size);
                TileEntity t = ((TileEntity) emitter).getWorldObj().getTileEntity(((TileEntity) emitter).xCoord + d.offsetX, ((TileEntity) emitter).yCoord + d.offsetY, ((TileEntity) emitter).zCoord + d.offsetZ);
                int sent = Math.min(emitter.extractEnergy(d, toSend, true), ((IEnergyHandler) t).receiveEnergy(d.getOpposite(), toSend, true));
                emitter.extractEnergy(d, sent, false);
                ((IEnergyHandler) t).receiveEnergy(d.getOpposite(), sent, false);
                size--;
            }
        }
    }
}
