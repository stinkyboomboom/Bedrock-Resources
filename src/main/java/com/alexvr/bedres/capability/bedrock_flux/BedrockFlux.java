package com.alexvr.bedres.capability.bedrock_flux;

import com.alexvr.bedres.gui.FluxOracleScreen;

import java.text.DecimalFormat;
import java.util.concurrent.Callable;

/**
 * Default implementation of IMana
 */
public class BedrockFlux implements IBedrockFlux
{
    private float bedrockflux = 0.0f;
    private float maxbedrockflux = 2500.0F;
    private boolean crafted = false;
    private FluxOracleScreen screen = null;

    public void consume(float points)
    {
        this.bedrockflux -= points;

        if (this.bedrockflux < 0.0F) this.bedrockflux = 0.0F;
        if (crafted){
            screen.flux = this;
        }
    }

    public float fill(float points)
    {
        float residue = 0;
        this.bedrockflux += points;
        if(bedrockflux>maxbedrockflux){
            residue = bedrockflux-maxbedrockflux;
            bedrockflux= maxbedrockflux;
        }
        if (crafted){
            screen.flux = this;
        }
        return residue;
    }

    public void set(float points)
    {
        this.bedrockflux = points;
        if (crafted){
            screen.flux = this;
        }
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
    public boolean getCrafterFlux() {
        return crafted;
    }

    @Override
    public void setCrafterFlux() {
        crafted = true;

    }

    @Override
    public FluxOracleScreen getScreen() {
        return screen;
    }

    @Override
    public void setScreen(FluxOracleScreen fx) {
        screen=fx;
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

