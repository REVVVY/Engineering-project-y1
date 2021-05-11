package Client.Controller;


/***
 * Main klass för att starta klienten
 */
public class StartClient {
    public static void main(String[] args) {
                new ClientController("localhost", 25755);
    }
}
