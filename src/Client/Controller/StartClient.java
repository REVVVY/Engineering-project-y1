package Client.Controller;


/***
 * Main klass för att starta klienten
 * @author Reem Mohamed
 */
public class StartClient {
    public static void main(String[] args) {
                new ClientController("localhost", 3737);
    }
}
