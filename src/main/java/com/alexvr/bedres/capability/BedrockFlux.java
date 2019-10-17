package com.alexvr.bedres.capability;

import java.text.DecimalFormat;
import java.util.concurrent.Callable;

/**
 * Default implementation of IMana
 */
public class BedrockFlux implements IBedrockFlux
{
    private float bedrockflux = 0.0f;
    private float maxbedrockflux = 2500.0F;

    public void consume(float points)
    {
        this.bedrockflux -= points;

        if (this.bedrockflux < 0.0F) this.bedrockflux = 0.0F;
    }

    public float fill(float points)
    {
        float residue = 0;
        this.bedrockflux += points;
        if(bedrockflux>maxbedrockflux){
            residue = bedrockflux-maxbedrockflux;
            bedrockflux= maxbedrockflux;
        }
        return residue;
    }

    public void set(float points)
    {
        this.bedrockflux = points;
    }

    public float getBedrockFlux()
    {
        return this.bedrockflux;
    }

    @Override
    public float getMaxBedrockFlux() {
        return maxbedrockflux;
    }

    @Override
    public String getBedrockFluxString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(getBedrockFlux());
    }

    private static class Factory implements Callable<IBedrockFlux> {

        @Override
        public IBedrockFlux call() throws Exception {
            return new BedrockFlux();
        }
    }
}

