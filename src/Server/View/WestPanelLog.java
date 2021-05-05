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
        this.setPreferredSize(new Dimension(500, 360));

        Border border = BorderFactory.createTitledBorder("Logg");
        this.setBorder(border);

        logList = new JList();
        model = new DefaultListModel<>();
        logList.getSelectionModel().addListSelectionListener(e -> {
            ServerLog log = (ServerLog) logList.getSelectedValue();
            if(log.getDescription().equals("UPD connection open")){
                sController.setContentInView(log.getUDPanslutningsString());

            }else if(log.getDescription().equals("Client connect to server")){
                sController.setContentInView(log.getClientConnectString());

            }else if(log.getDescription().equals("Game recived from client")){
                sController.setContentInView(log.getGameSentFromClientString());

            }else if(log.getDescription().equals("Updated highscore in database")){
                sController.setContentInView(log.getUpdatedHighscoreInDBString());

            } else if(log.getDescription().equals("Updated games in database")){
                sController.setContentInView(log.getUpdatedGameInDbString());

            } else if(log.getDescription().equals("Connection to database established")){
                sController.setContentInView(log.getDatabaseConnectionString());

            } else if(log.getDescription().equals("Recived highscorelist from database")){
                sController.setContentInView(log.getDatabaseHighscorelistString());

            } else if(log.getDescription().equals("Recived games from database")){
                sController.setContentInView(log.getDatabaseGamesListString());

            } else if(log.getDescription().equals("Sent highscorelist to client")){
                sController.setContentInView(log.getSentHighscoreListToClientString());

            }  else if(log.getDescription().equals("Sent game to client")){
                sController.setContentInView(log.getSentGameToClientString());

            } else if(log.getDescription().equals("Recived score from client")){
                sController.setContentInView(log.getReceivedScoreFromClientString());

            } else if(log.getDescription().equals("Received number of players from client")){
                sController.setContentInView(log.getReceivedNbrOfPlayersFromClientString());
            }

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
