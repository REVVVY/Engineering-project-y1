package Client.Controller;

import Client.Model.Client;
import Client.view.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientController {

    private Client client;
    private ClientUI ui = new ClientUI(this);

    public ClientController(String ip, int port) {

        this.client = new Client(ip, port, this);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showUI();
    }

    private void viewPlayerUI() {
        int controlNbrOfPlayers = controlNbrOfPlayers();

        if (controlNbrOfPlayers == 1) {
            ui.getSecondPlayer().removeAll();
            ui.getSecondPlayerFieldText().removeAll();
            ui.validate();
            ui.repaint();

        } else if (controlNbrOfPlayers == 2) {
            ui.getSecondPlayer().removeAll();
            ui.getSecondPlayerFieldText().removeAll();
            ui.validate();
            ui.repaint();
        }
    }

    public int controlNbrOfPlayers() {
        int numPlayers = 0;
        numPlayers = client.numOfPlayers();
        return numPlayers;
    }

    public void sendPlayers(String name1, String name2) {
        try {
            int controlNbrOfPlayers = controlNbrOfPlayers();
            if (controlNbrOfPlayers == 1) {
                client.onePlayer(name1);
            } else if (controlNbrOfPlayers == 2) {
                client.twoPlayers(name1, name2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void printScoreboard(String name1, String name2) {
        try {
            client.createScoreboard();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void newResponse(String response) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // ui.setResult(response);
            }
        });
    }

    public String getFirstPlayer() {
        if (new ClientUI().getName1().isEmpty() || new ClientUI().getName1().equals(null)) {
            return "NoName";
        }
        return new ClientUI().getName1();
    }

    public String getSecondPlayer() {
        if (new ClientUI().getName2().isEmpty() || new ClientUI().getName2().equals("")) {
            return "NoName";
        }
        return new ClientUI().getName2();
    }

    private void showUI() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Laser Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                ui.getSecondPlayer().removeAll();
                ui.getSecondPlayerFieldText().removeAll();
                ui.validate();
                ui.repaint();
                frame.setContentPane(ui.getMainPanel());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setSize(650,300);
                frame.setPreferredSize(new Dimension(50, 50));
            }
        });
    }
}
