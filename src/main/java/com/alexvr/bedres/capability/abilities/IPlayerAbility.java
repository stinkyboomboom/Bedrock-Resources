package com.alexvr.bedres.capability.abilities;

public interface IPlayerAbility {


    String getNAme();
    String getAxe();
    String getPick();
    String getShovel();
    String getSword();
    String getHoe();

    int getSpeedBoost();
    int getJumpBoost();
    float getGravityMultiplier();


    void setAxe(String name);
    void setPick(String name);
    void setShovel(String name);
    void setSword(String name);
    void setHoe(String name);
    void setname(String name);

    void setSpeedBoost(int amount);
    void setJumpBoost(int amount);
    void setGRavityMultiplier(float amount);

    void addSpeed(int amount);
    void addJump(int amount);
    void addGrav(float amount);


}
