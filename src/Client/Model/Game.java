package Client.Model;

import java.io.Serializable;
/**
 * Klass för att kunna skapa Game objekt med spelaren och vinnaren.
 * @author Isac Pettersson, Johan Skäremo
 */
public class Game implements Serializable {

    private Player player1;
    private Player player2;
    private Player winner;

    public Game(Player player1) {
        this.player1 = player1;
    }

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setWinner(Player p) {
        this.winner = p;
    }

    public Player getWinner() {
        return winner;
    }


    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}