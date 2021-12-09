package com.company;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.*;

import static java.util.Map.entry;

public class Main {
    public static Scanner in = new Scanner(System.in);
    // various stats
    public static int bone = 0;
    public static int life = 20;
    public static int manaMax = 0;
    public static int mana = 1;
    public static int AIbone = 0;
    public static int AIlife = 20;
    public static int AImana = 1;
    public static int blood = 0;
    public static int AIblood = 0;
    public static ArrayList<String> stateYour = new ArrayList<>(); // permanents you control. A list of keys to cardBase
    public static ArrayList<String> stateAI = new ArrayList<>(); // permanents the AI controls,
    public static Deck pl2 = new Deck();

    public static Creature declareCreature(int mana, int blood, int bone, int atk, int health, String Glyph) {
        // creates a new creature, and sets up its atributes.
        Creature card = new Creature();
        card.setAtk(atk);
        card.setDef(health);
        card.setManaCost(mana);
        card.setBloodCost(blood);
        card.setBoneCost(bone);
        card.setGlyph(Glyph);
        return (card);
    }

    public static Spell declareSpell(int mana, int blood, int bone, String action, int power, String type) {
        // creates a new spell, and sets each of its attributes
        Spell card = new Spell();
        card.setAction(action);
        card.setPower(power);
        card.setManaCost(mana);
        card.setBloodCost(blood);
        card.setBoneCost(bone);
        card.setType(type);
        return (card);
    }

    public static void DeclareCardBase(Map<String, Card> cardBase) {
        // Creates the card base, using the declareCreature and declareSpell methods. To add a card,
        // copy a line and change the stats. The key is the cards name.
        cardBase.put("ZapWiz", declareCreature(2, 0, 0, 5, 2, "drawZap"));
        cardBase.put("FireWiz", declareCreature(3, 0, 0, 4, 5, "burn"));
        cardBase.put("Skeletal Defender", declareCreature(0, 0, 10, 0, 10, null));
        cardBase.put("Demonic Entity", declareCreature(0, 3, 0, 7, 7, "burn"));
        cardBase.put("Sacrifice", declareCreature(0, 0, 0, 0, 1, "altar"));
        cardBase.put("Innocent", declareCreature(4, 0, 0, 2, 2, "divine"));
        cardBase.put("Priest", declareCreature(5, 0, 0, 0, 5, "healer"));
        cardBase.put("Knight", declareCreature(6, 0, 0, 5, 10, "guard"));
        cardBase.put("Dragon", declareCreature(0, 4, 5, 5, 10, "AOE"));
        cardBase.put("Vampire", declareCreature(0, 1, 2, 3, 5, "blood-drinker"));
        cardBase.put("Zap", declareSpell(2, 0, 0, "Attack", 4, "instant"));
        cardBase.put("Thunder", declareSpell(4, 0, 0, "Attack", 8, "instant"));
        cardBase.put("ThunderStorm", declareSpell(5, 1, 0, "Attack", 2, "enchantment"));
        cardBase.put("Disenchant", declareSpell(2, 0, 0, "DestroyEn", 1, "instant"));
        cardBase.put("Sniper", declareCreature(5, 0, 0, 25, 0, null));
    }

    public static boolean canCast(Card card, Boolean AI) {
        // checks if you, or the ai, can cast a given card.
        boolean m = Boolean.FALSE;
        if (AI) {
            if (card.getBoneCost() <= AIbone && card.getBloodCost() <= AIblood && card.getManaCost() <= AImana) {
                m = Boolean.TRUE;
            }
        } else {
            if (card.getBoneCost() <= bone && card.getBloodCost() <= blood && card.getManaCost() <= mana) {
                m = Boolean.TRUE;
            }
        }
        return (m);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        // get the setup function going, and then start gameplay loop
        Map<String, Card> cardBase = new HashMap<String, Card>();
        DeclareCardBase(cardBase);
        Deck pl1 = new Deck();
        while (true) {
            pl1 = turn(cardBase, pl1);
        }
    }

    public static Deck turn(Map<String, Card> cardBase, Deck pl1) {
        if (manaMax == 0) {
            //set up your deck
            pl1.createDeck(cardBase, in);
            manaMax += 1;
            //draw your hand
            ArrayList<String> t = new ArrayList<String>();
            t.add(pl1.draw());
            t.add(pl1.draw());
            t.add(pl1.draw());
            life = 20;
            pl1.setHand(t);
            //setup the AI
            pl2.setAIDECK(cardBase);
            //draw its hand
            t = new ArrayList<String>();
            t.add(pl2.draw());
            t.add(pl2.draw());
            t.add(pl2.draw());
            AIlife = 20;
            pl2.setHand(t);
            stateAI = new ArrayList<>();
            stateYour = new ArrayList<>();
            return (pl1);
        } else {
            pl1.getHand().add(pl1.draw());
            //reset mana
            if (manaMax != 10) {
                manaMax += 1;
            }
            mana = manaMax;
            AImana = manaMax;
            //print stats
            //print AI's board state
            boolean nextTurn = Boolean.FALSE;
            while (!nextTurn) {
                for (int i = 0; i < stateYour.size(); i++) {
                    Card card = cardBase.get(stateYour.get(i));
                    if (card.getClass().equals(Creature.class)) {
                        System.out.println(("Your Creature: " + stateYour.get(i) + " Atk: " + ((Creature) card).getAtk()) + " Def: " + ((Creature) card).getDef());
                    }
                    if (card.getClass().equals(Spell.class)) {
                        System.out.println(("Your Spell " + stateYour.get(i) + " Power: " + ((Spell) card).getPower()) + " Action: " + ((Spell) card).getAction());
                    }
                }
                //print AI's board state
                for (String s : stateAI) {
                    Card card = cardBase.get(s);
                    if (card.getClass().equals(Creature.class)) {
                        System.out.println(("Enemy Creature: " + s + " Atk: " + ((Creature) card).getAtk()) + " Def: " + ((Creature) card).getDef());
                    }
                    if (card.getClass().equals(Spell.class)) {
                        System.out.println(("Enemy Spell " + s + " Power: " + ((Spell) card).getPower()) + " Action: " + ((Spell) card).getAction());
                    }
                }
                for (int i = 0; i < pl1.hand.size(); i++) {
                    Card card = cardBase.get(pl1.hand.get(i));
                    System.out.println(i + "." + pl1.hand.get(i) + ": " + card.printCost());
                    if (card.getClass() == Creature.class) {
                        System.out.println(((Creature) card).print());
                    }
                    if (card.getClass() == Spell.class) {
                        System.out.println(((Spell) card).print());
                    }
                }
                String play = in.nextLine();
                if(play.equals("pass") || play.equals("Pass") || play.equals("next") || play.equals("next turn") || play.equals("t")){
                    nextTurn = true;
                }
                else if (Integer.parseInt(play) < pl1.hand.size()) {
                    Card card = cardBase.get(pl1.hand.get(Integer.parseInt(play)));
                    if (canCast(card, Boolean.FALSE)) {
                        blood = blood - card.getBloodCost();
                        bone = bone - card.getBoneCost();
                        mana = mana - card.getManaCost();
                        if (card.getClass() == Creature.class) {
                            stateYour.add(pl1.hand.get(Integer.parseInt(play)));
                        } else if (card.getClass() == Spell.class) {
                            if (((Spell) card).getType().equals("enchantment"))
                                stateYour.add(pl1.hand.get(Integer.parseInt(play)));
                            else {
                                ((Spell) card).cast(stateYour, stateAI, cardBase);
                            }
                        }
                        pl1.getHand().remove(Integer.parseInt(play));
                    }
                }
            }
            for (int i = 0; i < stateYour.size(); i++) {
                Card card = cardBase.get(stateYour.get(i));
                if (i < stateAI.size() && card.getClass() == Creature.class){
                    Card matchup = cardBase.get(stateAI.get(i));
                    if (matchup.getClass() == Creature.class){
                        if(((Creature) matchup).getDef() <= ((Creature) card).getAtk()){
                            if (((Creature) matchup).getGlyph().equals("Altar")){
                                AIblood = AIblood+1;
                            }
                            stateAI.remove(i);
                            AIbone = AIbone + 1;
                        }
                    }
                    else{
                        AIlife = AIlife - ((Creature) card).getAtk();
                    }
                }
                else if (card.getClass() == Creature.class){
                    AIlife = AIlife - ((Creature) card).getAtk();
                }
                else if (card.getClass() == Spell.class){
                    ((Spell) card).cast(stateYour, stateAI, cardBase);
                }
            }
            return (pl1);
        }
    }
}