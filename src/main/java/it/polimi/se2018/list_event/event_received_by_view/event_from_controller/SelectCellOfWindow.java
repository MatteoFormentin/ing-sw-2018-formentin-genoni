package it.polimi.se2018.list_event.event_received_by_view.event_from_controller;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

/**
 * Extends EventView, asks the view to active the window of the player
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class SelectCellOfWindow extends EventViewFromController {

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
