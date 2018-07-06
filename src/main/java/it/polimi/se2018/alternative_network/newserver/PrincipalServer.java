package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.LoginResponse;

public interface PrincipalServer {

    void sendEventToGame(EventController eventController);

    LoginResponse login(RemotePlayer2 remotePlayer);

}
