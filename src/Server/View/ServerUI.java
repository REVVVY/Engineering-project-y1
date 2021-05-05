package Server.View;
import javax.swing.*;

import Database.DataConn;
import Server.Controller.ServerController;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ServerUI{
    ServerController controller;
    JFrame frame;

    EastPanelInfo pnlEast;
    WestPanelLog pnlWest;

    public ServerUI(ServerController sController)
    {
        this.controller = sController;
    }

   public void setUpFrame(){
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
       frame.pack();
   }

    public void exitProgram() {
        frame.dispose();
        controller.closeConnection();
        System.exit(0);
    }

    public EastPanelInfo getPnlEast() {
        return pnlEast;
    }

    public WestPanelLog getPnlWest() {
        return pnlWest;
    }

}

