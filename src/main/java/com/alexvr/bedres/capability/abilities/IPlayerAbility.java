package com.alexvr.bedres.capability.abilities;

public interface IPlayerAbility {


    String getNAme();
    String getAxe();
    String getPick();
    String getShovel();
    String getSword();
    String getHoe();

    int getMiningSpeedBoost();
    float getJumpBoost();
    float getGravityMultiplier();


    void setAxe(String name);
    void setPick(String name);
    void setShovel(String name);
    void setSword(String name);
    void setHoe(String name);
    void setname(String name);

    void setMiningSpeedBoost(int amount);
    void setJumpBoost(float amount);
    void setGRavityMultiplier(float amount);

    void addMiningSpeed(int amount);
    void addJump(float amount);
    void addGrav(float amount);


}
