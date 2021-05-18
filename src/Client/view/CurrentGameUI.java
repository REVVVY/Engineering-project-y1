package Client.view;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CurrentGameUI extends JFrame implements ActionListener {
    private ClientController controller;
    private ClientUI clientUI;

    private JPanel scoreListPnl = new JPanel();
    private JPanel playersPnl = new JPanel();

    private JLabel scoreList = new JLabel("");
    private JButton btnSearch =  new JButton("Search");
    public CurrentGameUI(ClientController controller){
        this.controller = controller;
    }

    public void startCurrentGame(int numOfPlayers, String name1, String name2){
        openGameUI();
        openPlayersPnl(numOfPlayers, name1, name2);
    }


    public void openGameUI() {
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

    public void openSearch(){
        Font highScoreFont = null;

        try {
            highScoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")).deriveFont(22f);
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphics.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")));
        } catch (IOException | FontFormatException e){
            System.out.println("There is no such file!");
        }

        ArrayList<String> tempHighScore = controller.getComingPlayerScore();
        /*
        Reem
        17
        Johan
        4
         */

        JFrame searchFrame = new JFrame("Search Score");
        searchFrame.setSize(470, 670);

        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchFrame.setResizable(true);
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setVisible(true);
       // searchFrame.pack();

        JPanel searchPnl = new JPanel();
        searchPnl.setPreferredSize(new Dimension(470, 670));


        JLabel searchLbl = new JLabel("Search your name: ");
        searchLbl.setBounds(37, 27, 330, 31);
        searchLbl.setFont(highScoreFont);
        searchLbl.setForeground(Color.decode("#FAECD9"));

        JTextField searchFld = new JTextField();
        searchFld.setBounds(37, 64, 404, 52);


       // JList highScoreList = new JList();
        int arrayLength = tempHighScore.size()/2;
        String[] columnName = {"Name", "Score"};
        String[][] data = new String[arrayLength][arrayLength];
        int counter = 0;

        for (int i = 0; i < tempHighScore.size(); i++) {
            for (int j = 0; j < tempHighScore.size(); j++) {
                if (i < arrayLength){
                    data[i][0] = tempHighScore.get(counter); //name
                    data[i][1] = tempHighScore.get(counter+1); //score

                }
            }
            counter = counter + 2;
        }

        JTable table = new JTable(data, columnName);
        table.setPreferredScrollableViewportSize(new Dimension(411, 500));
        table.setFillsViewportHeight(true);

       // table.setBounds(30, 136, 400, 1000);

        JScrollPane scroller = new JScrollPane(table);


        /*
        DefaultListModel model = controller.getModelToUI();
        highScoreList.setModel(model);
        JScrollPane scroller = new JScrollPane(highScoreList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
*/



        System.out.println("TEMP "+ tempHighScore.get(1));


       // highScoreList.setListData(tempHighScore.toArray(new String[tempHighScore.size()/2]));
/*
        highScoreList.setBounds(30, 136, 411, 514);
        highScoreList.setBackground(Color.decode("#C07C73"));
        highScoreList.setForeground(Color.decode("#FAECD9"));
        highScoreList.setSelectionBackground(Color.decode("#0F192F"));
        highScoreList.setFont(highScoreFont);

*/
        searchPnl.add(searchLbl);
        searchPnl.add(searchFld);
        searchPnl.add(scroller);

        searchFrame.add(searchPnl);
    }

    /**
     * Skapar en panel för att visa highscore listan
     * Använder StringUtils för att ha specifik text-align
     * @param comingPlayerScore high score listan från Server --> Controller
     */
    public void openScorePnl(ArrayList<String> comingPlayerScore) {
        Font highScoreFont = null;
        System.out.println("UI: " + comingPlayerScore.size());
        try {
            highScoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")).deriveFont(22f);
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphics.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")));
        } catch (IOException | FontFormatException e){
            System.out.println("There is no such file!");
        }




        ImageIcon rPic = new ImageIcon("src/Client/view/images/RightPic.png");
        JLabel picLabel2 = new JLabel(rPic);
        JList list = new JList();
        ArrayList<String> tempScoreList = new ArrayList<>(comingPlayerScore.size());

        for (int i = 0; i < comingPlayerScore.size(); i+=2) {
            String name  = comingPlayerScore.get(i);
            String score = comingPlayerScore.get(i+1);

            StringAlignUtils utilLift = new StringAlignUtils(8, StringAlignUtils.Alignment.LEFT);
            StringAlignUtils utilLiftNbr = new StringAlignUtils(3, StringAlignUtils.Alignment.LEFT);
            StringAlignUtils utilRight = new StringAlignUtils(13, StringAlignUtils.Alignment.RIGHT);


            int[] numberOrder = new int[]{1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8, 0, 9, 0, 10};

            if (name.length() > 7) {
                utilRight = new StringAlignUtils(5, StringAlignUtils.Alignment.RIGHT);
            }
            String player = String.format(utilLiftNbr.format(String.valueOf(numberOrder[i])) +
                    utilLift.format(name) + utilRight.format(score));
            tempScoreList.add(player);
            tempScoreList.add("\n");
        }

        System.out.println("TEMP "+ tempScoreList.get(1));


        list.setListData(tempScoreList.toArray(new String[10]));

        list.setBounds(15, 169, 411, 490);
        list.setBackground(Color.decode("#C07C73"));
        list.setForeground(Color.decode("#FAECD9"));
        list.setSelectionBackground(Color.decode("#0F192F"));
        list.setFont(highScoreFont);



        picLabel2.add(list, BorderLayout.CENTER);

            scoreListPnl.setPreferredSize(new Dimension(470, 670));
            scoreListPnl.setLayout(new BorderLayout(0, 0)); //tar bort gaps

            scoreListPnl.add(picLabel2, BorderLayout.CENTER);

            add(scoreListPnl, BorderLayout.EAST);
            pack();
    }

    /**
     * Skapar players' panel enligt antal nuvarande spelare
     * @param numOfPlayers antal nuvarande spelare
     * @param name1 namn på player 1 som är hämtad från 1:st frame
     * @param name2 namn på player 1 som är hämtad från 1:st frame
     */

    public void openPlayersPnl(int numOfPlayers, String name1, String name2) {

        Font highScoreFont = null;
        try { //skapar en font
            highScoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")).deriveFont(80f);
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphics.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")));
        } catch (IOException | FontFormatException e){
            System.out.println("There is no such file!");
        }

        Font btnFont = null;
        try { //skapar en font
            btnFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")).deriveFont(40f);
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphics.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")));
        } catch (IOException | FontFormatException e){
            System.out.println("There is no such file!");
        }

        ImageIcon lPic;
        JLabel picLabel2;

        if (numOfPlayers == 1) {
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
            lPic = new ImageIcon("src/Client/view/images/leftPic.png");
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

        btnSearch.setBounds(396, 25, 263, 47);
        btnSearch.setBackground(Color.decode("#C07C73"));
        btnSearch.setForeground(Color.decode("#FAECD9"));
        btnSearch.setFont(btnFont);
        btnSearch.addActionListener(this);
        picLabel2.add(btnSearch);

        playersPnl.setPreferredSize(new Dimension(697, 670));
        playersPnl.setLayout(new BorderLayout(0, 0)); //tar bort gaps
        playersPnl.add(picLabel2, BorderLayout.CENTER);

        add(playersPnl, BorderLayout.WEST);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openSearch();
    }
}


