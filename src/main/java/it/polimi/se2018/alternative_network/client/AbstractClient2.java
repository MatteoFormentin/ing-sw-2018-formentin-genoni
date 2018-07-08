package it.polimi.se2018.alternative_network.client;

import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.view.UIInterface;

/**
 * @author DavideMammarella
 */
public abstract class AbstractClient2 {

    protected String ipHost;
    public int port;
    public UIInterface view;

    public AbstractClient2(String ipHost, int port, UIInterface view) {
        this.ipHost = ipHost;
        this.port = port;
        this.view = view;
    }

    public void sendEventToUIInterface2(EventClient event) {

    }
    public abstract boolean isDown();

    public void setView(UIInterface view) {
        this.view = view;
    }

    public abstract void shutDownClient2();

    //metodi per interagire con il server, necessitano delle eccezioni
    public abstract void sendEventToController2(EventServer eventController);

    public abstract void connectToServer2();

}
