package Client.Model;

import Client.Controller.ClientController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/***
 * Klient klass som representerar spelaren i spelet
 * @author Reem Mohamed
 */
public class Client implements Runnable {
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

    /**
     * Sätter det nya kommande antalet spelaren
     * @param numOfPlayers nytt antal spelaren
     */
    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * Skickar vidare antalet spelaren till ett refister-fönster enligt antal spelaren
     * @param numberOfPlayers
     */
    private void startNamePanels(int numberOfPlayers){
        controller.showUI(numberOfPlayers);
    }

    /**
     * Får antalet spelaren från servern, vilket lagras då hos controller
     * @return antal spelaren
     */
    public int getNumOfPlayersFromServer()  {
        String nbrOfPlayersStr = null;
        try {
            nbrOfPlayersStr = (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        int nbrOfPlayers = Integer.parseInt(nbrOfPlayersStr);
        System.out.println(nbrOfPlayers);
        controller.setNbrOfPlayers(nbrOfPlayers);
        return nbrOfPlayers;
    }

    /**
     * Retunerar antal spelaren
     * @return antal spelaren
     */
    public int getNumOfPlayers(){
        return numOfPlayers;
    }

    /**
     * Skickar namnet på spelaren om det är en spelare
     * @param name namn på spelaren som registreras på regiser-fönstret
     */
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

    /**
     * Skickar namnen på spelarna om det är två spelaren
     * @param name namn på första spelaren som registreras på regiser-fönstret
     * @param name2 namn på andra spelaren som registreras på regiser-fönstret
     */
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

    /**
     * Får en lista med alla spelaren som är registrerade på spelets databas från servern
     * Listan används som en high-scorelista med 10 spelaren
     * Listan används igen i sin helhet för att överföras vidare till view som en söklista
     * Båda listorna konverteras till strängar innan överföring
     */
    public void getFullScoreList() {
        try {
            fullScoreList = (ArrayList<Player>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

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
    }

    /**
     * Skickar highscore listan till Server
     * och vidare till controller
     */
    public void getScoreFromServer() {
        try {
            highScoreList = (ArrayList<Player>) ois.readObject();
        } catch (IOException | ClassNotFoundException e){}

       ArrayList<String> highScoreListStr = new ArrayList<>();

        for (Player p : highScoreList) {
            highScoreListStr.add(p.getName());
            highScoreListStr.add(String.valueOf(p.getScore()));
        }
        controller.saveHighScore(highScoreListStr);
    }

    /**
     * Får vinnaren från servern, om det är spelare 1, 2, eller samma score
     * Annonserar vinnarens namn efter mottagning av vinnaren
     */
    public void getCurrGameFromServer(){
        try {
            currentGame = (Game) ois.readObject();
            if (currentGame.getWinner() == null) { //draw, lika score
                winner = 0;
            } else if(currentGame.getWinner() == currentGame.getPlayer1()){
                winner = 1;
            } else if(currentGame.getWinner() == currentGame.getPlayer2()){
                winner = 2;
            }
            controller.sendWinnerToView(winner);
            controller.showScoreInFrame1();
        } catch (IOException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Klientens tråd som först tar emot spelarlista och visar den,
     * skickar skrivna namnen på spelarena till servern,
     * samt tar emot arraylistan med hela high-scorelistan
     * I slutet tas emot vinnaren från servern.
     */

    public void run() {
        getFullScoreList();
        controller.showScoreInFrame1();

        while(true) {
                int numberOfPlayers = getNumOfPlayersFromServer();
                startNamePanels(numberOfPlayers);
                getScoreFromServer();
                System.out.println("Score is sent");

                getCurrGameFromServer();
        }
    }


}