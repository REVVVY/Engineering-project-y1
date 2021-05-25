package Server.Controller;

import Server.Model.*;
import Server.View.ServerUI;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Klassen ServerController används som mellandhand för Servern och användargränssnitt.
 *
 * @author Isac Pettersson, Johan Skäremo
 * @version 1.0
 */
public class ServerController {

    private Server server;
    private ServerUI gui;

    /**
     * Konstruerar och initialiserar Servern samt användargränssnittet.
     *
     * @param port som används som serverport.
     */
    public ServerController(int port) {
        gui = new ServerUI(this);
        gui.setUpFrame();
        server = new Server(port, this);
    }

    /**
     * Metoden closeConnection används för att stänga serverns anslutning till databasen.
     */
    public void closeConnection() {
        server.closeConnectionToDatabase();
    }

    /**
     * Metoden setContentInView används för att sätta innehållet i logg-objekten i användargränssnittet.
     *
     * @param log listan som skall sättas i användargränssnittet.
     */
    public void setContentInView(ArrayList<String> log) {
        gui.getPnlEast().setContentList(log);
    }

    /**
     * Metoden setInfoInView  används för att sätta allmän info från loggobjekten i användargrännsnittet.
     *
     * @param log listan som skall sättas i användargränssnittet.
     */
    public void setInfoInView(ArrayList<String> log) {
        gui.getPnlEast().setInfoList(log);
    }

    /**
     * Metoden getServerLogToWestPanel används för att sätta själva loggen med logg-objekt i användargränssnittet.
     *
     * @param list listan med loggobjekt som skall sättas.
     */
    public void getServerLogToWestPanel(ArrayList<ServerLog> list) {
        DefaultListModel temp = new DefaultListModel();

        for (ServerLog sl : list) {
            temp.addElement(sl);
        }
        gui.getPnlWest().setListModel(temp);
    }
}
