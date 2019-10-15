package com.alexvr.bedres.capability;

import java.text.DecimalFormat;

public interface IBedrockFlux {
    public void consume(float points);
    public float fill(float points);
    public void set(float points);

    public float getBedrockFlux();

    public String getBedrockFluxString();
}