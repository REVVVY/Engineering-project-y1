package Client.Controller;

import Client.Model.Client;

import javax.swing.*;

/***
 * Main klass för att starta klienten
 */
public class StartClient {
    public static void main(String[] args) {
        //controller = new Client("localhost", 2323);

                new ClientController("localhost", 23233);

       // new ClientController(new Client("localhost", 2323));
    }
}
