package com.alexvr.bedres.capability;

import java.util.concurrent.Callable;

/**
 * Default implementation of IMana
 */
public class BedrockFlux implements IBedrockFlux
{
    private float bedrockflux = 250.0F;
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

    private static class Factory implements Callable<IBedrockFlux> {

        @Override
        public IBedrockFlux call() throws Exception {
            return new BedrockFlux();
        }
    }
}

