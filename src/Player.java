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

            System.out.println("It's your turn!");

            Card lastCard = (Game.trashDeck.getDeckSize() == 0) ? null : Game.trashDeck.getLastCardPlayed();
            int numToDraw = mustDraw(lastCard);

            if (numToDraw != 0){
                System.out.println();
                System.out.println("The computer's play has required you to draw " + numToDraw + " cards.");
                System.out.println("We have automatically drawn those cards for you.");

                printDeck();

            }

            boolean cardPlayed = false;
            boolean wildCardPlayed = false;

            while (!cardPlayed) {

                boolean inputGood = false;
                String input = "";

                while (!inputGood){
                    System.out.println("Enter your play or type 'draw' to draw a card: ");
                    Scanner scan = new Scanner(System.in);
                    input = scan.nextLine();

                    //TODO: Check if input is allowed
                    inputGood = goodInput(input);
                }


                if (input.toLowerCase().equals("draw")){
                    deck.draw();
                    System.out.println();
                    printDeck();
                }

                else {

                    Card choice = this.deck.deck.get((Integer.parseInt(input) - 1));

                    //Check if play legal, it's the first card play of game, it's a wildcard, or a wildcard has just been played
                    if (wildCardPlayed || checkIfPlayable(lastCard, choice, choice.isWildCard())) {

                        playCard(choice);

                        //If a wildcard was played, a second card must also be played
                        if (choice.isWildCard()){
                            wildCardPlayed = true;
                            System.out.println("You played a wildcard. You must play a second card.");
                        }
                        else {
                            cardPlayed = true;
                        }


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

        int numToDraw = mustDraw(lastCardPlayed);
        System.out.println(numToDraw);

        if (numToDraw != 0){
            for (int i = 0; i < numToDraw; i++){
                deck.draw();
            }
        }


        /*Loop through each card in hand until you find a card
          that matches either the color or value of last card played.
        */

        boolean didPlay = false;

        for (int i = 0; i < deck.getDeckSize(); i++){

            Card currentCard = deck.deck.get(i);

            boolean isPlayable = checkIfPlayable(lastCardPlayed, currentCard, currentCard.isWildCard());

            if (isPlayable) {

                playCard(currentCard);

                if (currentCard.isWildCard()){
                    //For our purposes, we will play the wildcard and then play the first card of the deck
                    //TODO: Improve this logic
                    summarizeMove();
                    playCard(deck.deck.get(0));
                }

                //Set flag to indicate we have playable card
                didPlay = true;
                break;
            }
        }

        //If you did not find a playable card, draw until you have one
        while (!didPlay){

            deck.draw();

            Card currentCard = deck.deck.get(deck.getDeckSize() - 1);

            boolean isPlayable = checkIfPlayable(lastCardPlayed, currentCard, currentCard.isWildCard());

            if (isPlayable) {
                //Set flag to indicate we have playable card
                didPlay = true;

                //Play current card
                playCard(currentCard);

            }
        }
    }


    public boolean checkIfPlayable(Card lastCard, Card currentCard, boolean isWildcard){

        //If it's the first card or a wildcard, it's always playable
        if (lastCard == null || isWildcard){
            return true;
        }

        Card.Color lastCardColor = lastCard.getColor();
        Card.Value lastCardValue = lastCard.getValue();

        Card.Color currentColor = currentCard.getColor();
        Card.Value currentValue = currentCard.getValue();

        if (currentColor == lastCardColor || currentValue == lastCardValue){
            return true;
        }
        return false;
    }


    public void playCard(Card choice){
        //Move card from player deck to trash deck
        deck.move(choice, this.deck, Game.trashDeck);

    }


    public void summarizeMove(){
        Card lastCardPlayed = Game.trashDeck.getLastCardPlayed();

        System.out.print("The Computer played a ");
        lastCardPlayed.printCard(0);
    }


    public int mustDraw(Card lastCard){

        if (lastCard == null){
            return 0;
        }

        if (lastCard.isDrawTwo()){
            return 2;
        }
        else if (lastCard.isDrawFour()){
            return 4;
        }
        return 0;
    }


    public boolean goodInput(String input){

        if (input.toLowerCase().equals("draw")){
            return true;
        }

        else {
            boolean inputGood = false;
            for (int i = 1; i <= this.deck.getDeckSize(); i++) {
                if (Integer.parseInt(input) == i) {
                    inputGood = true;
                }
            }
            return inputGood;
        }
    }

}
