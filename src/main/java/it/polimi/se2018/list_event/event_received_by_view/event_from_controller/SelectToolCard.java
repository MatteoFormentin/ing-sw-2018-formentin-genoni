package it.polimi.se2018.list_event.event_received_by_view.event_from_controller;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

/**
 * Extends EventView, asks the view to select a Toolcard
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class SelectToolCard extends EventViewFromController {

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
