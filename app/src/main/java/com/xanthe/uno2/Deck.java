package com.xanthe.uno2;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    public ArrayList<Card> deck;

    public Deck(){this.deck = new ArrayList<>();}

    //Build the initial deck
    public void buildDeck(){

        /*

        AN UNO DECK CONTAINS CARDS IN FOUR COLORS
        EACH COLOR CONTAINS THE FOLLOWING CARDS:
            2 of each number 1 - 9
            1 of number 0
            2 skips
            2 reverses
            2 draw 2s
        EACH DECK ALSO CONTAINS THE FOLLOWING:
            4 wild cards
            4 wild draw 4 cards

         */

        //Build the portion of the deck which occurs for every color
        for (int color = 1; color < 5; color++){

            for (int num = 0; num < 13; num++){
                int x = (num == 0) ? 1 : 2;

                for (int i = 0; i < x; i++){

                    deck.add(new Card(color, num));

                }
            }
        }

        //Add the wild cards
        for (int i = 0; i < 4; i++){
            for (int x = 0; x < 2; x++){
                int action = (x == 0) ? 13 : 14;
                deck.add(new Card(5, action));
            }
        }

    }


    public int getDeckSize(){
        return this.deck.size();
    }


    //Draws the top card from the main deck and adds it to this deck
    public Card draw(){
        //Check if pile empty, if so, take top card, shuffle trash deck and make it the main deck
        int mainDeckSize = Game.mainDeck.getDeckSize();
        if (mainDeckSize == 0){
            replaceMainDeck();
        }

        int lastIndex = mainDeckSize - 1;
        Card lastCard = Game.mainDeck.deck.get(lastIndex);
        Game.mainDeck.deck.remove(lastCard);
        this.deck.add(lastCard);

        return lastCard;
    }


    public void move(Card card, Deck originalDeck, Deck newDeck){
        originalDeck.deck.remove(card);
        newDeck.deck.add(card);
    }


    public void shuffle(){
        Collections.shuffle(this.deck);
    }

    public Card getLastCardPlayed(){
        int deckSize = Game.trashDeck.deck.size() - 1;
        return Game.trashDeck.deck.get(deckSize);
    }

    public void replaceMainDeck(){
        //Move top card in trash deck to new deck
        Deck newDeck = new Deck();
        move(getLastCardPlayed(), Game.trashDeck, newDeck);

        //Make trash deck the main deck and shuffle main deck
        Game.mainDeck = Game.trashDeck;
        Game.mainDeck.shuffle();

        //Make the new deck the trash deck
        Game.trashDeck = newDeck;

        //System.out.println("Trash deck length: " + Game.trashDeck.getDeckSize());
        //System.out.println("Main deck length: " + Game.mainDeck.getDeckSize());

    }

}