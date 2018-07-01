package it.polimi.se2018.alternative_network.client;

import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

public interface AbstractClient2 {

    //metodi del client(senza eccezioni)
    void sendEventToUIInterface2(EventView event);
    void shutDownClient2();

    //metodi per interagire con il server, necessitano delle eccezioni
    void sendEventToController2(EventController eventController)throws ConnectionProblemException;
    void login2(String nickname) throws ConnectionProblemException, PlayerAlreadyLoggedException, RoomIsFullException;
    void connectToServer2()throws ConnectionProblemException;

}
