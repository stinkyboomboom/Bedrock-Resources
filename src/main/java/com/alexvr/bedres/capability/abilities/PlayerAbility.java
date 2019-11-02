package com.alexvr.bedres.capability.abilities;

public class PlayerAbility implements  IPlayerAbility{

    String axe = "none",pick= "none",shovel= "none",sword= "none",hoe= "none",name = "none";
    int speed =1,jump=0;
    float gravilty = 0;

    @Override
    public String getNAme() {
        return name;
    }

    @Override
    public String getAxe() {
        return axe;
    }

    @Override
    public String getPick() {
        return pick;
    }

    @Override
    public String getShovel() {
        return shovel;
    }

    @Override
    public String getSword() {
        return sword;
    }

    @Override
    public String getHoe() {
        return hoe;
    }

    @Override
    public int getSpeedBoost() {
        return speed;
    }

    @Override
    public int getJumpBoost() {
        return jump;
    }

    @Override
    public float getGravityMultiplier() {
        return gravilty;
    }

    @Override
    public void setAxe(String name) {
        axe=name;
    }

    @Override
    public void setPick(String name) {
        pick=name;

    }

    @Override
    public void setShovel(String name) {
        shovel=name;

    }

    @Override
    public void setSword(String name) {
        sword=name;

    }

    @Override
    public void setHoe(String name) {
        hoe=name;

    }

    @Override
    public void setname(String name) {
        this.name=name;
    }

    @Override
    public void setSpeedBoost(int amount) {
        speed=amount;

    }

    @Override
    public void setJumpBoost(int amount) {
        jump=amount;

    }

    @Override
    public void setGRavityMultiplier(float amount) {
        gravilty=amount;
    }

    @Override
    public void addSpeed(int amount) {
        speed+=amount;
    }

    @Override
    public void addJump(int amount) {
        jump+=amount;
    }

    @Override
    public void addGrav(float amount) {
        gravilty+=amount;
    }
}
