package com.company;

import java.lang.reflect.Array;
import java.util.*;
public class Deck {
    public Map<String, Card> cards = new HashMap<String, Card>();
    public Map<String, Integer> cardNum = new HashMap<String, Integer>();
    public ArrayList<String> unDrawn = new ArrayList<String>();
    public boolean AI = Boolean.FALSE;
    public Random ran = new Random();
    public ArrayList<String> hand = new ArrayList<String>();
    public int coun = 0;

    public ArrayList<String> getHand() {
        return this.hand;
    }

    public void setHand(ArrayList<String> hand) {
        this.hand = hand;
    }

    public void createDeck(Map<String, Card> cardBase, Scanner in) {
        /*Allows a player to manually create a deck*/
        // resets the cards in the deck
        this.cards = new HashMap<String, Card>();
        Spell x = new Spell();
        Creature y = new Creature();
        // prints out the available cards, and the index to add them to the deck
        for (int i = 0; i < cardBase.size(); i++) {
            String indexKey = (String) cardBase.keySet().toArray()[i];
            Card index = (Card) cardBase.get(indexKey);
            System.out.println(i + ". " + indexKey);
            System.out.println(index.printCost());
            if (index.getClass().equals(x.getClass())) {
                System.out.println(((Spell) index).print());
            }
            if (index.getClass().equals(y.getClass())) {
                System.out.println(((Creature) index).print());
            }
        }
        for (int i = 0; i < 10; i++) {
            int index = Integer.parseInt(in.nextLine());
            // gets the integer input
            if (index > cardBase.size()) {
                //checks to see if a invalid index was selected
                i = i - 1;
                System.out.println("Please select valid card index");
            } else {
                if (this.cards.containsKey(cardBase.keySet().toArray()[index])) {
                    // if a card already exists in the deck, increase the amount of that card by one
                    this.cardNum.replace((String) cardBase.keySet().toArray()[index], this.cardNum.get((String) cardBase.keySet().toArray()[index]) + 1);
                } else {
                    // else, add it to the deck
                    this.cardNum.put((String) cardBase.keySet().toArray()[index], 1);
                    this.cards.put((String) cardBase.keySet().toArray()[index], (Card) cardBase.get(cardBase.keySet().toArray()[index]));
                }
            }
        }
        System.out.println("Deck Complete");
        for (int i = 0; i < this.cardNum.size(); i++) {
            System.out.println((String) this.cardNum.keySet().toArray()[i] + " " + this.cardNum.get(cardNum.keySet().toArray()[i]));
        }
        reshuffle();
    }

    public void reshuffle() {
        /*Reshuffles already draw cards into the drawing pool.*/
        this.unDrawn = new ArrayList<>();
        for (int i = 0; i < this.cardNum.size(); i++) {
            for (int j = 0; j < this.cardNum.get(this.cardNum.keySet().toArray()[i]); j++) {
                this.unDrawn.add((String) this.cardNum.keySet().toArray()[i]);
            }
        }
    }

    public String draw() {
        /*Draw a card, and then remove a card. Returns the string key(in cardBase) of the drawn card.
         * If there is no cards left to draw, reshuffles the deck and draws*/
        if (this.unDrawn.size() == 0) {
            this.reshuffle();
        }
        int m = ran.nextInt(this.unDrawn.size());
        String x = this.unDrawn.get(m);
        this.unDrawn.remove(m);
        return (x);
    }

    public void setAIDECK(Map<String, Card> cardBase) {
        /*Randomly creates a deck for the AI to play, and sets this deck to be a AI*/
        this.AI = Boolean.TRUE;
        /*
        for (int i = 0; i < 10; i++) {
            int index = ran.nextInt(cardBase.size());
            if (this.cards.containsKey(cardBase.keySet().toArray()[index])) {
                // if a card already exists in the deck, increase the amount of that card by one
                this.cardNum.replace((String) cardBase.keySet().toArray()[index], this.cardNum.get((String) cardBase.keySet().toArray()[index]) + 1);
            } else {
                // else, add it to the deck
                this.cardNum.put((String) cardBase.keySet().toArray()[index], 1);
                this.cards.put((String) cardBase.keySet().toArray()[index], (Card) cardBase.get(cardBase.keySet().toArray()[index]));
            }
        }
         */
        cards.put("Skeletal Defender", cardBase.get("Skeletal Defender"));
        cards.put("ZapWiz", cardBase.get("ZapWiz"));
        cards.put("Thunder", cardBase.get("Thunder"));
        cards.put("FireWiz", cardBase.get("FireWiz"));
        cardNum.put("ZapWiz", 3);
        cardNum.put("FireWiz", 3);
        cardNum.put("Thunder", 2);
        cardNum.put("Skeletal Defender", 2);
        reshuffle();
    }

    public String moveAI() {
        if (coun < hand.size()) {
            coun = coun + 1;
            return ("" + (coun - 1));
        } else {
            coun = 0;
            return ("pass");
        }
    }
}