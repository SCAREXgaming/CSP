package fr.scarex.csp.util.energy;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import fr.scarex.csp.CSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyUtils
{
    /**
     * use power distribution instead
     */
    @Deprecated
    public static void transmit(IEnergyHandler emitter) {
        if (!((TileEntity) emitter).getWorldObj().isRemote) {
            List<ForgeDirection> possibleDirections = new ArrayList<ForgeDirection>();
            for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
                if (emitter.canConnectEnergy(d)) {
                    TileEntity t = ((TileEntity) emitter).getWorldObj().getTileEntity(((TileEntity) emitter).xCoord + d.offsetX, ((TileEntity) emitter).yCoord + d.offsetY, ((TileEntity) emitter).zCoord + d.offsetZ);
                    if (t instanceof IEnergyHandler && ((IEnergyHandler) t).canConnectEnergy(d.getOpposite())) {
                        CSP.LOGGER.info("adding : " + d);
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

    /**
     * @author Crazypants
     */
    public static IPowerInterface create(Object o) {
        if (o instanceof IEnergyHandler)
            return new EnergyHandlerPI((IEnergyHandler) o);
        else if (o instanceof IEnergyProvider)
            return new EnergyProviderPI((IEnergyProvider) o);
        else if (o instanceof IEnergyReceiver)
            return new EnergyReceiverPI((IEnergyReceiver) o);
        else if (o instanceof IEnergyConnection) return new EnergyConnectionPI((IEnergyConnection) o);
        return null;
    }
}
