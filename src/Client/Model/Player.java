package Client.Model;

import java.io.Serializable;

/***
 * Klass för att kunna skapa Player objekt med namn och score.
 */
public class Player implements Serializable {

    private String name;
    private int score;

    /**
     * Konstruktor för att kunna skapa ett player objekt med ett namn i början av spelet från klienten.
     * @param name
     */
    public Player(String name){
        this.name = name;
    }

    //Getters o setters nedan

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
