package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;

public interface PrincipalServer {
    void sendEventToGameRoom(EventController eventController);

    void login(RemotePlayer2 remotePlayer)throws PlayerAlreadyLoggedException, RoomIsFullException;

}
