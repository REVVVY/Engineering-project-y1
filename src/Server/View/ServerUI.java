package Server.View;

import javax.swing.*;

import Server.Controller.ServerController;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klassen ServerUI används för att skapa en frame som skall representera serverloggen
 *
 * @author Johan Skäremo, Isac Pettersson
 * @version 1.0
 */
public class ServerUI {
    ServerController controller;
    JFrame frame;

    EastPanelInfo pnlEast;
    WestPanelLog pnlWest;

    /**
     * initialiserar instansvariablen för controllern som skall hantera informationen mellan servern och
     * användargränssnittet.
     *
     * @param sController skall hantera informationen från servern.
     */
    public ServerUI(ServerController sController) {
        this.controller = sController;
    }

    /**
     * Metoden setUpFrame används för att konstruera paneler samt sätta dem på en JFrame som representerar
     * serverLoggen
     */
    public void setUpFrame() {
        pnlWest = new WestPanelLog(controller);
        pnlEast = new EastPanelInfo(controller);
        frame = new JFrame("ServerLog");

        frame.setBackground(new Color(255, 255, 255));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProgram();
            }
        });

        frame.setLocation(140, 200);

        frame.add(pnlEast, BorderLayout.EAST);
        frame.add(pnlWest, BorderLayout.WEST);

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(1000, 420);
        //frame.pack();
    }

    /**
     * Metoden exitProgram används för att stänga fönstret som representerar serverLoggen.
     */
    public void exitProgram() {
        frame.dispose();
        controller.closeConnection();
        System.exit(0);
    }

    /**
     * Metoden getPnlEast används för att nå klassen EastPanelInfo från controllern.
     *
     * @return EastPanelInfo som är klassen som hanterar infoPanelen.
     */
    public EastPanelInfo getPnlEast() {
        return pnlEast;
    }

    /**
     * Metoden getPnlWest används för att kunna klassen WestPanelLog från controllern.
     *
     * @return WestPanelLog som är klassen som hanterar serverLoggen.
     */
    public WestPanelLog getPnlWest() {
        return pnlWest;
    }

}