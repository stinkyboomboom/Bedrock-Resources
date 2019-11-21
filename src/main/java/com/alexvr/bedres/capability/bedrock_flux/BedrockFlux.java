package com.alexvr.bedres.capability.bedrock_flux;

import com.alexvr.bedres.Config;
import com.alexvr.bedres.gui.FluxOracleScreen;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Default implementation of IMana
 */
public class BedrockFlux implements IBedrockFlux
{
    private float bedrockflux = 0.0f;
    private double maxbedrockflux = Config.DEF_MAX_FLUX.get();
    private float minbedrockflux = 0.0f;
    private boolean crafted = false;
    private FluxOracleScreen screen = null;
    private int timer =0;
    private int maxTimer = Config.DEF_MAX_TIMER.get();

    public void consume(float points)
    {
        this.bedrockflux -= points;

        if (this.bedrockflux < 0.0F) this.bedrockflux = 0.0F;
        if (crafted){
            screen.flux = this;
        }
        if (bedrockflux<minbedrockflux){
            bedrockflux=minbedrockflux;
        }
    }

    public float fill(float points)
    {
        float residue = 0;
        this.bedrockflux += points;
        if(bedrockflux>maxbedrockflux){
            residue = (float) (bedrockflux-maxbedrockflux);
            bedrockflux= (float) maxbedrockflux;
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
        if (bedrockflux<minbedrockflux){
            bedrockflux=minbedrockflux;
        }
    }

    @Override
    public void consumeMin(float points) {
        minbedrockflux-=points;
    }

    @Override
    public float fillMin(float points) {
        return minbedrockflux+=points;
    }

    @Override
    public void setMin(float points) {
        minbedrockflux=points;
    }

    public float getBedrockFlux()
    {
        return this.bedrockflux;
    }

    @Override
    public double getMaxBedrockFlux() {
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

    @Override
    public int getTimer() {
        return timer;
    }

    @Override
    public int getMaxTimer() {
        return maxTimer;
    }

    @Override
    public void count() {
        timer++;
    }

    @Override
    public void changeMax() {
        timer=0;
        maxTimer = new Random().nextInt((20*60)*5)+((20*60)*5);
    }

    private static class Factory implements Callable<IBedrockFlux> {

        @Override
        public IBedrockFlux call() throws Exception {
            return new BedrockFlux();
        }
    }
}

