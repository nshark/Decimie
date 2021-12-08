package com.company;


public class Card {
    private int manaCost;
    private int bloodCost;
    private int boneCost;

    public int getBloodCost() {
        return bloodCost;
    }
    public int getBoneCost() {
        return boneCost;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setBloodCost(int bloodCost) {
        this.bloodCost = bloodCost;
    }

    public void setBoneCost(int boneCost) {
        this.boneCost = boneCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
    public String printCost(){
        // return a string to properly print its price.
        return("Cost(mana, blood, bones): " + this.getManaCost() + ", " + this.getBloodCost() + ", " + this.getBoneCost());
    }
}
