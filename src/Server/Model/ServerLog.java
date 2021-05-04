package Server.Model;

import Client.Model.Game;
import Client.Model.Player;
import java.util.ArrayList;

import java.net.DatagramSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerLog {
    private String timeNow;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Thread thread;
    private String description;
    private Game game;
    private String databaseTable;
    private String databaseDriver;
    private Socket socket;
    private DatagramSocket dSocket;
    private String score;
    private String numOfPlayers;
    private String packetType;
    private ArrayList<Player> highscore;
    private String sendOrRecieve;

    public ServerLog(LocalDateTime ldt, Thread thread, String description){
         this.thread = thread;
         this.description = description;
         this.timeNow = ldt.format(formatter);
    }

    public String getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getDatabaseTable() {
        return databaseTable;
    }

    public void setDatabaseTable(String databaseTable) {
        this.databaseTable = databaseTable;
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public void setDatabaseDriver(String databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DatagramSocket getdSocket() {
        return dSocket;
    }

    public void setdSocket(DatagramSocket dSocket) {
        this.dSocket = dSocket;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(String numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public String getPacketType() {
        return packetType;
    }

    public void setPacketType(String packetType) {
        this.packetType = packetType;
    }

    public ArrayList<Player> getHighscore() {
        return highscore;
    }

    public void setHighscore(ArrayList<Player> highscore) {
        this.highscore = highscore;
    }

    public String getSendOrRecieve() {
        return sendOrRecieve;
    }

    public void setSendOrRecieve(String sendOrRecieve) {
        this.sendOrRecieve = sendOrRecieve;
    }

    @Override
    public String toString() {
        //return String.format("%s, " + thread.getName()+ "%s" , timeNow, description);
        return timeNow + " "+ thread.getName()+ " " + description;
    }
}
