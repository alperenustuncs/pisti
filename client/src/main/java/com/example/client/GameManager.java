package com.example.client;

import com.example.client.model.Card;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class GameManager {
    private boolean finished;
    private boolean playerWon;

    private List<Card> allCards;
    private List<Card> playerCards;
    private List<Card> aiCards;
    private Stack<Card> cardsInMiddle;
    private boolean playerTurn;
    private int playerScore;
    private int aiScore;
    private HBox playerBox;
    private HBox aiBox;

    public List<Card> getAllCards() { return allCards; }
    public void setPlayerScore(int score) { this.playerScore = score;}
    public List<Card> getPlayerCards() { return playerCards; }
    public List<Card> getAiCards() { return aiCards; }
    public boolean isFinished() { return finished; }
    public void finish() { this.finished = true; }
    public boolean isPlayerWon() { return playerWon; }
    public void makePlayerWin() { this.playerWon = true;}

    public GameManager(final List<Card> allCards, final HBox playerBox, final HBox aiBox) {
        this.allCards = allCards;
        playerCards = new ArrayList<>();
        aiCards = new ArrayList<>();
        cardsInMiddle = new Stack<>();
        playerScore = 0;
        aiScore = 0;
        this.playerBox = playerBox;
        this.aiBox = aiBox;

        for (var card : allCards) {
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    System.out.println("HELLO");
                    playerBox.getChildren().remove(card.getButton());
                    makeMove(card);
                }
            };
            card.setButtonEvent(event);
        }

    }

    public void spread4CardsToPlayers() {
        for (int i = 0; i < 2; i++) {
            var cards = new ArrayList<Card>();
            for (int j = 0; j < 4; j++) {
                int randIdx = new Random().nextInt(allCards.size());
                cards.add(allCards.get(randIdx));
                allCards.remove(randIdx);
            }

            if (i == 0) {
                playerCards = cards;
            }
            else {
                aiCards = cards;
                for (var card : cards) {
                    card.getButton().setBackground(Card.BLACK_BACKGROUND);
                    card.getButton().paddingProperty().set(new Insets(100, 0, 0, 100));
                }
            }
        }

    }

    private void easyAiMove() {

    }

    public void makeMove(Card card) {
        cardsInMiddle.add(card);
        card.disable();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (playerTurn) {
            playerScore += calculateScore(card);
        }
        else {
            aiScore += calculateScore(card);
        }


        playerCards.remove(card);
        playerTurn = !playerTurn;
    }

    private int calculateScore(Card card) {
        int score = 0;
        if (cardsInMiddle.lastElement().getRank() == card.getRank()) {
            if (cardsInMiddle.size() == 2) {
                if (card.getRank() == Card.JACK) {
                    score = 20;
                }
                else {
                    score = 10;
                }
            }
            else {
                for (var _card : cardsInMiddle) {
                    score += _card.getPoint();
                }
            }
        }

        return score;
    }
}
