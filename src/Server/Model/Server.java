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

/**
 * Klassen Server är huvuddelen i programmet som används för att kommunicera med anslutna klienter, både till och från.
 *
 * @author Isac Pettersson, Johan Skäremo
 * @version 1.0
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
    private Socket socket;

    /***
     * Konstruerar och initialiserar instansvariabler, ansluter mot databasen samt startar servertråden.
     * @param port som väljs när servern körs så att man vet vart informationen ska skickas/tas emot
     * @param controller används för att kommunicera mellan server och användargränssnitt.
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
     * Serverns trådmetod som används för att öppna en UDP anslutning på en specifik port samt lyssna på inkommande
     * TCP-anslutningar, startar respektives tråd.
     */
    @Override
    public void run() {
        try {
            DatagramSocket arduinoSocket = new DatagramSocket(port);
            InbyggdaSystemHandler inbyggdaSystemHandler = new InbyggdaSystemHandler(arduinoSocket);
            inbyggdaSystemHandler.start();


            socket = serverSocket.accept();
            ServerLog log = new ServerLog(LocalDateTime.now(), server, "Client connected to server", socket);
            addLogAndUpdate(log);
            ClientHandler ch = new ClientHandler(socket);
            ch.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoden closeConnection används för att stänga serverns anslutning till databasen.
     */
    public void closeConnectionToDatabase() {
        connection.closeConnection();
    }

    /**
     * Metoden connectToDatabase används för att servern skall etablera en asnlutning till databasen.
     */
    public void connectToDatabase() {
        connection = new DataConn();
        ServerLog log = new ServerLog(LocalDateTime.now(), "Connection to database established", "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11408587");
        addLogAndUpdate(log);
    }

    /**
     * Metoden getInfoFromDatabase används för att hämta lagrad info från databsen och spara dem i
     * lokala instansvariabler.
     */
    public void getInfoFromDatabase() {
        highscoreList = connection.getHighscore(highscoreList);
        gameList = connection.getGamelist(gameList);
        ServerLog loghighscore = new ServerLog(LocalDateTime.now(), "Recived highscorelist from database", "Highscore", "Recived");
        loghighscore.setHighscore(highscoreList);
        addLogAndUpdate(loghighscore);
        ServerLog loggames = new ServerLog(LocalDateTime.now(), "Recived games from database", "Games", "Recived");
        loggames.setGamelist(gameList);
        addLogAndUpdate(loggames);
    }


    /***
     * Metod som skickar hela highscoreListan till klienten så att klienten kan uppdatera sitt användargränssnitt
     * så att den är aktuell i både början och slutet av varje spel.
     * @param highscoreList en arraylista med alla personer som spelat spelet och dess score
     * @param thread tråden som läggas till i logg-objektet.
     * @throws IOException Kastar IOExeption för att kunna skicka över data över Objekt strömmen.
     */
    public void send(ArrayList<Player> highscoreList, Thread thread) throws IOException {
        Collections.sort(highscoreList, Collections.reverseOrder());
        ArrayList<Player> temp = new ArrayList<>();

        for (Player p : highscoreList) {
            temp.add(p);
        }

        oos.writeObject(temp);
        ServerLog log = new ServerLog(LocalDateTime.now(), thread, "Sent highscorelist to client", socket, "Sent");
        log.setPacketType("TCP");
        log.setHighscore(highscoreList);
        addLogAndUpdate(log);

        oos.writeObject(game);
        ServerLog loggame = new ServerLog(LocalDateTime.now(), thread, "Sent game to client", socket, "Sent");
        loggame.setPacketType("TCP");
        loggame.setGame(game);
        addLogAndUpdate(loggame);
        oos.flush();
    }

    /**
     * Metoden sendNbrOfPlayersToClient används för att skicka antalet spelare, hämtat från hårdvaran, till klienten.
     *
     * @param nbrOfPlayers antalet spelare som skall skickas.
     * @throws IOException Kastar IOExeption för att kunna skicka över data över Objekt strömmen.
     */
    public void sendNbrOfPlayersToClient(String nbrOfPlayers) throws IOException {
        oos.writeObject(nbrOfPlayers);
    }

    /**
     * Metoden addPlayersToList används för att lägga till aktuella spelare i highscorelistan.
     */
    public void addPlayersToList() {

        if (game.getPlayer1() != null) {
            highscoreList.add(game.getPlayer1());
        }
        if (game.getPlayer2() != null) {
            highscoreList.add(game.getPlayer2());
        }
    }

    /**
     * Metoden decideWinner används för att bestämma vinnaren på nuvarande game-objekt.
     */
    public void decideWinner() {

        if (game.getPlayer1() != null && game.getPlayer2() == null) {
            game.setWinner(game.getPlayer1());

        } else if (game.getPlayer1() != null && game.getPlayer2() != null) {

            if (game.getPlayer1().getScore() > game.getPlayer2().getScore()) {
                game.setWinner(game.getPlayer1());

            } else if (game.getPlayer2().getScore() > game.getPlayer1().getScore()) {
                game.setWinner(game.getPlayer2());
            } else if (game.getPlayer1().getScore() == game.getPlayer2().getScore()) {
                game.setWinner(null);
            }
        }
    }

    /**
     * Metoden addLogAndUpdate används för att lägga till ett log-objekt till listan i användargränssnittet.
     *
     * @param log som skall läggas till
     */
    public void addLogAndUpdate(ServerLog log) {
        serverlogs.add(log);
        controller.getServerLogToWestPanel(serverlogs);
    }

    /**
     * Metoden updateDatabase används för att uppdatera databasen med nuvarande spel.
     */
    public void updateDatabase() {
        connection.setDataInDatabase(game);
        ServerLog loghighscore = new ServerLog(LocalDateTime.now(), "Updated highscore in database", "Highscore", "Sent");
        loghighscore.setGame(game);
        addLogAndUpdate(loghighscore);
        ServerLog loggame = new ServerLog(LocalDateTime.now(), "Updated games in database", "Games", "Sent");
        loggame.setGame(game);
        addLogAndUpdate(loggame);
    }

    /***
     * Klassen ClientHandler är serverns sätt att hantera Klienter.
     */
    private class ClientHandler extends Thread {

        private ObjectInputStream ois;
        private Socket socket;

        /***
         * Konstruktor för att kunna använda socketen som skapades i servern
         * @param socket som används vid ut -och inströmmar.
         */
        public ClientHandler(Socket socket) {
            this.socket = socket;

        }

        /***
         * Metoden run är den inre klassens tråd som hanterar TCP-klienten.
         */
        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                Collections.sort(highscoreList, Collections.reverseOrder());
                ArrayList<Player> temp = new ArrayList<>();
                for (Player p : highscoreList) {
                    temp.add(p);
                }
                oos.writeObject(temp);
                ServerLog highscorelog = new ServerLog(LocalDateTime.now(), this, "Sent highscorelist to client", socket, "Sent");
                highscorelog.setPacketType("TCP");
                highscorelog.setHighscore(highscoreList);
                addLogAndUpdate(highscorelog);

                while (true) {
                    if (numOfPlayers != null) {
                        sendNbrOfPlayersToClient(numOfPlayers);
                        ServerLog logNbrOfPlayers = new ServerLog(LocalDateTime.now(), this, "Sent number of players to client", socket, "Sent");
                        logNbrOfPlayers.setNumOfPlayers(numOfPlayers);
                        logNbrOfPlayers.setPacketType("TCP");
                        addLogAndUpdate(logNbrOfPlayers);
                        numOfPlayers = null;

                        Object obj = ois.readObject();

                        if (obj instanceof Game) {
                            game = (Game) obj;
                            ServerLog log = new ServerLog(LocalDateTime.now(), this, "Game recived from client", socket, "Recived");
                            log.setGame(game);
                            log.setPacketType("TCP");
                            addLogAndUpdate(log);

                            gameList.add(game);
                            addPlayersToList();
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Klassen InbyggdaSystemHandler hanterar inkommande strömmar från hårdvaran genom UDP.
     */
    private class InbyggdaSystemHandler extends Thread {

        private DatagramSocket serverSocket;
        private byte[] receiveData = new byte[1024];
        private byte[] sendData = new byte[1024];

        /**
         * Konstruerar och iniatiliserar socket som skall användas för informationsflöde med hårdvaran.
         *
         * @param socket som skall användas för ta emot data från hårdvaran.
         */
        public InbyggdaSystemHandler(DatagramSocket socket) {
            this.serverSocket = socket;
        }

        /**
         * Metoden run är den inre klassens tråd som hanterar UDP-klienten.
         */
        public void run() {
            System.out.println("Inne i inbyggda");
            ServerLog log = new ServerLog(LocalDateTime.now(), this, "UPD connection open", serverSocket, port);
            addLogAndUpdate(log);

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    serverSocket.receive(receivePacket);
                    int dataLength = receivePacket.getLength();
                    char[] rawData = new char[dataLength];
                    byte[] rawBytes = receivePacket.getData();
                    for (int i = 0; i < dataLength; i++) {
                        rawData[i] = (char) rawBytes[i];
                    }
                    String sentence = new String(rawData);

                    String score1Pattern = "score1";
                    String score2Pattern = "score2";
                    String nbrOfPlayersPattern = "nbrOfPlayers";

                    if (sentence.regionMatches(0, score2Pattern, 0, 6)) {
                        String scoreStr = sentence.substring(6);
                        ServerLog logscore = new ServerLog(LocalDateTime.now(), this, "Recived score from client", serverSocket, "Recived", port);
                        logscore.setPacketType("UDP");
                        logscore.setScore(scoreStr);
                        addLogAndUpdate(logscore);

                        int score = Integer.parseInt(scoreStr);
                        game.getPlayer2().setScore(score);
                        decideWinner();
                        updateDatabase();
                        send(highscoreList, this);

                    } else if (sentence.regionMatches(0, score1Pattern, 0, 6)) {
                        String scoreStr = sentence.substring(6);
                        ServerLog logscore = new ServerLog(LocalDateTime.now(), this, "Recived score from client", serverSocket, "Recived", port);
                        logscore.setPacketType("UDP");
                        logscore.setScore(scoreStr);
                        addLogAndUpdate(logscore);

                        int score = Integer.parseInt(scoreStr);
                        game.getPlayer1().setScore(score);
                        if (game.getPlayer2() == null) {
                            decideWinner();
                            updateDatabase();
                            send(highscoreList, this);
                        }


                    } else if (sentence.regionMatches(0, nbrOfPlayersPattern, 0, 12)) {
                        String nbrOfplayerStr = sentence.substring(12);
                        ServerLog logNbrOfPlayers = new ServerLog(LocalDateTime.now(), this, "Received number of players from client", serverSocket, "Received", port);
                        logNbrOfPlayers.setNumOfPlayers(nbrOfplayerStr);
                        addLogAndUpdate(logNbrOfPlayers);
                        numOfPlayers = nbrOfplayerStr;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}