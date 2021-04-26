package Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/***
 * Klient klass för tester av servern.
 */
public class Client implements Runnable {

    private String ip;
    private int port;
    private Thread thread = new Thread(this);
    private Player player; //Skapa player arraylist
    private Player player1; //Skapa player arraylist
    private Socket socket;

    /***
     * Konstruktor för starten av klienten
     * @param ip addressen klienten använder vid start
     * @param port porten som används för att prata med servern
     */
    public Client(String ip, int port) {

        this.ip = ip;
        this.port = port;

        try {
            this.socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.start();
    }

    /**
     * Klientens tråd som lägger till spelares namn + score som test för servern
     * samt hämtar arraylistan med hela highscorelistan.
     */
    @Override
    public void run() {

        try {
            //DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            while (true) {

                String nbrOfPlayersStr = (String) ois.readObject();
                int nbrOfPlayers = Integer.parseInt(nbrOfPlayersStr);
                System.out.println(nbrOfPlayers);

                if (nbrOfPlayers == 1) {
                    String name = "player1" + JOptionPane.showInputDialog("Skriv in ditt namn");
                    oos.writeObject(name);
                } else if (nbrOfPlayers == 2) {

                    String name = JOptionPane.showInputDialog("Skriv in ditt namn");

                    String name1 = JOptionPane.showInputDialog("Skriv in ditt namn");

                    Player player1 = new Player(name);
                    Player player2 = new Player(name1);

                    Game game = new Game(player1, player2);
                    oos.writeObject(game);
                }

                /*String score = "score1" + JOptionPane.showInputDialog("Skriv in din score");
                dos.writeUTF(score);
                String score1 = "score2" + JOptionPane.showInputDialog("Skriv in din score");
                dos.writeUTF(score1);
                dos.flush();

                 */


                ArrayList<Player> scoreboard = (ArrayList<Player>) ois.readObject();
                for (Player p : scoreboard) {
                    System.out.println(p.getName() + " " + p.getScore());
                }
                Game game = (Game)ois.readObject();
                System.out.println(game.getWinner().getName() + " " + game.getWinner().getScore());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}