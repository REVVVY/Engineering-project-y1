package Client.Model;

import Client.Controller.ClientController;

import java.io.*;
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
    private ArrayList<Player> playerScore;
    private ArrayList<Player> fullScoreList;
    private Game currentGame;
    private int winner;
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

    private void startNamePanels(){
        controller.showUI(getNumOfPlayers());
    }

    public int getNumOfPlayersFromServer()  {
        String nbrOfPlayersStr = null;
        try {
            nbrOfPlayersStr = (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        int nbrOfPlayers = Integer.parseInt(nbrOfPlayersStr);
        System.out.println(nbrOfPlayers);
        numOfPlayers = nbrOfPlayers;
        return numOfPlayers;
    }
    public int getNumOfPlayers(){
        return numOfPlayers;
    }

    public void onePlayer(String name)  {
        Player player1 = new Player(name);
        Game game = new Game(player1);
        try {
            oos.writeObject(game);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        numOfPlayers = 0;
    }

    public void twoPlayers(String name, String name2) {
        Player player1 = new Player(name);
        Player player2 = new Player(name2);
        Game game = new Game(player1, player2);
        try {
            oos.writeObject(game);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        numOfPlayers = 0;
    }

    public void getFullScoreList() {
        try {
            fullScoreList = (ArrayList<Player>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String> comingPlayerScore = new ArrayList<>();
        ArrayList<String> tempTop10 = new ArrayList<>();
        for (Player p : fullScoreList) {
            comingPlayerScore.add(p.getName());
            comingPlayerScore.add(String.valueOf(p.getScore()));
            if(tempTop10.size() < 10){
                tempTop10.add(p.getName() + "   " + p.getScore());
            }
        }
        controller.saveTop10Score(tempTop10);
        controller.saveHighScore(comingPlayerScore); // TILL SEARCHKNAPP, FUNGERAR
    }

    /**
     * Skickar highscore listan till Server
     * och vidare till controller
     */
    public void getScoreFromServer() {
        try {
            playerScore = (ArrayList<Player>) ois.readObject();
          //  controller.printScoreboard(playerScore);
            for (Player p : playerScore) {
                System.out.println(p.getName() + " " + p.getScore());
            }
            System.out.println("GotList");
        } catch (IOException | ClassNotFoundException e){}

       ArrayList<String> comingPlayerScore = new ArrayList<>();
        ArrayList<String> comingPlayerScore2 = new ArrayList<>();

        int scoreLength = playerScore.size()/2;
        String[][] playerScoreSearch = new String[scoreLength][scoreLength];

        for (Player p : playerScore) {
            comingPlayerScore.add(p.getName());
            comingPlayerScore.add(String.valueOf(p.getScore()));
        }
        controller.showScore(comingPlayerScore);

        //  controller.setScoreList(playerScore);
    }

    public void getCurrGameFromServer(){
        winner = -1;
        try {
            currentGame = (Game) ois.readObject();
            if (currentGame == null) {
             //   winner = 0;
            } else if(currentGame.getWinner() == currentGame.getPlayer1()){
                winner = 1;
            } else if(currentGame.getWinner() == currentGame.getPlayer2()){
                winner = 2;
            }
            controller.sendWinnerToView(winner);

            //if winner = null --> lika
            //if winner = player2 --> winner
        } catch (IOException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Klientens tråd som lägger till spelares namn + score som test för servern
     * samt hämtar arraylistan med hela highscorelistan.
     */

    public void run() {

        while(true) {
            getFullScoreList(); //1
            getNumOfPlayersFromServer(); //2

            startNamePanels(); //3

            getScoreFromServer();
            System.out.println("Score is sent");

            getCurrGameFromServer();
        }
    }


}