package Client.Controller;


/***
 * Main klass för att starta klienten
 */
public class StartClient {
    public static void main(String[] args) {
        //controller = new Client("localhost", 2323);

                new ClientController("localhost", 25755);

       // new ClientController(new Client("localhost", 2323));
    }
}
