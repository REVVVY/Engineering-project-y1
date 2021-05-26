package Server.Controller;

/**
 * Klassen StartServer används för att starta serverprogrammet.
 *
 * @author Isac Pettersson, Johan Skäremo
 * @version 1.0
 */
public class StartServer {

    public static void main(String[] args) {
        ServerController controller = new ServerController(2323);
    }
}