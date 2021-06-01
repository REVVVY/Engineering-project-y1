package Client.view;

import Client.Controller.ClientController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * En gänssnitt klass som hanterar registrering av spelarna
 * Tar hand om kommunikation med CurrentGameUI klassen
 * @author Reem Mohamed
 */
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

    /**
     * Skapar panels om det är en spelare
     * @return centrala panelen
     */
    public JPanel onePlayerPnl(){
        tfPlayer2.setEnabled(false); //not used
        centerPanel.setBackground(Color.decode("#0F192F"));

        lblEnter = new JLabel("Enter your name: ", SwingConstants.LEFT);
        lblEnter.setForeground(Color.decode("#FAECD9"));
        lblEnter.setFont(currentGameUI.applyFont(20));

        tfPlayer1.setHorizontalAlignment(JTextField.LEFT);
        tfPlayer1.setPreferredSize(new Dimension(150,25));
        tfPlayer1.setFont(currentGameUI.applyFont(18));
        lblPlayer1.setForeground(Color.decode("#FAECD9"));
        lblPlayer1.setFont(currentGameUI.applyFont(20));

        centerPanel.add(lblEnter);
        centerPanel.add(lblPlayer1);
        centerPanel.add(tfPlayer1);

        btnRegister.setFont(currentGameUI.applyFont(18));
        centerPanel.add(btnRegister, BorderLayout.SOUTH);
        return centerPanel;
    }
    /**
     * Skapar panels om det är två spelaren
     * @return centrala panelen
     */
    public JPanel twoPlayersPnl(){
        centerPanel.setBackground(Color.decode("#0F192F"));

        lblEnter = new JLabel("Enter your names: ", SwingConstants.LEFT);
        lblEnter.setForeground(Color.decode("#FAECD9"));
        lblEnter.setFont(currentGameUI.applyFont(20));

        tfPlayer1.setHorizontalAlignment(JTextField.LEFT);
        tfPlayer2.setHorizontalAlignment(JTextField.LEFT);
        tfPlayer1.setFont(currentGameUI.applyFont(18));
        tfPlayer2.setFont(currentGameUI.applyFont(18));

        tfPlayer1.setPreferredSize(new Dimension(150,25));
        tfPlayer2.setPreferredSize(new Dimension(150,25));
        lblPlayer1.setForeground(Color.decode("#FAECD9"));
        lblPlayer2.setForeground(Color.decode("#FAECD9"));
        lblPlayer1.setFont(currentGameUI.applyFont(20));
        lblPlayer2.setFont(currentGameUI.applyFont(20));

        centerPanel.add(lblEnter);
        centerPanel.add(lblPlayer1);
        centerPanel.add(tfPlayer1);
        centerPanel.add(lblPlayer2);
        centerPanel.add(tfPlayer2);

        btnRegister.setFont(currentGameUI.applyFont(18));
        centerPanel.add(btnRegister, BorderLayout.SOUTH);

        return centerPanel;
    }

    /**
     * Tar emot antal spelaren från controller klassen
     * Skapar gällande fönster med panel enligt antal spelaren
     */

    public void playersPnl(int numOfPlayers) {
        if (numOfPlayers == 1){
            add(onePlayerPnl(), BorderLayout.CENTER);
        }
        else if(numOfPlayers == 2){
            add(twoPlayersPnl(), BorderLayout.CENTER);
        }
        this.numOfPlayers = numOfPlayers;
    }

    public CurrentGameUI getCurrentGameUI(){
        return currentGameUI;
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
     * Skapar namn på spelarens panel genom att använda namn och spelaren från controller
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

    /**
     * Visar söklistansfönster
     */
    public void showSearchWin() {
        currentGameUI.openSearch();
    }

    /**
     * Returnerar namn på spelare 1
     * @return spelare 1 namn
     */
    public String getName1(){
        return tfPlayer1.getText();
    }
    /**
     * Returnerar namn på spelare 2
     * @return null om det är bara 1 spelare
     * @return spelare 2 namn
     */
    public String getName2(){
        if (tfPlayer2.isEnabled() == false) {
            return null;
        }
        return tfPlayer2.getText();
    }

    /**
     * Stänger ner spelplanen
     * Visar ett nytt vinnarfönster
     * @param winner
     */
    public void showWinner(int winner) {
        if (winner != -1) {
            currentGameUI.close();
            currentGameUI.showWinner(winner, getName1(), getName2());
        }
    }

    /**
     * Stänger ner vinnarfönstret
     */
    public void closeWinnerFrame() {
        currentGameUI.closeWinnerFrame();
    }

    /**
     * Tömmar alla fält på registreringsföntret
     */
    public void resetUI() {
        tfPlayer1.setText("");
        tfPlayer2.setEnabled(true);
        tfPlayer2.setText("");
        centerPanel.removeAll();
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    /**
     * Tömmar spelplanen
     */
    public void resetCurrentGame() {
        currentGameUI.resetCurrentGame();
    }

    /**
     * Tömmar vinnarfönstret
     */
    public void resetWinner() {
        currentGameUI.resetWinner();
    }

    /**
     * Stänger ner fönstret på spelarsöklistan
     */
    public void closeSearchPanel() {
        currentGameUI.closeSearchPanel();
    }
}