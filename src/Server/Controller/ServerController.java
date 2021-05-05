package Server.Controller;

import Server.Model.*;
import Server.View.ServerUI;

import javax.swing.*;
import java.util.ArrayList;


public class ServerController {
    private Server server;
    private ServerUI gui;


    private ServerLog log;

    public ServerController(int port) {
        gui = new ServerUI(this);
        gui.setUpFrame();
        server = new Server(port, this);
    }

    public void addElementInView(ServerLog log) {
        gui.getPnlWest().addElemtent(log);
    }

    public void closeConnection(){
        server.closeConnectionToDatabase();
    }

    public void setContentInView(ArrayList<String> log){
        gui.getPnlEast().setContentList(log);
    }

    public void getServerLogToWestPanel(ArrayList<ServerLog> list){
        DefaultListModel temp = new DefaultListModel();

            for(ServerLog sl: list){
                temp.addElement(sl);
            }
            gui.getPnlWest().setListModel(temp);
    }
}
