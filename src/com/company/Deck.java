package com.company;

import java.lang.reflect.Array;
import java.util.*;

public class Deck {
    public Map<String, Card> cards = new HashMap<String, Card>();
    public Map<String, Integer> cardNum = new HashMap<String, Integer>();
    public ArrayList<String> unDrawn = new ArrayList<String>();
    public Random ran = new Random();
    public ArrayList<String> hand = new ArrayList<String>();

    public ArrayList<String> getHand() {
        return hand;
    }
    public void setHand(ArrayList<String> hand) {
        this.hand = hand;
    }

    public void createDeck(Map cardBase, Scanner in){
        Spell x = new Spell();
        Creature y = new Creature();
        for (int i = 0; i < cardBase.size(); i++) {
            String indexKey = (String) cardBase.keySet().toArray()[i];
            Card index = (Card) cardBase.get(indexKey);
            System.out.println(i+". "+ indexKey);
            System.out.println(index.printCost());
            if (index.getClass().equals(x.getClass())){
                System.out.println(((Spell) index).print());
            }
            if (index.getClass().equals(y.getClass())) {
                System.out.println(((Creature) index).print());
            }
        }
        for (int i = 0; i < 10; i++) {
            int index = Integer.parseInt(in.nextLine());
            if(index > cardBase.size()){
                i = i - 1;
                System.out.println("Please select valid card index");
            }
            else{
                if(cards.containsKey(cardBase.keySet().toArray()[index])){
                    cardNum.replace((String) cardBase.keySet().toArray()[index], cardNum.get((String) cardBase.keySet().toArray()[index])+1);
                }
                else {
                    cardNum.put((String) cardBase.keySet().toArray()[index], 1);
                    cards.put((String) cardBase.keySet().toArray()[index], (Card) cardBase.get(cardBase.keySet().toArray()[index]));
                }
            }
        }
        System.out.println("Deck Complete");
        for (int i = 0; i < cardNum.size(); i++) {
            System.out.println((String) cardNum.keySet().toArray()[i] + " " + cardNum.get(cardNum.keySet().toArray()[i]));
        }
        reshuffle();
    }
    public void reshuffle() {
        for (int i = 0; i < cardNum.size(); i++) {
            for (int j = 0; j < cardNum.get(cardNum.keySet().toArray()[i]); j++) {
                unDrawn.add((String) cardNum.keySet().toArray()[i]);
            }
        }
    }
    public String draw(){
        if (unDrawn.size() == 0){
            reshuffle();
        }
        int m = ran.nextInt(unDrawn.size());
        String x = unDrawn.get(m);
        unDrawn.remove(m);
        return(x);
    }
}