import java.util.Arrays;

public class Card {

    public enum Color{
        RED (1),
        BLUE (2),
        GREEN (3),
        YELLOW (4),
        WILD (5);

        private int value;
        private Color(int value){this.value = value;}

    }

    Color color;

    public enum Value{
        ZERO (0),
        ONE (1),
        TWO (2),
        THREE (3),
        FOUR (4),
        FIVE (5),
        SIX (6),
        SEVEN (7),
        EIGHT (8),
        NINE (9),
        SKIP (10),
        REVERSE(11),
        DRAWTWO (12),
        REGULAR (13),
        DRAWFOUR (14);

        private int value;
        private Value(int value){this.value = value;}

    }

    Value value;

    Card(int cardColor, int cardValue){

        this.color = Color.values()[cardColor - 1];
        this.value = Value.values()[cardValue];

    }

    public void printCard(int i){
        System.out.println(i + ": " + this.color + " " + this.value);
    }

}
