package com.alexvr.bedres.capability;


public interface IBedrockFlux {
    void consume(float points);
    float fill(float points);
    void set(float points);

    float getBedrockFlux();
    float getMaxBedrockFlux();


    String getBedrockFluxString();
}