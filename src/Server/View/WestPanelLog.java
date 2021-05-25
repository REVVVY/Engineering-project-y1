package Server.View;

import Server.Controller.ServerController;
import Server.Model.ServerLog;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Klassen WestPanelLog används för att lägga till de nya log-objekten på listan med alla loggade händelser
 *
 * @author Isac Pettersson, Johan Skäremo
 * @version 1.0
 */
public class WestPanelLog extends JPanel {

    private ServerController sController;
    private JList logList;
    private DefaultListModel<ServerLog> model;

    /**
     * Konstruerar och initialiserar instansvariabler och sätter JListen med listmodeln och uppdaterar den ifrån
     * vad användaren klickar på i EastPanelInfo.
     *
     * @param sController för att kunna anropa metoder i controllerna för att kunna kommunicera med servern.
     */
    public WestPanelLog(ServerController sController) {
        this.sController = sController;
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(500, 360));

        Border border = BorderFactory.createTitledBorder("Logg");
        this.setBorder(border);

        logList = new JList();
        model = new DefaultListModel<>();
        logList.getSelectionModel().addListSelectionListener(e -> {
            ServerLog log = (ServerLog) logList.getSelectedValue();
            if (log.getDescription().equals("UPD connection open")) {
                sController.setContentInView(log.getUDPanslutningsString());
                sController.setInfoInView(log.getUDPConnectionInfo());

            } else if (log.getDescription().equals("Client connected to server")) {
                sController.setContentInView(log.getClientConnectString());
                sController.setInfoInView(log.getClientConnectedInfo());

            } else if (log.getDescription().equals("Game recived from client")) {
                sController.setContentInView(log.getGameSentFromClientString());
                sController.setInfoInView(log.getGameRecivedInfo());

            } else if (log.getDescription().equals("Updated highscore in database")) {
                sController.setContentInView(log.getUpdatedHighscoreInDBString());
                sController.setInfoInView(log.getUpdatedHighscoreInDBInfo());

            } else if (log.getDescription().equals("Updated games in database")) {
                sController.setContentInView(log.getUpdatedGameInDbString());
                sController.setInfoInView(log.getUpdatedGamesInDBInfo());

            } else if (log.getDescription().equals("Connection to database established")) {
                sController.setContentInView(log.getDatabaseConnectionString());
                sController.setInfoInView(log.getConnectedToDBInfo());

            } else if (log.getDescription().equals("Recived highscorelist from database")) {
                sController.setContentInView(log.getDatabaseHighscorelistString());
                sController.setInfoInView(log.getRecievedHighscorelistFromDbInfo());

            } else if (log.getDescription().equals("Recived games from database")) {
                sController.setContentInView(log.getDatabaseGamesListString());
                sController.setInfoInView(log.getReceivedGamesFromDbInfo());

            } else if (log.getDescription().equals("Sent highscorelist to client")) {
                sController.setContentInView(log.getSentHighscoreListToClientString());
                sController.setInfoInView(log.getSentHighscoreToClientInfo());

            } else if (log.getDescription().equals("Sent game to client")) {
                sController.setContentInView(log.getSentGameToClientString());
                sController.setInfoInView(log.getSentGameToClientInfo());

            } else if (log.getDescription().equals("Recived score from client")) {
                sController.setContentInView(log.getReceivedScoreFromClientString());
                sController.setInfoInView(log.getScoreFromClientInfo());

            } else if (log.getDescription().equals("Received number of players from client")) {
                sController.setContentInView(log.getReceivedNbrOfPlayersFromClientString());
                sController.setInfoInView(log.getNbrOfPlayersRecievedInfo());

            } else if (log.getDescription().equals("Sent number of players to client")) {
                sController.setContentInView(log.getSentNumOfPlayersToClientString());
                sController.setInfoInView(log.getNbrOfPlayersSentInfo());
            }

        });
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logList.setModel(model);

        JScrollPane scroller = new JScrollPane(logList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setPreferredSize(new Dimension(480, 345));

        add(scroller);

    }

    /**
     * Metod som sätter en ny listmodel på listan för att uppdatera då det kommer nytt log-objekt.
     *
     * @param model är defaultListModeln som ska sättas i JListen med nya element som tillkommit.
     */
    public void setListModel(DefaultListModel model) {
        this.model = model;
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logList.setModel(model);
    }
}
