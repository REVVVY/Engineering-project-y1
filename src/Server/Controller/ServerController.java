package Server.Controller;
import Server.Model.*;
import Server.View.ServerUI;

public class ServerController {

    public ServerController(int port){
        Server server = new Server(port);
        ServerUI gui = new ServerUI(this);
    }
}
