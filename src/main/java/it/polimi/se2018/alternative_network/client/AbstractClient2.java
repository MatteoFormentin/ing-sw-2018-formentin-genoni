package it.polimi.se2018.alternative_network.client;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.view.UIInterface;

public abstract class AbstractClient2 {

    public final String ip_host;
    public final int port;
    public final UIInterface view;

    public AbstractClient2(String ip_host, int port, UIInterface view) {
        this.ip_host = ip_host;
        this.port = port;
        this.view = view;
    }

    //metodi del client(senza eccezioni)
    public abstract void sendEventToUIInterface2(EventClient event);

    public abstract void shutDownClient2();

    //metodi per interagire con il server, necessitano delle eccezioni
    public abstract void sendEventToController2(EventController eventController) throws ConnectionProblemException;

    public abstract void login2(String nickname) throws ConnectionProblemException, PlayerAlreadyLoggedException, RoomIsFullException;

    public abstract void connectToServer2() throws ConnectionProblemException;

}
