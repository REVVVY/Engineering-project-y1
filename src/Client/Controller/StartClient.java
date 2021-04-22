package Client.Controller;

import Client.Model.Client;

/***
 * Main klass för att starta klienten
 */
public class StartClient {
    public static void main(String[] args) {
        new ClientController(new Client("localhost", 2323));
    }
}
