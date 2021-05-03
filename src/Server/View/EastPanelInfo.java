package Server.View;

import Server.Controller.ServerController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EastPanelInfo extends JPanel {

    private ServerController sController;
    private JPanel pnlSouth;
    private JPanel pnlNorth;
    private JList contentList;
    private JList renameList;

    public EastPanelInfo(ServerController sController){
        this.sController = sController;
        setUpPanel();
    }

    public void setUpPanel() {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(500, 350));



        pnlNorth = new JPanel();
        pnlSouth = new JPanel();

        Border borderNorth = BorderFactory.createTitledBorder("RENAME");
        pnlNorth.setBorder(borderNorth);
        pnlNorth.setPreferredSize(new Dimension(500, 175));

        Border borderSouth = BorderFactory.createTitledBorder("Content");
        pnlSouth.setBorder(borderSouth);
        pnlSouth.setPreferredSize(new Dimension(500, 175));

        contentList = new JList();
        contentList.setPreferredSize(new Dimension(480, 170));
        renameList = new JList();

        String daysList[] = {"Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday"};

        contentList.setListData(daysList);

        pnlSouth.add(contentList);
        pnlNorth.add(renameList);

        JSplitPane splitPane = new JSplitPane(SwingConstants.HORIZONTAL, pnlNorth, pnlSouth);
        add(splitPane);


    }
}
