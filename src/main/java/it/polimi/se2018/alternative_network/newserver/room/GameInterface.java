package it.polimi.se2018.alternative_network.newserver.room;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

/**
 * when the remote player is associated with a game the reference to the interface that allows to send messages to the gameboard is returned, to notify a disconnection.
 */
public interface GameInterface {

    void disconnectFromGameRoom(int indexRoom);

    void sendEventToGameRoom(EventController eventController);

    void sendEventToView(EventView eventView);
}
