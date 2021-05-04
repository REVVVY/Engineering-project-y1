package Server.Controller;

import Server.Model.*;
import Server.View.ServerUI;


public class ServerController {
    private Server server;
    private ServerUI gui;


    private ServerLog log;

    public ServerController(int port) {
        gui = new ServerUI(this);
        server = new Server(port, this);
    }

    public void addElementInView(ServerLog log) {
        gui.getPnlWest().addElemtent(log);
    }

    public void closeConnection(){
        server.closeConnectionToDatabase();
    }
}
