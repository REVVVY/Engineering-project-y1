package Server.View;

import Server.Controller.ServerController;
import Server.Model.ServerLog;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WestPanelLog extends JPanel {

    private ServerController sController;
    private JList logList;
    private DefaultListModel<ServerLog> model;


    public WestPanelLog(ServerController sController){
        this.sController = sController;
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(500, 350));

        Border border = BorderFactory.createTitledBorder("Logg");
        this.setBorder(border);

        logList = new JList();
        model = new DefaultListModel<>();

        logList.setModel(model);

        logList.setPreferredSize(new Dimension(480, 345));
        add(logList);
    }

    public void addElemtent(ServerLog newLog){
        this.model.addElement(newLog);
       // logList.setModel(model);
    }
}
