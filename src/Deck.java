

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    ArrayList<Card> deck;

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
    public void draw(){
        //TODO: Check if pile empty, if so, take top card, shuffle trash deck and make it the main deck
        int lastIndex = Game.mainDeck.getDeckSize() - 1;
        Card lastCard = Game.mainDeck.deck.get(lastIndex);
        Game.mainDeck.deck.remove(lastCard);
        this.deck.add(lastCard);
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


}
