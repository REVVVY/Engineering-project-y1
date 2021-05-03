package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class MainPanel {

    private ServerController sController;
    private EastPanelInfo pnlEast;
    private WestPanelLog pnlWest;
    private JPanel mainPanel;

    public MainPanel(ServerController sController){
        this.sController = sController;

        mainPanel = new JPanel();
        setUpPanel();
    }

    public void setUpPanel() {

        BorderLayout layout = new BorderLayout();
        mainPanel.setLayout(layout);

        pnlWest = new WestPanelLog(sController);
        mainPanel.add(pnlWest, BorderLayout.WEST);
        pnlEast = new EastPanelInfo(sController);

    }
}
