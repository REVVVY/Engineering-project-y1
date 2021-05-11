package Client.Controller;

import Client.Model.Client;
import Client.view.ClientUI;

import javax.swing.*;

/***
 * Main klass för att starta klienten
 */
public class StartClient {
    public static void main(String[] args) {

        ClientController ctr = new ClientController("localhost", 2725);
        new ClientUI(ctr);
    }
}
