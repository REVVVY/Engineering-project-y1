package Client.view;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CurrentGameUI extends JFrame {
    private ClientController controller;
    private ClientUI clientUI;

    private JPanel scoreListPnl = new JPanel();
    private JPanel playersPnl = new JPanel();

    private JLabel scoreList = new JLabel("");
    private Font highScoreFont;

    // private ArrayList<Object> highScoreList;


    public CurrentGameUI(ClientController controller, String name1, String name2, ArrayList<Object> highScoreList){
        this.controller = controller;
        openGameUI();
        openPlayersPnl(name1, name2);
        openScorePnl(highScoreList);
    }

    public CurrentGameUI(){
    }





    private void openGameUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("Game");
                setSize(1167, 670);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setResizable(false);
                setLocationRelativeTo(null);
                setVisible(true);
                pack();
            }
        });
    }



    private void openScorePnl(ArrayList<Object> highScoreList) {
        ImageIcon rPic = new ImageIcon("src/Client/view/images/newRPic.png");
        JLabel picLabel2 = new JLabel(rPic);

       // System.out.println(highScoreList.size());
        JLabel test = new JLabel(" ");
        test.setBounds(14, 212, 146, 25);
        test.setLayout(null);
        picLabel2.add(test, BorderLayout.PAGE_START);

        scoreListPnl.setPreferredSize(new Dimension(470, 670));
        scoreListPnl.setLayout(new BorderLayout(0, 0)); //tar bort gaps

        scoreListPnl.add(picLabel2, BorderLayout.CENTER);
        //    scoreListPnl.setBackground(Color.);


        add(scoreListPnl, BorderLayout.EAST);
        pack();
    }

    private void openPlayersPnl(String name1, String name2) {

        try { //skapar en font
            highScoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")).deriveFont(80f);
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphics.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")));
        } catch (IOException | FontFormatException e){
            System.out.println("There is no such file!");
        }

        ImageIcon lPic;
        JLabel picLabel2;

        if (name2 == null) {
            lPic = new ImageIcon("src/Client/view/images/leftPicOne.png");
            picLabel2 = new JLabel(lPic);
            JLabel player1Lbl = new JLabel(name1);
            player1Lbl.setLayout(null);
            player1Lbl.setBounds(68, 300, 554, 70);
            player1Lbl.setForeground(Color.decode("#FAECD9"));
            player1Lbl.setFont(highScoreFont);
            player1Lbl.setHorizontalAlignment(SwingConstants.CENTER);
            picLabel2.add(player1Lbl, BorderLayout.PAGE_START);
        }
        else {
            lPic = new ImageIcon("src/Client/view/images/lPic.png");
            picLabel2 = new JLabel(lPic);

            JLabel player1Lbl = new JLabel(name1);
            player1Lbl.setLayout(null);
            player1Lbl.setBounds(68, 219, 554, 70);
            player1Lbl.setForeground(Color.decode("#FAECD9"));
            player1Lbl.setFont(highScoreFont);
            player1Lbl.setHorizontalAlignment(SwingConstants.CENTER);
            picLabel2.add(player1Lbl, BorderLayout.PAGE_START);

            JLabel player2Lbl = new JLabel(name2);
            player2Lbl.setLayout(null);
            player2Lbl.setBounds(68, 430, 554, 70);
            player2Lbl.setForeground(Color.decode("#FAECD9"));
            player2Lbl.setFont(highScoreFont);
            player2Lbl.setHorizontalAlignment(SwingConstants.CENTER);
            picLabel2.add(player2Lbl, BorderLayout.PAGE_START);
        }

        playersPnl.setPreferredSize(new Dimension(697, 670));
        playersPnl.setLayout(new BorderLayout(0, 0)); //tar bort gaps
        playersPnl.add(picLabel2, BorderLayout.CENTER);

        add(playersPnl, BorderLayout.WEST);
        pack();
    }
}
