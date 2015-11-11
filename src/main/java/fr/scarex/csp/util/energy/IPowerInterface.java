package fr.scarex.csp.util.energy;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Crazypants
 *
 */
public interface IPowerInterface
{
    Object getDelegate();

    boolean canConduitConnect(ForgeDirection direction);

    int getEnergyStored(ForgeDirection dir);

    int getMaxEnergyStored(ForgeDirection dir);

    int getPowerRequest(ForgeDirection dir);

    int getMinEnergyReceived(ForgeDirection dir);

    int recieveEnergy(ForgeDirection opposite, int canOffer);

    boolean isOutputOnly();

    boolean isInputOnly();
}
