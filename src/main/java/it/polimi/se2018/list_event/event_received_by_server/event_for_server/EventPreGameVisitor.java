package it.polimi.se2018.list_event.event_received_by_server.event_for_server;

import it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game.LoginRequest;

public interface EventPreGameVisitor {

    //for the init game choice
    void visit(LoginRequest event);
}
