package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerExeption;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

public interface ServerController2 {

    void sendEventToGameRoom(EventController eventController);

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    void sendEventToView(EventView eventView);


}

