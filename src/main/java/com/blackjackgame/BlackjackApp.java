package com.blackjackgame;

public class BlackjackApp {
    private static BlackjackGame game;

    public static void main(String[] args) {
        System.out.println("BLACKJACK!");
        System.out.println("Blackjack payout is 3:2");
        System.out.println();

        // Initialise un nouveau jeu et l'argent du joueur qu'il dispose au début du jeu
        game = new BlackjackGame();
        game.loadMoney();

        String playAgain = "y";
        while (playAgain.equalsIgnoreCase("y")) {

            // Relance le jeu
            game.resetGame();

            // Si le joueur n'a plus/pas assez d'argent, demande s'il veut remettre de l'argent
            if(game.isOutOfMoney()) {
                if(!buyMoreChips())
                    break;
            }

            // Affiche l'argent du joueur
            showMoney();

            // Demande le montant de la mise
            getBetAmount();

            // Distribue les cartes
            game.deal();

            // Montre les cartes du Joueur et du Croupier en fonction de l'action choisie : Hit ou Stand
            while(!game.isBlackjackOrBust()) {
                showHands();
                String choice = getHitOrStand();

                if (choice.equalsIgnoreCase("h")) {
                    game.hit();
                } else {
                    game.stand();
                    break;
                }
            }
            showWinner();

            // Demande au joueur s'il veut retenter une partie ou non
            String[] message = {"y", "n"};
            playAgain = Console.getString("\nPlay again ? (y/n) : ", message);
        }
        System.out.println("\nBye!");
    }

    // Affiche le message "Out of money ! Would you like to add more? (y/n) : ". Si le joueur entre y alors la fonction reset la balance du joueur à 100 et retourne true. False Sinon.
    private static boolean buyMoreChips() {
        String[] outOfMoneyMessage = {"y", "n"};
        String choice = Console.getString("\nOut of money! Would you like to add more? (y/n) : ", outOfMoneyMessage);

        if (choice.equalsIgnoreCase("y")) {
            game.resetMoney();
            return true;
        }
        return false;
    }

    // Affiche le message " Bet amount" et lit la valeur de la mise saisie par le joueur. Valide cette valeur. Si la valeur n'est pas valide, elle affiche le message " Bet must be between "
    private static void getBetAmount() {
        while (true) {
            double localAmount = Console.getDouble("\nBet amount : ");
            if (game.isValidBet(localAmount)) {
                game.setBet(localAmount);
                break;
            } else {
                System.out.printf("\nBet must be between $%,.2f  and $%,.2f \n", game.getMinBet(), game.getMaxBet());
            }
        }
    }

    // Affiche le message "Hit or Stand ? (h/s) :" et puis retourne ce que le joueur a entré dans la console
    private static String getHitOrStand() {
        String[] message = {"h", "s"};
        return Console.getString("\nHit or Stand ? (h/s) : ", message);
    }

    // Affiche les cartes dans la main du croupier et les cartes dans la main du joueur
    private static void showHands() {
        showDealerShowCard();
        showPlayerHand();
    }

    // Affiche le message DEALER'S SHOW CARD, puis affiche la deuxième carte dans la main du croupier
    private static void showDealerShowCard() {
        System.out.println("\nDEALER'S SHOW CARD ");
        System.out.println(game.getDealerShowCard().display());
    }

    // Affiche le message DEALER'S CARDS, puis affiche toutes les cartes dans la main du croupier
    private static void showDealerHand() {
        System.out.println("\nDEALER'S CARDS : ");

        for (Card card : game.getDealerHand().getCards()) {
            System.out.println(card.display());
        }
    }

    // Affiche le message YOUR CARDS, puis affiche toutes les cartes dans la main du joueur
    private static void showPlayerHand() {
       System.out.println("\nYOUR CARDS ");

        for (Card card : game.getPlayerHand().getCards()) {
            System.out.println(card.display());
        }
    }

    // Affiche " Total money : " et le montant total
    private static void showMoney() {
        System.out.printf("Total Money : %.2f %n", game.getTotalMoney());
    }

    // Affiche les points du croupier et du joueur, puis annonce le gagnant de la partie
    private static void showWinner() {
        showPlayerHand();
        System.out.printf("YOUR POINTS: %d%n", game.getPlayerHand().getPoints());

        showDealerHand();
        System.out.printf("DEALER'S POINTS: %d%n%n", game.getDealerHand().getPoints());

        if (game.isPush()) {
            System.out.println("Push!");
        } else if (game.getPlayerHand().isBlackjack()) {
            System.out.println("BLACKJACK! You win!");
            game.addBlackjackToTotal();
        } else if (game.playerWins()) {
            System.out.println("You win!");
            game.addBetToTotal();
        } else {
            System.out.println("Sorry, you lose.");
            game.subtractBetFromTotal();
        }
        showMoney();
//      game.saveMoney();
    }
}