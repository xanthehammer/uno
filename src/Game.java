

import java.util.Scanner;

public class Game {

    public static Deck mainDeck;
    public static Deck trashDeck;
    Player player1;
    Player player2;


   public void start(){

       setup();

       //TODO: Print rules and instructions to player1 to play the first card

       play();

   }

   private void play(){

       boolean gameOver = false;

       while (!gameOver){
           //TODO: Check if game over

           player1.takeTurn();
           player2.takeTurn();

           player1.printDeck();

       }

   }

   public void setup(){

       //Build main deck
       mainDeck = new Deck();
       mainDeck.buildDeck();

       //Shuffle deck
       mainDeck.shuffle();

       //Initialize trash deck
       trashDeck = new Deck();

       makePlayers();

       player1.dealInitialHand();
       player2.dealInitialHand();

       player1.printDeck();

   }

   public void makePlayers() {

       Scanner scan = new Scanner(System.in);
       System.out.println("Enter your name: ");
       String input = scan.nextLine();

       player1 = new Player(input);
       player2 = new Player("The Computer");

   }


}
