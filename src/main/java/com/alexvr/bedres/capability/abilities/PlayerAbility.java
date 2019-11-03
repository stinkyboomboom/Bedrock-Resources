package com.alexvr.bedres.capability.abilities;

public class PlayerAbility implements  IPlayerAbility{

    String axe = "no",pick= "no",shovel= "no",sword= "no",hoe= "no",name = "no";
    int speed =15;
    float gravilty = 0,jump=0;

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
    public int getMiningSpeedBoost() {
        return speed;
    }

    @Override
    public float getJumpBoost() {
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
    public void setMiningSpeedBoost(int amount) {
        speed=amount;

    }

    @Override
    public void setJumpBoost(float amount) {
        jump=amount;

    }

    @Override
    public void setGRavityMultiplier(float amount) {
        gravilty=amount;
    }

    @Override
    public void addMiningSpeed(int amount) {
        speed+=amount;
    }

    @Override
    public void addJump(float amount) {
        jump+=amount;
    }

    @Override
    public void addGrav(float amount) {
        gravilty+=amount;
    }
}
