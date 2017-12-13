package com.xanthe.uno2;


public class Player {

    public String name;
    public Deck deck;


    public Player(String playerName){
        this.name = playerName;
        this.deck = new Deck();
    }



    public Card attemptToPlayCard(Card choice) {

        boolean cardPlayed = false;
        boolean wildCardPlayed = false;


        while (!cardPlayed) {

            Card lastCard = (Game.trashDeck.getDeckSize() == 0) ? null : Game.trashDeck.getLastCardPlayed();

            boolean isPlayable = checkIfPlayable(lastCard, choice, choice.isWildCard());

                //Check if play legal, it's the first card play of Game, it's a wildcard, or a wildcard has just been played
                if (wildCardPlayed || checkIfPlayable(lastCard, choice, choice.isWildCard())) {

                    playCard(choice);

                    //If a wildcard was played, a second card must also be played
                    if (choice.isWildCard()){
                        wildCardPlayed = true;
                        //System.out.println("You played a wildcard. You must play a second card.");
                    }
                    else {
                        cardPlayed = true;
                        return choice;
                    }


                }
            }
            return null;

        }



    public int getDeckSize(){
        return this.deck.getDeckSize();
    }


    public String getName(){
        return this.name;
    }


    public Deck dealInitialHand(){

        for (int i = 0; i < 7; i++){
            Card card = this.deck.draw();
        }
        return this.deck;
    }


    public Card takeComputerTurn() {

        //Get last card played (last card in trash deck)
        Card lastCardPlayed = Game.trashDeck.getLastCardPlayed();

        int numToDraw = mustDraw(lastCardPlayed);
        //System.out.println(numToDraw);

        if (numToDraw != 0) {
            for (int i = 0; i < numToDraw; i++) {
                deck.draw();
            }
        }


        /*Loop through each card in hand until you find a card
          that Gamees either the color or value of last card played.
        */

        boolean didPlay = false;
        Card currentCard = null;
        for (int i = 0; i < deck.getDeckSize(); i++) {

            currentCard = deck.deck.get(i);

            boolean isPlayable = checkIfPlayable(lastCardPlayed, currentCard, currentCard.isWildCard());

            if (isPlayable) {

                playCard(currentCard);

                if (currentCard.isWildCard()) {
                    //For our purposes, we will play the wildcard and then play the first card of the deck
                    //TODO: Improve this logic
                    Card ourCard = deck.deck.get(0);
                    playCard(ourCard);
                }

                //Set flag to indicate we have playable card
                didPlay = true;
                break;
            }
        }


        //If you did not find a playable card, draw until you have one
        while (!didPlay) {

            deck.draw();

            currentCard = deck.deck.get(deck.getDeckSize() - 1);

            boolean isPlayable = checkIfPlayable(lastCardPlayed, currentCard, currentCard.isWildCard());

            if (isPlayable) {
                //Set flag to indicate we have playable card
                didPlay = true;

                //Play current card
                playCard(currentCard);

            }
        }
        return currentCard;
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


    public int mustDraw(Card lastCard){

        if (lastCard == null || Game.trashDeck.getDeckSize() < 2){
            return 0;
        }

        Card penultimateCard = Game.trashDeck.deck.get(Game.trashDeck.getDeckSize() - 2);
        int total = 0;


        if (lastCard.isDrawTwo()){
            total += 2;
        }
        if (penultimateCard.isDrawTwo()){
            total += 2;
        }
        if (penultimateCard.isDrawFour()){
            total += 4;
        }
        return total;
    }

    public Card drawCard(){
        Card card = this.deck.draw();
        return card;
    }


}