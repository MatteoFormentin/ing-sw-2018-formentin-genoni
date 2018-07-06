package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;

/**
 * @author DavideMammarella
 */
public interface PrincipalServer {

    void sendEventToGame(EventController eventController);

    void login(RemotePlayer2 remotePlayer);

}
