package com.example.client.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.*;

public class Card {
    public static Background BLACK_BACKGROUND;
    public static final int CLOVER = 0;
    public static final int HEART = 1;
    public static final int PIKE = 2;
    public static final int TILE = 3;
    public static final int A = 11;
    public static final int JACK = 12;
    public static final int KING = 13;
    public static final int QUEEN = 14;

    //private static final List<Integer> RANKS = Arrays.asList(2, 4);

    private int cardType;
    private int rank;
    private HBox parentHBox;
    private Button button;

    public Card(int cardType, int rank, final HBox parentHBox, final Button button) {
        this.cardType = cardType;
        this.rank = rank;
        this.parentHBox = parentHBox;
        this.button = button;
    }

    public int getCardType() { return cardType; }
    public int getRank() { return rank; }

    public int getPoint() {
        if (rank == JACK || rank == A) {
            return 1;
        }
        else if (cardType == CLOVER && rank == 2) {
            return 2;
        }
        else return 3;
    }

    public HBox getParentHBox() { return parentHBox; }
    public void setParentHBox(final HBox parentHBox) { this.parentHBox = parentHBox; }
    public Button getButton() { return button; }
    public void setButtonEvent(EventHandler event) { this.button.setOnAction(event);}
    public void disable() { this.button.setDisable(true); }

    public static List<Card> initCards(final HBox parentHBox) {
        List<Card> cards = new ArrayList<Card>();
        List<String> cardTypeStrings = Arrays.asList("Clovers_", "Hearts_", "Pikes_", "Tiles_");
        final String pathStringBase = "static/playing_cards/PNG/black/";
        final String blackCardPath = "static/playing_cards/PNG/all.png";
        BackgroundImage blackImg = new BackgroundImage(new Image(blackCardPath,93,110,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        BLACK_BACKGROUND = new Background(blackImg);
        for (int i = 0; i < 4; i++) {
            String cardType = cardTypeStrings.get(i);

            for (int j = 2; j < 15; j++) {
                String rank = rankToString(j);
                String pathString = pathStringBase + cardType + rank + "_black.png";

                System.out.println(pathString);

                BackgroundImage img = new BackgroundImage(new Image(pathString,93,110,false,true),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                Button button = new Button();
                button.setPrefSize(93, 110);
                button.setBackground(new Background(img));


                Card card = new Card(i, j, parentHBox, button);
                cards.add(card);
            }
        }

        return cards;
    }

    private static String rankToString(int rank) {
        if (rank == A) return "A";
        else if (rank == JACK) return "Jack";
        else if (rank == KING) return "King";
        else if (rank == QUEEN) return "Queen";
        else return Integer.toString(rank);
    }
}
