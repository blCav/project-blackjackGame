package com.blackjackgame;

public class Deck {
    private Card[] deck;
    private int currentCardIndex;

    // Stocke les cartes dans Card[] deck, ensuite la fonction shuffleDeck() est appelée
    public Deck() {
        deck = new Card[52];
        String[] allSuites = {"Spades", "Hearts", "Clubs", "Diamonds"};
        String[] allRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "King", "Queen", "Jack", "Ace"};
        int counter = 0;

        for (String suite: allSuites) {
            for (String rank : allRanks) {
                int points = switch (rank) {
                    case "King", "Queen", "Jack" -> 10;
                    case "Ace" -> 11;
                    default -> Integer.parseInt(rank);
                };
                deck[counter] = new Card(suite, rank, points);
                counter++;
            }
        }
        shuffleDeck();
    }

    // ShuffleDeck, pour mélanger les cartes à l'aide de l'algorithme de mélange de Fisher-Yates:
    // https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
    private void shuffleDeck() {
        for (int i = 51; i > 0; i--) {
            int j = (int)(Math.random()* 52);
            Card swapCard = deck[i];
            deck[i] = deck[j];
            deck[j] = swapCard;
        }
    }

    // Distribue les cartes
    public Card drawCard() {
        if (currentCardIndex == 51) {
            Card currCard = deck[currentCardIndex];
            shuffleDeck();
            return currCard;
        }
        else {
            return deck[currentCardIndex++];
        }
    }
}