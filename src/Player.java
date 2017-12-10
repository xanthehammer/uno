import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Stack;


public class Player {

    String name;
    Deck deck;

    public Player(String playerName){
        this.name = playerName;
        this.deck = new Deck();
    }

    public void takeTurn(){

        if (this.name == "The Computer"){
            //Computer's turn
            takeComputerTurn();
            summarizeMove();
        }
        else{
            //Human Player's turn

            System.out.println("It's your turn! Enter your play or type 'draw' to draw a card: ");

            boolean cardPlayed = false;

            while (!cardPlayed) {

                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();

                //TODO: Check if input is allowed
                if (input.toLowerCase() == "draw"){
                    deck.draw();
                }
                else {

                    Card choice = this.deck.deck.get((Integer.parseInt(input) - 1));

                    //Check if play legal or it's the first card play of game
                    boolean isFirstCard = Game.trashDeck.getDeckSize() == 0;

                    if (isFirstCard || checkIfPlayable(Game.trashDeck.getLastCardPlayed(), choice)) {
                        playAndDraw(choice);

                        cardPlayed = true;
                    } else {
                        System.out.println("Play not allowed. Please choose another card: ");
                    }
                }

            }

        }

    }

    public int getDeckSize(){
        return this.deck.getDeckSize();
    }

    public String getName(){
        return this.name;
    }

    public void dealInitialHand(){

        for (int i = 0; i < 7; i++){
            this.deck.draw();
        }

    }

    public void printDeck(){
        System.out.println("--------------------------");
        for(int i = 0; i < this.deck.getDeckSize(); i++){
            this.deck.deck.get(i).printCard(i + 1);
        }
    }

    public void takeComputerTurn(){

        //Get last card played (last card in trash deck)
        Card lastCardPlayed = Game.trashDeck.getLastCardPlayed();

        /*Loop through each card in hand until you find a card
          that matches either the color or value of last card played.
        */
        //TODO: Implement wildcard play functionality

        boolean didPlay = false;

        for (int i = 0; i < deck.getDeckSize(); i++){

            Card currentCard = deck.deck.get(i);

            boolean isPlayable = checkIfPlayable(lastCardPlayed, currentCard);

            if (isPlayable) {
                //Set flag to indicate we have playable card
                didPlay = true;

                playAndDraw(currentCard);

                break;
            }

        }

        //If you did not find a playable card, draw until you have one
        while (!didPlay){

            deck.draw();

            Card currentCard = deck.deck.get(deck.getDeckSize() - 1);

            //TODO: Check if playable. If so, play card, draw card, set sentinel flag.
            boolean isPlayable = checkIfPlayable(lastCardPlayed, currentCard);

            if (isPlayable) {
                //Set flag to indicate we have playable card
                didPlay = true;

                //Play current card
                playAndDraw(currentCard);

            }

        }


    }

    public boolean checkIfPlayable(Card lastCard, Card currentCard){

        Card.Color lastCardColor = lastCard.getColor();
        Card.Value lastCardValue = lastCard.getValue();

        Card.Color currentColor = currentCard.getColor();
        Card.Value currentValue = currentCard.getValue();

        if (currentColor == lastCardColor || currentValue == lastCardValue){
            return true;
        }
        return false;
    }

    public void playAndDraw(Card choice){
        //Move card from player deck to trash deck
        deck.move(choice, this.deck, Game.trashDeck);

        //Draw another card
        deck.draw();
    }

    public void summarizeMove(){
        Card lastCardPlayed = Game.trashDeck.getLastCardPlayed();

        System.out.print("The Computer played a ");
        lastCardPlayed.printCard(0);
    }


}
