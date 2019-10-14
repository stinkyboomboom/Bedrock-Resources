package com.alexvr.bedres.capability;

public interface IBedrockFlux {
    public void consume(float points);
    public float fill(float points);
    public void set(float points);

    public float getBedrockFlux();
}