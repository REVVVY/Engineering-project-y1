package Server.View;

import Server.Controller.ServerController;
import Server.Model.ServerLog;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class EastPanelInfo extends JPanel {

    private ServerController sController;
    private JPanel pnlSouth;
    private JPanel pnlNorth;
   // private JLabel contentLabel;
    private JList renameList;
    private JList contentList;

    public EastPanelInfo(ServerController sController){
        this.sController = sController;
        setUpPanel();
    }

    public void setUpPanel() {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(500, 350));


        contentList = new JList();
        pnlNorth = new JPanel();
        pnlSouth = new JPanel();

        Border borderNorth = BorderFactory.createTitledBorder("RENAME");
        pnlNorth.setBorder(borderNorth);
        pnlNorth.setPreferredSize(new Dimension(500, 175));

        Border borderSouth = BorderFactory.createTitledBorder("Content");
        pnlSouth.setBorder(borderSouth);
        pnlSouth.setPreferredSize(new Dimension(500, 175));

        contentList.setPreferredSize(new Dimension(490, 170));
        renameList = new JList();



        pnlSouth.add(contentList);
        pnlNorth.add(renameList);

        JSplitPane splitPane = new JSplitPane(SwingConstants.HORIZONTAL, pnlNorth, pnlSouth);
        add(splitPane);

    }

    public void setContentList(ArrayList<String> log){
            String[] array = new String[log.size()];

        for(int i = 0; i < log.size(); i++){
            array[i] = log.get(i);
        }
        contentList.setListData(array);
    }



    /*public JLabel getContentLabel() {
        return contentLabel;
    } */
}
