package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class EastPanelInfo extends JPanel {

    private ServerController sController;
    private JPanel pnlSouth;
    private JPanel pnlNorth;
   // private JLabel contentLabel;
    private JList infoList;
    private JList contentList;

    public EastPanelInfo(ServerController sController){
        this.sController = sController;
        setUpPanel();
    }

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

    public void setInfoList(ArrayList<String> log){
        String[] array = new String[log.size()];

        for(int i = 0; i < log.size(); i++){
            array[i] = log.get(i);
        }
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        infoList.setListData(array);
    }

    public void setContentList(ArrayList<String> log){
            String[] array = new String[log.size()];

        for(int i = 0; i < log.size(); i++){
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
