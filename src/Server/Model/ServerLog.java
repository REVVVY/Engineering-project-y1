package Server.Model;

import Client.Model.Game;
import Client.Model.Player;
import java.util.ArrayList;
import java.net.DatagramSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerLog {
    private String timeNow; // - Finns alltid
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // - Finns alltid
    private Thread thread; // - Finns alltid
    private String description; // - Finns alltid
    private Game game; // - Skickas och Tas emot
    private String databaseTable; // - Skickat eller tagit emot info från databas Samma
    private String databaseDriver; // - Skickat eller tagit emot info från databas Samma ^^
    private Socket socket; // - Ska alltid finns via TCP
    private DatagramSocket dSocket; // - Ska alltid finnas via UDP
    private String score; // - Tas emot
    private String numOfPlayers; // - Tas emot och skickas
    private String packetType; // - Ska alltid visas vid UDP / TCP överföring
    private ArrayList<Player> highscore; // - Skickas till java klient
    private String sendOrRecieve; // - Ska alltid vara med

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

    @Override
    public String toString() {
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


    public ArrayList getUDPanslutningsString(){
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time : " + timeNow);
        temp.add("Thread: " +thread.getName());
       return temp;
    }
    public ArrayList getClientConnectString(){
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Time : " + timeNow);
        temp.add("Thread: " +thread.getName());
        return temp;
    }
    public ArrayList getGameSentFromClientString(){
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Player 1: " + game.getPlayer1());
        if(game.getPlayer2()!= null){
            temp.add("Player 2:  " + game.getPlayer2().getName());
        }
        return temp;
    }
}
