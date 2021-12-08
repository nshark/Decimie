package com.company;

import java.lang.reflect.Array;
import java.util.*;

import static java.util.Map.entry;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static int bone = 0;
    public static int life = 20;
    public static int manaMax = 0;
    public static int mana = 1;
    public static Creature declareCreature(int mana, int blood, int bone, int atk, int health, String Glyph) {
        Creature card = new Creature();
        card.setAtk(atk);
        card.setDef(health);
        card.setManaCost(mana);
        card.setBloodCost(blood);
        card.setBoneCost(bone);
        card.setGlyph(Glyph);
        return(card);
    }
    public static Spell declareSpell(int mana, int blood, int bone, String action, int power, String type) {
        Spell card = new Spell();
        card.setAction(action);
        card.setPower(power);
        card.setManaCost(mana);
        card.setBloodCost(blood);
        card.setBoneCost(bone);
        card.setType(type);
        return(card);
    }
    public static void DeclareCardBase(Map<String, Card> cardBase){
        cardBase.put("ZapWiz", declareCreature(2, 0, 0, 5, 2, "drawZap"));
        cardBase.put("FireWiz", declareCreature(3, 0, 0, 4, 5, "burn"));
        cardBase.put("Skeletal Defender", declareCreature(0, 0, 10, 0, 10, null));
        cardBase.put("Demonic Entity", declareCreature(0, 3, 0, 7, 7, "burn"));
        cardBase.put("Sacrifice", declareCreature(0, 0, 0, 0, 1, "altar"));
        cardBase.put("Innocent", declareCreature(4, 0, 0, 2, 2, "divine"));
        cardBase.put("Priest", declareCreature(5, 0, 0, 0, 5, "healer"));
        cardBase.put("Knight", declareCreature(6, 0, 0, 5, 10, "guard"));
        cardBase.put("Dragon", declareCreature(0, 4, 5, 10, 20, "AOE"));
        cardBase.put("Vampire", declareCreature(0, 1, 2, 3, 5, "blood-drinker"));
        cardBase.put("Zap", declareSpell(2, 0, 0, "Attack", 4, "instant"));
        cardBase.put("Thunder", declareSpell(4, 0, 0, "Attack", 8, "instant"));
        cardBase.put("Heal", declareSpell(2, 0, 0, "Heal", 4, "instant"));
        cardBase.put("Surge", declareSpell(3, 0, 0, "BuffAtk", 2, "sorcery"));
        cardBase.put("ThunderStorm", declareSpell(5, 1, 0, "AttackAll", 2, "enchantment"));
        cardBase.put("Disenchant", declareSpell(2, 0, 0, "DestroyEn", 1, "instant"));
        }
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        Map<String, Card> cardBase =  new HashMap<String, Card>();
        DeclareCardBase(cardBase);
        Deck pl1 = new Deck();
        while(true){
            pl1 = turn(cardBase, pl1);
        }
    }
    public static Deck turn(Map<String, Card> cardBase, Deck pl1) {
        if(manaMax == 0){
            pl1.createDeck(cardBase, in);
            manaMax += 1;
            ArrayList<String> t = new ArrayList<String>();
            t.add(pl1.draw());
            t.add(pl1.draw());
            t.add(pl1.draw());
            life = 20;
            pl1.setHand(t);
            return(pl1);
        }
        else{
            pl1.getHand().add(pl1.draw());
            if(manaMax != 10){
                manaMax += 1;
            }
            mana = manaMax;
            System.out.println("(B:" + bone + ", M:" + mana + ", L:" + life);
            return(pl1);
        }
    }
}
