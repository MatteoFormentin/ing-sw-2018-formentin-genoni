package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerExeption;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

/**
 * Interface based on the Abstract Factory Design Pattern.
 * This interface is used as server controller in AbstractServer.
 * Implemented by Server class in order to provide basic methods for RMI and Socket Server.
 *
 * @author DavideMammarella
 */
public interface ServerController2 {

    void sendEventToGameRoom(EventController eventController);

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    void sendEventToView(EventView eventView)throws ConnectionPlayerExeption;


}

