package blackjack;


import java.util.Scanner;

public class BlackjackGame {

    private Deck deck;
    private Human player;
    private Dealer dealer;
    private ComputerPlayer computerPlayer1;
    private ComputerPlayer computerPlayer2;
    private int playerBet;

    private Turn currentTurn;
    private boolean gameOver;

    private enum Turn {
        HUMAN, BOT1, BOT2, DEALER;
    }


    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setPlayer(Human player) {
        this.player = player;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public void setComputerPlayer1(ComputerPlayer computerPlayer1) {
        this.computerPlayer1 = computerPlayer1;
    }

    public void setComputerPlayer2(ComputerPlayer computerPlayer2) {
        this.computerPlayer2 = computerPlayer2;
    }

    public void setPlayerBet(int playerBet) {
        this.playerBet = playerBet;
    }

    public void startGame() {

        while (!gameOver) {
            player.placeBet(playerBet);

            // Game starts here
            deck.shuffle();
            dealCards();

            // Players each play their turn
            currentTurn = Turn.HUMAN;
            player.playTurn(deck);
            switchTurn();
            computerPlayer1.playTurn1(deck);
            switchTurn();
            computerPlayer2.playTurn2(deck);
            switchTurn();
            dealer.revealSecondCard();
            dealer.playTurn(deck);

            // Checking results for each player, handles bets for the human only
            checkPlayerWinner(player, dealer);
            checkComputerWinner(computerPlayer1, dealer);
            checkComputerWinner(computerPlayer2, dealer);
            handleBet(player);
            updateHighScore(player);
            deck.reset();
            switchTurn();

        }


    }

    // All players will play their hand once it's their turn
    private void playTurn() {
        switch (currentTurn) {
            case HUMAN:
                player.playTurn(deck);
                break;
            case BOT1:
                computerPlayer1.playTurn1(deck);
                break;
            case BOT2:
                computerPlayer2.playTurn2(deck);
                break;
            case DEALER:
                dealer.playTurn(deck);
                break;
        }
    }

    private void dealCards() {
        for (int i = 0; i <= 2; i++) {
            dealer.deal(player);
            dealer.deal(computerPlayer1);
            dealer.deal(computerPlayer2);
            dealer.deal(dealer);
        }
    }

    // Turns rotate starting from Human to Dealer and then goes back to Human for the next round
    private void switchTurn() {
        switch (currentTurn) {
            case HUMAN:
                currentTurn = Turn.BOT1;
                break;
            case BOT1:
                currentTurn = Turn.BOT2;
                break;
            case BOT2:
                currentTurn = Turn.DEALER;
                break;
            case DEALER:
                currentTurn = Turn.HUMAN;
                break;
        }
    }

    // Checking player's hand and outcome
    private boolean checkPlayerWinner(Human player, Dealer dealer) {
        int playerTotal = player.getPlayerHandTotal();
        int dealerTotal = dealer.getPlayerHandTotal();

        if (playerTotal == 21 && dealerTotal != 21) {
            System.out.println("Blackjack! " + player + " win!");
            return true;
        } else if (player.bust()) {
            System.out.println(player + " busted!");
            return false;
        } else if (dealer.bust()) {
            System.out.println(dealer + " busted! " + player + " win!");
            return true;
        } else if (isDraw(player, dealer)) {
            System.out.println("Push! It's a draw!");
            return false;
        } else if (playerTotal > dealerTotal) {
            System.out.println(player + " have " + playerTotal);
            System.out.println(dealer + " has " + dealerTotal);
            System.out.println(player + " win!");
            return true;
        } else {
            System.out.println(player + " has " + playerTotal);
            System.out.println(dealer + " has " + dealerTotal);
            System.out.println(player + " lose!");
            return false;
        }
    }

    // Checking computer player's hand and outcome
    private void checkComputerWinner(ComputerPlayer computerPlayer, Dealer dealer) {
        int computerTotal = computerPlayer.getPlayerHandTotal();
        int dealerTotal = dealer.getPlayerHandTotal();

        if (computerTotal == 21 && dealerTotal != 21) {
            System.out.println("Blackjack! " + computerPlayer + " wins!");
        } else if (computerPlayer.bust()) {
            System.out.println(computerPlayer + " busted!");
        } else if (dealer.bust()) {
            System.out.println(dealer + " busted! " + computerPlayer + " wins!");
        } else if (isDraw(computerPlayer, dealer)) {
            System.out.println(computerPlayer + " ties with the " + dealer + "!");
        } else if (computerTotal > dealerTotal) {
            System.out.println(computerPlayer + " has " + computerTotal);
            System.out.println(dealer + " has " + dealerTotal);
            System.out.println(computerPlayer + "wins!");
        } else {
            System.out.println(computerPlayer + " has " + computerTotal);
            System.out.println(dealer + " has " + dealerTotal);
            System.out.println(computerPlayer + "loses!");
        }
    }

    private boolean isDraw(Player player, Dealer dealer) {
        int playerTotal = player.getPlayerHandTotal();
        int dealerTotal = dealer.getPlayerHandTotal();

        return playerTotal == dealerTotal;
    }

    // Paying out bet depending on the game outcome
    private void handleBet(Human player) {
        int playerBalance = player.getPlayerBalance();

        // Updating player balance after the round
        if (checkPlayerWinner(player, dealer)) {
            player.winBet(playerBet);
        } else if (isDraw(player, dealer)) {
            player.setPlayerBalance(playerBalance + playerBet);
        } else {
            player.loseBet(playerBet);
        }

        player.setPlayerBalance(playerBalance);

    }

    private void updateHighScore(Human player) {
        int balance = player.getPlayerBalance();
        int highScore = player.getHighScore();

        if (balance > highScore) {
            highScore = balance;
            player.setHighScore(balance);
        }
    }

    private boolean isGameOver(Human player) {
        return player.getPlayerBalance() == 0;
    }
}

