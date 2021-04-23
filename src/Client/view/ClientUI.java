package Client.view;

import Client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI extends JPanel  implements ActionListener{
    private JButton btnRegister = new JButton("Register");
    private JLabel lblTitle = new JLabel("Welcome to our Laser-game!", SwingConstants.CENTER);
    private JTextArea taResult = new JTextArea("Score: ");
    private JLabel lblPlayer1 = new JLabel("Player 1:");
    private JLabel lblPlayer2 = new JLabel("player 2:");
    private JTextField tfPlayer1 = new JTextField();
    private JTextField tfPlayer2 = new JTextField();

    private ClientController controller;

    private JPanel centerPanel = new JPanel();

    /** Creates new form UserInput */
    public ClientUI(ClientController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());


        lblTitle.setFont(new Font("Apple Chancery", Font.BOLD, 24));
        taResult.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        taResult.setPreferredSize(new Dimension(400,40));
        btnRegister.addActionListener(this);

        add(lblTitle,BorderLayout.NORTH);
        add(taResult, BorderLayout.SOUTH);

    }

    public JPanel onePlayerPnl(){
        centerPanel.setBorder(BorderFactory.createTitledBorder("Enter your name: "));
        tfPlayer1.setHorizontalAlignment(JTextField.LEFT);
        tfPlayer1.setPreferredSize(new Dimension(150,25));
        centerPanel.add(lblPlayer1);
        centerPanel.add(tfPlayer1);

        btnRegister.setFont(new Font("Apple Chancery", Font.BOLD, 15));
        centerPanel.add(btnRegister, BorderLayout.SOUTH);
        return centerPanel;
    }
    public JPanel twoPlayersPnl(){
        centerPanel.setBorder(BorderFactory.createTitledBorder("Enter your names: "));
        tfPlayer1.setHorizontalAlignment(JTextField.LEFT);
        tfPlayer2.setHorizontalAlignment(JTextField.LEFT);
        tfPlayer1.setPreferredSize(new Dimension(150,25));
        tfPlayer2.setPreferredSize(new Dimension(150,25));
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
        JPanel panel = new JPanel(new GridLayout(2,5));

        if (numOfPlayers == 1){
            add(onePlayerPnl(), BorderLayout.CENTER);
        }
        else if(numOfPlayers == 2){
            add(twoPlayersPnl(), BorderLayout.CENTER);
        }

    }

    public void setResult(String result) {
      //  controller.printScoreboard(tfPlayer1.getText(), tfPlayer2.getText());

        taResult.setText(result);
    }
    public static void main(String[] args) {
        ClientUI ui = new ClientUI(null);
        ui.playersPnl(2);
     //   ui.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JOptionPane.showMessageDialog( null, ui );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String playerInfo = getName1() +"\n" + getName2();

        taResult.setText(playerInfo);
        //anropa twoPlayers

        controller.sendPlayers(getName1(), getName2());
    }

    public String getName1(){
        return tfPlayer1.getText();
    }
    public String getName2(){
        return tfPlayer2.getText();
    }
}
