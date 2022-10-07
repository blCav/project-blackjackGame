package com.blackjackgame;

public class BlackjackGame {
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private double betAmount;
    private final double minBet;
    private final double maxBet;
    private double totalMoney;

    // Initialise deck, playerHand, dealerHand, minBet et maxBet
    // Le minimum et le maximum de la mise sont de 5 et 1000 respectivement
    public BlackjackGame() {
        this.deck = new Deck();
        this.playerHand = new Hand("Player");
        this.dealerHand = new Hand("Dealer");
        this.minBet = 5.0;
        this.maxBet = 1000.0;
    }

    // valeur initiale, version TP2
//    public void loadMoney() {
//        totalMoney = 100.0;
//    }

    public Object loadMoney() {
        return totalMoney = 100.0;
    }

    // Retourne true si le total d’argent dont le joueur dispose est inférieur au minimum de la mise. False sinon.
    public boolean isOutOfMoney() {
        if (this.totalMoney < this.minBet)
            return true;
        else
            return false;
    }

    // Initialise le reset de totalMoney a 100

    //valeur initiale, version TP2
    // public void resetMoney() {
//        this.totalMoney = 100.0;
//    }

    public Object resetMoney() {
        return this.totalMoney = 100.0;
    }

    // Retourne false si double localBetAmt est inférieur au minBet ou supérieur au maxBet ou supérieur au totalMoney. True sinon.
    public boolean isValidBet(double localBetAmt) {
        if (localBetAmt < this.minBet || localBetAmt > this.maxBet || localBetAmt > this.totalMoney)
            return false;
        else
            return true;
    }

    // Retourne minBet
    public double getMinBet() {
        return this.minBet;
    }

    // Retourne le montant total que le joueur peut utiliser pour la mise.
    public double getMaxBet() { return this.maxBet; }

    // Retourne le montant total
    public double getTotalMoney() { return this.totalMoney; }

    // Initialise le montant de la mise que l'on va faire
    public void setBet(double amt) {
        this.betAmount = amt;
    }

    // Distribue deux cartes pour le joueur (playerHand) et deux cartes pour le croupier (dealerHand)
    public void deal() {
        this.playerHand.addCard(deck.drawCard());
        this.playerHand.addCard(deck.drawCard());
        this.dealerHand.addCard(deck.drawCard());
        this.dealerHand.addCard(deck.drawCard());
    }

    // Distribue une carte en plus pour le joueur dans le cas où il fait hit.
    //ancienne version
//    public void hit() {
//        playerHand.addCard(deck.drawCard());
//    }

    public Object hit() {
        return playerHand.addCard(deck.drawCard());
    }
    // Ajoute des cartes dans la main du croupier tant que la somme des points dont il dispose est inférieur à 17
    //ancienne version
//    public void stand() {
//        while (dealerHand.getPoints() < 17)
//            dealerHand.addCard(deck.drawCard());
//    }


    public Object stand() {
        while (dealerHand.getPoints() < 17)
            dealerHand.addCard(deck.drawCard());

        return dealerHand;
    }


    // Retourne la deuxième carte dans la main du courtier
    public Card getDealerShowCard() {
        return this.dealerHand.getCards().get(1);
    }

    // Retourne dealerHand
    public Hand getDealerHand() {
        return this.dealerHand;
    }

    // Retourne playerHand
    public Hand getPlayerHand() {
        return this.playerHand;
    }

    // Ice Cream
    public boolean isBlackjackOrBust() {
        if(playerHand.isBlackjack() || playerHand.isBust() || dealerHand.isBlackjack() || dealerHand.isBust())
            return true;
        else
            return false;
    }

    // Retourne true si les points dans la main de joueur sont inférieurs ou égaux à 21 et ces points sont égaux aux points du croupier. False sinon.
    public boolean isPush() {
        if (playerHand.getPoints() <= 21 && playerHand.getPoints() == dealerHand.getPoints())
            return true;
        else
            return false;
    }

    // Retourne true si le player gagne. False sinon.
    public boolean playerWins() {
        if (!playerHand.isBust() && playerHand.getPoints() > dealerHand.getPoints() || playerHand.getPoints() == 21 ||  playerHand.isBlackjack() || dealerHand.isBust())
            return true;
        else
            return false;
    }

    // Ajoute le montant de la mise gagnée au montant total
    //ancienne version
//    public void addBetToTotal() {
//        totalMoney += betAmount;
//    }

    public Object addBetToTotal() {
        return totalMoney += betAmount;
    }


    // Ajoute le montant de la mise gagnée selon 3:2 au montant total dans le cas d'un blackjack
    //ancienne version
//    public void addBlackjackToTotal() {
//        totalMoney += betAmount * 1.5;
//    }
    public Object addBlackjackToTotal() {
        return totalMoney += betAmount * 1.5;
    }

    // Soustrait le montant de la mise perdue du montant total
    //ancienne version
    //public void subtractBetFromTotal() { totalMoney -= betAmount; }
    public Object subtractBetFromTotal() { return totalMoney -= betAmount; }

    // Recommence le jeu et enlève toutes les cartes du joueur et du croupier
    public void resetGame() {
        this.playerHand.removeCard();
        this.dealerHand.removeCard();
    }
}