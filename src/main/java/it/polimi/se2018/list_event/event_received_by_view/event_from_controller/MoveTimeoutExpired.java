package it.polimi.se2018.list_event.event_received_by_view.event_from_controller;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

/**
 * Extends EventView, tells the view that the time for his turn is over
 *
 * @author Matteo Formentin
 */
public class MoveTimeoutExpired extends EventViewFromController {

    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
