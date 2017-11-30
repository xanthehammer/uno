import java.util.ArrayList;
import java.util.Scanner;
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
            //TODO: Write logic for computer to take a turn
        }
        else{
            //TODO: Check if play is legal and loop until it is
            System.out.println("It's your turn! Enter your play: ");

            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            Card choice = this.deck.deck.get(Integer.parseInt(input));

            //Move card from player deck to trash deck
            deck.move(choice, this.deck, Game.trashDeck);

            //Draw another card
            deck.draw();

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


}
