package Client.view;

import Client.Controller.ClientController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CurrentGameUI extends JFrame implements ActionListener {
    private ClientController controller;
    private ClientUI clientUI;
    private DefaultTableModel tableModel;
    private ArrayList<String> fullScoreList = new ArrayList<>();

    private JPanel scoreListPnl = new JPanel();
    private JPanel playersPnl = new JPanel();
    private JPanel winnerPnl;
    private JLabel scoreList = new JLabel("");
    private JButton btnSearch =  new JButton("Search Score");
    private JTable searchTable = new JTable();
    private JFrame winnerFrame;
    private JTextField searchFld = new JTextField();
    private JFrame searchFrame;

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

    public void setFullScoreList(ArrayList<String> fullScoreList) {
        this.fullScoreList = fullScoreList;
    }

    public void openSearch(){

        fullScoreList = controller.getComingPlayerScore();

        searchFrame = new JFrame("Search Score");
        searchFrame.setSize(470, 670);

        searchFrame.setLocation(280, 200);
        searchFrame.setResizable(true);
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setVisible(true);
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel searchPnl = new JPanel();
        searchPnl.setPreferredSize(new Dimension(470, 670));

        int arrayLength = fullScoreList.size()/2;
        int counter = 0;

        tableModel = new DefaultTableModel();
        searchTable = new JTable(tableModel);
        tableModel.addColumn("Name");
        tableModel.addColumn("score");


        for (int i = 0; i < fullScoreList.size(); i++) {
                if (i < arrayLength){
                    String name = fullScoreList.get(counter); //name
                    String score = fullScoreList.get(counter+1); //score
                    tableModel.addRow(new Object[]{name, score});
            }
            counter = counter + 2;
        }


        searchTable.setRowHeight(25);
        searchTable.setPreferredScrollableViewportSize(new Dimension(411, 500));
        searchTable.setFillsViewportHeight(true);
        searchTable.setDefaultEditor(Object.class, null);
        searchTable.setVisible(true);
        searchTable.setBackground(Color.decode("#C07C73"));
        searchTable.setForeground(Color.decode("#FAECD9"));
        searchTable.setSelectionBackground(Color.decode("#0F192F"));
        searchTable.setFont(applyFont(22));

        JScrollPane scroller = new JScrollPane(searchTable);

        JLabel searchLbl = new JLabel("Search your name: ");
        searchLbl.setBounds(37, 27, 330, 31);
        searchLbl.setFont(applyFont(22));
        searchLbl.setForeground(Color.decode("#FAECD9"));

        searchFld.setPreferredSize(new Dimension(226, 43));
        searchFld.setEnabled(true);
        searchFld.setVisible(true);
        searchFld.setHorizontalAlignment(JTextField.CENTER);
        searchFld.setFont(applyFont(30));
        searchFld.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        searchPnl.setBackground(Color.decode("#0F192F"));
        searchPnl.add(searchLbl);
        searchPnl.add(searchFld);
        searchPnl.add(scroller);

        searchFrame.add(searchPnl);
        searchFrame.pack();
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        searchTable.setModel(tableModel);
    }

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        DefaultTableModel table = (DefaultTableModel) searchTable.getModel();
        String search = searchFld.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
        searchTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + search));
    }

    /**
     * Skapar en panel för att visa highscore listan
     * Använder StringUtils för att ha specifik text-align
     */
    public void openScorePnl() {
        ImageIcon rPic = new ImageIcon("src/Client/view/images/RightPic.png");
        JLabel picLabel2 = new JLabel(rPic);
        JList list = new JList();
        ArrayList<String> comingPlayerScore =  controller.getTop10ScoreList();
        ArrayList<String> tempScoreList = new ArrayList<>(comingPlayerScore.size()/2);

        int numberOrder = 1;
        for (int i = 0; i < comingPlayerScore.size(); i+=2) {
            String name  = comingPlayerScore.get(i);
            String score = comingPlayerScore.get(i+1);

            StringAlignUtils utilLift = new StringAlignUtils(8, StringAlignUtils.Alignment.LEFT);
            StringAlignUtils utilLiftNbr = new StringAlignUtils(3, StringAlignUtils.Alignment.LEFT);
            StringAlignUtils utilRight = new StringAlignUtils(13, StringAlignUtils.Alignment.RIGHT);

            if (name.length() > 9) {
                utilRight = new StringAlignUtils(5, StringAlignUtils.Alignment.RIGHT);
            }

            String player = String.format(utilLiftNbr.format(String.valueOf(numberOrder))
                    + utilLift.format(name) + utilRight.format(score));

            tempScoreList.add(player);
            numberOrder++;
       //     tempScoreList.trimToSize();
        }

        list.setFixedCellHeight(48);
        list.setListData(tempScoreList.toArray(new String[10]));
        list.setBounds(15, 169, 411, 490);
        list.setBackground(Color.decode("#C07C73"));
        list.setForeground(Color.decode("#FAECD9"));
        list.setSelectionBackground(Color.decode("#0F192F"));
        list.setFont(applyFont(22));



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

        ImageIcon lPic;
        JLabel picLabel2;

        if (numOfPlayers == 1) {
            lPic = new ImageIcon("src/Client/view/images/leftPicOne.png");
            picLabel2 = new JLabel(lPic);
            JLabel player1Lbl = new JLabel(name1);
            player1Lbl.setLayout(null);
            player1Lbl.setBounds(68, 300, 554, 70);
            player1Lbl.setForeground(Color.decode("#FAECD9"));
            player1Lbl.setFont(applyFont(80));
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
            player1Lbl.setFont(applyFont(80));
            player1Lbl.setHorizontalAlignment(SwingConstants.CENTER);
            picLabel2.add(player1Lbl, BorderLayout.PAGE_START);

            JLabel player2Lbl = new JLabel(name2);
            player2Lbl.setLayout(null);
            player2Lbl.setBounds(68, 430, 554, 70);
            player2Lbl.setForeground(Color.decode("#FAECD9"));
            player2Lbl.setFont(applyFont(80));
            player2Lbl.setHorizontalAlignment(SwingConstants.CENTER);
            picLabel2.add(player2Lbl, BorderLayout.PAGE_START);
        }

        btnSearch.setBounds(396, 30, 244, 35);

        btnSearch.setFont(applyFont(30));
        btnSearch.setBorder(null);
        btnSearch.setOpaque(true);
        btnSearch.setBackground(Color.decode("#C07C73"));
        btnSearch.setForeground(Color.decode("#FAECD9"));
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
        if (e.getSource() != null) {
            btnSearch.setBackground(Color.decode("#A2786F"));
        }
    }

    public Font applyFont(float fontSize){
        Font highScoreFontMethod = null;
        try {
            highScoreFontMethod = Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")).deriveFont((float) fontSize);
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphics.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Client/view/customFonts/HighscoreHero.ttf")));
        } catch (IOException | FontFormatException e){
            System.out.println("There is no such file!");
        }
        return highScoreFontMethod;
    }

    public void close() {
        dispose();
        setVisible(false);
    }

    public void closeWinnerFrame(){
        winnerFrame.dispose();
        winnerFrame.setVisible(false);
    }

    public void showWinner(int winner, String name1, String name2) {
        ImageIcon lPic = new ImageIcon("src/Client/view/images/LeftPicOne.png");
        JLabel picLabel2 = new JLabel(lPic);

        winnerFrame = new JFrame("Winner");
        winnerFrame.setSize(697, 670);
        winnerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        winnerFrame.setResizable(false);
        winnerFrame.setLocationRelativeTo(null);
        winnerFrame.setVisible(true);
        winnerPnl = new JPanel();
        JLabel winnerLbl = new JLabel();

        winnerLbl.setFont(applyFont(80));
        winnerLbl.setForeground(Color.decode("#FAECD9"));
        if (winner == 0) {
            winnerLbl = new JLabel("<html>"+"Ended in a draw!!"+"</html>");
            //  winnerLbl.setText("Ended in a draw!!");
        } else if (winner == 1) {
            String txt = name1 + " won!!";
            winnerLbl = new JLabel("<html>"+txt+"</html>");
        } else if (winner == 2) {
            String txt = name2 + " won!!";
            winnerLbl = new JLabel("<html>"+txt+"</html>");
        }

        winnerLbl.setLayout(null);
        winnerLbl.setBounds(69, 250, 554, 200);
        winnerLbl.setForeground(Color.decode("#FAECD9"));
        winnerLbl.setFont(applyFont(80));

        picLabel2.add(winnerLbl);

        winnerPnl.setPreferredSize(new Dimension(697, 670));
        winnerPnl.setLayout(new BorderLayout(0, 0)); //tar bort gaps
        winnerPnl.add(picLabel2);
        winnerFrame.add(winnerPnl);

    }

    public void resetCurrentGame() {
        scoreListPnl.removeAll();
        scoreListPnl.revalidate();
        scoreListPnl.repaint();

        playersPnl.removeAll();
        playersPnl.revalidate();
        playersPnl.repaint();
    }

    public void resetWinner(){
        winnerPnl.removeAll();
        winnerPnl.revalidate();
        winnerPnl.repaint();

    }

    public void closeSearchPanel() {
        searchFrame.dispose();
        searchFrame.setVisible(false);
    }
}


