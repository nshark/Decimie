package com.company;

import java.util.*;

@SuppressWarnings("ALL")
public class Main {
    public static Scanner in = new Scanner(System.in);
    // various stats
    private static int bone = 0;
    private static int life = 40;
    private static int manaMax = 0;
    private static int mana = 1;
    private static int AIbone = 0;
    public static int AIlife = 40;
    public static int AImana = 1;
    public static int blood = 0;
    public static int AIblood = 0;
    private static ArrayList<String> stateYour = new ArrayList<>(); // permanents you control. A list of keys to cardBase
    public static ArrayList<String> stateAI = new ArrayList<>(); // permanents the AI controls,
    public static Deck pl2 = new Deck();

    public static Creature declareCreature(int mana, int blood, int bone, int atk, int health, String Glyph) {
        // creates a new creature, and sets up its attributes.
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
        cardBase.put("Skeleton", declareCreature(0, 0, 2, 2, 2, "bone"));
        cardBase.put("ZapWiz", declareCreature(3, 0, 0, 5, 2, "spell-caster"));
        cardBase.put("FireWiz", declareCreature(3, 0, 0, 4, 5, "burn"));
        cardBase.put("Skeletal Defender", declareCreature(0, 0, 10, 0, 10, null));
        cardBase.put("Demonic Entity", declareCreature(0, 3, 0, 7, 7, "burn"));
        cardBase.put("Sacrifice", declareCreature(0, 0, 0, 0, 1, "altar"));
        cardBase.put("Innocent", declareCreature(4, 0, 0, 2, 2, "divine"));
        cardBase.put("Priest", declareCreature(5, 0, 0, 2, 5, "healer"));
        cardBase.put("Knight", declareCreature(6, 0, 0, 5, 10, "guard"));
        cardBase.put("Dragon", declareCreature(0, 4, 5, 5, 10, "AOE"));
        cardBase.put("Vampire", declareCreature(0, 1, 2, 3, 5, "blood-drinker"));
        cardBase.put("Zap", declareSpell(2, 0, 0, "Attack", 4, "instant"));
        cardBase.put("Thunder", declareSpell(4, 0, 0, "Attack", 8, "instant"));
        cardBase.put("ThunderStorm", declareSpell(5, 1, 0, "Attack", 2, "enchantment"));
        cardBase.put("Disenchant", declareSpell(4, 0, 0, "DestroyEn", 1, "instant"));
        cardBase.put("Sniper", declareCreature(5, 0, 0, 25, 0, null));
        cardBase.put("ArchPriest", declareCreature(8, 0, 0, 5, 5, "healer"));
        cardBase.put("DarkPriest", declareCreature(5, 3, 0, 10, 5, "healer"));

    }

    public static boolean canCast(Card card, Boolean AI) {
        // checks if you, or the AI, can cast a given card.
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
        Map<String, Card> cardBase = new HashMap<>();
        DeclareCardBase(cardBase);
        Deck pl1 = new Deck();
        while (true) {
            turn(cardBase, pl1);
        }
    }

    public static void turn(Map<String, Card> cardBase, Deck pl1) {
        if (manaMax == 0) {
            //set up your deck
            pl1.createDeck(cardBase, in);
            manaMax += 1;
            //draw your hand
            ArrayList<String> t = new ArrayList<String>();
            t.add(pl1.draw());
            t.add(pl1.draw());
            t.add(pl1.draw());
            life = 40;
            pl1.setHand(t);
            //set up the AI
            pl2.setAIDECK(cardBase);
            //draw its hand
            t = new ArrayList<String>();
            t.add(pl2.draw());
            t.add(pl2.draw());
            t.add(pl2.draw());
            AIlife = 40;
            pl2.setHand(t);
            stateAI = new ArrayList<>();
            stateYour = new ArrayList<>();
        } else {
            pl1.getHand().add(pl1.draw());
            pl2.getHand().add(pl2.draw());
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
                System.out.println("Enemy Life:" + AIlife + " Your life" + life + " (mana, blood, bone)" + mana + "," + blood + "," + bone);
                System.out.println(stateYour);
                System.out.println(stateAI);
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
                if (play.equals("pass") || play.equals("Pass") || play.equals("next") || play.equals("next turn") || play.equals("t")) {
                    nextTurn = true;
                } else if (Integer.parseInt(play) < pl1.hand.size()) {
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
                                ((Spell) card).cast(stateYour, stateAI, cardBase, blood, AIbone);
                            }
                        }
                        pl1.getHand().remove(Integer.parseInt(play));
                    }
                }
            }
            for (int i = 0; i < stateYour.size(); i++) {
                Card card = cardBase.get(stateYour.get(i));
                if ((i < stateYour.size() && card.getClass() == Creature.class) || stateAI.contains("Knight")) {
                    Card matchup;
                    if (stateAI.contains("Knight")) {
                        matchup = cardBase.get("Knight");
                    } else {
                        matchup = cardBase.get(stateAI.get(i));
                    }
                    if (matchup.getClass() == Creature.class) {
                        if (((Creature) matchup).getDef() <= ((Creature) card).getAtk()) {
                            if (((Creature) matchup).getGlyph().equals("altar")) {
                                AIblood = AIblood + 1;
                            }
                            if (((Creature) card).getGlyph().equals("blood-drinker")) {
                                blood = blood + 1;
                            }
                            if (((Creature) card).getGlyph().equals("blood-drinker")) {
                                blood = blood + 1;
                            }
                            if (((Creature) card).getGlyph().equals("bone")) {
                                bone = bone + 1;
                            }
                            if (((Creature) matchup).getGlyph().equals("bone")) {
                                AIbone = AIbone + 1;
                            }
                            if (((Creature) card).getGlyph().equals("burn")) {
                                AIlife = AIlife - 1;
                            }
                            if (stateAI.contains("Knight")) {
                                stateAI.remove("Knight");
                            } else {
                                stateAI.remove(i);
                            }
                            AIbone = AIbone + 1;
                        }
                    } else if (((Creature) card).getGlyph().equals("healer")) {
                        life = life + ((Creature) card).getAtk();
                    } else {
                        AIlife = AIlife - ((Creature) card).getAtk();
                    }
                } else if (card.getClass() == Creature.class) {
                    AIlife = AIlife - ((Creature) card).getAtk();
                } else if (card.getClass() == Spell.class) {
                    ((Spell) card).cast(stateYour, stateAI, cardBase, blood, AIbone);
                }
            }
            nextTurn = Boolean.FALSE;
            while (!nextTurn) {
                String play = pl2.moveAI();
                if (play.equals("pass") || play.equals("Pass") || play.equals("next") || play.equals("next turn") || play.equals("t")) {
                    nextTurn = true;
                } else if (Integer.parseInt(play) < pl2.hand.size()) {
                    Card card = cardBase.get(pl2.hand.get(Integer.parseInt(play)));
                    if (canCast(card, Boolean.TRUE)) {
                        AIblood = AIblood - card.getBloodCost();
                        AIbone = AIbone - card.getBoneCost();
                        AImana = AImana - card.getManaCost();
                        if (card.getClass() == Creature.class) {
                            stateAI.add(pl2.hand.get(Integer.parseInt(play)));
                        } else if (card.getClass() == Spell.class) {
                            if (((Spell) card).getType().equals("enchantment"))
                                stateAI.add(pl2.hand.get(Integer.parseInt(play)));
                            else {
                                ((Spell) card).cast(stateAI, stateYour, cardBase, AIblood, bone);
                            }
                        }
                        pl2.getHand().remove(Integer.parseInt(play));
                    }
                }
            }
            for (int i = 0; i < stateAI.size(); i++) {
                Card card = cardBase.get(stateAI.get(i));
                if ((i < stateYour.size() && card.getClass() == Creature.class) || stateYour.contains("Knight")) {
                    Card match;
                    if (stateYour.contains("Knight")) {
                        match = cardBase.get("Knight");
                    } else {
                        match = cardBase.get(stateYour.get(i));
                    }
                    if (match.getClass() == Creature.class) {
                        if (((Creature) match).getDef() <= ((Creature) card).getAtk()) {
                            if (((Creature) match).getGlyph().equals("altar")) {
                                blood = blood + 1;
                            }
                            if (((Creature) card).getGlyph().equals("blood-drinker")) {
                                AIblood = AIblood + 1;
                            }
                            if (((Creature) card).getGlyph().equals("bone")) {
                                AIbone = AIbone + 1;
                            }
                            if (((Creature) match).getGlyph().equals("bone")) {
                                bone = bone + 1;
                            }
                            if (((Creature) card).getGlyph().equals("burn")) {
                                life = life - 1;
                            }
                            if (stateYour.contains("Knight")) {
                                stateYour.remove("Knight");
                            } else {
                                stateYour.remove(i);
                            }
                            bone = bone + 1;
                        }
                    } else if (((Creature) card).getGlyph().equals("healer")) {
                        AIlife = AIlife + ((Creature) card).getAtk();
                    } else {
                        AIlife = AIlife - ((Creature) card).getAtk();
                    }
                } else if (card.getClass() == Creature.class) {
                    if (((Creature) card).getGlyph().equals("healer")) {
                        AIlife = AIlife + ((Creature) card).getAtk();
                    }
                    life = life - ((Creature) card).getAtk();
                } else if (card.getClass() == Spell.class) {
                    ((Spell) card).cast(stateAI, stateYour, cardBase, AIblood, bone);
                }
            }
        }
    }
}
