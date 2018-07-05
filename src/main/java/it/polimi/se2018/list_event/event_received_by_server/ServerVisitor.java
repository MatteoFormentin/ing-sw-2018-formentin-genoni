package it.polimi.se2018.list_event.event_received_by_server;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGame;

public interface ServerVisitor {

    void visit(EventController event);

    void visit(EventPreGame event);

}
