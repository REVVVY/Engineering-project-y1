package Client.view;

import Client.Controller.ClientController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientUI extends JPanel implements ActionListener{
    private JButton btnRegister = new JButton("Register");
    private JLabel lblTitle = new JLabel("Welcome to our Laser-game!", SwingConstants.CENTER);
    private JLabel lblEnter;
    private JLabel lblPlayer1 = new JLabel("Player 1:");
    private JLabel lblPlayer2 = new JLabel("Player 2:");
    private JTextField tfPlayer1 = new JTextField();
    private JTextField tfPlayer2 = new JTextField();

    private ClientController controller;
    private CurrentGameUI currentGameUI;
    private JPanel centerPanel = new JPanel();

    private ArrayList<Object> highScoreList;
    private int numOfPlayers;
    /** Creates new form UserInput */
    public ClientUI(ClientController controller) {
        this.controller = controller;
        currentGameUI = new CurrentGameUI(controller);
        setLayout(new BorderLayout());


        lblTitle.setFont(currentGameUI.applyFont(40));
        lblTitle.setForeground(Color.decode("#FAECD9"));
        btnRegister.addActionListener(this);

        setBackground(Color.decode("#0F192F"));
        add(lblTitle,BorderLayout.NORTH);
    }


        public JPanel onePlayerPnl(){
        tfPlayer2.setEnabled(false); //not used
        centerPanel.setBackground(Color.decode("#0F192F"));

        lblEnter = new JLabel("Enter your name: ", SwingConstants.LEFT);
        lblEnter.setForeground(Color.decode("#FAECD9"));
        lblEnter.setFont(currentGameUI.applyFont(20));

        tfPlayer1.setHorizontalAlignment(JTextField.LEFT);
        tfPlayer1.setPreferredSize(new Dimension(150,25));
        lblPlayer1.setForeground(Color.decode("#FAECD9"));

        centerPanel.add(lblEnter);
        centerPanel.add(lblPlayer1);
        centerPanel.add(tfPlayer1);

        btnRegister.setFont(new Font("Apple Chancery", Font.BOLD, 15));
        centerPanel.add(btnRegister, BorderLayout.SOUTH);
        return centerPanel;
    }
    public JPanel twoPlayersPnl(){
        centerPanel.setBackground(Color.decode("#0F192F"));

        lblEnter = new JLabel("Enter your names: ", SwingConstants.LEFT);
        lblEnter.setForeground(Color.decode("#FAECD9"));
        lblEnter.setFont(currentGameUI.applyFont(20));

        tfPlayer1.setHorizontalAlignment(JTextField.LEFT);
        tfPlayer2.setHorizontalAlignment(JTextField.LEFT);
        tfPlayer1.setPreferredSize(new Dimension(150,25));
        tfPlayer2.setPreferredSize(new Dimension(150,25));
        lblPlayer1.setForeground(Color.decode("#FAECD9"));
        lblPlayer2.setForeground(Color.decode("#FAECD9"));

        centerPanel.add(lblEnter);
        centerPanel.add(lblPlayer1);
        centerPanel.add(tfPlayer1);
        centerPanel.add(lblPlayer2);
        centerPanel.add(tfPlayer2);

        btnRegister.setFont(new Font("Apple Chancery", Font.BOLD, 15));
        centerPanel.add(btnRegister, BorderLayout.SOUTH);

        return centerPanel;
    }

    /** Gets number of players from controller, and opens its player panel, 1 or 2 */

    public void playersPnl(int numOfPlayers) {
        if (numOfPlayers == 1){
            add(onePlayerPnl(), BorderLayout.CENTER);
        }
        else if(numOfPlayers == 2){
            add(twoPlayersPnl(), BorderLayout.CENTER);
        }
        this.numOfPlayers = numOfPlayers;
    }

    public static void main(String[] args) {
        ClientUI ui = new ClientUI(null);
        ui.playersPnl(2);
        JOptionPane.showMessageDialog( null, ui );
    }

    /**
     * Om "Register button är klickad
     * @param e visar controller att button har blivit klickad
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister)
            controller.buttonPressed(BtnType.btnRegister);
    }

    /**
     * Startar players' panel genom att använda namn och spelaren från controller
     * @param numOfPlayers antal nuvarande spelare
     * @param name1 namn på player 1 som är hämtad från 1:st frame
     * @param name2 namn på player 1 som är hämtad från 1:st frame
     */
    public void startCurrentGame(int numOfPlayers, String name1, String name2){
        currentGameUI.startCurrentGame(numOfPlayers, name1, name2);

        currentGameUI.pack();
        currentGameUI.setVisible(true);
        System.out.println("Started");
    }

    public void startScorePnl(){
        currentGameUI.openScorePnl();
        currentGameUI.pack();
        currentGameUI.setVisible(true);
        System.out.println("showed score");
    }

    public void showSearchWin() {
        currentGameUI.openSearch();
    }

    public String getName1(){
        return tfPlayer1.getText();
    }
    public String getName2(){
        if (tfPlayer2.isEnabled() == false) {
            return null;
        }
        return tfPlayer2.getText();
    }

    public void setHighScoreList(ArrayList<Object> highScoreList) {
        this.highScoreList = highScoreList;
        System.out.println("Size1 " + highScoreList.size());

    }

    public void updateScoreList(ArrayList<Object> comingPlayerScoreObj) {
        highScoreList = comingPlayerScoreObj;
        setHighScoreList(highScoreList);
    }


    public void showWinner(int winner) {
        if (winner != -1) {
            currentGameUI.close();
            currentGameUI.showWinner(winner, getName1(), getName2());
        }
    }
}