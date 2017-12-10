

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
           gameOver = checkIfGameOver(player1);
           if (gameOver) System.exit(0);

           player2.takeTurn();
           gameOver = checkIfGameOver(player2);
           if (gameOver) System.exit(0);

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

   public boolean checkIfGameOver(Player player){
       int playerDeckSize = player.deck.getDeckSize();

       if (playerDeckSize == 0){
           System.out.println(player.getName() + " has won! Game over.");
           return true;
       }

       return false;
   }

   }


