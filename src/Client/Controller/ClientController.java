package Client.Controller;

import Client.Controller.*;
import Client.Model.Client;
import Client.view.*;
import javax.swing.*;
import java.io.IOException;

public class ClientController {

    private Client client;
    private ClientUI ui = new ClientUI(this);


    public ClientController(Client client) {
        this.client = client;

        client.setController(this);
        controlNbrOfPlayers();
        viewPlayerUI();
        showUI();
        client.getScoreFromServer();
        printScoreboard(getFirstPlayer(), getSecondPlayer());
    }

    private void viewPlayerUI() {
        if (controlNbrOfPlayers() == 1) {
            ui.playersPnl(1);
        }
        else if(controlNbrOfPlayers() == 2) {
            ui.playersPnl(2);
        }

    }

    public int controlNbrOfPlayers(){
        int numPlayers = 0;
        try {
            numPlayers = client.numOfPlayers();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return numPlayers;
    }

    public void printScoreboard(String name1, String name2) {
        try {
            client.createScoreboard();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
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
        if (ui.getName1() == null) {
            return "";
        }
        return ui.getName1();
    }

    public String getSecondPlayer(){
        if (ui.getName2() == null) {
            return "";
        }
        return ui.getName2();
    }
    private void showUI(){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Client");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(ui);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
