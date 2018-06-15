package it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventViewFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

/**
 * Extends EventView, tells the view to Start the turn.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class StartPlayerTurn extends EventViewFromController {

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }

}
