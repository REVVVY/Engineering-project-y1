package Client.Controller;

import Client.Model.Client;
import Client.Model.Player;
import Client.view.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ClientController {

    private Client client;
    private ClientUI ui = new ClientUI(this);;

    private JFrame frame;
    private ArrayList<String> top10ScoreList;
    private ArrayList<String> comingPlayerScore;
    private int nbrOfPlayers;

    public ClientController(String ip, int port) {
        this.client = new Client(ip, port, this);
    }

    /**
     * Visar panel enligt antal spelare som är skickad från Server
     * Anrops efter att skapa 1:st frame
     * @param nbrOfPlayers
     */
    public void viewPlayerUI(int nbrOfPlayers) {
        if (nbrOfPlayers == 1) {
            ui.playersPnl(1);
        }
        else if(nbrOfPlayers == 2) {
            ui.playersPnl(2);
        } else {
            System.out.println("NO NR!");
        }
    }

    /**
     * Startar nuvarande spelets frame och en panel med namn
     * Anrops efter att klicka på Register
     * @param numOfPlayers Antal spelare som ko
     * @param name1 Namn på player 1
     * @param name2 Namn på player 2
     */
    public void startCurrentGame(int numOfPlayers, String name1, String name2) {
        ui.startCurrentGame(numOfPlayers, name1, name2);
    }

    /**
     * Startar Score panel i nuvarande spelets frame
     * Anrops efter att ta emot highscore listan från Client (genom Server)
     */
    public void showScore() {
        ui.startScorePnl();
    }

    /**
     * Gör det som ska göras i ordning efter att klicka på Register button:
     * Tar emot namn från view
     * Skickar i sin tur namnen till Server (genom Client)
     * Stänger ner namnens frame
     * Startar nästa frame för nuvarande spel
     * @param button Register button
     */
    public void buttonPressed(BtnType button)
    {
       String name1 = null;
       String name2 = null;
       if (button == BtnType.btnRegister){
           int nbrOfPlayers = 0;
           name1 = ui.getName1();
           name2 = ui.getName2();

           if (name2 == null) {
               nbrOfPlayers = 1;
               client.onePlayer(name1);
               //startCurrentGame(nbrOfPlayers, name1, null);
           } else{
               nbrOfPlayers = 2;
               client.twoPlayers(name1, name2);
           }
           close();
           ui.closeSearchPanel();
           resetCurrentGame();
           startCurrentGame(nbrOfPlayers, name1, name2);
       } /*else if(button == BtnType.btnSearch){
           ui.showSearchWin();
       }*/
    }

    private void resetCurrentGame() {
        ui.resetCurrentGame();
    }


    /**
     * Stäger ner första frame efter att ta emot namn
     */
    public void close(){
        frame.dispose();
    }

    /**
     * Skapar en ny frame för att få namn på spelaren
     * @param numOfPlayers antal spelaren som skickas vidare
     *                     som skickas vidare till den metoden
     *                     vilken skapar panels på 1:st frame
     */
    public void showUI(int numOfPlayers){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("Laser-Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1020, 210);
                frame.setLocation(160, 200);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                viewPlayerUI(numOfPlayers);

                frame.add(ui);
                frame.pack();
            }
        });
    }

    public ArrayList<String> getComingPlayerScore(){
        return comingPlayerScore;
    }

    public void saveHighScore(ArrayList<String> playerScore) {

        comingPlayerScore = playerScore;
        DefaultTableModel temp = new DefaultTableModel();
        temp.addColumn("Name");
        temp.addColumn("Score");
        int counter = 0;

        for (int i = 0; i < comingPlayerScore.size(); i+=2) {
            String name = comingPlayerScore.get(counter); //name
            String score = comingPlayerScore.get(counter+1); //score
            temp.addRow(new Object[]{name, score});

            counter = counter + 2;
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ui.getCurrentGameUI().setTableModel(temp);
    }

    public DefaultListModel getModelToUI(){
        DefaultListModel temp = new DefaultListModel();
        for(String s: comingPlayerScore){
            temp.addElement(s);
        }
        return temp;
    }

    public void saveTop10Score(ArrayList<String> tempTop10) {
        top10ScoreList = tempTop10;
    }

    public ArrayList<String> getTop10ScoreList() {
        return top10ScoreList;
    }

    public void sendWinnerToView(int winner) {
       ui.showWinner(winner);

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ui.closeWinnerFrame();
        ui.resetWinner();
        ui.resetUI();
        client.setNumOfPlayers(0);
    }


    public void setNbrOfPlayers(int nbrOfPlayers) {
        this.nbrOfPlayers = nbrOfPlayers;
    }

    public void showScoreInFrame1() {
        ui.showSearchWin();
    }

    public ClientUI getUi() {
        return ui;
    }
    /*public void updateFullScoreList(ArrayList<String> comingPlayerScore) {
        this.comingPlayerScore = comingPlayerScore;
        DefaultTableModel temp = new DefaultTableModel();
        int counter = 0;

        for (int i = 0; i < comingPlayerScore.size(); i+=2) {
                String name = comingPlayerScore.get(counter); //name
                String score = comingPlayerScore.get(counter+1); //score
                temp.addRow(new Object[]{name, score});

            counter = counter + 2;
        }
        ui.getCurrentGameUI().setTableModel(temp);
    } */
}