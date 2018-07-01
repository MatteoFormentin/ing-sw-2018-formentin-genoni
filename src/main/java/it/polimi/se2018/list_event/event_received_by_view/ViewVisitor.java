package it.polimi.se2018.list_event.event_received_by_view;


import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventViewFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_server.PlayerDisconnected;

/**
 * Visitor Pattern for the event received by the view.
 * all the method are public by default, it's an interface
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public interface ViewVisitor {
    void visit(EventViewFromController event);
    void visit(EventViewFromModel event);
    void visit(PlayerDisconnected event);
}
