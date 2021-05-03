package Server.View;
import javax.swing.*;
import Server.Controller.ServerController;

import java.awt.*;
import java.util.ArrayList;

public class ServerUI{
    ServerController controller;
    JFrame frame;
    EastPanelInfo pnlEast;
    WestPanelLog pnlWest;
    public ServerUI(ServerController sController)
    {
        this.controller = sController;
        pnlWest = new WestPanelLog(controller);
        pnlEast = new EastPanelInfo(controller);
        frame = new JFrame("ServerLog");

        frame.setBackground(new Color(255, 255, 255));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(140,200);

        frame.add(pnlEast, BorderLayout.EAST);
        frame.add(pnlWest, BorderLayout.WEST);

        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
    }

}

