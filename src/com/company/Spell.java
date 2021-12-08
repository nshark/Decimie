package com.company;

public class Spell extends Card {
    private String type;
    private int power;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String print() {
        return("Action: " + this.getAction() + ", " + "Type: " + this.getType() + ", " + "Power: " + this.getPower());
    }
}
