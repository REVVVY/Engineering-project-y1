package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Klassen EastPanelInfo används för att hantera när man klickar på ett log-objekt så det visas
 * info och innehåll av loggen i användargränssnittet.
 *
 * @author Isac Pettersson, Johan Skäremo
 * @version 1.0
 */
public class EastPanelInfo extends JPanel {

    private ServerController sController;
    private JPanel pnlSouth;
    private JPanel pnlNorth;
    private JList infoList;
    private JList contentList;

    /**
     * Konstruerar och initialiserar instansvariabeln controller och sätter upp panelen.
     *
     * @param sController som används för att kunna kommunicera med servern via controllern.
     */
    public EastPanelInfo(ServerController sController) {
        this.sController = sController;
        setUpPanel();
    }

    /**
     * Metod som sätter upp panelen med alla komponenter och design.
     */
    public void setUpPanel() {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(500, 350));


        contentList = new JList();
        infoList = new JList();
        pnlNorth = new JPanel();
        pnlSouth = new JPanel();

        Border borderNorth = BorderFactory.createTitledBorder("Info");
        pnlNorth.setBorder(borderNorth);
        pnlNorth.setPreferredSize(new Dimension(500, 175));

        Border borderSouth = BorderFactory.createTitledBorder("Content");
        pnlSouth.setBorder(borderSouth);
        pnlSouth.setPreferredSize(new Dimension(500, 185));


        JScrollPane scrollerInfo = new JScrollPane(infoList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollerInfo.setPreferredSize(new Dimension(490, 160));

        JScrollPane scrollerContent = new JScrollPane(contentList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollerContent.setPreferredSize(new Dimension(490, 160));

        pnlSouth.add(scrollerContent);
        pnlNorth.add(scrollerInfo);

        JSplitPane splitPane = new JSplitPane(SwingConstants.HORIZONTAL, pnlNorth, pnlSouth);
        add(splitPane);
    }

    /**
     * Metod som sätter infolistan med det valda log-objekets infomation.
     *
     * @param log arraylista som innehåller rätt information för det valdra objekt som ska visas i användargränssnittet.
     */
    public void setInfoList(ArrayList<String> log) {
        String[] array = new String[log.size()];

        for (int i = 0; i < log.size(); i++) {
            array[i] = log.get(i);
        }
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        infoList.setListData(array);
    }

    /**
     * Metod som sätter händelsens innehåll på det valda log-objeket i användargränssnittet.
     *
     * @param log arraylista med allt innehåll som ska visas i listan.
     */
    public void setContentList(ArrayList<String> log) {
        String[] array = new String[log.size()];

        for (int i = 0; i < log.size(); i++) {
            array[i] = log.get(i);
        }
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        contentList.setListData(array);
    }
}
