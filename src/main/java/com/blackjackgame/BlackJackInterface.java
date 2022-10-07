package com.blackjackgame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BlackJackInterface extends Application {
    private static BlackjackGame game;
    static String money;
    static Object saveMoney;
    
    // LABELS
    private final Label moneyLabel = new Label("Money : ");
    private final Label betLabel = new Label("Bet : ");
    private final Label dealerLabel = new Label("DEALER");
    private final Label dealerCardsLabel = new Label("Cards : ");
    private final Label dealerCardsPointsLabel = new Label("Points : ");
    private final Label playerLabel = new Label("YOU");
    private final Label playerCardsLabel = new Label("Cards : ");
    private final Label playerCardsPointsLabel = new Label("Points : ");
    private final Label resultGameLabel = new Label("RESULT : ");

    // TEXTFIELDS & LISTVIEWS
    private static TextField moneyTextField = new TextField();
    private static TextField betTextField = new TextField();
    private static ListView dealerCardsListView = new ListView<>();
    private static TextField dealerCardsPointsTextField = new TextField();
    private static ListView playerCardsListView = new ListView<>();
    private static TextField playerCardsPointsTextField = new TextField();
    private static TextField resultGameTextField = new TextField();


    // BUTTONS
    static Button buttonHit = new Button("Hit");
    static Button buttonStand = new Button("Stand");
    static Button buttonPlay = new Button("Play");
    static Button buttonExit = new Button("Exit");

    //Affiche la première carte du croupier et les cartes du joueur
    private static void showHands() {
        dealerCardsListView.getItems().add(game.getDealerShowCard().display());
        showPlayerHand();
    }

    //Affiche toutes les cartes dans la main du croupier
    private static void showDealerHand() {
        for (Card card : game.getDealerHand().getCards()) {
            dealerCardsListView.getItems().add(card.display());
        }
    }

    //Affiche toutes les cartes dans la main du joueur
    private static void showPlayerHand() {
        for(Card card: game.getPlayerHand().getCards()) {
            playerCardsListView.getItems().add(card.display());
        }
    }

    //Distribue une carte en plus pour le joueur dans le cas où il clique sur le bouton hit
    private static void buttonHitClick() {
        //Le jeu s'arrête s'il y a un Blackjack ou un Burst
        if(game.isBlackjackOrBust()) {
            showWinner();
        } else {
            game.hit();
            showWinner();
        }
    }

    //Distribue des cartes au croupier dans le cas où le joueur clique sur le bouton stand
    private void buttonStandClick() {
        //Le jeu s'arrête s'il y a un Blackjack ou un Burst
        if (game.isBlackjackOrBust()) {
            showWinner();
        } else {
            game.stand();
            showWinner();
        }
    }

    // Affiche les points du croupier et du joueur, puis annonce le gagnant de la partie
    public static void showWinner() {
        playerCardsListView.getItems().clear();
        showPlayerHand();
        dealerCardsListView.getItems().clear();
        showDealerHand();

        String playerPoints = String.valueOf(game.getPlayerHand().getPoints());
        playerCardsPointsTextField.setText(playerPoints);

        String dealerPoints = String.valueOf(game.getDealerHand().getPoints());
        dealerCardsPointsTextField.setText(dealerPoints);

        if (game.isPush()) {
            resultGameTextField.setText("Push !");
            endGame();

        } else if (game.getPlayerHand().isBlackjack()) {
            saveMoney = game.addBlackjackToTotal();
            resultGameTextField.setText("BLACKJACK! You win!");
            money = String.valueOf(saveMoney);
            endGame();

        } else if (game.playerWins()) {
            saveMoney = game.addBetToTotal();
            resultGameTextField.setText("You win!");
            money = String.valueOf(saveMoney);
            endGame();

        } else {
            resultGameTextField.setText("You lose !");
            saveMoney = game.subtractBetFromTotal();
            money = String.valueOf(saveMoney);
            endGame();
        }
        showMoney();
    }

    // Affiche l'argent total
    private static void showMoney() {
        money = String.valueOf(saveMoney);
        moneyTextField.setText(money);
    }

    //Démarre une partie de jeu
    private void buttonPlayClick() {
        resetGame();
        outOfMoney();

        if (betTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid data !");
            alert.setContentText("Bet must be set before clicking on play button");
            alert.showAndWait();

        } else {
            double bet = Double.parseDouble(betTextField.getText());

            // Validation du BET
            if (!game.isValidBet(bet)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Bet !");
                alert.setContentText("Bet must be between " + game.getMinBet() + " and " + game.getMaxBet() + ". You also cannot set your bet higher than your money.");
                alert.showAndWait();

            } else {
                game.setBet(bet);
                game.deal();

                showHands();
                hitOrStandButtons();
                noPlayOrExitButtons();
            }
        }
    }

    // Remet l'argent du joueur à 100
    public static  void outOfMoney(){
        if(game.getTotalMoney() == 0) {
            saveMoney = game.resetMoney();
            moneyTextField.setText("100");
        } else {
            saveMoney = game.getTotalMoney();
        }
    }

    // Reset le jeu et efface les champs
    private static void resetGame() {
        game.resetGame();
        playerCardsListView.getItems().clear();
        dealerCardsListView.getItems().clear();
        resultGameTextField.clear();
        dealerCardsPointsTextField.clear();
        playerCardsPointsTextField.clear();
    }

    // Fin du jeu et réactive tous les boutons
    public static void endGame() {
        noHitOrStandButtons();
        PlayOrExitButtons();
    }

    //Active les boutons Hit et Stand
    private static void hitOrStandButtons() {
        buttonHit.setDisable(false);
        buttonStand.setDisable(false);
    }

    //Désactive les boutons Hit et Stand
    private static void noHitOrStandButtons() {
        buttonHit.setDisable(true);
        buttonStand.setDisable(true);
    }

    //Active les boutons Play et Exit
    private static void noPlayOrExitButtons() {
        buttonPlay.setDisable(true);
        buttonExit.setDisable(true);
    }

    // Désactive les boutons Play et Exit
    public static void PlayOrExitButtons() {
        buttonPlay.setDisable(false);
        buttonExit.setDisable(false);
    }

    //Quitte le jeu
    private void buttonExitClick() {
        System.exit(0);
    }

    //Commence une nouvelle partie avec l'argent de départ à 100
    public static void startGame() {
        game = new BlackjackGame();
        saveMoney = game.loadMoney();

        if (moneyTextField.getText().isEmpty()) {
            money = String.valueOf(saveMoney);
            moneyTextField.setText(money);
            moneyTextField.setEditable(false);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Blackjack");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);

        grid.setPadding(new Insets(25,25,25,25));
        grid.setHgap(10);
        grid.setVgap(10);

        dealerCardsListView.setPrefHeight(3 * 24);
        playerCardsListView.setPrefHeight(3 * 24);

        ScrollPane scrollPaneDealerCards = new ScrollPane(dealerCardsListView);
        ScrollPane scrollPanePlayerCards = new ScrollPane(playerCardsListView);

        //BOX
        HBox moneyBox = new HBox(15, moneyLabel, moneyTextField);
        HBox betBox = new HBox(33, betLabel, betTextField);
        HBox dealerBox = new HBox(15, dealerCardsLabel, dealerCardsListView);
        HBox dealerPointsBox = new HBox(15, dealerCardsPointsLabel, dealerCardsPointsTextField);
        HBox playerBox = new HBox(15, playerCardsLabel, playerCardsListView);
        HBox playerPointsBox = new HBox(15, playerCardsPointsLabel, playerCardsPointsTextField);
        HBox hitOrStandButtons = new HBox(10, buttonHit, buttonStand);
        HBox resultGameBox = new HBox(5, resultGameLabel, resultGameTextField);
        HBox playOrExitButtons = new HBox(10, buttonPlay, buttonExit);

        VBox appContainer = new VBox(10, moneyBox, betBox, dealerLabel, dealerBox, dealerPointsBox, playerLabel, playerBox, playerPointsBox, hitOrStandButtons, resultGameBox, playOrExitButtons);

        grid.add(appContainer, 0, 0, 2,3);

        noHitOrStandButtons();

        playerCardsPointsTextField.setEditable(false);
        dealerCardsPointsTextField.setEditable(false);

        buttonHit.setOnAction(actionEvent -> buttonHitClick());
        buttonStand.setOnAction(actionEvent -> buttonStandClick());
        buttonPlay.setOnAction(actionEvent -> buttonPlayClick());
        buttonExit.setOnAction(actionEvent -> buttonExitClick());

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        startGame();
        launch(args);
    }
}