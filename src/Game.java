

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

           player1.takeTurn();
           checkIfGameOver(player1);

           player2.takeTurn();
           checkIfGameOver(player2);

           player1.printDeck();

           //TODO: Implement logic for shuffling trash deck when main deck runs out

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

   public boolean checkIfGameOver(Player player){
       int playerDeckSize = player.deck.getDeckSize();

       if (playerDeckSize == 0){
           System.out.println(player.getName() + " has won! Game over.");
           playAgain();
           return true;
       }

       return false;
   }

   public void playAgain(){
       Scanner scan = new Scanner(System.in);
       System.out.println("Play again? Type Y for Yes or N for No: ");

       String input = scan.nextLine();
       if (input.toLowerCase() == "y"){
           Main.main(new String[0]);

       }
   }


}
