package Client.Model;

import java.io.Serializable;

/**
 * Klassen Game används för att kunna skickas mellan klient och servern för att hålla koll på nuvarande spelomgång.
 *
 * @author Isac Pettersson, Johan Skäremo
 * @version 1.0
 */
public class Game implements Serializable {

    private Player player1;
    private Player player2;
    private Player winner;

    /**
     * Konstruerar och initialiserar ett game objekt med en spelare ifall den nuvarande
     * spelomgången består av 1 spelare.
     *
     * @param player1 är spelaren som klienten skapar och skapar sedan game-objektet med som ska skickas till servern.
     */
    public Game(Player player1) {
        this.player1 = player1;
    }

    /**
     * Konstruerar och initialiserar ett game objekt med två spelare ifall den nuvarande
     * spelomgången består av 2 spelare.
     *
     * @param player1 är spelaren1 som klienten skapar och skapar sedan game-objektet med som ska skickas till servern.
     * @param player2 är spelaren2 som klienten skapar och skapar sedan game-objektet med som ska skickas till servern.
     */
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Metod som sätter vilken spelare som vann den nuvarande spelomgången.
     *
     * @param p spelaren som vunnit spelet efter kontroll i server.
     */
    public void setWinner(Player p) {
        this.winner = p;
    }

    /**
     * Metod som hämtar vinnaren ur ett game-objekt.
     *
     * @return spelaren som vann den aktuella spelomgången.
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Metod som hämtar spelare1 ur ett game-objekt.
     *
     * @return spelare1 i game-objektet.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Metod som hämtar spelare2 ur ett game-objekt.
     *
     * @return spelare2 i game-objektet.
     */
    public Player getPlayer2() {
        return player2;
    }
}