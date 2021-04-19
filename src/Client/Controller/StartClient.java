package Client.Controller;

import Client.Model.Client;

/***
 * Main klass för att starta klienten
 */
public class StartClient {
    public static void main(String[] args) {
       //Client client = new Client("localhost", 2323);
        new ClientController(new Client("localhost", 2323));
    }
}
