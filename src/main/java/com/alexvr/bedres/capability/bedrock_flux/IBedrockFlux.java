package com.alexvr.bedres.capability.bedrock_flux;


import com.alexvr.bedres.gui.FluxOracleScreen;

public interface IBedrockFlux {
    void consume(float points);
    float fill(float points);
    void set(float points);

    float getBedrockFlux();
    float getMaxBedrockFlux();

    boolean getCrafterFlux();
    void setCrafterFlux();

    FluxOracleScreen getScreen();
    void setScreen(FluxOracleScreen fx);

    String getBedrockFluxString();

    int getTimer();
    int getMaxTimer();
    void count();
    void changeMax();
}