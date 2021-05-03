package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WestPanelLog extends JPanel {

    private ServerController sController;
    private JList logList;

    public WestPanelLog(ServerController sController){
        this.sController = sController;
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(500, 350));

        Border border = BorderFactory.createTitledBorder("Logg");
        this.setBorder(border);

        logList = new JList();
    }
}
