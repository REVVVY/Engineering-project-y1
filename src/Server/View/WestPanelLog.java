package Server.View;

import Server.Controller.ServerController;
import Server.Model.ServerLog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.concurrent.TimeUnit;

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

            }else if(log.getDescription().equals("Client connect to server")){
                sController.setContentInView(log.getClientConnectString());

            }else if(log.getDescription().equals("Game Sent From Client")){
                sController.setContentInView(log.getGameSentFromClientString());

            }/*else if(log.getDescription().equals("")){
                sController.setContentInView();
            } */

        });
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logList.setModel(model);

       // logList.setPreferredSize(new Dimension(480, 330));
        JScrollPane scroller = new JScrollPane(logList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setPreferredSize(new Dimension(480, 345));

        add(scroller);
       // add(logList);
    }

    public void addElemtent(ServerLog newLog){
        model.addElement(newLog);
        logList.setModel(model);
    }

    public void setListModel(DefaultListModel model){
        this.model = model;
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logList.setModel(model);
    }
}
