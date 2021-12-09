package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public void cast(ArrayList<String> stateYour, ArrayList<String> stateAI, Map<String, Card> cardBase) {
        if(this.action.equals("Attack")){
            for (int i = 0; i < stateAI.size(); i++) {
                Card card = cardBase.get(stateAI.get(i));
                if(card.getClass() == Creature.class){
                    System.out.println(i + ": " + stateAI.get(i) + ", " + ((Creature) card).getDef());
                }
            }
        }
    }
}
