package com.alexvr.bedres.capability.bedrock_flux;


import com.alexvr.bedres.gui.FluxOracleScreen;

public interface IBedrockFlux {
    void consume(float points);
    float fill(float points);
    void set(float points);

    void consumeMin(float points);
    float fillMin(float points);
    void setMin(float points);
    float getMin();

    float getBedrockFlux();
    double getMaxBedrockFlux();
    void setMaxBedrockFlux(double amount);

    boolean getCrafterFlux();
    void setCrafterFlux();
    void setCrafterFlux(boolean flag);

    FluxOracleScreen getScreen();
    void setScreen(FluxOracleScreen fx);

    String getBedrockFluxString();

    int getTimer();
    int getMaxTimer();
    void setTimer(int x);
    void setMaxTimer(int x);
    void count();
    void changeMax();
}