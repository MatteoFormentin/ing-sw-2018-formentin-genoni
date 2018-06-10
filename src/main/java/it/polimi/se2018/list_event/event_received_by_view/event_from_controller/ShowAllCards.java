package it.polimi.se2018.list_event.event_received_by_view.event_from_controller;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

/**
 * Extends EventView, tells the view to show the initial setup
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ShowAllCards extends EventViewFromController {

    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
