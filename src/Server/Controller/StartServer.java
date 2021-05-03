package Server.Controller;

import Server.Model.Server;

/***
 * Main klass för att starta servern
 */
public class StartServer {
    public static void main(String[] args) {
        ServerController controller = new ServerController(2525);
    }

}