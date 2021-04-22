package Client.Model;

import Client.Controller.ClientController;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/***
 * Klient klass för tester av servern.
 */
public class Client implements Runnable {
    private Client client;
    private ClientController controller;
    private String ip;
    private int port;
    private Thread thread = new Thread(this);
    private Player player; //Skapa player arraylist
    private Player player1; //Skapa player arraylist

    private ArrayList<Player> playerScore;

    private Socket socket;

    private int numOfPlayers;

    private ObjectInputStream ois;
    private DataOutputStream dos;


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

            dos = new DataOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.start();
    }
    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public int numOfPlayers() throws IOException, ClassNotFoundException {
        return numOfPlayers;
    }

    public void onePlayer() throws IOException {
        String name = controller.getFirstPlayer();
        dos.writeUTF(name);
    }

    public void twoPlayers() throws IOException {
        String name = controller.getFirstPlayer();
        dos.writeUTF(name);
        String name1 = controller.getSecondPlayer();
        dos.writeUTF(name1);
    }

    public void createScoreboard() throws IOException, ClassNotFoundException {
        ArrayList<Player> scoreboard = (ArrayList<Player>) ois.readObject();
        for (Player p : scoreboard) {
            System.out.println(p.getName() + " " + p.getScore());
        }

    }

    public void getScoreFromServer() {
        try {
            playerScore = (ArrayList<Player>) ois.readObject();
            for (Player p : playerScore) {
                System.out.println(p.getName() + " " + p.getScore());
            }
        } catch (IOException | ClassNotFoundException e){

        }

    }
    /**
     * Klientens tråd som lägger till spelares namn + score som test för servern
     * samt hämtar arraylistan med hela highscorelistan.
     */
    @Override
    public void run() {

        try {
            while (true) {
                String nbrOfPlayersStr = (String) ois.readObject();
                int nbrOfPlayers = Integer.parseInt(nbrOfPlayersStr);
                System.out.println(nbrOfPlayers);

                numOfPlayers = nbrOfPlayers;

                if (nbrOfPlayers == 1) {
                    onePlayer();
                    String score = "score1" + JOptionPane.showInputDialog("Skriv in din score");
                    dos.writeUTF(score);
                } else if (nbrOfPlayers == 2) {
                    twoPlayers();
                   /* String score = "score1" + JOptionPane.showInputDialog("Skriv in din score");
                    dos.writeUTF(score);
                    String score1 = "score2" + JOptionPane.showInputDialog("Skriv in din score");
                    dos.writeUTF(score1);*/
                }
                dos.flush();
                createScoreboard();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch(Exception e) {}
        controller.newResponse("Klient kopplar ner");

    }


}
