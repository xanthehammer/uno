package com.xanthe.uno2;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class Game extends Activity {

    public LinearLayout layout;
    public LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    public Resources res;
    public String packageName;
    public static Deck mainDeck;
    public static Deck trashDeck;
    public static Player player1;
    public static Player player2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        layout = (LinearLayout) findViewById(R.id.linear_root);
        res = this.getResources();
        packageName = this.getPackageName();

        setup();
    }

    public void start(){

        setup();

        //TODO: Print rules and instructions to player1 to play the first card

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

        Deck playerDeck = player1.dealInitialHand();
        player2.dealInitialHand();

        doIt(playerDeck);

    }

    public void makePlayers() {

        player1 = new Player("Human");
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


    public void addToHand(Card card){
        String imageName = card.getCardResource();
        String buttonTag = card.getButtonTag();
        int resId = res.getIdentifier(imageName, "drawable", packageName);
        int buttonId = layout.getChildCount() + 1;
        ImageButton button = new ImageButton(this);
        button.setImageResource(resId);

        button.setTag(buttonTag);
        button.setId(buttonId);

        lp.width = 300;
        button.setLayoutParams(lp);
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);

        layout.addView(button);

        ImageButton btn1 = findViewById(buttonId);

        btn1.setOnClickListener(chooseCard);

    }

    View.OnClickListener chooseCard = new View.OnClickListener() {
        public void onClick(View v) {

            Card cardPlayed = null;
            while (cardPlayed == null) {
                String[] tagArray = v.getTag().toString().split(" ");
                cardPlayed = (player1.attemptToPlayCard(new Card(tagArray[0], tagArray[1])) == null) ? null : new Card(tagArray[0], tagArray[1]);
            }
            showMostRecentCard(cardPlayed);
            layout.removeView(v);
            Log.e("blah", "here");

            Card computerCard = player2.takeComputerTurn();

            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }


            showMostRecentCard(computerCard);
        }
    };

    public void cardDraw(View v){
        Card card = player1.drawCard();
        addToHand(card);
    }


    public void doIt(Deck deck){
        for (int i = 0; i < deck.getDeckSize(); i++){
            Card card = deck.deck.get(i);
            Log.e("bloop", Integer.toString(i));
            addToHand(card);
        }
    }

    public void humanTurn(){
        int numToDraw = player1.mustDraw(trashDeck.getLastCardPlayed());
        //TODO:Game over check
        if (numToDraw != 0){
            for (int i = 0; i < numToDraw; i++){
                Card card = player1.deck.draw();
                addToHand(card);
            }
        }
    }

    public void showMostRecentCard(Card card){

        ImageView img = (ImageView) findViewById(R.id.trashDeck);
        String imageName = card.getCardResource();
        int resId = res.getIdentifier(imageName, "drawable", packageName);
        img.setImageResource(resId);


    }

}
