package Server;

import Client.Player;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.ToDoubleBiFunction;

/***
 * Server klass som lagrar data och skickar vidare data från inbyggda systemet till klienten.
 */
public class Server implements Runnable {

    private Thread server = new Thread(this);
    private ServerSocket serverSocket;
    private int port;

    private LinkedList<ClientHandler> clientList;
    private ArrayList<Player> highscoreList;

    /***
     * Konstruktor för att starta servern och initzialisera arraylisten samt porten.
     * @param port porten som väljs när servern körs så att man vet vart informationen ska skickas/tas emot
     */
    public Server(int port) {
        this.port = port;
        clientList = new LinkedList<>();
        highscoreList = new ArrayList<>();

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
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler ch = new ClientHandler(socket);
                clientList.add(ch);
                ch.start();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * En inre klass för att kunna ta emot strängar och hantera datan utifrån patterns
     */
    private class ClientHandler extends Thread {

        private DataInputStream dis;
        private ObjectOutputStream oos;
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
                dis = new DataInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject("2");

                String score1Pattern = "score1";
                String score2Pattern = "score2";
                String player1Pattern = "player1";
                String player2Pattern = "player2";
                String nbrOfPlayersPattern = "nbrOfPlayers";

                while (true) {
                    String incomingString = dis.readUTF();

                    //TODO, Skapa ett till pattern för att kolla antalet spelare som ska spela spelet,
                    // beroende på siffran som kommer in så skapas ett gameobjekt med antingen 1 eller 2 spelare
                    // efter det mottagits namn från java klient.

                    if (incomingString.regionMatches(0, player2Pattern, 0, 7)) {
                        System.out.println("Player2 pattern");
                        String player = incomingString.substring(7);
                        Player player1 = new Player(player);
                        highscoreList.add(player1);

                    } else if (incomingString.regionMatches(0, player1Pattern, 0, 7)){
                        System.out.println("Player1 pattern");
                        String player = incomingString.substring(7);
                        Player player2 = new Player(player);
                        highscoreList.add(player2);

                    } else if (incomingString.regionMatches(0, score2Pattern, 0, 6)) {
                        System.out.println("Score2 pattern");
                        String scoreStr = incomingString.substring(6);
                        int score = Integer.parseInt(scoreStr);
                        addScoreToPlayer(score);
                        showList();
                        checkIfReadyToSend();

                    } else if(incomingString.regionMatches(0, score1Pattern, 0, 6)){
                        System.out.println("Score1 pattern");
                        String scoreStr = incomingString.substring(6);
                        int score = Integer.parseInt(scoreStr);
                        addScoreToPlayer(score);
                        showList();
                        checkIfReadyToSend();
                    }

                    else if(incomingString.regionMatches(0, nbrOfPlayersPattern, 0, 12)){
                        System.out.println("nbrOfPlayersPattern");
                        String nbrOfplayerStr = incomingString.substring(12);
                        sendNbrOfPlayersToClient(nbrOfplayerStr);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void sendNbrOfPlayersToClient(String nbrOfPlayers) throws IOException{
            oos.writeObject(nbrOfPlayers);
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

        //TODO - Lägg till GUI för servern som loggar all trafik in och ut och den specifika datan
        /***
         * Metod för att skriva ut listan i terminalen för tester.
         */
        public void showList() {
            for (Player player : highscoreList) {
                System.out.println(player.getName() + " " + player.getScore());
            }
        }

        /***
         * Kollar ifall sista spelaren har fått in score för att kunna skicka till klienten.
         * @throws IOException kastar IOExeption för att kunna anropa send metoden som skickar via en ström.
         */
        public void checkIfReadyToSend() throws IOException{
            int lastIndex = highscoreList.size() - 1;
            Player lastPlayer = highscoreList.get(lastIndex);
            if (lastPlayer.getScore() != 0){
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
            oos.writeObject(highscoreList);
            oos.flush();
        }
    }
}
