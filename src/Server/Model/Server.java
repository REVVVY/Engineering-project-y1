package Server.Model;

import Client.Model.Player;
import Client.Model.Game;
import Database.DataConn;
import Server.Controller.ServerController;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/***
 * Server klass som lagrar data och skickar vidare data från inbyggda systemet till klienten.
 */
public class Server implements Runnable {

    private Thread server = new Thread(this);
    private ServerSocket serverSocket;
    private int port;
    private ObjectOutputStream oos;
    private Game game;

    private LinkedList<ClientHandler> clientList;
    private ArrayList<Player> highscoreList;
    private ArrayList<Game> gameList;
    private DataConn connection;
    private String numOfPlayers;
    private ServerController controller;
    private ArrayList<ServerLog> serverlogs;
    /***
     * Konstruktor för att starta servern och initzialisera arraylisten samt porten.
     * @param port porten som väljs när servern körs så att man vet vart informationen ska skickas/tas emot
     */
    public Server(int port, ServerController controller) {
        this.controller = controller;
        this.port = port;
        clientList = new LinkedList<>();
        highscoreList = new ArrayList<>();
        serverlogs = new ArrayList<>();
        gameList = new ArrayList<>();
        connectToDatabase();
        getInfoFromDatabase();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();
    }



    /***
     * Accepterar klientens anslutning och skapar ett objekt av en inre klass.
     * Lägger till i klientlistan och startar klientens tråd.
     */
    @Override
    public void run() {
        //while (true) {
        try {
            DatagramSocket arduinoSocket = new DatagramSocket(2525);
            InbyggdaSystemHandler inbyggdaSystemHandler = new InbyggdaSystemHandler(arduinoSocket);
            inbyggdaSystemHandler.start();


            Socket socket = serverSocket.accept();
            addLogAndUpdate(new ServerLog(LocalDateTime.now(), server, "Client connect to server"));
            oos = new ObjectOutputStream(socket.getOutputStream());
            ClientHandler ch = new ClientHandler(socket);
            //clientList.add(ch);
            ch.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnectionToDatabase(){
        connection.closeConnection();
    }

    public void connectToDatabase(){
        connection = new DataConn();
    }

    public void getInfoFromDatabase() {     //icke ifyllt namn i databas = tom sträng
        highscoreList = connection.getHighscore(highscoreList);
        gameList = connection.getGamelist(gameList);
        //Bara tester undan för att visa databasen
        for (Player p: highscoreList) {
            System.out.println(p.getName() + ": " + p.getScore());
        }
        System.out.println("-------------------------");
        for(Game g: gameList){
            System.out.println("player1: " + g.getPlayer1().getName() + ", Score: " + g.getPlayer1().getScore());
            if(!g.getPlayer2().getName().equals("")){
                System.out.println("player2: " + g.getPlayer2().getName() + ", Score: " + g.getPlayer2().getScore());
            }
        }
    }

    /***
     * Kollar ifall sista spelaren har fått in score för att kunna skicka till klienten.
     * @throws IOException kastar IOExeption för att kunna anropa send metoden som skickar via en ström.
     */
    public void checkIfReadyToSend() throws IOException {
        int lastIndex = highscoreList.size() - 1;
        Player lastPlayer = highscoreList.get(lastIndex);
        if (lastPlayer.getScore() != 0) {
            updateDatabase();
            send(highscoreList);
        }
    }

    /***
     * Metod som skickar hela highscoreListan till klienten så att klienten kan uppdatera JListen
     * Så att JListen är aktuell i början/slut av varje spel.
     * @param highscoreList en arraylista med alla personer som spelat spelet och dess score
     * @throws IOException Kastar IOExeption för att kunna skicka över data över Objekt strömmen.
     */
    public void send(ArrayList<Player> highscoreList) throws IOException {
        Collections.sort(highscoreList, Collections.reverseOrder());
        ArrayList<Player> temp = new ArrayList<>();
        for(Player p: highscoreList){
            if(temp.size() < 10){
                temp.add(p);
            }
        }
        oos.writeObject(temp);
        oos.writeObject(game);
        oos.flush();
    }

    public void sendNbrOfPlayersToClient(String nbrOfPlayers) throws IOException {
        oos.writeObject(nbrOfPlayers);
        numOfPlayers = null;
    }

    /***
     * Metod som lägger till score på den specifika spelaren
     * @param score score som en viss spelare fått
     */
    public void addScoreToPlayer(int score) {

        for (Player p : highscoreList) {
            if (p.getScore() == 0) {
                p.setScore(score);
                break;
            }
        }
    }

    public void addPlayersToList(){

        if(game.getPlayer1() != null){
            highscoreList.add(game.getPlayer1());
        }
        if(game.getPlayer2() != null){
            highscoreList.add(game.getPlayer2());
        }
    }

    public void decideWinner() {

        if(game.getPlayer1() != null && game.getPlayer2() == null){
            game.setWinner(game.getPlayer1());

        }else if(game.getPlayer1() != null && game.getPlayer2() != null){

            if(game.getPlayer1().getScore() > game.getPlayer2().getScore()){
                game.setWinner(game.getPlayer1());

            }else if(game.getPlayer2().getScore() > game.getPlayer1().getScore()){
                game.setWinner(game.getPlayer2());
            } else if(game.getPlayer1().getScore() == game.getPlayer2().getScore()){
                game.setWinner(null);
            }
        }
    }

    public void addLogAndUpdate(ServerLog log){
        serverlogs.add(log);
        controller.getServerLogToWestPanel(serverlogs);
    }

    public void updateDatabase(){
        connection.setDataInDatabase(game);
    }

    /***
     * En inre klass för att kunna ta emot strängar och hantera datan utifrån patterns
     */
    private class ClientHandler extends Thread {

        private DataInputStream dis;
        private ObjectInputStream ois;
        private Socket socket;

        /***
         * Konstruktor för att kunna använda socketen som skapades i servern
         * @param socket socket för att använda vid strömmarna
         */
        public ClientHandler(Socket socket) {
            this.socket = socket;

        }

        /***
         * Den inre klassens tråd som hämtar strängar och hanterar dem utifrån deras specifika mönstert.
         */
        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                numOfPlayers = "2";

                while(true) {

                    if (numOfPlayers != null) {
                        sendNbrOfPlayersToClient(numOfPlayers);
                        Object obj = ois.readObject();

                        //Todo ändra när vi mergar med klient så vi hanterar det korrekt och inte lägger score osv
                        if (obj instanceof Game) {
                            game = (Game) obj;
                            ServerLog log = new ServerLog(LocalDateTime.now(), this, "Game Sent From Client");
                            log.setGame(game);
                            addLogAndUpdate(log);

                            gameList.add(game);
                            addPlayersToList();
                            addScoreToPlayer(40);
                            addScoreToPlayer(40);
                            decideWinner();
                            checkIfReadyToSend();
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private class InbyggdaSystemHandler extends Thread {

        private DatagramSocket serverSocket;
        private byte[] receiveData = new byte[1024];
        private byte[] sendData = new byte[1024];

        public InbyggdaSystemHandler(DatagramSocket socket) {
            this.serverSocket = socket;
        }

        public void run() {
            System.out.println("Inne i inbyggda");
            addLogAndUpdate(new ServerLog(LocalDateTime.now(), this, "Öppnar UDP Anslutning"));

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    serverSocket.receive(receivePacket);
                    int dataLength = receivePacket.getLength();
                    char[] rawData = new char[dataLength];
                    byte[] rawBytes = receivePacket.getData();
                    for (int i = 0; i < dataLength; i++) {
                        rawData[i] = (char)rawBytes[i];
                    }
                    String sentence = new String(rawData);

                    System.out.println("RECEIVED: " + sentence);

                    String score1Pattern = "score1";
                    String score2Pattern = "score2";
                    String nbrOfPlayersPattern = "nbrOfPlayers";

                    if (sentence.regionMatches(0, score2Pattern, 0, 6)) {
                        System.out.println("Score2 pattern");
                        String scoreStr = sentence.substring(6);
                        int score = Integer.parseInt(scoreStr);
                        addScoreToPlayer(score);
                        decideWinner();
                        checkIfReadyToSend();

                    } else if (sentence.regionMatches(0, score1Pattern, 0, 6)) {
                        System.out.println("Score1 pattern");
                        String scoreStr = sentence.substring(6);
                        int score = Integer.parseInt(scoreStr);
                        addScoreToPlayer(score);
                        decideWinner();
                        checkIfReadyToSend();

                    } else if (sentence.regionMatches(0, nbrOfPlayersPattern, 0, 12)) {
                        System.out.println("nbrOfPlayersPattern");
                        String nbrOfplayerStr = sentence.substring(12);
                       // numOfPlayers = nbrOfplayerStr;

                    }


                    //Ifall vi vill kunna skicka till det inbyggda systemet
                   /* InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();
                    String capitalizedSentence = sentence.toUpperCase();

                    sendData = capitalizedSentence.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

                    serverSocket.send(sendPacket);
                    */

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}