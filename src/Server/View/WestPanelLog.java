package Server.View;

import Server.Controller.ServerController;
import Server.Model.ServerLog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
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
        logList.getSelectionModel().addListSelectionListener(e -> {
            ServerLog log = (ServerLog) logList.getSelectedValue();
            if(log.getDescription().equals("Öppnar UDP Anslutning")){
                sController.setContentInView(log.getUDPanslutningsString());
            }

        });

        logList.setModel(model);

        logList.setPreferredSize(new Dimension(480, 345));
        add(logList);
    }

    public void addElemtent(ServerLog newLog){
        model.addElement(newLog);
        logList.setModel(model);
    }
}
