package com.blackjackgame;

public class Card {
    private final String suite; // "Spades", "Hearts", "Clubs" ou "Diamonds"
    private final String rank; // 2, 3, 4, ..., 10
    private final int points;

    public Card(String suite, String rank, int points) {
        this.suite = suite;
        this.rank = rank;
        this.points = points;
    }

    public Card(Card c) {
        this.rank = c.rank;
        this.suite = c.suite;
        this.points = c.points;
    }

    public String getSuite() {
        return this.suite;
    }

    public String getRank() { return this.rank;}

    public int getPoints() {
        return this.points;
    }

    // Retourne true si la carte est un Ace
    public boolean isAce() {
        if (this.getRank().equalsIgnoreCase("ace"))
            return true;

        return false;
    }

    // Retourne une chaine de caractère : Valeur_rank_variable + " of " + valeur_suite_variable. Par exemple : 3 of Hearts
    public String display() {
        return this.rank + " of " + this.suite;
    }
}