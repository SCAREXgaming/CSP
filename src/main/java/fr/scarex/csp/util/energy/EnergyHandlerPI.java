package fr.scarex.csp.util.energy;

import cofh.api.energy.IEnergyHandler;

/**
 * @author Crazypants
 *
 */
public class EnergyHandlerPI extends EnergyReceiverPI
{
    private IEnergyHandler rfPower;

    public EnergyHandlerPI(IEnergyHandler powerReceptor) {
        super(powerReceptor);
    }

    @Override
    public boolean isInputOnly() {
        return false;
    }

    @Override
    public boolean isOutputOnly() {
        return false;
    }
}
