package Client.view;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientUI extends JPanel implements ActionListener {
    private JPanel MainPanel;
    private JPanel TopPanel;
    private JPanel CenterPanel;
    private JTextField WELCOMETOTHEGAMETextField;
    private JTextField pleaseEnterYourNamesTextField;
    private JTextField textField2;
    private JTextField textField3;
    private JButton registerButton;
    private JLabel SecondPlayer;
    private JLabel FirstPlayer;
    private ImageIcon icon;

    private JPanel panel = new JPanel();
    private ClientController controller;
    private CurrentGameUI currentGameUI;

    private ArrayList<Object> highScoreList;
    private int numOfPlayers;

    public ClientUI() {}

    public ClientUI(ClientController ctr) {
        controller = ctr;
        currentGameUI = new CurrentGameUI(ctr);
        registerButton.addActionListener(this);
    }

    public String getName1(){
        return textField2.getText().trim();
    }
    public String getName2(){
        return textField3.getText().trim();
    }

    public static void main(String[] args){
        ClientUI ui = new ClientUI();

        JOptionPane.showMessageDialog(null, ui);
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public JLabel getSecondPlayer() {
        return SecondPlayer;
    }

    public JTextField getSecondPlayerFieldText() {
        return textField3;
    }

    public void clearFields() {
        textField2.setText("");
        textField3.setText("");
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

    public void startScorePnl(ArrayList<String> comingPlayerScore){
        currentGameUI.openScorePnl(comingPlayerScore);
        currentGameUI.pack();
        currentGameUI.setVisible(true);
        System.out.println("showed score");
    }


    public void setHighScoreList(ArrayList<Object> highScoreList) {
        this.highScoreList = highScoreList;
        System.out.println("Size1 " + highScoreList.size());
    }

    public void updateScoreList(ArrayList<Object> comingPlayerScoreObj) {
        highScoreList = comingPlayerScoreObj;
        setHighScoreList(highScoreList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "Register") {
            controller.sendPlayers(getName1(), getName2());
            clearFields();
        }
    }
}
