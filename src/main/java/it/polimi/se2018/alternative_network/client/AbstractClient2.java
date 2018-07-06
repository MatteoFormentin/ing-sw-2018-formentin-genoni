package it.polimi.se2018.alternative_network.client;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.view.UIInterface;

public abstract class AbstractClient2 {

    public String ip_host;
    public int port;
    public  UIInterface view;

    //metodi del client(senza eccezioni)
    public void sendEventToUIInterface2(EventClient event){

    }

    public void setView(UIInterface view) {
        this.view = view;
    }

    public abstract void shutDownClient2();

    //metodi per interagire con il server, necessitano delle eccezioni
    public abstract void sendEventToController2(EventServer eventController);

    public abstract void connectToServer2();

}
