package Client.Controller;

import Client.Model.Client;
import Client.Model.Player;
import Client.view.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClientController {

    private Client client;
    private ClientUI ui = new ClientUI(this);
    private JFrame frame;
    private ArrayList<Object> comingPlayerScore;

    public ClientController(String ip, int port) {

        this.client = new Client(ip, port, this);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showUI();
        viewPlayerUI();
        //client.getScoreFromServer();
        //printScoreboard(getFirstPlayer(), getSecondPlayer());
    }

    private void viewPlayerUI() {
        int controlNbrOfPlayers = controlNbrOfPlayers();
        if (controlNbrOfPlayers == 1) {
            ui.playersPnl(1);
        }
        else if(controlNbrOfPlayers == 2) {
            ui.playersPnl(2);
        }
    }

    public int controlNbrOfPlayers(){
        int numPlayers = 0;
        numPlayers = client.numOfPlayers();
        return numPlayers;
    }

    public void sendPlayers(String name1, String name2) {
        try {
            int controlNbrOfPlayers = controlNbrOfPlayers();
            if (controlNbrOfPlayers == 1) {
                client.onePlayer(name1);
            }
            else if(controlNbrOfPlayers == 2) {
                client.twoPlayers(name1, name2);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Uppdaterar highscore listan och skickar den vidare till view
     * @param playerScore players' name & score coming from client
     */
    public void printScoreboard(ArrayList<Player> playerScore) {
            comingPlayerScore = new ArrayList<>();
        for (Player p : playerScore) {
            comingPlayerScore.add(p.getName());
            comingPlayerScore.add(p.getScore());
        }
        ui.updateScoreList(comingPlayerScore);
    }



    public void newResponse(String response) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.setResult(response);
            }
        });
    }
    public String getFirstPlayer(){
        if (ui.getName1().isEmpty() || ui.getName1().equals(null)) {
            return "NoName";
        }
        return ui.getName1();
    }

    public String getSecondPlayer(){
        if (ui.getName2().isEmpty() || ui.getName2().equals("")) {
            return "NoName";
        }
        return ui.getName2();
    }

    public void close(){
        frame.dispose();
    }

    private void showUI(){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("Laser-Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(ui);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                viewPlayerUI();
            }
        });
    }
}