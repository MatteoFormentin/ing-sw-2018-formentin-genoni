package it.polimi.se2018.alternative_network.newserver.room;

import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;

/**
 * when the remote player is associated with a game the reference to the interface that allows to send messages to the gameboard is returned, to notify a disconnection.
 *
 * @author DavideMammarella
 * @author Luca Genoni
 */
public interface GameInterface {

    void sendEventToGameRoom(EventController eventController);

    void sendEventToView(EventClient eventClient);

    void reLogin(RemotePlayer2 oldRemotePlayer, RemotePlayer2 newRemotePlayer);

}
