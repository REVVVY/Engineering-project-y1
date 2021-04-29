package Client.Model;

import Client.Controller.ClientController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private Game currentGame;

    private Socket socket;

    private int numOfPlayers;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    /***
     * Konstruktor för starten av klienten
     * @param ip addressen klienten använder vid start
     * @param port porten som används för att prata med servern
     */
    public Client(String ip, int port, ClientController controller) {
        this.controller = controller;
        this.ip = ip;
        this.port = port;
        try {
            this.socket = new Socket(ip, port);

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.start();
    }

    public ClientController setController() {
        return controller;
    }

    public int numOfPlayers(){

        return numOfPlayers;
    }

    public void onePlayer(String name) throws IOException {
        Player player1 = new Player(name);
        Game game = new Game(player1);
        oos.writeObject(game);
        oos.flush();
    }

    public void twoPlayers(String name, String name2) throws IOException {
        Player player1 = new Player(name);
        Player player2 = new Player(name2);
        Game game = new Game(player1, player2);
        oos.writeObject(game);
        oos.flush();
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
        } catch (IOException | ClassNotFoundException e){}
    }

    public void getCurrGameFromServer(){
        try {
            currentGame = (Game) ois.readObject();
        } catch (IOException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Klientens tråd som lägger till spelares namn + score som test för servern
     * samt hämtar arraylistan med hela highscorelistan.
     */
    @Override
    public void run() {
        try {
            while(true){
                String nbrOfPlayersstr = (String) ois.readObject();
                int nbrOfPlayers = Integer.parseInt(nbrOfPlayersstr);
                System.out.println(nbrOfPlayers);

                numOfPlayers = nbrOfPlayers;
                getScoreFromServer();
                getCurrGameFromServer();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }



    }








/*
    public void run(){
        String nbrOfPlayersStr = "";
        try {
            nbrOfPlayersStr = (String) ois.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int nbrOfPlayers = Integer.parseInt(nbrOfPlayersStr);
        System.out.println(nbrOfPlayers);

        numOfPlayers = nbrOfPlayers;



    }
*/
    /*try {
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
    /*
}
                dos.flush();
                        //createScoreboard();
                        }

                        } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        }
                        try {
                        socket.close();
                        } catch(Exception e) {}
                        controller.newResponse("Klient kopplar ner");

                        }
*/

}