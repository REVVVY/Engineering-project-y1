package Client.view;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class CurrentGameUI extends JFrame {
    private ClientController controller;
    private ClientUI clientUI;

    private JPanel scoreListPnl = new JPanel();
    private JPanel playersPnl = new JPanel();
    private JList<String> scoreList = new JList<>();
    private JScrollPane scroll = new JScrollPane();

    public CurrentGameUI(ClientController controller, String name1, String name2){
        this.controller = controller;

        openGameUI();
        openPlayersPnl(name1, name2);
        openScorePnl();

    }


    private void openGameUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("Game");
                // setPreferredSize(new Dimension(1140, 750));
                setSize(1140, 750);
                //     getContentPane().setLayout(new BorderLayout(0, 0));
                //setLayout(new BorderLayout());
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // frame.add(ui);
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
            }
        });
    }

    private void openScorePnl() {
        ImageIcon rPic = new ImageIcon("src/Client/view/images/RightPic.png");

        JLabel picLabel2 = new JLabel(rPic);
        // picLabel2.setLayout(new FlowLayout());
        JLabel test = new JLabel("Hello");
        test.setBounds(14, 212, 146, 25);
        test.setLayout(null);
        picLabel2.add(test, BorderLayout.PAGE_START);

        scoreListPnl.setPreferredSize(new Dimension(400, 750));
        scoreListPnl.setLayout(new BorderLayout(0, 0)); //tar bort gaps

        scoreListPnl.add(picLabel2, BorderLayout.CENTER);
        //    scoreListPnl.setBackground(Color.);


        add(scoreListPnl, BorderLayout.EAST);
        pack();
    }

    private void openPlayersPnl(String name1, String name2) {
        ImageIcon lPic = new ImageIcon("src/Client/view/images/LeftPic.png");
        JLabel picLabel2 = new JLabel(lPic);
        //picLabel2.setLayout(new FlowLayout());



        //  String player1 = clientUI.getName1();
        System.out.println(name1);
        JLabel player1Lbl = new JLabel("<html>" +
                "<span style='text-align: center;" +
                "font: normal normal normal 60px/72px Highscore Hero;" +
                "color: #FAECD9;" +
                "opacity: 1;'>"+name1+"</span>" +
                "</html>", SwingConstants.CENTER);

        JLabel player2Lbl = new JLabel("<html>" +
                "<span style='text-align: right;" +
                "font: normal normal normal 60px/72px Highscore Hero;" +
                "color: #FAECD9;" +
                "opacity: 1;'>"+name2+"</span>" +
                "</html>", SwingConstants.CENTER);


        // player1Lbl.setLocation(500, 400);
        player1Lbl.setLayout(null);
        player1Lbl.setBounds(200, 246, 310, 60);
        player2Lbl.setLayout(null);
        player2Lbl.setBounds(200, 430, 310, 60);
        picLabel2.add(player1Lbl, BorderLayout.PAGE_START);
        picLabel2.add(player2Lbl, BorderLayout.PAGE_START);

        playersPnl.setPreferredSize(new Dimension(697, 750));
        playersPnl.setLayout(new BorderLayout(0, 0)); //tar bort gaps
        playersPnl.add(picLabel2, BorderLayout.CENTER);

        add(playersPnl, BorderLayout.WEST);
        pack();
    }

}
