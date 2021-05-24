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
    private ArrayList<Player> highScoreList;
    private ArrayList<Player> fullScoreList;
    private Game currentGame;
    private int winner;
    private Socket socket;

    private int numOfPlayers = 0;

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

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    private void startNamePanels(int numberOfPlayers){
        controller.showUI(numberOfPlayers);
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
        /*numOfPlayers = nbrOfPlayers;
        return numOfPlayers;

         */
        controller.setNbrOfPlayers(nbrOfPlayers);
        return nbrOfPlayers;
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
        System.out.println("1 " + fullScoreList.size());


        ArrayList<String> fullScoreListStr = new ArrayList<>();
        ArrayList<String> tempTop10 = new ArrayList<>();
        for (Player p : fullScoreList) {
            fullScoreListStr.add(p.getName());
            fullScoreListStr.add(String.valueOf(p.getScore()));
            if(tempTop10.size() < 20){
                tempTop10.add(p.getName());
                tempTop10.add(String.valueOf(p.getScore()));
            }
        }

        controller.saveTop10Score(tempTop10); //highscorelista top 10
        controller.saveHighScore(fullScoreListStr); // TILL SEARCHKNAPP, FUNGERAR
        controller.showScoreInFrame1();

    }

    /**
     * Skickar highscore listan till Server
     * och vidare till controller
     */
    public void getScoreFromServer() {
        try {
            highScoreList = (ArrayList<Player>) ois.readObject();

            /*System.out.println(highScoreList.get(0).getName() + highScoreList.get(0).getScore());
            System.out.println(highScoreList.get(1).getName() + highScoreList.get(1).getScore());
            System.out.println(highScoreList.get(2).getName() + highScoreList.get(2).getScore());

             */

          //  controller.printScoreboard(playerScore);
            /*for (Player p : highScoreList) {
                System.out.println(p.getName() + " " + p.getScore());
            }

             */
            //System.out.println("GotList");
            //System.out.println("2 " + highScoreList.size());
        } catch (IOException | ClassNotFoundException e){}

       ArrayList<String> highScoreListStr = new ArrayList<>();

        for (Player p : highScoreList) {
            highScoreListStr.add(p.getName());
            highScoreListStr.add(String.valueOf(p.getScore()));
        }

        controller.showScore();
        controller.saveHighScore(highScoreListStr);

    }

    public void getCurrGameFromServer(){
        try {
            currentGame = (Game) ois.readObject();
            if (currentGame.getWinner() == null) {
                winner = 0;
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
            //if(numOfPlayers == 0){
              //  numOfPlayers = 1; //en annan siffra än 0
                getFullScoreList(); //1
                int numberOfPlayers = getNumOfPlayersFromServer(); //2
                //setNumOfPlayers(numberOfPlayers);
                startNamePanels(numberOfPlayers); //3
                getScoreFromServer();
                System.out.println("Score is sent");

                getCurrGameFromServer();
            //}

        }
    }


}