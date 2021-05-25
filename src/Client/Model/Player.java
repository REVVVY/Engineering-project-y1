package Client.Model;

import java.io.Serializable;

/**
 * Klassen Player används för att kunna spara spelare med deras unika attribut som namn och score.
 *
 * @author Isac Pettersson, Johan Skäremo
 * @version 1.0
 */
public class Player implements Serializable, Comparable<Player> {

    private String name;
    private int score;

    /**
     * Konstruerar och initialiserar ett Player-objekt med namn.
     *
     * @param name namnet som ska sparas på det skapade player-objektet.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Konstuerar och initialiserar ett Player-objekt med namn och score.
     *
     * @param name  som ska sparas på det skapade player-objektet.
     * @param score som ska sparas på det skapade player-objektet.
     */
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Metoden används för att hämta namnet på ett visst Player-objekt.
     *
     * @return namnet på Player-objektet.
     */
    public String getName() {
        return name;
    }

    /**
     * Metod som används för att sätta ett namn på ett Player-objekt.
     *
     * @param name som ska sättas på Player-objektet.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metod som används för att hämta score från Player-objektet.
     *
     * @return poängen från Player-objektet.
     */
    public int getScore() {
        return score;
    }

    /**
     * Metoden sätter poäng på ett Player-objekt.
     *
     * @param score som ska sättas på Player-objektet.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Metoden används för att sortera listor som är av typen Player utifrån spelarnas score. (Högre score är bättre).
     *
     * @param o Player-objeket som skickas in och jämnförst.
     * @return poängen för att sorteringen av listan ska ske.
     */
    @Override
    public int compareTo(Player o) {
        return this.score - o.score;
    }
}