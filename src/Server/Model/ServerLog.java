package Server.Model;

import Client.Model.Game;
import Client.Model.Player;

import java.util.ArrayList;
import java.net.DatagramSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Klassen ServerLog används för att kunna skapa log-objekt med olika attribut
 * som sedan ska visas i server användargränssnittet.
 *
 * @author Isac Pettersson, Johan Skäremo
 * @version 1.0
 */
public class ServerLog {

    private String timeNow;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Thread thread;
    private String description;
    private Game game;
    private String databaseTable;
    private String databaseURL;
    private Socket socket;
    private DatagramSocket dSocket;
    private String score;
    private String numOfPlayers;
    private String packetType;
    private ArrayList<Player> highscore;
    private ArrayList<Game> gamelist;
    private String sendOrRecieve;
    private int port;

    /**
     * Konstruerar och initialiserar instansvariablerna för det specifika log-objekt som skapats.
     *
     * @param ldt         tiden som loggades då objektet skapades för visning i användargränssnitt.
     * @param thread      tråden som utförde händelsen som loggas.
     * @param description på vad som loggades.
     * @param socket      som används vid den specifika TCP händelsen.
     * @param direction   på händelsen ifall den togs emot eller skickades.
     */
    public ServerLog(LocalDateTime ldt, Thread thread, String description, Socket socket, String direction) {
        this.thread = thread;
        this.description = description;
        this.timeNow = ldt.format(formatter);
        this.socket = socket;
        this.sendOrRecieve = direction;
    }

    /**
     * Konstruerar och initialiserar instansvariablerna för det specifika log-objekt som skapats.
     *
     * @param ldt         tiden som loggades då objektet skapades för visning i användargränssnitt.
     * @param thread      tråden som utförde händelsen som loggas.
     * @param description på vad som loggades.
     * @param socket      som används vid den specifika TCP händelsen.
     */
    public ServerLog(LocalDateTime ldt, Thread thread, String description, Socket socket) {
        this.thread = thread;
        this.description = description;
        this.timeNow = ldt.format(formatter);
        this.socket = socket;
    }

    /**
     * Konstruerar och initialiserar instansvariablerna för det specifika log-objekt som skapats.
     *
     * @param ldt            tiden som loggades då objektet skapades för visning i användargränssnitt.
     * @param thread         tråden som utförde händelsen som loggas.
     * @param description    på vad som loggades.
     * @param datagramSocket som används vid den specifika UDP händelsen.
     * @param direction      på händelsen ifall den togs emot eller skickades.
     * @param port           porten som används för den specifika händelsen.
     */
    public ServerLog(LocalDateTime ldt, Thread thread, String description, DatagramSocket datagramSocket, String direction, int port) {
        this.thread = thread;
        this.description = description;
        this.timeNow = ldt.format(formatter);
        this.dSocket = datagramSocket;
        this.sendOrRecieve = direction;
        this.port = port;
    }

    /**
     * Konstruerar och initialiserar instansvariablerna för det specifika log-objekt som skapats.
     *
     * @param ldt            tiden som loggades då objektet skapades för visning i användargränssnitt.
     * @param thread         tråden som utförde händelsen som loggas.
     * @param description    på vad som loggades.
     * @param datagramSocket som används vid den specifika UDP händelsen.
     * @param port           porten som används för den specifika händelsen.
     */
    public ServerLog(LocalDateTime ldt, Thread thread, String description, DatagramSocket datagramSocket, int port) {
        this.thread = thread;
        this.description = description;
        this.timeNow = ldt.format(formatter);
        this.dSocket = datagramSocket;
        this.port = port;
    }

    /**
     * Konstruerar och initialiserar instansvariablerna för det specifika log-objekt som skapats.
     *
     * @param ldt         tiden som loggades då objektet skapades för visning i användargränssnitt.
     * @param description på vad som loggades.
     * @param dbDriver    för databasen som används vid anslutningen till databasen.
     */
    public ServerLog(LocalDateTime ldt, String description, String dbDriver) {
        this.description = description;
        this.timeNow = ldt.format(formatter);
        this.databaseURL = dbDriver;
    }

    /**
     * Konstruerar och initialiserar instansvariablerna för det specifika log-objekt som skapats.
     *
     * @param ldt         tiden som loggades då objektet skapades för visning i användargränssnitt.
     * @param description på vad som loggades.
     * @param dbTable     tabellen som används vid hämtningen eller inmatningen av data till/från databasen.
     * @param direction   på händelsen ifall den togs emot eller skickades.
     */
    public ServerLog(LocalDateTime ldt, String description, String dbTable, String direction) {
        this.description = description;
        this.timeNow = ldt.format(formatter);
        this.databaseTable = dbTable;
        this.databaseURL = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11408587";
        this.sendOrRecieve = direction;
    }

    public String getDescription() {

        return description;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setNumOfPlayers(String numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public void setPacketType(String packetType) {
        this.packetType = packetType;
    }

    public void setGamelist(ArrayList<Game> gamelist) {
        this.gamelist = gamelist;
    }

    public void setHighscore(ArrayList<Player> highscore) {
        this.highscore = highscore;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getUDPanslutningsString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getClientConnectString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getGameSentFromClientString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        temp.add("Player 1: " + game.getPlayer1().getName());
        if (game.getPlayer2() != null) {
            temp.add("Player 2:  " + game.getPlayer2().getName());
        }
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getDatabaseConnectionString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        temp.add("DatabaseURL: " + databaseURL);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getDatabaseHighscorelistString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        for (Player p : highscore) {
            temp.add("Name: " + p.getName() + ", Score: " + p.getScore());
        }
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getUpdatedHighscoreInDBString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        temp.add("Player 1: " + game.getPlayer1().getName() + ", Score: " + game.getPlayer1().getScore());
        if (game.getPlayer2() != null) {
            temp.add("Player 2:  " + game.getPlayer2().getName() + ", Score: " + game.getPlayer2().getScore());
        }
        return temp;

    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getUpdatedGameInDbString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        temp.add("Player 1: " + game.getPlayer1().getName() + ", Score: " + game.getPlayer1().getScore());
        if (game.getPlayer2() != null) {
            temp.add("Player 2:  " + game.getPlayer2().getName() + "Score: " + game.getPlayer2().getScore());
        }
        if (game.getWinner() == null) {
            temp.add("Winner: Draw");
        } else {
            temp.add("Winner: " + game.getWinner().getName());
        }
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getDatabaseGamesListString() {
        ArrayList<String> temp = new ArrayList<>();
        int counter = 1;
        temp.add("Time: " + timeNow);
        for (Game g : gamelist) {
            if (g.getPlayer2() == null) {
                temp.add(counter + ": Player 1: " + g.getPlayer1().getName() + ", Score: " + g.getPlayer1().getScore() + ", Winner: " + g.getWinner().getName());
                counter++;
            } else {
                if (g.getWinner() == null) {
                    temp.add(counter + ": Player 1: " + g.getPlayer1().getName() + " Score: " + g.getPlayer1().getScore() + ", Player 2: " + g.getPlayer2().getName() + ", Score: " + g.getPlayer2().getScore() + ", Winner: Draw");
                    counter++;
                } else {
                    temp.add(counter + ": Player 1: " + g.getPlayer1().getName() + " Score: " + g.getPlayer1().getScore() + ", Player 2: " + g.getPlayer2().getName() + ", Score: " + g.getPlayer2().getScore() + ", Winner: " + g.getWinner().getName());
                    counter++;
                }
            }
        }
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getSentHighscoreListToClientString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        for (Player p : highscore) {
            temp.add("Name: " + p.getName() + ", Score: " + p.getScore());
        }
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getSentGameToClientString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        temp.add("Player 1: " + game.getPlayer1().getName() + ", Score: " + game.getPlayer1().getScore());
        if (game.getPlayer2() != null) {
            temp.add("Player 2:  " + game.getPlayer2().getName() + ", Score: " + game.getPlayer2().getScore());
        }
        if (game.getWinner() == null) {
            temp.add("Winner: Draw");
        } else {
            temp.add("Winner: " + game.getWinner().getName());
        }
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getSentNumOfPlayersToClientString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        temp.add("Number of players: " + numOfPlayers);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getReceivedScoreFromClientString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        temp.add("Score: " + score);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under content.
     */
    public ArrayList getReceivedNbrOfPlayersFromClientString() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time: " + timeNow);
        temp.add("Number of players: " + numOfPlayers);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getUDPConnectionInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Thread: " + thread.getName() + ", ThreadID: " + thread.getId());
        temp.add("Port: " + port);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getClientConnectedInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Thread: " + thread.getName() + ", ThreadID: " + thread.getId());
        temp.add("Socket address: " + socket.getInetAddress());
        temp.add("Socket port: " + socket.getPort());
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getGameRecivedInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Thread: " + thread.getName() + ", ThreadID: " + thread.getId());
        temp.add("Socket address: " + socket.getInetAddress());
        temp.add("Socket port: " + socket.getPort());
        temp.add("Packettype: " + packetType);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getUpdatedHighscoreInDBInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Database table: " + databaseTable);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getUpdatedGamesInDBInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Database table: " + databaseTable);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getConnectedToDBInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Database connection established");
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getNbrOfPlayersRecievedInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Thread: " + thread.getName() + ", ThreadID: " + thread.getId());
        temp.add("Socket address: " + dSocket.getInetAddress());
        temp.add("Port: " + dSocket.getPort());
        temp.add("Packettype: " + packetType);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getNbrOfPlayersSentInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Thread: " + thread.getName() + ", ThreadID: " + thread.getId());
        temp.add("Socket address: " + socket.getInetAddress());
        temp.add("Port: " + socket.getPort());
        temp.add("Packettype: " + packetType);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getScoreFromClientInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Thread: " + thread.getName() + "ThreadID: " + thread.getId());
        temp.add("Socket address: " + dSocket.getInetAddress());
        temp.add("Port: " + dSocket.getPort());
        temp.add("Packettype: " + packetType);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getSentGameToClientInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Thread: " + thread.getName() + ", ThreadID: " + thread.getId());
        temp.add("Socket address: " + socket.getInetAddress());
        temp.add("Port: " + socket.getPort());
        temp.add("Packettype: " + packetType);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getSentHighscoreToClientInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Thread: " + thread.getName() + ", ThreadID: " + thread.getId());
        temp.add("Socket address: " + socket.getInetAddress());
        temp.add("Port: " + socket.getPort());
        temp.add("Packettype: " + packetType);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getReceivedGamesFromDbInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Database table: " + databaseTable);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden används för att lätt kunna samla all data i en arraylista som ska visas i användagränssnittet.
     *
     * @return arraylistan som ska visas i användargränssnittet under info.
     */
    public ArrayList getRecievedHighscorelistFromDbInfo() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Database table: " + databaseTable);
        temp.add("Direction: " + sendOrRecieve);
        return temp;
    }

    /**
     * Metoden nedan gör om objektet till en string för att visas i användagränssnitet.
     *
     * @return en string som beskriver händelsen som loggats.
     */
    @Override
    public String toString() {
        return timeNow + "   " + description;
    }
}
